package com.example.infosystems.web;

import com.example.infosystems.model.dto.CreateEmployeeDTO;
import com.example.infosystems.model.dto.EmployeeDTO;
import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Integer id) {
        EmployeeDTO employeeDTO = this.employeeService.findEmployeeByIdMapToDTO(id);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> searchEmployee(@RequestParam("search") String search) {
        List<EmployeeDTO> employeeDTOS = this.employeeService.searchEmployees(search);
        return ResponseEntity.ok(employeeDTOS);
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid final CreateEmployeeDTO createEmployeeDTO,
                                               final UriComponentsBuilder uriComponentsBuilder) {

        final Integer newEmployeeId = this.employeeService.createEmployeeReturnId(createEmployeeDTO);
        final URI uri = uriComponentsBuilder.path("/{id}").build(newEmployeeId);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{id}")
    ResponseEntity<EmployeeDTO> editEmployee(@RequestBody @Valid EmployeeDTO employeeDTO,
                                             @PathVariable("id") Integer id) {

        EmployeeDTO employee = this.employeeService.editEmployee(employeeDTO, id);
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("/{id}")
    ResponseEntity<EmployeeDTO> disableEmployee(@PathVariable("id") Integer id) {
        this.employeeService.disableEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
