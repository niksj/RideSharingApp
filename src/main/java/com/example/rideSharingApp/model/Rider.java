package com.example.rideSharingApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Rider {
    @Id
    String id;
    String name;
    Double rating;

    public Rider(String id, String name){
        this.id = id;
        this.name = name;
    }
}
