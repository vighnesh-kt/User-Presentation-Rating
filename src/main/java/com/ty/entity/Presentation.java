package com.ty.entity;

import com.ty.enums.PresentationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "presentation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String topic;

    @Enumerated(EnumType.STRING)
    private PresentationStatus presentationStatus;

    private Double userTotalScore;

    // Bidirectional mapping to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Bidirectional mapping to Ratings
    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    // Helper methods for bidirectional relationship
    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setPresentation(this);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setPresentation(null);
    }
}
