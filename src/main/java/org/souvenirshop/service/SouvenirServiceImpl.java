package org.souvenirshop.service;

import org.souvenirshop.dao.SouvenirDao;
import org.souvenirshop.model.Souvenir;

import java.util.List;

public class SouvenirServiceImpl implements SouvenirService {

    private final SouvenirDao souvenirDao = new SouvenirDao();

    public void saveSouvenir(Souvenir souvenir) {
        if (isValidSouvenir(souvenir) && isExistSouvenir(souvenir)) {
            souvenirDao.save(souvenir);
        }
    }

    public void updateSouvenir(Souvenir souvenir) {
        if (isValidSouvenir(souvenir)) {
            souvenirDao.update(souvenir);
        }
    }

    public void removeSouvenir(Souvenir souvenir) {
        if (isValidSouvenir(souvenir)) {
            souvenirDao.delete(souvenir);
        }
    }

    public Souvenir getSouvenirByData(String souvenirName, String manufacturerName){
        return souvenirDao.getAll().stream()
                .filter(souvenir -> souvenirName.equals(souvenir.getName()))
                .filter(souvenir -> manufacturerName.equals(souvenir.getDetailsOfTheManufacturer().getName()))
                .findAny()
                .orElse(null);
    }

    private boolean isValidSouvenir(Souvenir souvenir) {
        boolean isValid = souvenir.getName() != null && souvenir.getPrice() != null
                && souvenir.getReleaseDate() != null && souvenir.getDetailsOfTheManufacturer() != null;
        if (!isValid) {
            System.out.println("The souvenir's data is not valid.");
            return false;
        }
        System.out.println("The souvenir's data is valid.");
        return true;
    }

    private boolean isExistSouvenir(Souvenir souvenir) {
        for (Souvenir tempSouvenir : souvenirDao.getAll()) {
            if (tempSouvenir.getName().equals(souvenir.getName())
                    && tempSouvenir.getDetailsOfTheManufacturer().equals(souvenir.getDetailsOfTheManufacturer())) {
                System.out.println("The souvenir's data exists");
                return false;
            }
        }
        return true;
    }

    public List<Souvenir> getAllSouvenirs() {
        return souvenirDao.getAll();
    }

    public List<Souvenir> searchSouvenirs(String searchText) {
        return souvenirDao.searchBy(searchText);
    }

    public List<Souvenir> searchSouvenirsByManufacturer(String manufacturerName) {
        return souvenirDao.getByManufacturer(manufacturerName);
    }

    @Override
    public List<Souvenir> searchSouvenirsByCountry(String souvenirCountry) {
        return souvenirDao.geSouvenirsByCountry(souvenirCountry);
    }

    @Override
    public List<Souvenir> searchSouvenirsByReleaseYear(int souvenirReleaseYear) {
        return souvenirDao.getSouvenirsByReleaseYear(souvenirReleaseYear);
    }
}