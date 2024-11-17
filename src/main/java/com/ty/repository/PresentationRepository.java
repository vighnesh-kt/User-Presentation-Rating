package com.ty.repository;

import com.ty.entity.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {
    List<Presentation> findByUserId(Integer userId);
}
