package com.ify.box_drone.models.entities;

import com.ify.box_drone.models.enums.State;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;
import java.util.UUID;

@Entity
public class Box {
    @Id
    private String id;
    @Column(length = 20)
    private String txref;

    @Column(name = "weightInGrams")
    private double weight;

    private double batteryCapacity;
    @Enumerated(value = EnumType.STRING)
    private State state;

    private List<Item> loadedItems;

    public Box(int weight, double batteryCapacity) {
        this.id = UUID.randomUUID().toString().substring(25);
        this.state = State.IDLE;
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
    }

    public String getTxref() {
        return txref;
    }

    public void setTxref(String txref) {
        this.txref = txref;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Item> getLoadedItems() {
        return loadedItems;
    }

    public void setLoadedItems(List<Item> loadedItems) {
        this.loadedItems = loadedItems;
    }
}

/*
* - txref (20 characters max);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).
*
*
*
*
*
*
* */
