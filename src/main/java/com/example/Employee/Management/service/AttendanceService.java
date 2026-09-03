package com.example.Employee.Management.service;

import com.example.Employee.Management.dto.request.AttendanceRequest;
import com.example.Employee.Management.dto.response.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse createAttendance(AttendanceRequest request);

    List<AttendanceResponse> getAttendanceByDate(LocalDate date);

    AttendanceResponse getEmployeeAttendance(
            Long employeeId,
            LocalDate date
    );

    List<AttendanceResponse> getEmployeeAttendanceHistory(
            Long employeeId
    );

    AttendanceResponse updateAttendance(
            Long id,
            AttendanceRequest request
    );
}