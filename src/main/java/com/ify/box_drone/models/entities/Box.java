package com.ify.box_drone.models.entities;

import com.ify.box_drone.models.enums.State;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.*;


@Entity
public class Box {
    @Id
    private String txref;

    @Column(name = "weightInGrams")
    private double weight;

    private double batteryCapacity;
    @Enumerated(value = EnumType.STRING)
    private State state;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> loadedItems = new ArrayList<>();

    public Box(double weight, double batteryCapacity) {
        this.txref = generateShortUUID();
        this.state = State.IDLE;
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
    }

    public Box() {
        this.txref = generateShortUUID();
    }

    public String getTxref() {
        return txref;
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

    private String generateShortUUID() {
        return  UUID.randomUUID().toString().substring((int)Math.round(21.0));

    }

    @Override
    public String toString() {
        return "Box{" +
                "txref='" + txref + '\'' +
                ", weight=" + weight +
                ", batteryCapacity=" + batteryCapacity +
                ", state=" + state +
                ", loadedItems=" + loadedItems +
                '}';
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
