package green.router.telegram.commands;

import green.router.commandservice.CommandService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.List;

public class RoleCommand extends AbstractCommand {
    private static final String WRONG_ROLE_FORMAT_ERROR = "Please specify user and role: /role [user_id] [role]\n" +
            "To see list of users use: /list";

    private static final String WRONG_ROLE_ERROR = "Wrong role, list of accepted roles is: mentor, lead, student\n";

    private static final String SUCCESS_MESSAGE = "Role is successfully changed";

    private static final List<String> POSSIBLE_ROLES = Arrays.asList("mentor", "lead", "student");


    public RoleCommand(String identifier, String description) {
        super(identifier, description);
        registerCommandFilter(identifier, Arrays.asList("mentor"));
    }

    @Override
    public void executeCommand(AbsSender absSender, User user, Chat chat, String[] strings) {

        if (strings.length != 2) {
            sendError(absSender, chat.getId(), WRONG_ROLE_FORMAT_ERROR);
            return ;
        }

        String userId = strings[0];
        green.router.commandservice.User updatedUser = CommandService.getService().findById(userId);

        String role = strings[1];

        if (!POSSIBLE_ROLES.contains(role)) {
            sendError(absSender, chat.getId(), WRONG_ROLE_ERROR);
            return ;
        }

        if (role.equals("lead")) {
            List<green.router.commandservice.User> users = CommandService.getService().findByGroup(updatedUser.getGroup());
            for (green.router.commandservice.User groupUser : users) {
                if (groupUser.getRole().equals("lead")) {
                    groupUser.setRole("student");
                    CommandService.getService().update(groupUser);
                    break ;
                }
            }
        }

        updatedUser.setRole(role);

        CommandService.getService().update(updatedUser);

        sendAnswer(absSender, chat.getId(), SUCCESS_MESSAGE);
    }
}
