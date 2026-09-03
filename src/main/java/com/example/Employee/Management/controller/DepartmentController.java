package com.example.Employee.Management.controller;

import com.example.Employee.Management.dto.request.DepartmentRequest;
import com.example.Employee.Management.dto.response.ApiResponse;
import com.example.Employee.Management.dto.response.DepartmentResponse;
import com.example.Employee.Management.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // Create Department
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody DepartmentRequest request
    ) {

        DepartmentResponse department =
                departmentService.createDepartment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<DepartmentResponse>builder()
                                .success(true)
                                .message("Department created successfully")
                                .data(department)
                                .build()
                );
    }

    // Get All Departments
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {

        List<DepartmentResponse> departments =
                departmentService.getAllDepartments();

        return ResponseEntity.ok(
                ApiResponse.<List<DepartmentResponse>>builder()
                        .success(true)
                        .message("Departments fetched successfully")
                        .data(departments)
                        .build()
        );
    }

    // Get Department By ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
            @PathVariable Long id
    ) {

        DepartmentResponse department =
                departmentService.getDepartmentById(id);

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department fetched successfully")
                        .data(department)
                        .build()
        );
    }

    // Update Department
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request
    ) {

        DepartmentResponse department =
                departmentService.updateDepartment(id, request);

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department updated successfully")
                        .data(department)
                        .build()
        );
    }

    // Change Active / Inactive
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> changeStatus(
            @PathVariable Long id
    ) {

        DepartmentResponse department =
                departmentService.changeStatus(id);

        return ResponseEntity.ok(
                ApiResponse.<DepartmentResponse>builder()
                        .success(true)
                        .message("Department status updated successfully")
                        .data(department)
                        .build()
        );
    }

    // Delete Department
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(
            @PathVariable Long id
    ) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Department deleted successfully")
                        .data(null)
                        .build()
        );
    }
}