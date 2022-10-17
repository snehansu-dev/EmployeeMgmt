package com.dailycode.employeesystemapi01.services;

import com.dailycode.employeesystemapi01.model.Employee;
import com.dailycode.employeesystemapi01.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.PipedWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Long createEmployee(Employee employee) {
        // Encode Password
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Optional<Employee> findByUserName(String username) {
        return employeeRepository.findByUserName(username);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();;
        return employeeList;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Employee employeeEntity = employeeRepository.findById(id).get();
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeEntity, employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee employeeEntity = employeeRepository.findById(id).get();
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setEmailId(employee.getEmailId());
        employeeRepository.save(employeeEntity);
        return employee;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> emp = findByUserName(username); // username is in-parm from login form/rest
        if(emp.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Employee employee = emp.get();
        return new User(username,
                employee.getPassword(),
                employee.getRoles().stream()
                        .map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList()));

    }
}
