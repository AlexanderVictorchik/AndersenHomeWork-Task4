package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CreateTeamCommand extends OperationCommand {
    private Logger logger = LoggerFactory.getLogger(CreateTeamCommand.class);

    public CreateTeamCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        EmployeeService employeeService = new EmployeeServiceImplService()
                .getPort(EmployeeService.class);

        logger.info("Invocation of SOAP Server - start.");

//        System.out.println("/create called");

        Employee employee = employeeService.getEmployee("Ivan");

        logger.info("Invocation of SOAP Server - end. Employee's last name: "
                + employee.getLastName());
//        System.out.println(employee.getLastName());
    }
}
