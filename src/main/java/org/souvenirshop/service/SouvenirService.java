package org.souvenirshop.service;

import org.souvenirshop.model.Souvenir;

import java.util.List;

public interface SouvenirService {
    void saveSouvenir(Souvenir souvenir);

    void updateSouvenir(Souvenir souvenir);

    void removeSouvenir(Souvenir souvenir);

    Souvenir getSouvenirByData(String souvenirName, String manufacturerName);

    List<Souvenir> getAllSouvenirs();

    List<Souvenir> searchSouvenirs(String searchText);

    List<Souvenir> searchSouvenirsByManufacturer(String manufacturerName);

    List<Souvenir> searchSouvenirsByCountry(String souvenirCountry);

    List<Souvenir> searchSouvenirsByReleaseYear(int souvenirReleaseYear);
}

