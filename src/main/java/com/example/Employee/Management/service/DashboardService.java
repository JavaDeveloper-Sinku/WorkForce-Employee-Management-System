package com.example.Employee.Management.service;

import com.example.Employee.Management.dto.response.DashboardResponse;
import com.example.Employee.Management.dto.response.EmployeeDashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboardStats();

    EmployeeDashboardResponse getEmployeeDashboard(Long employeeId);
}