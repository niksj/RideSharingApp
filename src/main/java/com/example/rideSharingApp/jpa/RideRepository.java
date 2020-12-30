package com.example.rideSharingApp.jpa;

import com.example.rideSharingApp.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, String> {

    @Query(value = "", nativeQuery = true)
    Optional<List<Ride>> findAllRidesByRiderId(@Param("riderId") String riderId);

}
