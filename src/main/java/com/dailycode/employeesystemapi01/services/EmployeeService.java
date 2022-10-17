package com.dailycode.employeesystemapi01.services;

import com.dailycode.employeesystemapi01.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Long createEmployee(Employee employee);

    Optional<Employee> findByUserName(String username);

    List<Employee> getAllEmployees();

    boolean deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);
}
