package com.mayur.RestaurantNearbyFinder.service;

import com.mayur.RestaurantNearbyFinder.model.Restaurant;
import com.mayur.RestaurantNearbyFinder.reponse.RestaurantResponse;
import com.mayur.RestaurantNearbyFinder.repository.RestaurantRepository;
import com.mayur.RestaurantNearbyFinder.request.RestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MongoTemplate mongoTemplate) {
        this.restaurantRepository = restaurantRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // Add Restaurant
    public Restaurant saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant(restaurantRequest.getName(), restaurantRequest.getLatitude(), restaurantRequest.getLongitude());
        return restaurantRepository.save(restaurant);
    }

    // Method to find nearby restaurants
    public Page<RestaurantResponse> findNearbyRestaurants(double latitude, double longitude, double maxDistanceInMeters, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Restaurant> nearbyRestaurants = restaurantRepository.findNearbyRestaurants(longitude, latitude, maxDistanceInMeters, pageable);

        return nearbyRestaurants.map(restaurant -> {
            double distance = calculateDistance(latitude, longitude, restaurant.getLatitude(), restaurant.getLongitude());
            return mapToResponse(restaurant, distance);
        });
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Earth's radius in meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Map Restaurant entity to RestaurantResponse
    private RestaurantResponse mapToResponse(Restaurant restaurant, double distance) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                distance
        );
    }
}
