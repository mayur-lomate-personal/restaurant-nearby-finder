package com.mayur.RestaurantNearbyFinder.controller;

import com.mayur.RestaurantNearbyFinder.model.Restaurant;
import com.mayur.RestaurantNearbyFinder.reponse.RestaurantResponse;
import com.mayur.RestaurantNearbyFinder.request.RestaurantRequest;
import com.mayur.RestaurantNearbyFinder.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Add Restaurant
    @PostMapping
    public Restaurant addRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    // Get Restaurants within a radius and sorted by distance
    @GetMapping("/nearby")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurantsNearby(@RequestParam double latitude,
                                                                         @RequestParam double longitude,
                                                                         @RequestParam double radius,
                                                                         @RequestParam(defaultValue = "0") int page,  // Default to page 0
                                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(restaurantService.findNearbyRestaurants(latitude, longitude, radius, page, size));
    }
}
