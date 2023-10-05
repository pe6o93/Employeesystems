package com.example.infosystems.service;

import com.example.infosystems.model.dto.DirectorateDTO;
import com.example.infosystems.model.entity.DirectorateEntity;
import com.example.infosystems.repository.DirectorateRepository;
import com.example.infosystems.repository.EmployeeRepository;
import com.example.infosystems.web.exception.DirectorateNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DirectorateService {

    private final DirectorateRepository directorateRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DirectorateDTO getDirectorateById(Integer id) {

        DirectorateEntity directorateEntity = this.directorateRepository
                .findById(id).orElseThrow(() -> new DirectorateNotFoundException(id));
        return directorateEntityToDTO(directorateEntity);
    }

    public List<DirectorateDTO> searchDirectorates(String query) {
        List<DirectorateEntity> directorateEntities = this.directorateRepository.searchDirectorates(query);
        if (org.springframework.util.CollectionUtils.isEmpty(directorateEntities)) {
            return new ArrayList<>();
        }
        return directorateEntities.stream().map(this::directorateEntityToDTO).toList();
    }

    public DirectorateDTO editDepartment(DirectorateDTO directorateDTO) {
        DirectorateEntity directorateEntity = directorateDtoToEntity(directorateDTO);
        this.directorateRepository.save(directorateEntity);
        return directorateDTO;
    }


    public Integer createDirectorateAndReturnId(DirectorateDTO directorateDTO) {
        boolean isDirectoratePresent = this.directorateRepository.findById(directorateDTO.getId()).isPresent();
        if (directorateDTO.getId() != null && isDirectoratePresent) {
            throw new DirectorateNotFoundException("Вече съществува дирекция с това Id.");
        }
        DirectorateEntity directorateEntity = directorateDtoToEntity(directorateDTO);
        this.directorateRepository.save(directorateEntity);
        return directorateEntity.getId();
    }

    private DirectorateEntity directorateDtoToEntity(DirectorateDTO directorateDTO) {

        DirectorateEntity directorateEntity = this.modelMapper.map(directorateDTO, DirectorateEntity.class);
        if (directorateDTO.getDirectorId() != null) {
            directorateEntity.setDirector(this.employeeRepository
                    .findById(directorateDTO.getId()).orElseThrow(() ->
                            new DirectorateNotFoundException(directorateDTO.getId())));
        }
        return directorateEntity;
    }

    private DirectorateDTO directorateEntityToDTO(DirectorateEntity directorateEntity) {
        DirectorateDTO directorateDTO = this.modelMapper.map(directorateEntity, DirectorateDTO.class);
        if (directorateEntity.getDirector() != null) {
            directorateDTO.setDirectorId(directorateEntity.getDirector().getId());
        }
        return directorateDTO;
    }

    public void disableDirectorate(Integer id) {
        DirectorateEntity directorateEntity = this.directorateRepository
                .findById(id).orElseThrow(() -> new DirectorateNotFoundException(id));
        directorateEntity.setIsActive(false);
        this.directorateRepository.save(directorateEntity);
    }

    public void initDirectorates() {
        if (this.directorateRepository.findAll().isEmpty()) {
            DirectorateEntity directorateEntity = new DirectorateEntity();
            directorateEntity.setName("Directorate 1");
//            directorateEntity.setIsActive(true);
            directorateEntity.setDescription("This is directorate description.");

            DirectorateEntity directorateEntity2 = new DirectorateEntity();
            directorateEntity2.setName("Directorate 2");
            directorateEntity2.setDescription("This is directorate description.");

            DirectorateEntity directorateEntity3 = new DirectorateEntity();
            directorateEntity3.setName("Directorate 3");
            directorateEntity3.setDescription("This is directorate description.");

            this.directorateRepository.saveAll(List.of(directorateEntity, directorateEntity2, directorateEntity3));
        }
    }
}