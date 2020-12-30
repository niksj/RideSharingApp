package com.example.rideSharingApp.services;

import com.example.rideSharingApp.model.Location;
import com.example.rideSharingApp.model.Rider;
import org.springframework.stereotype.Service;

@Service
public class PricingModelService {
    public Double getPrice(Rider rider, Location src, Location dest, long duration, String couponCode){
        Double price;
        //String city = getCity(src, dest);
        //Double rate = getRate(city);

        Double distance = src.getDistance(dest);

        Double defaultRate = 10.0;
        price = defaultRate * distance;
        //couponcode logic...
        //price = getDiscountedPrice(price);
        return price;
    }

    public Double getEstimatedPrice(Rider rider, Location src, Location dest, String couponCode){
        Double price;
        //String city = getCity(src, dest);
        //Double rate = getRate(city);

        Double distance = src.getDistance(dest);

        Double defaultRate = 10.0;
        price = defaultRate * distance;
        //couponcode logic...
        //price = getDiscountedPrice(price);
        return price;
    }


}
