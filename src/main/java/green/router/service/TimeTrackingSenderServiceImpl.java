package green.router.service;


import green.router.AndersenTimeTrackingBotApplication;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TimeTrackingSenderServiceImpl {


    public TimeTrackingSenderServiceImpl() {
    }


    @WebMethod
    public void sendMessage(Long chatId, String text) {
        AndersenTimeTrackingBotApplication.botS.sendMessage(chatId, text);
    }
}