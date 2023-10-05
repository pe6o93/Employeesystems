package com.example.infosystems.web;

import com.example.infosystems.service.EmployeeValidationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {

    private final EmployeeValidationService employeeValidationService;

    @PostMapping("/login")
    public String getToken(@RequestParam("username") final String username,
                           @RequestParam("password") final String password) {
        return employeeValidationService.login(username, password);
    }
}