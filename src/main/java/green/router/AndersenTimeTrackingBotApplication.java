package green.router;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import green.router.service.TimeTrackingSenderServiceImpl;
import green.router.telegram.Bot;

import javax.xml.ws.Endpoint;

import java.util.Map;

public class AndersenTimeTrackingBotApplication {
    public static Bot botS;
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            String botname = "andersenTimeTrackerTestBot";
            String botToken = "2108893485:AAGfO6IiAyQZ8ntF2fNqlzC-CEldQDGL3YI";
            Bot bot = new Bot(botname, botToken);

            botsApi.registerBot(bot);

            Endpoint.publish("http://0.0.0.0:8087/sendservice",
                    new TimeTrackingSenderServiceImpl());

            botS = bot;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}