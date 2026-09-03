package com.example.Employee.Management.service.impl;

import com.example.Employee.Management.dto.response.DashboardResponse;
import com.example.Employee.Management.dto.response.EmployeeDashboardResponse;
import com.example.Employee.Management.entity.Attendance;
import com.example.Employee.Management.entity.Employee;
import com.example.Employee.Management.enums.AttendanceStatus;
import com.example.Employee.Management.repository.AttendanceRepository;
import com.example.Employee.Management.repository.DepartmentRepository;
import com.example.Employee.Management.repository.EmployeeRepository;
import com.example.Employee.Management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;

    // =========================================================
    // ADMIN / HR DASHBOARD
    // =========================================================

    @Override
    public DashboardResponse getDashboardStats() {

        LocalDate today = LocalDate.now();

        // Total employees
        long totalEmployees = employeeRepository.count();

        // Total departments
        long totalDepartments = departmentRepository.count();

        // Today's attendance
        long present =
                attendanceRepository.countByDateAndStatus(
                        today,
                        AttendanceStatus.PRESENT
                );

        long absent =
                attendanceRepository.countByDateAndStatus(
                        today,
                        AttendanceStatus.ABSENT
                );

        long onLeave =
                attendanceRepository.countByDateAndStatus(
                        today,
                        AttendanceStatus.ON_LEAVE
                );

        long late =
                attendanceRepository.countByDateAndStatus(
                        today,
                        AttendanceStatus.LATE
                );

        // Attendance percentage
        double attendancePercentage = 0;

        if (totalEmployees > 0) {
            attendancePercentage =
                    ((double) (present + late)
                            / totalEmployees) * 100;
        }

        return DashboardResponse.builder()
                .totalEmployees(totalEmployees)
                .totalDepartments(totalDepartments)
                .attendancePercentage(
                        Math.round(attendancePercentage * 100.0) / 100.0
                )
                .monthlyPayroll(0)
                .present(present)
                .absent(absent)
                .onLeave(onLeave)
                .late(late)
                .build();
    }


    // =========================================================
    // EMPLOYEE DASHBOARD
    // =========================================================

    @Override
    public EmployeeDashboardResponse getEmployeeDashboard(
            Long employeeId
    ) {

        // -----------------------------------------------------
        // Find Employee
        // -----------------------------------------------------

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with id: " + employeeId
                        )
                );

        LocalDate today = LocalDate.now();

        // -----------------------------------------------------
        // Employee Basic Information
        // -----------------------------------------------------

        String employeeName =
                employee.getFirstName() + " " + employee.getLastName();

        String role = "EMPLOYEE";

        if (employee.getUser() != null
                && employee.getUser().getRole() != null
                && employee.getUser().getRole().getName() != null) {

            role = employee.getUser()
                    .getRole()
                    .getName()
                    .name();
        }

        // -----------------------------------------------------
        // Today's Attendance
        // -----------------------------------------------------

        Attendance todayAttendance =
                attendanceRepository
                        .findByEmployeeIdAndDate(employeeId, today)
                        .orElse(null);

        String todayStatus = null;
        String checkIn = null;
        String checkOut = null;
        String workingHours = "0h";

        if (todayAttendance != null) {

            todayStatus =
                    todayAttendance.getStatus().name();

            if (todayAttendance.getCheckIn() != null) {

                checkIn =
                        todayAttendance.getCheckIn().toString();
            }

            if (todayAttendance.getCheckOut() != null) {

                checkOut =
                        todayAttendance.getCheckOut().toString();
            }

            workingHours =
                    calculateWorkingHours(
                            todayAttendance.getCheckIn(),
                            todayAttendance.getCheckOut()
                    );
        }

        // -----------------------------------------------------
        // Attendance History
        // -----------------------------------------------------

        List<Attendance> attendanceHistory =
                attendanceRepository
                        .findByEmployeeIdOrderByDateDesc(employeeId);

        // -----------------------------------------------------
        // Current Month
        // -----------------------------------------------------

        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        long presentDays = 0;
        long absentDays = 0;
        long leaveDays = 0;
        long lateDays = 0;

        for (Attendance attendance : attendanceHistory) {

            LocalDate attendanceDate =
                    attendance.getDate();

            if (attendanceDate.getMonthValue() != currentMonth
                    || attendanceDate.getYear() != currentYear) {

                continue;
            }

            AttendanceStatus status =
                    attendance.getStatus();

            if (status == AttendanceStatus.PRESENT) {

                presentDays++;

            } else if (status == AttendanceStatus.ABSENT) {

                absentDays++;

            } else if (status == AttendanceStatus.ON_LEAVE) {

                leaveDays++;

            } else if (status == AttendanceStatus.LATE) {

                lateDays++;
            }
        }

        // -----------------------------------------------------
        // Build Response
        // -----------------------------------------------------

        return EmployeeDashboardResponse.builder()
                .employeeId(employee.getId())
                .employeeName(employeeName)
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .role(role)
                .todayStatus(todayStatus)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .workingHours(workingHours)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .leaveDays(leaveDays)
                .lateDays(lateDays)
                .build();
    }


    // =========================================================
    // Calculate Working Hours
    // =========================================================

    private String calculateWorkingHours(
            LocalTime checkIn,
            LocalTime checkOut
    ) {

        if (checkIn == null || checkOut == null) {
            return "0h";
        }

        long minutes =
                Duration.between(checkIn, checkOut)
                        .toMinutes();

        if (minutes <= 0) {
            return "0h";
        }

        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;

        if (remainingMinutes == 0) {
            return hours + "h";
        }

        return hours + "h "
                + remainingMinutes + "m";
    }
}