package com.mayur.RestaurantNearbyFinder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restaurants")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id
    private String id;

    private String name;

    @GeoSpatialIndexed
    private GeoJsonPoint location;

    private double latitude;
    private double longitude;

    // Constructor with latitude and longitude
    public Restaurant(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = createGeoJsonPoint(latitude, longitude);
    }

    private GeoJsonPoint createGeoJsonPoint(double latitude, double longitude) {
        return new GeoJsonPoint(longitude, latitude);
    }
}
