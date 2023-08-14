package org.souvenirshop.model;

import java.io.Serial;
import java.io.Serializable;

public class Manufacturer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "name=\"" + name + '\"' +
                ", country=\"" + country + '\"' +
                '}';
    }
}