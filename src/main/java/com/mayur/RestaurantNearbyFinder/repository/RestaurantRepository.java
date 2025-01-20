package com.mayur.RestaurantNearbyFinder.repository;

import com.mayur.RestaurantNearbyFinder.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    @Query("{'location': { $near: { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: ?2 } } }")
    Page<Restaurant> findNearbyRestaurants(double longitude, double latitude, double maxDistanceInMeters, Pageable pageable);
}