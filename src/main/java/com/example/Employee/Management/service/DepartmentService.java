package com.example.Employee.Management.service;

import com.example.Employee.Management.dto.request.DepartmentRequest;
import com.example.Employee.Management.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest request
    );

    void deleteDepartment(Long id);

    DepartmentResponse changeStatus(Long id);
}