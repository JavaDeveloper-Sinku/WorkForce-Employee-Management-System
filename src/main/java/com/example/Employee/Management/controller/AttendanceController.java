package com.example.Employee.Management.controller;

import com.example.Employee.Management.dto.request.AttendanceRequest;
import com.example.Employee.Management.dto.response.ApiResponse;
import com.example.Employee.Management.dto.response.AttendanceResponse;
import com.example.Employee.Management.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Create Attendance
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> createAttendance(
            @Valid @RequestBody AttendanceRequest request
    ) {

        AttendanceResponse attendance =
                attendanceService.createAttendance(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<AttendanceResponse>builder()
                                .success(true)
                                .message("Attendance created successfully")
                                .data(attendance)
                                .build()
                );
    }

    // Get Attendance By Date
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByDate(
            @RequestParam LocalDate date
    ) {

        List<AttendanceResponse> attendance =
                attendanceService.getAttendanceByDate(date);

        return ResponseEntity.ok(
                ApiResponse.<List<AttendanceResponse>>builder()
                        .success(true)
                        .message("Attendance fetched successfully")
                        .data(attendance)
                        .build()
        );
    }

    // Get Employee Attendance
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> getEmployeeAttendance(
            @PathVariable Long employeeId,
            @RequestParam LocalDate date
    ) {

        AttendanceResponse attendance =
                attendanceService.getEmployeeAttendance(
                        employeeId,
                        date
                );

        return ResponseEntity.ok(
                ApiResponse.<AttendanceResponse>builder()
                        .success(true)
                        .message("Employee attendance fetched successfully")
                        .data(attendance)
                        .build()
        );
    }

    // Update Attendance
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequest request
    ) {

        AttendanceResponse attendance =
                attendanceService.updateAttendance(id, request);

        return ResponseEntity.ok(
                ApiResponse.<AttendanceResponse>builder()
                        .success(true)
                        .message("Attendance updated successfully")
                        .data(attendance)
                        .build()
        );
    }

    @GetMapping("/employee/{employeeId}/history")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getEmployeeAttendanceHistory(
            @PathVariable Long employeeId
    ) {

        List<AttendanceResponse> response =
                attendanceService.getEmployeeAttendanceHistory(employeeId);

        return ResponseEntity.ok(
                ApiResponse.<List<AttendanceResponse>>builder()
                        .success(true)
                        .message("Attendance history fetched successfully")
                        .data(response)
                        .build()
        );
    }
}