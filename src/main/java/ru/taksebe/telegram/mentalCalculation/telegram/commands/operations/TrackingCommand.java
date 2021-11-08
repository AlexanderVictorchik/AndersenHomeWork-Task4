package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.taksebe.telegram.mentalCalculation.Utils;
import ru.taksebe.telegram.mentalCalculation.enums.OperationEnum;
import ru.taksebe.telegram.mentalCalculation.exceptions.WrongReportException;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.filter.CommandAuthFilter;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.operations.model.Report;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.operations.model.Task;
import ru.taksebe.telegram.mentalCalculation.commandservice.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class TrackingCommand extends OperationCommand {
    private static final ArrayList<String> ROLES = new ArrayList<>(Arrays.asList("admin"));
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getGlobal();

    private Logger logger = LoggerFactory.getLogger(CreateTeamCommand.class);

    public TrackingCommand(String identifier, String description) {
        super(identifier, description);
        CommandAuthFilter.register(identifier, Arrays.asList("user"));
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        System.out.println("TrackingCommand.execute()");
        System.out.println(user);
        System.out.println(chat);
        System.out.println(Arrays.asList(strings));

        String userName = Utils.getUserName(user);

        if (!CommandAuthFilter.authFilter(absSender, chat.getId(), this.getCommandIdentifier(), userName)) {
            return ;
        }

        try {
            saveReport(chat.getId(), strings);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName);
            getReport();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongReportException e) {
            sendError(absSender, chat.getId(), this.getCommandIdentifier(), userName);
        }
    }

    private void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Thank you, your tracking registered"));
        } catch (RuntimeException e) {
            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            sendError(absSender, chatId, commandName, userName);
            e.printStackTrace();
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }

    /**
     * Отправка пользователю сообщения об ошибке
     */
    private void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Wrong format for command /tracking.\n" +
                    "Please, enter time spended and desctiption in following format:" +
                    "\n'/tracking [time] [task1 description] ; [time] [task2] ...'\n" +
                    "for example:" +
                    "\n'/tracking 1.5 learned acid ; 2 installed tomcat'(time in hours, no quotes)")
            );
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
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
        if (response.getStatusLine().getStatusCode() != 201) {
            System.out.println("Record is not created");
            throw new WrongReportException();
        }

//        HttpEntity entity = response.getEntity();
//
//        if (entity != null) {
//            try (InputStream responseContent = entity.getContent()) {
//                byte[] bytes = new byte[100];
//                int bytesRead = responseContent.read(bytes);
//                System.out.println(new String(bytes, 0 ,bytesRead));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void getReport() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet("http://localhost:8083/");

        System.out.println("Request Type: " + httpget.getMethod());

        HttpResponse httpresponse = httpclient.execute(httpget);

        ObjectMapper mapper = new ObjectMapper();
        Report[] reports = mapper.readValue(httpresponse.getEntity().getContent(), Report[].class);

        System.out.println(Arrays.asList(reports));
//        for (Report report : reports) {
//            System.out.println("report");
//            System.out.println(report);
//            for (Task task : report.getTasks()) {
//                System.out.println("task");
//                System.out.println(task);
//            }
//        }
    }

}
