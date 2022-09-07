package com.example.infosystems.web;

import com.example.infosystems.model.dto.CreateEmployeeDTO;
import com.example.infosystems.model.dto.EmployeeDTO;
import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Integer id) {
        return ResponseEntity
                .ok(this.employeeService.findEmployeeByIdMapToDTO(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> searchEmployee(@RequestParam("search") String search) {
        return ResponseEntity
                .ok(this.employeeService.searchEmployees(search));
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid CreateEmployeeDTO createEmployeeDTO,
                                               UriComponentsBuilder uriComponentsBuilder) {

        Integer newEmployeeId = this.employeeService.createEmployeeReturnId(createEmployeeDTO);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/{id}").build(newEmployeeId)).build();
    }

    @PostMapping("/{id}")
    ResponseEntity<EmployeeDTO> editEmployee(@RequestBody @Valid EmployeeDTO employeeDTO,
                                             @PathVariable("id") Integer id){
        employeeDTO.setId(id);
        return ResponseEntity.ok(this.employeeService.editEmployee(employeeDTO));
    }

    @PatchMapping("/{id}")
    ResponseEntity<EmployeeDTO> disableEmployee(@PathVariable("id") Integer id){
        this.employeeService.disableEmployee(id);
        return ResponseEntity.
                noContent().build();
    }
}
