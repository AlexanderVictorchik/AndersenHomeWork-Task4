package com.mishenev.soappetservice;

import com.mishenev.soappetservice.service.EmployeeServiceImpl;
import java.util.logging.Logger;
import javax.xml.ws.Endpoint;

/**
 * SoapPetServiceApplication.
 *
 * @author Dmitrii_Mishenev
 */
public class SoapPetServiceServer {

    private static final Logger LOGGER = Logger.getGlobal();

    public static void main(String[] args) {

        LOGGER.info("Creating WEB server and publishing SOAP endpoint");
        Endpoint.publish("http://localhost:8080/employeeservice",
                new EmployeeServiceImpl());
    }
}