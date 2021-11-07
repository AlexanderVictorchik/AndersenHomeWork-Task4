package ru.taksebe.telegram.mentalCalculation.service;


import ru.taksebe.telegram.mentalCalculation.telegram.Bot;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TimeTrackingSenderServiceImpl implements TimeTrackingSenderService {
    Bot bot;

    public TimeTrackingSenderServiceImpl() {
    }


    public void setBot(Bot bot) {
        this.bot = bot;
    }

    @WebMethod
    @Override
    public void sendMessage(Long chatId, String userName, String text) {
        bot.sendMessage(chatId, userName, text);
    }
}