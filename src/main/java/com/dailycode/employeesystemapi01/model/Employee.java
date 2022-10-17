package com.dailycode.employeesystemapi01.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String userName;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="Roles",
                     joinColumns = @JoinColumn(name = "id"))
    @Column(name="role")
    private Set<String> roles;

}
