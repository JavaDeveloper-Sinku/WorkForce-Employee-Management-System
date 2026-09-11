package com.example.Employee.Management.dto.response;

import com.example.Employee.Management.enums.EmployeeStatus;
import com.example.Employee.Management.enums.EmploymentType;
import com.example.Employee.Management.enums.RoleType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;
    private String employeeCode;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private RoleType role;

    private String department;
    private String designation;

    private LocalDate joiningDate;
    private EmploymentType employmentType;

    private Double salary;

    private EmployeeStatus status;
}