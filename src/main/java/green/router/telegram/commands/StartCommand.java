package green.router.telegram.commands;

import green.router.Utils;
import green.router.commandservice.CommandService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends AbstractCommand {

    private final String DEFAULT_ROLE = "student";

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {
        green.router.commandservice.User newUser = new green.router.commandservice.User();
        newUser.setId(chat.getId().intValue());
        newUser.setUsername(Utils.getUserName(user));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setRole(DEFAULT_ROLE);
        newUser.setGroup(null);
        CommandService.getService().save(newUser);
        sendAnswer(absSender, chat.getId(), "Lets start, type /help for help");
    }
}
