package com.example.infosystems.config;

import com.example.infosystems.model.entity.EmployeeEntity;
import com.example.infosystems.repository.EmployeeRepository;
import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

public class BeforeLogoutFilter implements Filter {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            String username = authentication.getName();
            EmployeeEntity currentEmployee = this.employeeRepository.findEmployeeEntityByUsername(username).get();
            currentEmployee.setToken("");
            this.employeeRepository.save(currentEmployee);
        }
        chain.doFilter(request, response);
    }
}
