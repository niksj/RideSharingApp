package com.example.rideSharingApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Location {
    Double x;
    Double y;

    public Double getDistance(Location l2){
        return Math.sqrt(Math.pow(l2.x-this.x,2) + Math.pow(l2.y-this.y,2));
    }
}
