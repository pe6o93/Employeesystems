package com.example.infosystems.web;

import com.example.infosystems.model.dto.DirectorateDTO;
import com.example.infosystems.service.DirectorateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/directorate")
@AllArgsConstructor
public class DirectorateController {

    private final DirectorateService directorateService;

    @GetMapping("/{id}")
    public ResponseEntity<DirectorateDTO> getDirectorate(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.directorateService.getDirectorateById(id));
    }

    @GetMapping
    public ResponseEntity<List<DirectorateDTO>> searchDirectorates(@RequestParam("search") String search) {
        return ResponseEntity
                .ok(this.directorateService.searchDirectorates(search));
    }

    @PostMapping
    ResponseEntity<DirectorateDTO> createDirectorate(@RequestBody @Valid DirectorateDTO directorateDTO,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        Integer newDirectorateId = this.directorateService.createDirectorateAndReturnId(directorateDTO);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/{id}").build(newDirectorateId)).build();
    }

    @PostMapping("/{id}")
    ResponseEntity<DirectorateDTO> editDirectorate(@PathVariable("id") Integer id,
                                                 @RequestBody @Valid DirectorateDTO directorateDTO) {
        directorateDTO.setId(id);
        return ResponseEntity
                .ok(this.directorateService.editDepartment(directorateDTO));
    }

    @PatchMapping("/{id}")
    ResponseEntity<DirectorateDTO> disableDirectorate(@PathVariable("id") Integer id){
        this.directorateService.disableDirectorate(id);
        return ResponseEntity.
                noContent().build();
    }
}
