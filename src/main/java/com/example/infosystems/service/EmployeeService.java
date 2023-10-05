package com.example.infosystems.service;

import com.example.infosystems.model.dto.CreateEmployeeDTO;
import com.example.infosystems.model.dto.EmployeeDTO;
import com.example.infosystems.model.entity.DepartmentEntity;
import com.example.infosystems.model.entity.DirectorateEntity;
import com.example.infosystems.model.entity.EmployeeEntity;
import com.example.infosystems.model.enums.PositionEnum;
import com.example.infosystems.repository.DepartmentRepository;
import com.example.infosystems.repository.DirectorateRepository;
import com.example.infosystems.repository.EmployeeRepository;
import com.example.infosystems.web.exception.EmployeeNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;
    private final DirectorateRepository directorateRepository;
    private final DepartmentRepository departmentRepository;
    private final FileService fileService;

    public EmployeeDTO findEmployeeByIdMapToDTO(Integer id) {
        EmployeeEntity employeeEntity = this.employeeRepository
                .findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return mapEntityToDTO(employeeEntity);
    }

    public List<EmployeeDTO> searchEmployees(String query) {
        List<EmployeeEntity> employees = this.employeeRepository.searchEmployee(query);
        if (employees.isEmpty()) {
            return new ArrayList<>();
        }
        return employees.stream().map(this::mapEntityToDTO).toList();
    }

    public Integer createEmployeeReturnId(CreateEmployeeDTO createEmployeeDTO) {
        EmployeeEntity employeeEntity = mapCreateEmployeeDTOtoEntity(createEmployeeDTO);
        this.employeeRepository.save(employeeEntity);
        return employeeEntity.getId();
    }



    public EmployeeDTO editEmployee(EmployeeDTO employeeDTO, Integer id) {
        employeeDTO.setId(id);
        EmployeeEntity edited = mapEmployeeDTOtoEntity(employeeDTO);
        this.employeeRepository.save(edited);
        return employeeDTO;
    }

    public void disableEmployee(Integer id) {
        EmployeeEntity employeeEntity = this.employeeRepository
                .findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeEntity.setIsActive(false);
        this.employeeRepository.save(employeeEntity);
    }

    private EmployeeDTO mapEntityToDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO dto = this.modelMapper.map(employeeEntity, EmployeeDTO.class);
        dto.setDepartmentName(employeeEntity.getDepartment().getName());
        return dto;
    }

    private EmployeeEntity mapEmployeeDTOtoEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity entity = this.modelMapper.map(employeeDTO, EmployeeEntity.class);
        DepartmentEntity departmentEntity = this.departmentService.getDepartmentEntityByName(employeeDTO.getDepartmentName());
        entity.setDepartment(departmentEntity);
        return entity;
    }
    private EmployeeEntity mapCreateEmployeeDTOtoEntity(CreateEmployeeDTO createEmployeeDTO) {
        EmployeeEntity entity = this.modelMapper.map(createEmployeeDTO, EmployeeEntity.class);
        DepartmentEntity departmentEntity =
                this.departmentService.getDepartmentEntityByName(createEmployeeDTO.getDepartmentName());
        entity.setDepartment(departmentEntity);
        return entity;
    }

    @Transactional
    public boolean checkIfBoss(Integer id) {
        EmployeeEntity currentEmployee = getEmployeeEntityByPrincipal();
        EmployeeEntity checkedEmployee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        EmployeeEntity boss = checkedEmployee.getDepartment().getBoss();
        if (boss != null) {
            if (currentEmployee.getPosition().name().equals(PositionEnum.BOSS.name())) {
                return boss.getId().equals(currentEmployee.getId());
            }
        }
        return false;
    }

    public boolean checkIfDirector(Integer id) {

        EmployeeEntity currentEmployee = getEmployeeEntityByPrincipal();
        if (currentEmployee.getPosition().equals(PositionEnum.DIRECTOR)) {
            EmployeeEntity checkedEmployee = this.employeeRepository.findById(id).
                    orElseThrow(() -> new EmployeeNotFoundException(id));
            Integer directorateId = checkedEmployee.getDepartment().getDirectorate().getId();
            Integer directorId = this.directorateRepository.findById(directorateId).get().getDirector().getId();
            return currentEmployee.getId().equals(directorId);
        }
        return false;
    }

    public Boolean isExistingEmployee(String personalNumber){
       return !this.employeeRepository.existsEmployeeEntityByEgn(personalNumber);
    }

    private EmployeeEntity getEmployeeEntityByPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        EmployeeEntity currentEmployee = this.employeeRepository.findEmployeeEntityByUsername(username).get();
        return currentEmployee;
    }

    public boolean isUserNameFree(String username) {
      return this.employeeRepository.findEmployeeEntityByUsername(username).isEmpty();
    }

    public void initEmployees() {
        if(this.employeeRepository.findAll().isEmpty()){
            List<DepartmentEntity> departments = this.departmentRepository.findAll();
            DepartmentEntity departmentEntity = departments.get(0);
            DepartmentEntity departmentEntity2 = departments.get(1);
            DepartmentEntity departmentEntity3 = departments.get(2);
            DepartmentEntity departmentEntity4 = departments.get(3);
            EmployeeEntity employee1= new EmployeeEntity("Petar","Petrov","9999999999", (byte) 24,"petar","parola", departmentEntity, PositionEnum.DIRECTOR);
            EmployeeEntity employee2= new EmployeeEntity("Ivan","Ivan","9999999999", (byte) 22,"ivan","parola", departmentEntity2, PositionEnum.EMPLOYEE);
            EmployeeEntity employee3= new EmployeeEntity("Dimitar","Dimitar","9999999999", (byte) 44,"dimitar","parola", departmentEntity3, PositionEnum.BOSS);
            EmployeeEntity employee4= new EmployeeEntity("Maria","Maria","9999999999", (byte) 21,"maria","parola", departmentEntity, PositionEnum.EMPLOYEE);
            EmployeeEntity employee5= new EmployeeEntity("employee1","employee1","9999999999", (byte) 21,"employee1","parola", departmentEntity, PositionEnum.EMPLOYEE);
            EmployeeEntity employee6= new EmployeeEntity("employee2","employee2","9999999999", (byte) 21,"employee2","parola", departmentEntity2, PositionEnum.EMPLOYEE);
            EmployeeEntity employee7= new EmployeeEntity("employee2","employee2","9999999999", (byte) 21,"employee3","parola", departmentEntity2, PositionEnum.EMPLOYEE);
            EmployeeEntity employee8= new EmployeeEntity("employee4","employee4","9999999999", (byte) 21,"employee4","parola", departmentEntity4, PositionEnum.DIRECTOR);
            EmployeeEntity admin= new EmployeeEntity("admin","admin","9999999999", (byte) 21,"admin","parola", departmentEntity4, PositionEnum.ADMIN);
            this.employeeRepository.saveAll(List.of(admin,employee1,employee2,employee3,employee4,employee5,employee6,employee7,employee8));
            departmentEntity.setBoss(employee3);
            departmentEntity2.setBoss(employee3);
            departmentEntity3.setBoss(employee3);
            departmentEntity4.setBoss(employee3);
            this.departmentRepository.saveAll(List.of(departmentEntity,departmentEntity2,departmentEntity3,departmentEntity4));
            List<DirectorateEntity> directorateEntities = this.directorateRepository.findAll();
            directorateEntities.get(0).setDirector(employee1);
            directorateEntities.get(1).setDirector(employee1);
            directorateEntities.get(2).setDirector(employee8);
            this.directorateRepository.saveAll(directorateEntities);
        }
    }
}
