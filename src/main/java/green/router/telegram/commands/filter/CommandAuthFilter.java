package green.router.telegram.commands.filter;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import green.router.commandservice.UserService;
import green.router.commandservice.UserServiceImplementationService;

import java.util.HashMap;
import java.util.List;

public class CommandAuthFilter {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getGlobal();

    private static HashMap<String, List<String>> commandsAndRoles = new HashMap<>();

    public static void register(String identifier, List<String> roles) {
        commandsAndRoles.put(identifier, roles);
    }

    public static boolean authFilter(AbsSender absSender, Long chatId, String commandName) {
        LOGGER.info("TrackingCommand.authFilter()");

        if (!commandsAndRoles.containsKey(commandName)) {
            return true;
        }

        UserService userServiceImplementation = new UserServiceImplementationService()
                .getPort(UserService.class);

        green.router.commandservice.User dbUser = userServiceImplementation.findById(chatId.toString());
        System.out.println("user");
        System.out.println(dbUser.getUsername());
        if (!commandsAndRoles.get(commandName).contains(dbUser.getRole())) {
            sendUnauthorizedError(absSender, chatId);
            return false;
        }
        return true;
    }

    private static void sendUnauthorizedError(AbsSender absSender, Long chatId) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Sorry, you cant use this command"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
