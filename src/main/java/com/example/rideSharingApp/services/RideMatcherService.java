package com.example.rideSharingApp.services;

import com.example.rideSharingApp.model.Driver;
import com.example.rideSharingApp.model.Location;
import org.springframework.stereotype.Service;

@Service
public class RideMatcherService {
    public Driver getMatchingDriver(Location src, Location dest){
        Driver driver = new Driver();

        return driver;
    }
}
