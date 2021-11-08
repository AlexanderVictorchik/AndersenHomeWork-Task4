package ru.taksebe.telegram.mentalCalculation.telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommand extends BotCommand {

    AbstractCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Long chatId, String answer) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), answer));
        } catch (RuntimeException e) {
            sendError(absSender, chatId);
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendError(AbsSender absSender, Long chatId) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Some error occured"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}