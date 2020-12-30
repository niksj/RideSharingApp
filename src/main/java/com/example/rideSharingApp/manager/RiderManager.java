package com.example.rideSharingApp.manager;

import com.example.rideSharingApp.jpa.RiderRepository;
import com.example.rideSharingApp.model.Rider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class RiderManager {
    @Autowired
    RiderRepository riderRepository;
    public void createRider(Rider rider) {
        if (riderRepository.exists(Example.of(rider))) {
            throw new RuntimeException("Exception occurred, Rider Already Exists.");
        }

        riderRepository.save(rider);
    }

    public Rider getRider(String riderId) {
        if (!riderRepository.existsById(riderId)){
            throw new RuntimeException("Exception occurred, Rider Not Found.");
        }

        return riderRepository.findById(riderId).orElse(null);
    }
}
