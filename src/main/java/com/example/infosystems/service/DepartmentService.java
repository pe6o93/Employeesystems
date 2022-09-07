package com.example.infosystems.service;

import com.example.infosystems.model.dto.DepartmentDTO;
import com.example.infosystems.model.entity.DepartmentEntity;
import com.example.infosystems.model.entity.DirectorateEntity;
import com.example.infosystems.model.entity.EmployeeEntity;
import com.example.infosystems.repository.DepartmentRepository;
import com.example.infosystems.repository.DirectorateRepository;
import com.example.infosystems.repository.EmployeeRepository;
import com.example.infosystems.web.exception.DepartmentNotFoundException;
import com.example.infosystems.web.exception.DirectorateNotFoundException;
import com.example.infosystems.web.exception.EmployeeNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final DirectorateRepository directorateRepository;

    public DepartmentDTO getDepartmentDTObyId(Integer id) {
        DepartmentEntity departmentEntity = this.departmentRepository
                .findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
        return departmentEntityToDTO(departmentEntity);
    }


    public List<DepartmentDTO> searchDepartments(String query) {
        List<DepartmentEntity> departments = this.departmentRepository.searchDepartments(query);
        if (departments.isEmpty()) {
            return new ArrayList<>();
        }
        return departments.stream().map(this::departmentEntityToDTO).toList();
    }


    public Integer createDepartmentAndReturnId(DepartmentDTO departmentDTO) {
        if (departmentDTO.getId() != null && this.employeeRepository.findById(departmentDTO.getId()).isPresent()) {
            throw new DepartmentNotFoundException("Вече съществува отдел с това Id.");
        }
        DepartmentEntity departmentEntity = departmentDtoToEntity(departmentDTO);
        this.departmentRepository.save(departmentEntity);
        return departmentEntity.getId();
    }

    public DepartmentDTO editDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = departmentDtoToEntity(departmentDTO);
        this.departmentRepository.save(departmentEntity);
        return departmentDTO;
    }

    private DepartmentEntity departmentDtoToEntity(DepartmentDTO departmentDTO) {

        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(departmentDTO.getId());
        departmentEntity.setName(departmentDTO.getName());
        departmentEntity.setDescription(departmentDTO.getDescription());
        if (departmentDTO.getBossId() != null) {
            departmentEntity.setBoss(this.employeeRepository.findById(
                    departmentDTO.getBossId()).orElseThrow(() -> new EmployeeNotFoundException(departmentDTO.getBossId())));
        }
        if (departmentDTO.getDirectorateId() != null) {
            departmentEntity.setDirectorate(this.directorateRepository.findById(
                    departmentDTO.getDirectorateId()).orElseThrow(() -> new DirectorateNotFoundException(departmentDTO.getDirectorateId())));
        }
        return departmentEntity;
    }

    private DepartmentDTO departmentEntityToDTO(DepartmentEntity departmentEntity) {
        DepartmentDTO departmentDTO = this.modelMapper.map(departmentEntity, DepartmentDTO.class);
        if (departmentEntity.getBoss() != null) {
            departmentDTO.setBossId(departmentEntity.getBoss().getId());
        }
        if (departmentEntity.getDirectorate() != null) {
            departmentDTO.setDirectorateId(departmentEntity.getDirectorate().getId());
        }
        return departmentDTO;
    }

    public DepartmentEntity getDepartmentEntityByName(String departmentName) {
        return this.departmentRepository.findDepartmentEntityByName(departmentName)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentName));
    }

    public void disableDepartment(Integer id) {
        DepartmentEntity departmentEntity = this.departmentRepository
                .findById(id).orElseThrow(() -> new DirectorateNotFoundException(id));
        departmentEntity.setIsActive(false);
        this.departmentRepository.save(departmentEntity);
    }

    public void initDepartments() {
        if(this.departmentRepository.findAll().isEmpty()){
            DepartmentEntity department=new DepartmentEntity();
            department.setName("Department 1");
            department.setDescription("This is department description.");
            department.setDirectorate(this.directorateRepository.findAll().get(0));

            DepartmentEntity department2=new DepartmentEntity();
            department2.setName("Department 2");
            department2.setDescription("This is department description.");
            department2.setDirectorate(this.directorateRepository.findAll().get(0));

            DepartmentEntity department3=new DepartmentEntity();
            department3.setName("Department 3");
            department3.setDescription("This is department description.");
            department3.setDirectorate(this.directorateRepository.findAll().get(1));

            DepartmentEntity department4=new DepartmentEntity();
            department4.setName("Department 4");
            department4.setDescription("This is department description.");
            department4.setDirectorate(this.directorateRepository.findAll().get(2));
            this.departmentRepository.saveAll(List.of(department,department2,department3,department4));
        }
    }
}

