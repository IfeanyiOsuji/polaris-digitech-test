package com.ify.box_drone.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.naming.NamingException;
import java.util.UUID;

@Entity
public class Item {
    @Id
    private String id;
    private String name;
    private String code;
    private int weight;

    public Item(String name, String code, int weight) {
        this.id = UUID.randomUUID().toString().substring(25);
        this.name = name;
        this.code = code;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NamingException {
        if (!name.matches("[a-zA-Z0-9_-]*$"))
            throw new NamingException("Name can only contain alphabets, numbers, _ and -");
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws NamingException {
        if(!code.matches("[A-Z0-9_]*"))
            throw new NamingException("Code can only contain Uppercase letters, Numbers, _ and -");
        this.code = code;
    }

    public int getWeight() {
        return weight;
    }
}


