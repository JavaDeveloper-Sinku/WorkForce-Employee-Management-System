package com.example.Employee.Management.dto.response;

import com.example.Employee.Management.enums.DepartmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {

    private Long id;

    private String name;

    private String headOfDepartment;

    private String description;

    private DepartmentStatus status;

    private LocalDateTime createdAt;
}