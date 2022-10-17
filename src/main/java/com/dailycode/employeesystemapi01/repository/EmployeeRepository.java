package com.dailycode.employeesystemapi01.repository;

import com.dailycode.employeesystemapi01.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUserName(String username);
}
