package com.dailycode.employeesystemapi01.controller;

import com.dailycode.employeesystemapi01.Utils.JwtUtil;
import com.dailycode.employeesystemapi01.model.Employee;
import com.dailycode.employeesystemapi01.model.UserRequest;
import com.dailycode.employeesystemapi01.model.UserResponse;
import com.dailycode.employeesystemapi01.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil util;

    @PostMapping("/register")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        Long id = employeeService.createEmployee(employee);
        String body = "User " + id + " Saved";
        return ResponseEntity.ok(body);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> logIn(@RequestBody UserRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        String token = util.generateToken(request.getUserName());
        return ResponseEntity.ok(new UserResponse(token,"Success! Token Generated"));
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable long id){
        boolean deleted = false;
        deleted = employeeService.deleteEmployee(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        employee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(employee);
    }

}
