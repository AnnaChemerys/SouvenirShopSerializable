package org.souvenirshop.dao;

import org.souvenirshop.model.Manufacturer;

import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerDao extends AbstractDao<Manufacturer> {
    @Override
    protected String getFileName() {
        return FileNames.MANUFACTURERS.getFileName();
    }

    @Override
    public void update(Manufacturer manufacturer) {
        List<Manufacturer> tempList = items;
        for (Manufacturer tempManufacturer : tempList) {
            if (tempManufacturer.getName().equals(manufacturer.getName())) {
                tempManufacturer.setName(manufacturer.getName());
                tempManufacturer.setCountry(manufacturer.getCountry());
            }
        }
        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }


    public List<Manufacturer> searchBy(String text) {
        return items.stream()
                .filter(item -> item.getName().contains(text))
                .collect(Collectors.toList());
    }
}