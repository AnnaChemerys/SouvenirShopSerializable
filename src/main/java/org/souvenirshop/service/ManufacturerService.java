package org.souvenirshop.service;

import org.souvenirshop.model.Manufacturer;
import org.souvenirshop.model.Souvenir;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ManufacturerService {
    void saveManufacturer(Manufacturer manufacturer);

    void updateManufacturer(Manufacturer manufacturer);

    void removeManufacturerWithHisSouvenirs(String manufacturerName);

    List<Manufacturer> getAllManufacturers();

    List<Manufacturer> searchManufacturers(String searchText);

    Manufacturer searchManufacturerByName(String manufacturerName);

    List<Manufacturer> searchBySouvenirAllManufacturersInGivenYear(String souvenirName, int souvenirYear);

    List<Manufacturer> searchByThresholdPrice(BigDecimal price);

    Map<Manufacturer, List<Souvenir>> getAllManufacturersWithTheirSouvenirs();
}
