package com.mishenev.soappetservice.service;

import com.mishenev.soappetservice.model.Employee;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * {@link EmployeeService} implementation.
 *
 * @author Dmitrii_Mishenev
 */
@WebService(endpointInterface = "com.mishenev.soappetservice.service.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    @WebMethod
    public Employee getEmployee(String name) {
        return new Employee(name,
                "Ivanov",
                18,
                "Senior Java developer");
    }
}