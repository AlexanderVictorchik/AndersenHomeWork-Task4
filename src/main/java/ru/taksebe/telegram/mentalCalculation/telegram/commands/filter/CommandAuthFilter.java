package ru.taksebe.telegram.mentalCalculation.telegram.commands.filter;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.taksebe.telegram.mentalCalculation.commandservice.UserService;
import ru.taksebe.telegram.mentalCalculation.commandservice.UserServiceImplementationService;

import java.util.HashMap;
import java.util.List;

public class CommandAuthFilter {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getGlobal();

    private static HashMap<String, List<String>> commandAccess = new HashMap<>();

    public static void register(String identifier, List<String> roles) {
        commandAccess.put(identifier, roles);
    }

    public static boolean authFilter(AbsSender absSender, Long chatId, String commandName, String userName) {
        LOGGER.info("TrackingCommand.authFilter()");
        UserService userServiceImplementation = new UserServiceImplementationService()
                .getPort(UserService.class);

        ru.taksebe.telegram.mentalCalculation.commandservice.User dbUser = userServiceImplementation.findById(chatId.toString());
        System.out.println("user");
        System.out.println(dbUser.getUsername());
        if (!commandAccess.get(commandName).contains(dbUser.getRole())) {
            sendUnauthorizedError(absSender, chatId, commandName, userName);
            return false;
        }
        return true;
    }

    /**
     * Отправка пользователю сообщения об ошибке
     */
    private static void sendUnauthorizedError(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Sorry, you cant use this command"));
        } catch (TelegramApiException e) {
            LOGGER.info(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }
}
