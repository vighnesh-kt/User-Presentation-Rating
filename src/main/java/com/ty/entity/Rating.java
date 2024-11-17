package com.ty.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    private Integer communication;
    private Integer confidence;
    private Integer content;
    private Integer interaction;
    private Integer liveIdeas; // Added column 'live_ideas'
    private Integer usagePros; // Added column 'usage_pros'
    private Integer totalScore; // Added column 'total_score'

    // Bidirectional mapping to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Bidirectional mapping to Presentation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_pid", nullable = false)
    private Presentation presentation;
}
