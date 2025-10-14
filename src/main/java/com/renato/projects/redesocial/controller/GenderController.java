package com.renato.projects.redesocial.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.controller.dto.ReadGenderDTO;
import com.renato.projects.redesocial.domain.enums.Gender;

@RequestMapping("/genders")
@RestController
public class GenderController {
	
	@GetMapping
    public ResponseEntity<List<ReadGenderDTO>> getGenders() {
        List<ReadGenderDTO> genders = Arrays.stream(Gender.values())
                .map(g -> new ReadGenderDTO(g.name(), formatLabel(g.name())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(genders);
    }

    private String formatLabel(String name) {
        // Example: "NON_BINARY" → "Non Binary"
        return name.toLowerCase().replace("_", " ");
    }
	
}
