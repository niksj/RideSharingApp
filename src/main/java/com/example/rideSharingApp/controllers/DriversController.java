package com.example.rideSharingApp.controllers;

import com.example.rideSharingApp.manager.DriverManager;
import com.example.rideSharingApp.manager.RideManager;
import com.example.rideSharingApp.model.Driver;
import com.example.rideSharingApp.model.Location;
import com.example.rideSharingApp.model.Ride;
import com.example.rideSharingApp.model.enums.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/driver")
public class DriversController {
    @Autowired
    DriverManager driverManager;
    @Autowired
    RideManager rideManager;

    @RequestMapping(value = "/add/details", method = RequestMethod.POST)
    public ResponseEntity addDriverDetails(@RequestBody Driver driver){
        driverManager.addDriverDetails(driver);
        return ResponseEntity.ok("");
    }
    /**
     * Driver starts the ride after matching the shared otp (RideId in our case)
     */
    @RequestMapping(value = "/ride/start", method = RequestMethod.PATCH)
    public ResponseEntity startRide(String rideId, String driverId) {
        rideManager.startRide(rideId, driverId);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/ride/end", method = RequestMethod.PATCH)
    public ResponseEntity endRide(String rideId, String driverId) {
        rideManager.endRide(rideId, driverId);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/location", method = RequestMethod.PUT)
    public ResponseEntity updateDriverLocation(String driverId, Double updatedX, Double updatedY) {
        driverManager.updateLocation(driverId, new Location(updatedX, updatedY));
        return ResponseEntity.ok("");
    }
    /**
     * Temporary Block/Unblock bookings / Driver's availability preference updating.
     * */
    @RequestMapping(value = "/block/bookings", method = RequestMethod.PATCH)
    public ResponseEntity updateDriverPreference(String driverId, Preference preference){
        Boolean availability = preference == Preference.BLOCK ? false : true;
        driverManager.updatePreference(driverId, availability);
        return ResponseEntity.ok("");
    }

    //accept request
    @RequestMapping(value = "/accept/request", method = RequestMethod.PATCH)
    public ResponseEntity acceptRequest(String driverId, String rideId) throws InterruptedException{
//        if(rideManager.getRide(rideId).getDriver()==null){
//            Ride ride = driverManager.acceptRequest(driverId, rideId);
//            return ResponseEntity.ok(ride);
//        }
        return keepPolling(driverId, rideId);
    }

    private ResponseEntity<Ride> keepPolling(String driverId, String rideId) throws InterruptedException {
        Thread.sleep(5000);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/accept/request?driverId=" + driverId + "&rideId=" + rideId));
        return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
    }
}
