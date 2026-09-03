package com.example.Employee.Management.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDashboardResponse {

    private Long employeeId;

    private String employeeName;

    private String email;

    private String department;

    private String role;

    // Today's attendance
    private String todayStatus;
    private String checkIn;
    private String checkOut;
    private String workingHours;

    // Monthly attendance
    private long presentDays;
    private long absentDays;
    private long leaveDays;
    private long lateDays;
}