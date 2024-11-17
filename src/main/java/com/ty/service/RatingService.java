package com.ty.service;

import com.ty.dto.ResponseStructure;
import com.ty.entity.Presentation;
import com.ty.entity.Rating;
import com.ty.entity.User;
import com.ty.exception.PresentationNotFoundException;
import com.ty.exception.RatingNotFoundException;
import com.ty.exception.UserNotFoundException;
import com.ty.repository.PresentationRepository;
import com.ty.repository.RatingRepository;
import com.ty.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
    private PresentationRepository presentationRepository;

    // Rate a presentation
    public ResponseEntity<ResponseStructure<Rating>> ratePresentation(Integer userId, Integer presentationPid, Rating rating) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        
        Presentation presentation = presentationRepository.findById(presentationPid)
                .orElseThrow(() -> new PresentationNotFoundException("Presentation not found with id: " + presentationPid));

        
        rating.setUser(user);
        rating.setPresentation(presentation);

        
        Rating savedRating = ratingRepository.save(rating);

        // Prepare the response
        ResponseStructure<Rating> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Presentation rated successfully");
        response.setData(savedRating);

        // Return the response
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Get rating by presentation ID
    public ResponseEntity<ResponseStructure<Rating>> getRatingByPresentationId(Integer presentationPid) {
        Rating rating = ratingRepository.findByPresentationPid(presentationPid);
        if (rating == null) {
            throw new RatingNotFoundException("Rating not found for presentation id: " + presentationPid);
        }

        ResponseStructure<Rating> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Rating found");
        response.setData(rating);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all ratings for a user
    public ResponseEntity<ResponseStructure<List<Rating>>> getRatingsByUser(Integer userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new RatingNotFoundException("No ratings found for user id: " + userId);
        }

        ResponseStructure<List<Rating>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Ratings retrieved successfully");
        response.setData(ratings);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
