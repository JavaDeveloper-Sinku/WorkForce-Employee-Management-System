package com.example.Employee.Management.service.impl;

import com.example.Employee.Management.dto.request.EmployeeRequest;
import com.example.Employee.Management.dto.response.EmployeeResponse;
import com.example.Employee.Management.entity.Employee;
import com.example.Employee.Management.entity.Role;
import com.example.Employee.Management.entity.User;
import com.example.Employee.Management.enums.RoleType;
import com.example.Employee.Management.exception.DuplicateResourceException;
import com.example.Employee.Management.exception.ResourceNotFoundException;


import com.example.Employee.Management.repository.EmployeeRepository;


import com.example.Employee.Management.repository.RoleRepository;
import com.example.Employee.Management.repository.UserRepository;
import com.example.Employee.Management.service.EmployeeService;
import com.example.Employee.Management.specification.EmployeeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;



    @Transactional
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        // Check employee email
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Employee already exists with this email"
            );
        }


        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "User already exists with this email"
            );
        }

        // Find EMPLOYEE role
        Role employeeRole = roleRepository
                .findByName(RoleType.EMPLOYEE)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "EMPLOYEE role not found"
                        )
                );

        // Create User account
        User user = User.builder()
                .username(request.getEmail())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .role(employeeRole)
                .enabled(true)
                .build();

        // Save User first
        User savedUser = userRepository.save(user);

        // Create Employee
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .department(request.getDepartment())
                .salary(request.getSalary())
                .user(savedUser)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToResponse(savedEmployee);
    }



    @Override
    public Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();


        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<Employee> employees = employeeRepository.findAll(pageable);


        return employees.map(this::mapToResponse);

    }

    @Override
    public Page<EmployeeResponse> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employees = employeeRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                                keyword,
                                keyword,
                                keyword,
                                keyword,
                                pageable
                        );

        return employees.map(this::mapToResponse);
    }

    @Override
    public Page<EmployeeResponse> filterEmployees(

            String department,
            Double minSalary,
            Double maxSalary,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Employee> specification =
                EmployeeSpecification.hasDepartment(department)
                        .and(EmployeeSpecification.hasMinSalary(minSalary))
                        .and(EmployeeSpecification.hasMaxSalary(maxSalary));

        Page<Employee> employees = employeeRepository.findAll(specification, pageable);

        return employees.map(this::mapToResponse);
    }



    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with this id"));

        return mapToResponse(employee);
    }





    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request){

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with this id"));

        if (!employee.getEmail().equals(request.getEmail()) && employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee already exists with this email");
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }



    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with this id"));
        employeeRepository.delete(employee);
    }

    private EmployeeResponse mapToResponse(Employee employee){

        return EmployeeResponse.builder()

                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }

}
