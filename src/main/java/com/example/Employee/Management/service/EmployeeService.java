package com.example.Employee.Management.service;

import com.example.Employee.Management.dto.request.EmployeeRequest;
import com.example.Employee.Management.dto.response.EmployeeResponse;
import com.example.Employee.Management.enums.EmployeeStatus;
import org.springframework.data.domain.Page;


import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<EmployeeResponse> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<EmployeeResponse> filterEmployees(
            String department,
            Double minSalary,
            Double maxSalary,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);


    EmployeeResponse updateEmployeeStatus(
            Long id,
            EmployeeStatus status
    );
}