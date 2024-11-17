package com.ty.controller;

import com.ty.dto.ResponseStructure;
import com.ty.entity.Presentation;
import com.ty.enums.PresentationStatus;
import com.ty.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presentations")
public class PresentationController {

    @Autowired
    private PresentationService presentationService;

    // Assign a new presentation
    @PostMapping("/assign")
    public ResponseEntity<ResponseStructure<Presentation>> assignPresentation(@RequestParam Integer id,@RequestBody Presentation presentation) {
        return presentationService.assignPresentation(id,presentation);
    }

    // Update presentation status
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseStructure<PresentationStatus>> updatePresentationStatus(@PathVariable Integer id, @RequestParam PresentationStatus status) {
        return presentationService.updatePresentationStatus(id, status);
    }

    // Get presentation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Presentation>> getPresentationById(@PathVariable Integer id) {
        return presentationService.getPresentationById(id);
    }

    // Get all presentations for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<Presentation>>> getPresentationsByUser(@PathVariable Integer userId) {
        return presentationService.getPresentationsByUser(userId);
    }

    // Save presentation score
    @PutMapping("/{id}/score")
    public ResponseEntity<ResponseStructure<Double>> savePresentationScore(@PathVariable Integer id, @RequestParam Double score) {
        return presentationService.savePresentationScore(id, score);
    }
}
