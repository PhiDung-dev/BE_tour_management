package com.example.BE_tour_management.mapper;

import com.example.BE_tour_management.dto.request.TourCreateRequest;
import com.example.BE_tour_management.dto.request.TourUpdateRequest;
import com.example.BE_tour_management.dto.response.TourResponse;
import com.example.BE_tour_management.entity.Tour;
import com.example.BE_tour_management.entity.TourImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourMapper {

    @Mapping(target = "images", ignore = true)
    Tour toTour(TourCreateRequest request);

    TourResponse toTourResponse(Tour tour);

    List<TourResponse> toTourResponseList(List<Tour> tours);

    @Mapping(target = "images", ignore = true)
    void updateTour(@MappingTarget Tour tour, TourUpdateRequest request);

    default String map(TourImage tourImage) {
        return tourImage.getUrl();
    }

}
