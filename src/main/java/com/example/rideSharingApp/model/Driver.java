package com.example.rideSharingApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    String id;
    String driverName;
    Boolean isAvailable;
    Location currentLocation;
    Vehicle vehicle;
    Ride currentRide;
    Double rating;

    public Driver(String id, String driverName){
        this.id = id;
        this.driverName = driverName;
    }
}
