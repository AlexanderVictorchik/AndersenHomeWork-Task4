package green.router.telegram.commands;

import green.router.commandservice.CommandService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.List;

public class ListCommand extends AbstractCommand {
    public ListCommand(String identifier, String description) {
        super(identifier, description);
        registerCommandFilter(identifier, Arrays.asList("mentor"));
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {
        List<String> allUsers = CommandService.getAllUsers();

        String list = "user_id | username | first_name | last_name | user_role | user_group\n";
        for(String item : allUsers) {
            list += item + "\n";
        }

        sendAnswer(absSender, chat.getId(), list);
    }
}
