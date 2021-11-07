package ru.taksebe.telegram.mentalCalculation;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.taksebe.telegram.mentalCalculation.service.TimeTrackingSenderServiceImpl;
import ru.taksebe.telegram.mentalCalculation.telegram.Bot;

import javax.xml.ws.Endpoint;

import java.util.Map;

public class MentalCalculationApplication {
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            String botname = "trackingsender_bot";
            String botToken = "2146386769:AAFnpu88nlw9TpWfSv_VR9g4HDPkkyTLgn4";
            Bot bot = new Bot(botname, botToken);

            botsApi.registerBot(bot);

            TimeTrackingSenderServiceImpl timeTrackingSenderService = new TimeTrackingSenderServiceImpl();
            timeTrackingSenderService.setBot(bot);

            Endpoint.publish("http://localhost:8087/sendservice",
                    new TimeTrackingSenderServiceImpl());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}