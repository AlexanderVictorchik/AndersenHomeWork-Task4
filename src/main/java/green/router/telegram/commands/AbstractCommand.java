package green.router.telegram.commands;

import green.router.telegram.commands.filter.CommandAuthFilter;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

abstract class AbstractCommand extends BotCommand {

    private static String DEFAULT_MESSAGE = "Some error occured";

    AbstractCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!CommandAuthFilter.authFilter(absSender, chat.getId(), this.getCommandIdentifier())) {
            return ;
        }
        executeCommand(absSender, user, chat, strings);
    }

    abstract void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings);

    void registerCommandFilter(String identifier, List<String> roles) {
        CommandAuthFilter.register(identifier, roles);
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