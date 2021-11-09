package green.router.telegram.commands;

import green.router.commandservice.CommandService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.List;

public class GroupCommand extends AbstractCommand {
    private static final String WRONG_SET_GROUP_FORMAT_ERROR = "Please set user and group: /group [user_id] [group]\n" +
            "To see list of users use: /list";

    private static final String SUCCESS_MESSAGE = "Group is successfully changed";


    public GroupCommand(String identifier, String description) {
        super(identifier, description);
        registerCommandFilter(identifier, Arrays.asList("admin"));
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {

        if (strings.length != 2) {
            sendError(absSender, chat.getId(), WRONG_SET_GROUP_FORMAT_ERROR);
            return ;
        }

        String userId = strings[0];
        green.router.commandservice.User updatedUser = CommandService.getService().findById(userId);

        String group = strings[1];

        if (updatedUser.getRole().equals("lead")) {
            List<green.router.commandservice.User> users = CommandService.getService().findByGroup(group);
            for (green.router.commandservice.User groupUser : users) {
                if (groupUser.getRole().equals("lead")) {
                    updatedUser.setRole("user");
                }
            }
        }

        updatedUser.setGroup(group);

        CommandService.getService().update(updatedUser);

        sendAnswer(absSender, chat.getId(), SUCCESS_MESSAGE);
    }
}
