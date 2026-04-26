package com.example.BE_tour_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    LocalDate startDate;
    LocalDate endDate;
    int slot;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    Tour tour;

    @OneToMany(mappedBy = "schedule")
    List<Booking> bookings;

}
