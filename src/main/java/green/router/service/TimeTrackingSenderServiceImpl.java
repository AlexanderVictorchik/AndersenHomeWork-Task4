package green.router.service;


import green.router.MentalCalculationApplication;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TimeTrackingSenderServiceImpl {


    public TimeTrackingSenderServiceImpl() {
    }


    @WebMethod
    public void sendMessage(Long chatId, String text) {
        MentalCalculationApplication.botS.sendMessage(chatId, text);
    }
}