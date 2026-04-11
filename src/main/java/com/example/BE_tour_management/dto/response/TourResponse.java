package com.example.BE_tour_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourResponse {

    String id;
    String title;
    String description;
    Double price;
    String location;
    List<String> images;

}
