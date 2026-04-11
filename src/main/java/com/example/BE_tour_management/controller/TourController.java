package com.example.BE_tour_management.controller;

import com.example.BE_tour_management.dto.ApiResponse;
import com.example.BE_tour_management.dto.request.TourCreateRequest;
import com.example.BE_tour_management.dto.request.TourUpdateRequest;
import com.example.BE_tour_management.dto.response.TourResponse;
import com.example.BE_tour_management.service.TourService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TourController {

    TourService tourService;

    @PostMapping
    public ApiResponse<TourResponse> createTour(@RequestBody TourCreateRequest request) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.createTour(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<TourResponse>> readTours() {
        return ApiResponse.<List<TourResponse>>builder()
                .result(tourService.readTours())
                .build();
    }

    @GetMapping("/{tourId}")
    public ApiResponse<TourResponse> readTour(@PathVariable("tourId") String id) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.readTour(id))
                .build();
    }

    @PutMapping("/{tourId}")
    public ApiResponse<TourResponse> updateTour(@PathVariable("tourId") String id, @RequestBody TourUpdateRequest request) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.updateTour(id, request))
                .build();
    }

    @DeleteMapping("/{tourId}")
    public ApiResponse<Void> deleteTour(@PathVariable("tourId") String id) {
        tourService.deleteTour(id);
        return ApiResponse.<Void>builder()
                .message("tour has been deleted")
                .build();
    }

}
