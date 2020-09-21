package dev.serhats.hoodie.dto;

import lombok.Data;

@Data
public class Geolocation {
    private double latitude;
    private double longitude;

    public Geolocation(double latitude,double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
