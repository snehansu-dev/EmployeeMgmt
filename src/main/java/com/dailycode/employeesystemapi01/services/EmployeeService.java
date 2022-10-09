package com.dailycode.employeesystemapi01.services;

import com.dailycode.employeesystemapi01.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployees();

    boolean deleteEmployee(long id);
}
