package com.example.rideSharingApp.controllers;

import com.example.rideSharingApp.manager.RideManager;
import com.example.rideSharingApp.manager.RiderManager;
import com.example.rideSharingApp.model.Location;
import com.example.rideSharingApp.model.Ride;
import com.example.rideSharingApp.model.enums.RideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rider")
public class RidersController {
    @Autowired
    RiderManager riderManager;
    @Autowired
    RideManager rideManager;

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity getHistory(String riderId){
        List<Ride> rides = rideManager.getRiderHistory(riderId);
        return ResponseEntity.ok(rides);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity bookRide(String riderId, RideType rideType, Double srcX, Double srcY,
                                   Double destX, Double destY, String couponCode) {

        rideManager.createRide(
                riderManager.getRider(riderId),
                rideType,
                new Location(srcX, srcY),
                new Location(destX, destY),
                couponCode);

        return ResponseEntity.ok("");
    }

    /**
     * User can cancel ride without penalty if ride is yet to begin otherwise some penalty charges will be applied.
     * [App UI will have confirmation popup to check with user if they still want to cancel, same api can be called with forceCancel=true]
     * @param rideId
     * @param forceCancel
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.PATCH)
    public ResponseEntity cancelBooking(String rideId, Boolean forceCancel){

        Boolean response = rideManager.cancelRide(forceCancel, rideId);

        if(!response){
            return ResponseEntity.ok("Cancellation failed");
        }else{
            return ResponseEntity.ok("Ride cancelled successfully.");
        }
    }

    @RequestMapping(value = "/destination", method = RequestMethod.PUT)
    public ResponseEntity changeDestination(String rideId, Double destX, Double destY){
        rideManager.updateDestination(rideId, new Location(destX, destY));
        return ResponseEntity.ok("Destination updated successfully");
    }

    @RequestMapping(value = "/add/intermediatestops", method = RequestMethod.PUT)
    public ResponseEntity addIntermediateStops(String rideId, Double destX, Double destY){
        rideManager.addIntermediateStopsToRide(rideId, new Location(destX, destY));
        return ResponseEntity.ok("Intermediate stop added successfully");
    }

}
