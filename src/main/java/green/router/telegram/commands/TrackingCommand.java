package green.router.telegram.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import green.router.exceptions.WrongReportException;
import green.router.telegram.commands.model.Report;

import java.io.IOException;
import java.util.*;

public class TrackingCommand extends AbstractCommand {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getGlobal();

    private static final String SUCCESS_MESSAGE = "Thank you, your tracking registered";
    private static final String WRONG_REPORT_FORMAT_ERROR = "Wrong report format";
    private static final String ALREADY_REPORTED_ERROR = "You already reported today";

    public TrackingCommand(String identifier, String description) {
        super(identifier, description);
        registerCommandFilter(identifier, Arrays.asList("user", "lead"));
    }

    void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {
        System.out.println("TrackingCommand.execute()");
        System.out.println(user);
        System.out.println(chat);
        System.out.println(Arrays.asList(strings));

        try {
            if (alreadyReported(absSender, chat.getId())) {
                return ;
            }
            saveReport(chat.getId(), strings);
            sendAnswer(absSender, chat.getId(), SUCCESS_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongReportException e) {
            sendError(absSender, chat.getId(), WRONG_REPORT_FORMAT_ERROR);
        }
    }

    private void saveReport(Long userId, String[] strings) throws IOException, WrongReportException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://localhost:8083/");

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("userId", userId.toString()));

        boolean nextTask = true;
        String hours = "";
        String taskDescription = "";
        if (strings.length < 2) {
            throw new WrongReportException();
        }
        for (String str : strings) {
            if (str.equals(";")) {
                nextTask = true;
                params.add(new BasicNameValuePair(hours, taskDescription));
                hours = "";
                taskDescription = "";
            } else if (nextTask) {
                hours = str.trim();
                nextTask = false;
            } else {
                if (taskDescription.length() != 0) {
                    taskDescription += " ";
                }
                taskDescription += str;
            }
        }
        if (hours.length() == 0 || taskDescription.length() == 0) {
            throw new WrongReportException();
        }
        params.add(new BasicNameValuePair(hours, taskDescription));

        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpclient.execute(httppost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 201) {
            System.out.println("Record is not created, status code: " + statusCode);
            throw new WrongReportException();
        }
    }

    private boolean alreadyReported(AbsSender absSender, Long chatId) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet("http://localhost:8083/");

        System.out.println("Request Type: " + httpget.getMethod());

        HttpResponse httpresponse = httpclient.execute(httpget);

        ObjectMapper mapper = new ObjectMapper();
        Report[] reports = mapper.readValue(httpresponse.getEntity().getContent(), Report[].class);

        System.out.println(Arrays.asList(reports));
        for (Report report : reports) {
            if (report.getUserId() == chatId) {
                sendError(absSender, chatId, ALREADY_REPORTED_ERROR);
                return true;
            }
        }
        return false;
    }

}
