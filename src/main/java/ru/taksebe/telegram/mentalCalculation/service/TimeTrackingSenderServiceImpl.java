package ru.taksebe.telegram.mentalCalculation.service;


import ru.taksebe.telegram.mentalCalculation.MentalCalculationApplication;
import ru.taksebe.telegram.mentalCalculation.telegram.Bot;
import ru.taksebe.telegram.mentalCalculation.telegram.commands.operations.CreateTeamCommand;

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