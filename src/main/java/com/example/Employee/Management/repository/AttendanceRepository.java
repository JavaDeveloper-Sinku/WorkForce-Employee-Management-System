package com.example.Employee.Management.repository;

import com.example.Employee.Management.entity.Attendance;
import com.example.Employee.Management.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeIdAndDate(
            Long employeeId,
            LocalDate date
    );

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByEmployeeIdOrderByDateDesc(Long employeeId);

    long countByDateAndStatus(
            LocalDate date,
            AttendanceStatus status
    );
}