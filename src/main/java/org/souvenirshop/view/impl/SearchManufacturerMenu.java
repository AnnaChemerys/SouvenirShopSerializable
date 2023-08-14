package org.souvenirshop.view.impl;

import org.souvenirshop.model.Manufacturer;
import org.souvenirshop.service.ManufacturerService;
import org.souvenirshop.service.ManufacturerServiceImpl;
import org.souvenirshop.view.Menu;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SearchManufacturerMenu implements Menu {
    private final ManufacturerService manufacturerService = new ManufacturerServiceImpl();

    private final String[] items = {"1. Search by name",
            "2. Search manufacturers whose prices for souvenirs are less than the specified price",
            "3. Search by souvenir all manufacturers in a given year", "0. Exit"};
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
                case 2 -> searchByPrice();
                case 3 -> searchBySouvenirManufacturersInGivenYear();

                case 0 -> exit();
            }
        }
    }

    private void searchByName() {
        System.out.println("Enter manufacturer name:");
        scanner.nextLine();
        String manufacturerName = scanner.nextLine().toUpperCase();
        List<Manufacturer> manufacturers = manufacturerService.searchManufacturers(manufacturerName);
        if (!manufacturers.isEmpty()) {
            manufacturers.forEach(System.out::println);
        } else {
            System.out.println("There is no manufacturer with this name");
        }
        show();
    }

    private void searchByPrice() {
        System.out.println("Enter threshold price: (delim: \",\"): ");
        BigDecimal price = BigDecimal.valueOf(-1);
        try {
            price = scanner.nextBigDecimal();
        } catch (InputMismatchException ignored) {
        }
        List<Manufacturer> manufacturers =
                manufacturerService.searchByThresholdPrice(price);
        if (!manufacturers.isEmpty()) {
            manufacturers.forEach(System.out::println);
        } else {
            System.out.println("There is no manufacturer whose prices for souvenirs are less than the specified price");
        }
        show();
    }

    private void searchBySouvenirManufacturersInGivenYear() {
        System.out.println("Enter souvenir's  name:");
        scanner.nextLine();
        String souvenirName = scanner.nextLine();
        System.out.println("Enter souvenir's release year:");
        int souvenirYear = scanner.nextInt();
        scanner.nextLine();
        List<Manufacturer> manufacturers =
                manufacturerService.searchBySouvenirAllManufacturersInGivenYear(souvenirName, souvenirYear);
        if (!manufacturers.isEmpty()) {
            manufacturers.forEach(System.out::println);
        } else {
            System.out.println("There is no manufacturer with this year and souvenirs");
        }
        show();
    }

    @Override
    public void exit() {
        new SouvenirMenu().show();
    }
}
