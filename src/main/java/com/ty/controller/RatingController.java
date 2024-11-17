package com.ty.controller;

import com.ty.dto.ResponseStructure;
import com.ty.entity.Rating;
import com.ty.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Rate a presentation
    @PostMapping("/rate")
    public ResponseEntity<ResponseStructure<Rating>> ratePresentation(
            @RequestParam Integer userId,
            @RequestParam Integer presentationPid,
            @RequestBody Rating rating) {
        return ratingService.ratePresentation(userId, presentationPid, rating);
    }

    // Get rating by presentation ID
    @GetMapping("/{presentationPid}")
    public ResponseEntity<ResponseStructure<Rating>> getRatingByPresentationId(@PathVariable Integer presentationPid) {
        return ratingService.getRatingByPresentationId(presentationPid);
    }

    // Get all ratings for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<Rating>>> getRatingsByUser(@PathVariable Integer userId) {
        return ratingService.getRatingsByUser(userId);
    }
}
