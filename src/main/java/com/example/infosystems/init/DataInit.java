package com.example.infosystems.init;

import com.example.infosystems.service.DepartmentService;
import com.example.infosystems.service.DirectorateService;
import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInit implements CommandLineRunner {

    private final DirectorateService directorateService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;


    @Override
    public void run(String... args) {
        this.directorateService.initDirectorates();
        this.departmentService.initDepartments();
        this.employeeService.initEmployees();
    }
}
