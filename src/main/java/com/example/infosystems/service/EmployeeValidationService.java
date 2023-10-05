package com.example.infosystems.service;

import com.example.infosystems.model.entity.EmployeeEntity;
import com.example.infosystems.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeValidationService {

    private final EmployeeRepository employeeRepository;
    private final static String NOT_FOUNT_TOKEN = "No token found.";

    public String login(String username, String password) {
        Optional<EmployeeEntity> employee =
                this.employeeRepository.login(username, password);
        if (employee.isPresent()) {
            String token = UUID.randomUUID().toString();
            EmployeeEntity custom = employee.get();
            custom.setToken(token);
            this.employeeRepository.save(custom);
            return token;
        }
        return NOT_FOUNT_TOKEN;
    }

    public Optional<User> findByToken(String token) {
        Optional<EmployeeEntity> employee = this.employeeRepository.findByToken(token);
        if (employee.isPresent()) {
            EmployeeEntity customer1 = employee.get();
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_" + customer1.getPosition().name());
            User user = new User(customer1.getUsername(), customer1.getPassword(),
                    true, true, true, true,
                    authorityList);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
