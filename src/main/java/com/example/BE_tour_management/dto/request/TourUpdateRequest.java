package com.example.BE_tour_management.dto.request;

import com.example.BE_tour_management.entity.TourImage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourUpdateRequest {

    String description;
    Double price;
    String location;
    List<String> images;

}
