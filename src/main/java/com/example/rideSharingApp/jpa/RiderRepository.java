package com.example.rideSharingApp.jpa;

import com.example.rideSharingApp.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {


}
