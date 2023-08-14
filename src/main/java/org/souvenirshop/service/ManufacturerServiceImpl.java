package org.souvenirshop.service;

import org.souvenirshop.dao.ManufacturerDao;
import org.souvenirshop.dao.SouvenirDao;
import org.souvenirshop.model.Manufacturer;
import org.souvenirshop.model.Souvenir;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerDao manufacturerDao = new ManufacturerDao();

    private final SouvenirDao souvenirDao = new SouvenirDao();

    private final SouvenirService souvenirService = new SouvenirServiceImpl();

    public void saveManufacturer(Manufacturer manufacturer) {
        if (isValidManufacturer(manufacturer) && isExistManufacturer(manufacturer)) {
            manufacturerDao.save(manufacturer);
        }
    }

    public void updateManufacturer(Manufacturer manufacturer) {
        if (isValidManufacturer(manufacturer)) {
            manufacturerDao.update(manufacturer);
        }
    }

    @Override
    public void removeManufacturerWithHisSouvenirs(String manufacturerName) {
        souvenirService.searchSouvenirsByManufacturer(manufacturerName).forEach(souvenirService::removeSouvenir);
        removeManufacturer(searchManufacturerByName(manufacturerName));
    }

    public void removeManufacturer(Manufacturer manufacturer) {
        if (isValidManufacturer(manufacturer)) {
            manufacturerDao.delete(manufacturer);
        }
    }

    private boolean isValidManufacturer(Manufacturer manufacturer) {
        boolean isValid = manufacturer.getName() != null && manufacturer.getCountry() != null;
        if (!isValid) {
            System.out.println("The manufacturer's data is not valid.");
            return false;
        }
        System.out.println("The manufacturer's data is valid.");
        return true;
    }

    private boolean isExistManufacturer(Manufacturer manufacturer) {
        for (Manufacturer tempManufacturer : manufacturerDao.getAll()) {
            if (tempManufacturer.getName().equals(manufacturer.getName())
                    && tempManufacturer.getCountry().equals(manufacturer.getCountry())) {
                System.out.println("The manufacturer's data exists");
                return false;
            }
        }
        return true;
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerDao.getAll();
    }

    public List<Manufacturer> searchManufacturers(String searchText) {
        return manufacturerDao.searchBy(searchText);
    }

    @Override
    public Manufacturer searchManufacturerByName(String manufacturerName) {
        return manufacturerDao.getAll().stream()
                .filter(manufacturer -> manufacturerName.equals(manufacturer.getName()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Manufacturer> searchBySouvenirAllManufacturersInGivenYear(String souvenirName, int souvenirYear) {
        List<Souvenir> souvenirs = souvenirDao.getSouvenirsByReleaseYear(souvenirYear);
        return souvenirs.stream()
                .map(Souvenir::getDetailsOfTheManufacturer).collect(Collectors.toList());
    }

    @Override
    public List<Manufacturer> searchByThresholdPrice(BigDecimal price) {
        List<Souvenir> souvenirs = souvenirDao.getSouvenirByThresholdPrice(price);
        return souvenirs.stream()
                .map(Souvenir::getDetailsOfTheManufacturer).collect(Collectors.toList());
    }

    @Override
    public Map<Manufacturer, List<Souvenir>> getAllManufacturersWithTheirSouvenirs() {
        return souvenirDao.getAll().stream()
                .collect(Collectors.groupingBy(Souvenir::getDetailsOfTheManufacturer));
    }
}