package com.example.Employee.Management.controller;

import com.example.Employee.Management.dto.request.EmployeeRequest;
import com.example.Employee.Management.dto.response.ApiResponse;
import com.example.Employee.Management.dto.response.EmployeeResponse;
import com.example.Employee.Management.enums.EmployeeStatus;
import com.example.Employee.Management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Create Employee
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid
            @RequestBody EmployeeRequest request
    ) {

        EmployeeResponse employee = employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<EmployeeResponse>builder()
                                .success(true)
                                .message("Employee created successfully")
                                .data(employee)
                                .build()
                );
    }

    // Get All Employees
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> getAllEmployees(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir
    ) {

        Page<EmployeeResponse> employees =
                employeeService.getAllEmployees(
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(
                ApiResponse.<Page<EmployeeResponse>>builder()
                        .success(true)
                        .message("Employees fetched successfully")
                        .data(employees)
                        .build()
        );
    }

    // Search Employees
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> searchEmployees(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir
    ) {

        Page<EmployeeResponse> employees =
                employeeService.searchEmployees(
                        keyword,
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(
                ApiResponse.<Page<EmployeeResponse>>builder()
                        .success(true)
                        .message("Employees fetched successfully")
                        .data(employees)
                        .build()
        );
    }

    // Filter Employees
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> filterEmployees(

            @RequestParam(required = false) String department, @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Page<EmployeeResponse> employees = employeeService.
                filterEmployees(
                        department,
                        minSalary,
                        maxSalary,
                        page,
                        size,
                        sortBy,
                        sortDir
                );

        return ResponseEntity.ok(
                ApiResponse.<Page<EmployeeResponse>>builder()
                        .success(true)
                        .message("Employees fetched successfully")
                        .data(employees)
                        .build()
        );
    }

    // Get Employee By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id
    ) {

        EmployeeResponse employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee fetched successfully")
                        .data(employee)
                        .build()
        );
    }

    // Update Employee
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(

            @PathVariable Long id,
            @Valid
            @RequestBody EmployeeRequest request
    ) {

        EmployeeResponse employee = employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee updated successfully")
                        .data(employee)
                        .build()
        );
    }

    // Delete Employee
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(
            @PathVariable Long id
    ) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Employee deleted successfully")
                        .data(null)
                        .build()
        );
    }



    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployeeStatus(
            @PathVariable Long id,
            @RequestParam EmployeeStatus status
    ) {
        EmployeeResponse employee =
                employeeService.updateEmployeeStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeResponse>builder()
                        .success(true)
                        .message("Employee status updated successfully")
                        .data(employee)
                        .build()
        );
    }
}


