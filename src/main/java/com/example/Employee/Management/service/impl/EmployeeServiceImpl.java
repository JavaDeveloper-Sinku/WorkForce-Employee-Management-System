package com.example.Employee.Management.service.impl;

import com.example.Employee.Management.dto.request.EmployeeRequest;
import com.example.Employee.Management.dto.response.EmployeeResponse;
import com.example.Employee.Management.entity.Employee;
import com.example.Employee.Management.entity.Role;
import com.example.Employee.Management.entity.User;
import com.example.Employee.Management.enums.EmployeeStatus;
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

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // =========================================================
    // CREATE EMPLOYEE
    // =========================================================

    @Transactional
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        // 1. Check employee email
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Employee already exists with this email"
            );
        }

        // 2. Check user email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "User already exists with this email"
            );
        }

        // 3. Find requested role
        Role role = roleRepository
                .findByName(request.getRole())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found: " + request.getRole()
                        )
                );

        // 4. Generate unique username
        String username = generateUsername(
                request.getFirstName(),
                request.getLastName()
        );

        // 5. Generate temporary password
        String temporaryPassword = generateTemporaryPassword();

        // 6. Create User
        User user = User.builder()
                .username(username)
                .email(request.getEmail())
                .password(passwordEncoder.encode(temporaryPassword))
                .role(role)
                .enabled(true)
                .build();

        // 7. Save User first
        User savedUser = userRepository.save(user);

        // 8. Generate employee code
        String employeeCode = generateEmployeeCode();

        // 9. Create Employee
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .employeeCode(employeeCode)
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .joiningDate(request.getJoiningDate())
                .employmentType(request.getEmploymentType())
                .salary(request.getSalary())

                // System automatically sets ACTIVE
                .status(EmployeeStatus.ACTIVE)

                .user(savedUser)
                .build();

        // 10. Save Employee
        Employee savedEmployee = employeeRepository.save(employee);

        // 11. Return response
        return mapToResponse(savedEmployee);
    }


    // =========================================================
    // GET ALL EMPLOYEES
    // =========================================================

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

        Page<Employee> employees =
                employeeRepository.findAll(pageable);

        return employees.map(this::mapToResponse);
    }


    // =========================================================
    // SEARCH EMPLOYEES
    // =========================================================

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

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<Employee> employees =
                employeeRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                                keyword,
                                keyword,
                                keyword,
                                keyword,
                                pageable
                        );

        return employees.map(this::mapToResponse);
    }


    // =========================================================
    // FILTER EMPLOYEES
    // =========================================================

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

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Specification<Employee> specification =
                EmployeeSpecification.hasDepartment(department)
                        .and(EmployeeSpecification.hasMinSalary(minSalary))
                        .and(EmployeeSpecification.hasMaxSalary(maxSalary));

        Page<Employee> employees =
                employeeRepository.findAll(
                        specification,
                        pageable
                );

        return employees.map(this::mapToResponse);
    }


    // =========================================================
    // GET EMPLOYEE BY ID
    // =========================================================

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with this id"
                                )
                        );

        return mapToResponse(employee);
    }


    // =========================================================
    // UPDATE EMPLOYEE
    // =========================================================

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request
    ) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with this id"
                                )
                        );

        // Check email only when email is changed
        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Employee already exists with this email"
            );
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setEmploymentType(request.getEmploymentType());
        employee.setSalary(request.getSalary());

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }


    // =========================================================
    // DELETE EMPLOYEE
    // =========================================================

    @Override
    @Transactional
    public void deleteEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with this id"
                                )
                        );

        User user = employee.getUser();

        if (user != null) {
            user.setEmployee(null);
            userRepository.save(user);
        }

        employeeRepository.delete(employee);

        if (user != null) {
            userRepository.delete(user);
        }
    }


    // =========================================================
    // GENERATE USERNAME
    // =========================================================

    private String generateUsername(
            String firstName,
            String lastName
    ) {

        String baseUsername =
                (firstName + "." + lastName)
                        .toLowerCase()
                        .replaceAll("\\s+", "");

        String username = baseUsername;

        int counter = 1;

        while (userRepository.existsByUsername(username)) {

            username =
                    baseUsername + counter;

            counter++;
        }

        return username;
    }


    // =========================================================
    // GENERATE TEMPORARY PASSWORD
    // =========================================================

    private String generateTemporaryPassword() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        + "abcdefghijklmnopqrstuvwxyz"
                        + "0123456789"
                        + "@#$";

        SecureRandom random =
                new SecureRandom();

        StringBuilder password =
                new StringBuilder();

        for (int i = 0; i < 10; i++) {

            password.append(
                    characters.charAt(
                            random.nextInt(
                                    characters.length()
                            )
                    )
            );
        }

        return password.toString();
    }


    // =========================================================
    // GENERATE EMPLOYEE CODE
    // =========================================================

    private String generateEmployeeCode() {

        long nextNumber =
                employeeRepository.count() + 1;

        String employeeCode =
                String.format(
                        "EMP-%03d",
                        nextNumber
                );

        while (
                employeeRepository
                        .existsByEmployeeCode(employeeCode)
        ) {

            nextNumber++;

            employeeCode =
                    String.format(
                            "EMP-%03d",
                            nextNumber
                    );
        }

        return employeeCode;
    }


    // =========================================================
    // MAP ENTITY TO RESPONSE
    // =========================================================

    private EmployeeResponse mapToResponse(
            Employee employee
    ) {

        return EmployeeResponse.builder()

                .id(employee.getId())

                .employeeCode(
                        employee.getEmployeeCode()
                )

                .firstName(
                        employee.getFirstName()
                )

                .lastName(
                        employee.getLastName()
                )

                .email(
                        employee.getEmail()
                )

                .phone(
                        employee.getPhone()
                )

                .role(
                        employee.getUser() != null
                                && employee.getUser().getRole() != null
                                ? employee.getUser()
                                .getRole()
                                .getName()
                                : null
                )

                .department(
                        employee.getDepartment()
                )

                .designation(
                        employee.getDesignation()
                )

                .joiningDate(
                        employee.getJoiningDate()
                )

                .employmentType(
                        employee.getEmploymentType()
                )

                .salary(
                        employee.getSalary()
                )

                .status(
                        employee.getStatus()
                )

                .build();
    }


    @Override
    @Transactional
    public EmployeeResponse updateEmployeeStatus(
            Long id,
            EmployeeStatus status
    ) {
        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with this id"
                                )
                        );

        employee.setStatus(status);

        User user = employee.getUser();

        if (user != null) {
            user.setEnabled(status == EmployeeStatus.ACTIVE);
            userRepository.save(user);
        }

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }
}


