package com.example.infosystems.service;

import com.example.infosystems.model.entity.EmployeeEntity;
import com.example.infosystems.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeValidationService{

    private final EmployeeRepository employeeRepository;

    public String login(String username, String password) {
        Optional<EmployeeEntity> employee = this.employeeRepository.login(username,password);
        if(employee.isPresent()){
            String token = UUID.randomUUID().toString();
            EmployeeEntity custom= employee.get();
            custom.setToken(token);
            this.employeeRepository.save(custom);
            return token;
        }
        return StringUtils.EMPTY;
    }

    public Optional<User>  findByToken(String token) {
        Optional<EmployeeEntity> employee= this.employeeRepository.findByToken(token);
        if(employee.isPresent()){
            EmployeeEntity customer1 = employee.get();
            User user= new User(customer1.getUsername(), customer1.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_"+customer1.getPosition().name()));
            return Optional.of(user);
        }
        return  Optional.empty();
    }
}
