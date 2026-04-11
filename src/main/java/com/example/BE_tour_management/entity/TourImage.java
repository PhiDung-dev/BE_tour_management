package com.example.BE_tour_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String url;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;

}
