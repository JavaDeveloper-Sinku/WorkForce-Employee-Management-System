package com.example.Employee.Management.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMeResponse {

    private String username;
    private String email;
    private String role;

    private Long employeeId;
}