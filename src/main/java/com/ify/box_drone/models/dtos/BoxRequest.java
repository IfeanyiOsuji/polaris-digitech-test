package com.ify.box_drone.models.dtos;

import com.ify.box_drone.models.enums.State;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class BoxRequest {

    private double weight;
    private double batteryCapacity;
    @Enumerated(value = EnumType.STRING)
    private State state;

    public BoxRequest(double weight, double batteryCapacity) {
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
    }

    public BoxRequest() {
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
}
