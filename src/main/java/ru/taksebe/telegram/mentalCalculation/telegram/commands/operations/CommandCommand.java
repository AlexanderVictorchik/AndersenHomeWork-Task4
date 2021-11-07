package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CommandCommand extends OperationCommand {
    private final Logger logger = LoggerFactory.getLogger(CommandCommand.class);

    public CommandCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
//        TestInterface testInterface = new TestInterfaceImplService()
//                .getPort(TestInterface.class);
//
//        logger.info("Invocation of SOAP Server - start.");

//        System.out.println("/create called");

//        String res = testInterface.hello();
//
//        logger.info("Invocation of SOAP Server - end. Employee's last name: "
//                + res);
//        System.out.println(employee.getLastName());
    }
}
