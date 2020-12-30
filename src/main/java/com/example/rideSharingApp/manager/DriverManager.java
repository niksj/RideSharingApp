package com.example.rideSharingApp.manager;

import com.example.rideSharingApp.jpa.DriverRepository;
import com.example.rideSharingApp.model.Driver;
import com.example.rideSharingApp.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


@Service
public class DriverManager {
    @Autowired
    DriverRepository driverRepository;

    public void createDriver(Driver driver){
        if(driverRepository.exists(Example.of(driver))){
            throw new RuntimeException("Exception occurred, Driver Already Exists.");
        }
        driver.setIsAvailable(false);
        driverRepository.save(driver);
    }

    public void addDriverDetails(Driver driver){

        if(driverRepository.existsById(driver.getId())){
            throw new RuntimeException("Exception occurred, Driver Already Exists.");
        }

        if(driver.getVehicle()!=null && driver.getCurrentLocation()!=null && driver.getCurrentRide()==null){
            driver.setIsAvailable(true);
        }
        driverRepository.save(driver);
    }

    public Driver getDriver(String driverId) {
        if (!driverRepository.existsById(driverId)){
            throw new RuntimeException("Exception occurred, Driver Not Found.");
        }

        return driverRepository.findById(driverId).orElse(null);
    }

    public void updatePreference(String driverId, Boolean preference){
        if (!driverRepository.existsById(driverId)){
            throw new RuntimeException("Exception occurred, Driver Not Found.");
        }
        Driver driver = driverRepository.findById(driverId).orElse(null);
        driver.setIsAvailable(preference);
        driverRepository.save(driver);
    }

    public void updateLocation(String driverId, Location updatedLocation){
        if (!driverRepository.existsById(driverId)){
            throw new RuntimeException("Exception occurred, Driver Not Found.");
        }
        Driver driver = driverRepository.findById(driverId).orElse(null);
        driver.setCurrentLocation(updatedLocation);
        driverRepository.save(driver);
    }



}
