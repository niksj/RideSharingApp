package com.example.rideSharingApp.manager;

import com.example.rideSharingApp.jpa.DriverRepository;
import com.example.rideSharingApp.jpa.RideRepository;
import com.example.rideSharingApp.model.Driver;
import com.example.rideSharingApp.model.Location;
import com.example.rideSharingApp.model.Ride;
import com.example.rideSharingApp.model.Rider;
import com.example.rideSharingApp.model.enums.RideStatus;
import com.example.rideSharingApp.model.enums.RideType;
import com.example.rideSharingApp.services.PricingModelService;
import com.example.rideSharingApp.services.RideMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideManager {
    @Autowired
    RideRepository rideRepository;
    @Autowired
    RideMatcherService rideMatcherService;
    @Autowired
    PricingModelService pricingModelService;
    @Autowired
    DriverRepository driverRepository;

    public Ride getRide(String rideId) {
        if (!rideRepository.existsById(rideId)){
            throw new RuntimeException("Exception occurred, Ride Not Found.");
        }

        return rideRepository.findById(rideId).orElse(null);
    }

    public void createRide(Rider rider, RideType rideType, Location srcLocation, Location destLocation, String couponCode){
        Ride ride = new Ride();
        ride.setRider(rider);
        ride.setDropoffLocation(destLocation);
        ride.setPickupLocation(srcLocation);
        ride.setRideType(rideType);
        Driver matchedDriver = rideMatcherService.getMatchingDriver(srcLocation, destLocation);

        if(rideRepository.exists(Example.of(ride))){
            throw new RuntimeException("Exception occurred, Ride Already Exists.");
        }

        ride.setRideStatus(RideStatus.BOOKED);
        rideRepository.save(ride);
    }

    public List<Ride> getRiderHistory(String riderId){
        return rideRepository.findAllRidesByRiderId(riderId).orElse(null);
    }

    public Boolean cancelRide(Boolean forceCancel, String rideId){

        if(isFreeCancellationEligible(rideId) || forceCancel){
            Ride ride = rideRepository.findById(rideId).orElse(null);
            ride.setRideStatus(RideStatus.CANCELLED);
            rideRepository.save(ride);
            return true;
        }
        return false;
    }

    private Boolean isFreeCancellationEligible(String rideId){
        Ride ride = rideRepository.findById(rideId).orElse(null);
        if(ride.getRideStatus()==RideStatus.BOOKED){
            return true;
        }
        return false;
    }

    public Ride addIntermediateStopsToRide(String rideId, Location location){
        if(!rideRepository.existsById(rideId)){
            throw new RuntimeException("Exception occurred, ride Does not exist.");
        }
        Ride currentRide = rideRepository.findById(rideId).orElse(null);
        if(currentRide.getRideStatus()==RideStatus.IN_PROGRESS || currentRide.getRideStatus()==RideStatus.BOOKED ){
            if(currentRide.getIntermediateStops()==null){
                currentRide.setIntermediateStops(new ArrayList<>());
            }
            currentRide.getIntermediateStops().add(location);
            return currentRide;
        }else{
            throw new RuntimeException("Exception occurred, Invalid update request, Ride is either finished or cancelled.");
        }
    }

    public Ride updateDestination(String rideId, Location newDestination){
        if(!rideRepository.existsById(rideId)){
            throw new RuntimeException("Exception occurred, ride Does not exist.");
        }
        Ride currentRide = rideRepository.findById(rideId).orElse(null);
        if(currentRide.getRideStatus()==RideStatus.IN_PROGRESS || currentRide.getRideStatus()==RideStatus.BOOKED ){
            currentRide.setDropoffLocation(newDestination);
            return currentRide;
        }else{
            throw new RuntimeException("Exception occurred, Invalid update request, Ride is either finished or cancelled.");
        }
    }

    public Ride startRide(String rideId, String driverId){
        if(!rideRepository.existsById(rideId)){
            throw new RuntimeException("Exception occurred, ride Does not exist.");
        }
        Ride ride = rideRepository.findById(rideId).orElse(null);
        if(!ride.getDriver().getId().equals(driverId)){
            throw new RuntimeException("Exception occurred, Driver and ride assigned driver Mismatched.");
        }else{
            ride.setRideStatus(RideStatus.IN_PROGRESS);
            long start = System.currentTimeMillis();
            ride.setRideEndTime(start);
            rideRepository.save(ride);
        }
        return ride;
    }

    public Ride endRide(String rideId, String driverId){
        if(!rideRepository.existsById(rideId)){
            throw new RuntimeException("Exception occurred, ride Does not exist.");
        }
        Ride ride = rideRepository.findById(rideId).orElse(null);
        if(!ride.getDriver().getId().equals(driverId)){
            throw new RuntimeException("Exception occurred, Driver and ride assigned driver Mismatched.");
        }else{
            ride.setRideStatus(RideStatus.FINISHED);
            long end = System.currentTimeMillis();
            ride.setRideEndTime(end);
            long duration  = ride.getRideEndTime() - ride.getRideStartTime();
            Double price = pricingModelService.getPrice(ride.getRider(), ride.getPickupLocation(), ride.getDropoffLocation(), duration, "");
            ride.setPrice(price);
            rideRepository.save(ride);

            //update driver's details
            Driver driver = ride.getDriver();
            driver.setIsAvailable(true);
            driver.setCurrentLocation(ride.getDropoffLocation());
            driverRepository.save(driver);
        }

        return ride;
    }
}
