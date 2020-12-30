package com.example.rideSharingApp.controllers;

import com.example.rideSharingApp.manager.DriverManager;
import com.example.rideSharingApp.manager.RideManager;
import com.example.rideSharingApp.manager.RiderManager;
import com.example.rideSharingApp.model.Rider;
import com.example.rideSharingApp.model.Driver;
import com.example.rideSharingApp.model.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @Autowired
    RiderManager riderManager;
    @Autowired
    RideManager rideManager;
    @Autowired
    DriverManager driverManager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity check(){
        return ResponseEntity.ok("Welcome to Ride Sharing Application!");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(UserType userType, String id, String name){
        if(userType == UserType.DRIVER){
            return registerDriver(id, name);
        }else if(userType == UserType.RIDER){
            return registerRider(id, name);
        }else{
            //throw new RuntimeException("Exception occurred, Invalid UserType, Registration failed.");
            return ResponseEntity.badRequest()
                    .body("Invalid UserType, Registration failed.");
        }
    }
    public ResponseEntity registerDriver(String id, String driverName){
        driverManager.createDriver(new Driver(id, driverName));
        return ResponseEntity.ok("Driver Registration Successful!");
    }

    public ResponseEntity registerRider(final String riderId, final String riderName) {
        riderManager.createRider(new Rider(riderId, riderName));
        return ResponseEntity.ok("Rider Registration successful");
    }
}
