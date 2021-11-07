package ru.taksebe.telegram.mentalCalculation.soapservices;


import ru.taksebe.telegram.mentalCalculation.telegram.Bot;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TimeTrackingSenderService {
    Bot bot;

    public TimeTrackingSenderService() {
    }

    @WebMethod
    public void sendTacking(Long chatId, String userName, String text) {

    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
