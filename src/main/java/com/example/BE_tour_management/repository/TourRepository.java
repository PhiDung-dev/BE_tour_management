package com.example.BE_tour_management.repository;

import com.example.BE_tour_management.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, String> {

    boolean existsByTitle(String title);

}
