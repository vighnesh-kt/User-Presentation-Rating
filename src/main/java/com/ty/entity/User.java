package com.ty.entity;

import com.ty.enums.Role;
import com.ty.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // Renamed to 'users' to avoid PostgreSQL reserved keyword conflict
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Long phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double userTotalScore;

    // Bidirectional mapping to Presentations
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Presentation> presentations = new ArrayList<>();

    // Bidirectional mapping to Ratings
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    // Helper methods for bidirectional relationship
    public void addPresentation(Presentation presentation) {
        presentations.add(presentation);
        presentation.setUser(this);
    }

    public void removePresentation(Presentation presentation) {
        presentations.remove(presentation);
        presentation.setUser(null);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setUser(this);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setUser(null);
    }
}
