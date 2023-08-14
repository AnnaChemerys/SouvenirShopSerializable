package org.souvenirshop.dao;

import org.souvenirshop.model.Manufacturer;
import org.souvenirshop.model.Souvenir;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class SouvenirDao extends AbstractDao<Souvenir> {

    @Override
    protected String getFileName() {
        return FileNames.SOUVENIRS.getFileName();
    }

    @Override
    public void update(Souvenir souvenir) {
        List<Souvenir> tempList = items;
        for (Souvenir tempSouvenir : tempList) {
            if (tempSouvenir.getName().equals(souvenir.getName())) {
                tempSouvenir.setName(souvenir.getName());
                tempSouvenir.setPrice(souvenir.getPrice());
                tempSouvenir.setReleaseDate(souvenir.getReleaseDate());
                tempSouvenir.setDetailsOfTheManufacturer(souvenir.getDetailsOfTheManufacturer());
            }
        }
        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }


    public List<Souvenir> searchBy(String text) {
        return items.stream()
                .filter(item -> item.getName().contains(text))
                .collect(Collectors.toList());
    }


    public List<Souvenir> getByManufacturer(String manufacturerName) {
        return items.stream()
                .filter(item -> item.getDetailsOfTheManufacturer().getName().equals(manufacturerName))
                .collect(Collectors.toList());
    }

    public List<Souvenir> geSouvenirsByCountry(String souvenirCountry) {
        return items.stream()
                .filter(item -> item.getDetailsOfTheManufacturer().getCountry().equals(souvenirCountry))
                .collect(Collectors.toList());
    }

    public List<Souvenir> getSouvenirsByReleaseYear(int souvenirReleaseYear) {
        return items.stream()
                .filter(item -> item.getReleaseDate().getYear() == souvenirReleaseYear)
                .collect(Collectors.toList());
    }

    public List<Souvenir> getSouvenirByThresholdPrice(BigDecimal price) {
        return items.stream()
                .filter(item -> item.getPrice().compareTo(price) < 0)
                .collect(Collectors.toList());
    }
}
