package com.example.Employee.Management.controller;

import com.example.Employee.Management.dto.response.ApiResponse;
import com.example.Employee.Management.dto.response.DashboardResponse;
import com.example.Employee.Management.dto.response.EmployeeDashboardResponse;
import com.example.Employee.Management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    // =========================================================
    // ADMIN / HR DASHBOARD
    // =========================================================

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboardStats() {

        DashboardResponse response =
                dashboardService.getDashboardStats();

        return ResponseEntity.ok(
                ApiResponse.<DashboardResponse>builder()
                        .success(true)
                        .message("Dashboard stats fetched successfully")
                        .data(response)
                        .build()
        );
    }


    // =========================================================
    // EMPLOYEE DASHBOARD
    // =========================================================

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<EmployeeDashboardResponse>> getEmployeeDashboard(
            @PathVariable Long employeeId
    ) {

        EmployeeDashboardResponse response =
                dashboardService.getEmployeeDashboard(employeeId);

        return ResponseEntity.ok(
                ApiResponse.<EmployeeDashboardResponse>builder()
                        .success(true)
                        .message("Employee dashboard fetched successfully")
                        .data(response)
                        .build()
        );
    }
}