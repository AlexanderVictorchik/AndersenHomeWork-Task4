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
                        "admin commands:\n" +
                        "/list to get a list of users\n" +
                        "/group to set a group to user\n" +
                        "/role to set a role to user\n" +
                        "user commands:\n" +
                        "/tracking [hours1] [description1] [;] [hours2] [description2] - send a tracking\n" +
                        "/help - for this help\n\n");
    }
}