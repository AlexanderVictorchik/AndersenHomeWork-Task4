package green.router.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, chat.getId(),
                        "❗*Commands list*\n" +
                        "/start to start using bot\n" +
                        "/list to get list of users\n" +
                        "/group to set group to user\n" +
                        "/tracking [hours1] [description1] [;] [hours2] [description2] - send a tracking\n" +
                        "/help - помощь\n\n");
    }
}