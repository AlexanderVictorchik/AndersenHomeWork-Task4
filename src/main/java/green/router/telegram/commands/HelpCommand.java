package green.router.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import green.router.Utils;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(),
                        "❗*Commands list*\n" +
                        "/tracking [hours1] [description1] [;] [hours2] [description2] - send a tracking\n" +
                        "/help - помощь\n\n");
    }
}