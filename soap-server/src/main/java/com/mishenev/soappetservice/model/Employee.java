package com.mishenev.soappetservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Employee.
 *
 * @author Dmitrii_Mishenev
 */
@XmlRootElement
public class Employee {
    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private int age;

    @XmlElement
    private String position;

    public Employee(String firstName, String lastName, int age, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.position = position;
    }

    public Employee() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public String getPosition() {
        return this.position;
    }
}