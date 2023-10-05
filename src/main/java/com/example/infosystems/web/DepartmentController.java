package com.example.infosystems.web;

import com.example.infosystems.model.dto.DepartmentDTO;
import com.example.infosystems.service.DepartmentService;
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
import java.util.List;

@RestController
@RequestMapping("/api/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.departmentService.getDepartmentDTObyId(id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> searchDepartments(@RequestParam("search") String search) {
        return ResponseEntity
                .ok(this.departmentService.searchDepartments(search));
    }

    @PostMapping
    ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        Integer newDepartmentId = this.departmentService.createDepartmentAndReturnId(departmentDTO);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/{id}").build(newDepartmentId)).build();
    }

    @PostMapping("/{id}")
    ResponseEntity<DepartmentDTO> editDepartment(@PathVariable("id") Integer id,
                                                 @RequestBody @Valid DepartmentDTO departmentDTO) {
        departmentDTO.setId(id);
        return ResponseEntity
                .ok(this.departmentService.editDepartment(departmentDTO));
    }

    @PatchMapping("/{id}")
    ResponseEntity<DepartmentDTO> disableDepartment(@PathVariable("id") Integer id){
        this.departmentService.disableDepartment(id);
        return ResponseEntity.
                noContent().build();
    }
}
