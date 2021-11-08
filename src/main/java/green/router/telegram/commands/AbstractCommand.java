package green.router.telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommand extends BotCommand {

    private static String DEFAULT_MESSAGE = "Some error occured";

    AbstractCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Long chatId, String answer) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), answer));
        } catch (RuntimeException e) {
            sendError(absSender, chatId, DEFAULT_MESSAGE);
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendError(AbsSender absSender, Long chatId, String message) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}