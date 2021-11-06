package ru.taksebe.telegram.mentalCalculation;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.taksebe.telegram.mentalCalculation.telegram.Bot;

import java.util.Map;

public class MentalCalculationApplication {
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("andersentimetrackingbot", "2128890774:AAEzTh0oxx7JlUmyJeRhyzkHhgfNKx1R4OM"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}