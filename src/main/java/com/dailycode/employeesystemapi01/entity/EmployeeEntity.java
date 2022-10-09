package com.dailycode.employeesystemapi01.entity;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
}
