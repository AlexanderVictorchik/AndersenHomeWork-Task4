package ru.taksebe.telegram.mentalCalculation;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.taksebe.telegram.mentalCalculation.service.TimeTrackingSenderServiceImpl;
import ru.taksebe.telegram.mentalCalculation.telegram.Bot;

import javax.xml.ws.Endpoint;

import java.util.Map;

public class MentalCalculationApplication {
    public static Bot botS;
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            String botname = "tasktrackingbot";
            String botToken = "2033620622:AAG3hdT89gBiwmwte4x3P4eJsYQnHf5MSt4";
            Bot bot = new Bot(botname, botToken);

            botsApi.registerBot(bot);

            TimeTrackingSenderServiceImpl timeTrackingSenderService = new TimeTrackingSenderServiceImpl();


            Endpoint.publish("http://localhost:8087/sendservice",
                    new TimeTrackingSenderServiceImpl());

            botS = bot;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}