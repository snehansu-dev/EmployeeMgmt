package com.dailycode.employeesystemapi01.services;

import com.dailycode.employeesystemapi01.entity.EmployeeEntity;
import com.dailycode.employeesystemapi01.model.Employee;
import com.dailycode.employeesystemapi01.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeRepository.save(employeeEntity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees= employeeEntities.stream().
                map(emp-> new Employee(emp.getId(),emp.getFirstName(),emp.getLastName(),emp.getEmailId())).
                collect(Collectors.toList());
        return employees;
    }

    @Override
    public boolean deleteEmployee(long id) {
        EmployeeEntity employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }

}
