package org.souvenirshop.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Souvenir implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private Manufacturer detailsOfTheManufacturer;
    private LocalDate releaseDate;
    private BigDecimal price;

    public Souvenir() {
    }

    public Souvenir(String name, Manufacturer detailsOfTheManufacturer, LocalDate releaseDate, BigDecimal price) {
        this.name = name;
        this.detailsOfTheManufacturer = detailsOfTheManufacturer;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getDetailsOfTheManufacturer() {
        return detailsOfTheManufacturer;
    }

    public void setDetailsOfTheManufacturer(Manufacturer detailsOfTheManufacturer) {
        this.detailsOfTheManufacturer = detailsOfTheManufacturer;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "name='" + name + '\'' +
                ", detailsOfTheManufacturer='" + detailsOfTheManufacturer + '\'' +
                ", releaseDate=" + releaseDate +
                ", price=" + price +
                '}';
    }
}
