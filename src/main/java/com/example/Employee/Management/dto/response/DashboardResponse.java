package com.example.Employee.Management.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private long totalEmployees;

    private long totalDepartments;

    private double attendancePercentage;

    private double monthlyPayroll;

    private long present;

    private long absent;

    private long onLeave;

    private long late;
}