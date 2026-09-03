package com.example.Employee.Management.dto.response;

import com.example.Employee.Management.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

    private Long id;

    private Long employeeId;

    private String employeeName;

    private LocalDate date;

    private AttendanceStatus status;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private LocalDateTime createdAt;
}