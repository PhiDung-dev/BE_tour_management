package com.example.BE_tour_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String title;
    String description;
    Double price;
    String location;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TourImage> images;

    @OneToMany(mappedBy = "tour")
    List<Schedule> schedules;

    @OneToMany(mappedBy = "tour")
    List<Rating> ratings;

    @OneToMany(mappedBy = "tour")
    List<FavoriteTour> favoriteTours;

}
