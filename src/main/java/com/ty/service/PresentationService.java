package com.ty.service;

import com.ty.dto.ResponseStructure;
import com.ty.entity.Presentation;
import com.ty.entity.User;
import com.ty.enums.PresentationStatus;
import com.ty.exception.PresentationNotFoundException;
import com.ty.repository.PresentationRepository;
import com.ty.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PresentationService {

	@Autowired
	private PresentationRepository presentationRepository;

	private UserRepository userRepository;

	// Assign a new presentation
	public ResponseEntity<ResponseStructure<Presentation>> assignPresentation(Integer id, Presentation presentation) {
		ResponseStructure<Presentation> response = new ResponseStructure<>();
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			List<Presentation> p = user.get().getPresentations();
			p.add(presentation);
			user.get().setPresentations(p);
			userRepository.save(user.get());
			Presentation savedPresentation = presentationRepository.save(presentation);
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Presentation assigned successfully");
			response.setData(savedPresentation);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Failed");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}

	// Update presentation status
	public ResponseEntity<ResponseStructure<PresentationStatus>> updatePresentationStatus(Integer pid,
			PresentationStatus status) {
		Presentation presentation = presentationRepository.findById(pid)
				.orElseThrow(() -> new PresentationNotFoundException("Presentation not found with id: " + pid));

		presentation.setPresentationStatus(status);
		Presentation updatedPresentation = presentationRepository.save(presentation);

		ResponseStructure<PresentationStatus> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Presentation status updated successfully");
		response.setData(updatedPresentation.getPresentationStatus());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get presentation by ID
	public ResponseEntity<ResponseStructure<Presentation>> getPresentationById(Integer pid) {
		Presentation presentation = presentationRepository.findById(pid)
				.orElseThrow(() -> new PresentationNotFoundException("Presentation not found with id: " + pid));

		ResponseStructure<Presentation> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Presentation found");
		response.setData(presentation);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get all presentations for a user
	public ResponseEntity<ResponseStructure<List<Presentation>>> getPresentationsByUser(Integer userId) {
		List<Presentation> presentations = presentationRepository.findByUserId(userId);

		ResponseStructure<List<Presentation>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Presentations retrieved successfully");
		response.setData(presentations);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Save presentation score
	public ResponseEntity<ResponseStructure<Double>> savePresentationScore(Integer pid, Double score) {
		Presentation presentation = presentationRepository.findById(pid)
				.orElseThrow(() -> new PresentationNotFoundException("Presentation not found with id: " + pid));

		presentation.setUserTotalScore(score);
		presentationRepository.save(presentation);

		ResponseStructure<Double> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Presentation score updated successfully");
		response.setData(presentation.getUserTotalScore());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
