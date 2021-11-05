package com.mishenev.soappetservice;

import java.util.logging.Logger;

/**
 * SOAP protocol based client.
 * Response classes and WEB services generated from WSDL schema.
 *
 * @author Dmitrii_Mishenev
 */
public class SoapPetServiceClient {

    private static final Logger LOGGER = Logger.getGlobal();

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeServiceImplService()
                        .getPort(EmployeeService.class);

        LOGGER.info("Invocation of SOAP Server - start.");

        Employee employee = employeeService.getEmployee("Ivan");

        LOGGER.info("Invocation of SOAP Server - end. Employee's last name: "
                + employee.getLastName());
    }
}