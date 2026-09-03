package com.example.Employee.Management.service.impl;

import com.example.Employee.Management.dto.request.DepartmentRequest;
import com.example.Employee.Management.dto.response.DepartmentResponse;
import com.example.Employee.Management.entity.Department;
import com.example.Employee.Management.enums.DepartmentStatus;
import com.example.Employee.Management.exception.DuplicateResourceException;
import com.example.Employee.Management.repository.DepartmentRepository;
import com.example.Employee.Management.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse createDepartment(
            DepartmentRequest request
    ) {

        if (departmentRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException(
                    "Department already exists"
            );
        }

        Department department = Department.builder()
                .name(request.getName())
                .headOfDepartment(request.getHeadOfDepartment())
                .description(request.getDescription())
                .status(DepartmentStatus.ACTIVE)
                .build();

        Department saved = departmentRepository.save(department);

        return mapToResponse(saved);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        return mapToResponse(department);
    }

    @Override
    @Transactional
    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest request
    ) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        if (!department.getName().equalsIgnoreCase(request.getName())
                && departmentRepository
                .existsByNameIgnoreCase(request.getName())) {

            throw new DuplicateResourceException(
                    "Department already exists"
            );
        }

        department.setName(request.getName());
        department.setHeadOfDepartment(
                request.getHeadOfDepartment()
        );
        department.setDescription(
                request.getDescription()
        );

        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        departmentRepository.delete(department);
    }

    @Override
    @Transactional
    public DepartmentResponse changeStatus(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        if (department.getStatus() == DepartmentStatus.ACTIVE) {
            department.setStatus(DepartmentStatus.INACTIVE);
        } else {
            department.setStatus(DepartmentStatus.ACTIVE);
        }

        return mapToResponse(departmentRepository.save(department));
    }

    private DepartmentResponse mapToResponse(
            Department department
    ) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .headOfDepartment(
                        department.getHeadOfDepartment()
                )
                .description(department.getDescription())
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .build();
    }
}