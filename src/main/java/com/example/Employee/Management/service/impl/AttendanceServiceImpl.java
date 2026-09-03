package com.example.Employee.Management.service.impl;

import com.example.Employee.Management.dto.request.AttendanceRequest;
import com.example.Employee.Management.dto.response.AttendanceResponse;
import com.example.Employee.Management.entity.Attendance;
import com.example.Employee.Management.entity.Employee;
import com.example.Employee.Management.repository.AttendanceRepository;
import com.example.Employee.Management.repository.EmployeeRepository;
import com.example.Employee.Management.service.AttendanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public AttendanceResponse createAttendance(
            AttendanceRequest request
    ) {

        Employee employee = employeeRepository.findById(
                request.getEmployeeId()
        ).orElseThrow(() ->
                new EntityNotFoundException(
                        "Employee not found with id: "
                                + request.getEmployeeId()
                )
        );

        if (attendanceRepository
                .findByEmployeeIdAndDate(
                        request.getEmployeeId(),
                        request.getDate()
                ).isPresent()) {

            throw new IllegalStateException(
                    "Attendance already exists for this employee on this date"
            );
        }

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(request.getDate())
                .status(request.getStatus())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .build();

        return mapToResponse(
                attendanceRepository.save(attendance)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<AttendanceResponse> getAttendanceByDate(
            LocalDate date
    ) {

        return attendanceRepository.findByDate(date)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public AttendanceResponse getEmployeeAttendance(
            Long employeeId,
            LocalDate date
    ) {

        Attendance attendance =
                attendanceRepository
                        .findByEmployeeIdAndDate(employeeId, date)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Attendance not found"
                                )
                        );

        return mapToResponse(attendance);
    }

    @Override
    @Transactional
    public AttendanceResponse updateAttendance(
            Long id,
            AttendanceRequest request
    ) {

        Attendance attendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Attendance not found with id: "
                                                + id
                                )
                        );

        Employee employee = employeeRepository.findById(
                request.getEmployeeId()
        ).orElseThrow(() ->
                new EntityNotFoundException(
                        "Employee not found with id: "
                                + request.getEmployeeId()
                )
        );

        attendance.setEmployee(employee);
        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());

        return mapToResponse(
                attendanceRepository.save(attendance)
        );
    }

    private AttendanceResponse mapToResponse(
            Attendance attendance
    ) {

        Employee employee = attendance.getEmployee();

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .employeeId(employee.getId())
                .employeeName(
                        employee.getFirstName()
                                + " "
                                + employee.getLastName()
                )
                .date(attendance.getDate())
                .status(attendance.getStatus())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .createdAt(attendance.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AttendanceResponse> getEmployeeAttendanceHistory(Long employeeId) {

        employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Employee not found with id: " + employeeId
                        )
                );

        return attendanceRepository
                .findByEmployeeIdOrderByDateDesc(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}