package com.example.BE_tour_management.service;

import com.example.BE_tour_management.dto.request.TourCreateRequest;
import com.example.BE_tour_management.dto.request.TourUpdateRequest;
import com.example.BE_tour_management.dto.response.TourResponse;
import com.example.BE_tour_management.entity.Tour;
import com.example.BE_tour_management.entity.TourImage;
import com.example.BE_tour_management.exception.AppException;
import com.example.BE_tour_management.exception.ErrorCode;
import com.example.BE_tour_management.mapper.TourMapper;
import com.example.BE_tour_management.repository.TourRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TourService {

    TourRepository tourRepository;
    TourMapper tourMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public TourResponse createTour(TourCreateRequest request) {
        String title = request.getTitle().trim();
        if(tourRepository.existsByTitle(title)) {
            throw new AppException(ErrorCode.TOUR_EXISTED);
        }
        Tour tour = tourMapper.toTour(request);
        List<TourImage> images = request.getImages().stream()
                .map(img->{
                    return TourImage.builder()
                            .url(img)
                            .tour(tour)
                            .build();
        }).toList();
        tour.setImages(images);
        return tourMapper.toTourResponse(tourRepository.save(tour));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TourResponse> readTours() {
        return tourMapper.toTourResponseList(tourRepository.findAll());
    }

    public TourResponse readTour(String id) {
        Tour tour = tourRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.TOUR_NOT_FOUND));
        return tourMapper.toTourResponse(tour);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TourResponse updateTour(String id, TourUpdateRequest request) {
        Tour tour = tourRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.TOUR_NOT_FOUND));
        tourMapper.updateTour(tour, request);
        tour.getImages().clear();
        request.getImages().forEach(
                img->tour.getImages().add(TourImage.builder()
                        .url(img)
                        .tour(tour)
                        .build())
                );
        return tourMapper.toTourResponse(tourRepository.save(tour));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTour(String id) {
        if(!tourRepository.existsById(id)) {
            throw new AppException(ErrorCode.TOUR_NOT_FOUND);
        }
        tourRepository.deleteById(id);
    }

}