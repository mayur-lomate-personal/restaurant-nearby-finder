package com.mayur.RestaurantNearbyFinder.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantResponse {

    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double distance; // Distance in meters (optional, used for nearby search results)

    public RestaurantResponse(String id, String name, double latitude, double longitude, double distance) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
