package org.souvenirshop.view.impl;

import org.souvenirshop.model.Souvenir;
import org.souvenirshop.service.SouvenirService;
import org.souvenirshop.service.SouvenirServiceImpl;
import org.souvenirshop.view.Menu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SearchSouvenirMenu implements Menu {

    private final SouvenirService souvenirService = new SouvenirServiceImpl();
    private final String[] items = {"1. Search by name", "2. Search by manufacturer", "3. Search by country",
            "4. Search by release year", "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        showItems(items);

        //noinspection InfiniteLoopStatement
        while (true) {
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException ignored) {
                System.out.println("Incorrect input");
                scanner.nextLine();
                show();
            }
            switch (choice) {
                case 1 -> searchByName();
                case 2 -> searchByManufacturer();
                case 3 -> searchByCountry();
                case 4 -> searchByReleaseYear();

                case 0 -> exit();
            }
        }
    }
    private void searchByManufacturer() {
        System.out.println("Enter souvenir's manufacturer:");
        scanner.nextLine();
        String manufacturerName = scanner.nextLine().toUpperCase();
        List<Souvenir> souvenirs = souvenirService.searchSouvenirsByManufacturer(manufacturerName);
        if (!souvenirs.isEmpty()) {
            souvenirs.forEach(System.out::println);
        } else {
            System.out.println("There is no souvenirs with this manufacturer");
        }
        show();
    }

    private void searchByName() {
        System.out.println("Enter souvenir name:");
        scanner.nextLine();
        String souvenirName = scanner.nextLine();
        List<Souvenir> souvenirs = souvenirService.searchSouvenirs(souvenirName);
        if (!souvenirs.isEmpty()) {
            souvenirs.forEach(System.out::println);
        } else {
            System.out.println("There is no souvenir with this name");
        }
        show();
    }

    private void searchByCountry() {
        System.out.println("Enter souvenir country:");
        scanner.nextLine();
        String souvenirCountry = scanner.nextLine();
        List<Souvenir> souvenirs = souvenirService.searchSouvenirsByCountry(souvenirCountry);
        if (!souvenirs.isEmpty()) {
            souvenirs.forEach(System.out::println);
        } else {
            System.out.println("There is no souvenir from this country");
        }
        show();
    }

    private void searchByReleaseYear() {
        System.out.println("Enter souvenir release year:");
        int souvenirReleaseYear = scanner.nextInt();
        List<Souvenir> souvenirs = souvenirService.searchSouvenirsByReleaseYear(souvenirReleaseYear);
        if (!souvenirs.isEmpty()) {
            souvenirs.forEach(System.out::println);
        } else {
            System.out.println("There is no souvenir by this release year");
        }
        show();
    }

    @Override
    public void exit() {
        new SouvenirMenu().show();
    }
}