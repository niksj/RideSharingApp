package com.example.rideSharingApp.model;

import com.example.rideSharingApp.model.enums.RideStatus;
import com.example.rideSharingApp.model.enums.RideType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Ride {

    @Id
    String rideId;
    Location pickupLocation;
    Location dropoffLocation;
    List<Location> intermediateStops;
    Driver driver;
    Rider rider;
    RideStatus rideStatus;
    Double price;
    RideType rideType;
    long rideStartTime;
    long rideEndTime;

}
