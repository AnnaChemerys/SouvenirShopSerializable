package org.souvenirshop.view.impl;

import org.souvenirshop.model.Manufacturer;
import org.souvenirshop.model.Souvenir;
import org.souvenirshop.model.User;
import org.souvenirshop.service.ManufacturerService;
import org.souvenirshop.service.ManufacturerServiceImpl;
import org.souvenirshop.service.SouvenirService;
import org.souvenirshop.service.SouvenirServiceImpl;
import org.souvenirshop.util.CurrentUser;
import org.souvenirshop.view.Menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SouvenirMenu implements Menu {

    private final ManufacturerService manufacturerService = new ManufacturerServiceImpl();
    private final SouvenirService souvenirService = new SouvenirServiceImpl();
    private final User.UserRole currentRole = CurrentUser.user.getRole();

    private final String[] items = currentRole == User.UserRole.USER
            ? new String[]{"1. Souvenir list", "2. Search souvenir", "0. Exit"}
            : new String[]{"1. Souvenir list", "2. Edit souvenir", "3. Add souvenir", "0. Exit"};

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
            switch (currentRole) {
                case USER -> {
                    switch (choice) {
                        case 1 -> souvenirList();
                        case 2 -> new SearchSouvenirMenu().show();
                        case 0 -> exit();
                    }
                }
                case ADMIN -> {
                    switch (choice) {
                        case 1 -> souvenirList();
                        case 2 -> editSouvenir();
                        case 3 -> addSouvenir();
                        case 0 -> exit();
                    }
                }
            }
        }
    }

    private void addSouvenir() {
        System.out.println("Enter souvenir name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.println("Enter souvenir price (delim: \",\"): ");

        BigDecimal price = BigDecimal.valueOf(-1);
        try {
            price = scanner.nextBigDecimal();
        } catch (InputMismatchException ignored) {
        }

        System.out.println("Enter souvenir's release date: (delim: \"-\")" +
                "(in format YYYY-MM-DD)");

        LocalDate releaseDate = null;
        try {
            releaseDate = LocalDate.parse(scanner.next());
        } catch (InputMismatchException ignored) {
        }

        System.out.println("Souvenir's manufacturer: ");
        for (int i = 0; i < manufacturerService.getAllManufacturers().size(); i++) {
            System.out.println((i + 1) +
                    " " + manufacturerService.getAllManufacturers().get(i).getName().toUpperCase());
        }

        System.out.println("Enter souvenir's manufacturer: ");
        //scanner.nextLine();
        Manufacturer manufacturer = null;

        try {
            int i = scanner.nextInt();
            if (i <= manufacturerService.getAllManufacturers().size() && i > 0) {
                manufacturer = manufacturerService.searchManufacturerByName(manufacturerService.getAllManufacturers().get(i - 1).getName());
            }
        } catch (IllegalArgumentException e) {
        }
        Souvenir souvenir = new Souvenir(name, manufacturer, releaseDate, price);

        souvenirService.saveSouvenir(souvenir);

        show();
    }

    private void editSouvenir() {
        System.out.println("Enter souvenir name: ");
        scanner.nextLine();
        String souvenirName = scanner.nextLine();

        System.out.println("Enter souvenir's manufacturer: ");
        //scanner.nextLine();
        String souvenirManufacturer = scanner.nextLine().toUpperCase();

        Souvenir souvenirToAdd = souvenirService.getSouvenirByData(souvenirName, souvenirManufacturer);

        if (souvenirToAdd != null) {

            System.out.println("Enter souvenir name: ");
            scanner.nextLine();
            String name = scanner.nextLine();

            System.out.println("Enter souvenir price (delim: \",\"): ");
            BigDecimal price = BigDecimal.valueOf(-1);
            try {
                price = scanner.nextBigDecimal();
            } catch (InputMismatchException ignored) {
                ignored.printStackTrace();
            }

            System.out.println("Enter souvenir's release date: (delim: \"-\")" +
                    "(in format YYYY-MM-DD)");

            LocalDate releaseDate = null;
            try {
                releaseDate = LocalDate.parse(scanner.next());
            } catch (InputMismatchException ignored) {
                System.out.println("Incorrect value");
            }

            System.out.println("Souvenir's manufacturer: ");
            for (int i = 0; i < manufacturerService.getAllManufacturers().size(); i++) {
                System.out.println((i + 1) +
                        " " + manufacturerService.getAllManufacturers().get(i).getName().toUpperCase());
            }

            System.out.println("Enter souvenir's manufacturer: ");
            scanner.nextLine();
            Manufacturer manufacturer = null;

            try {
                int i = scanner.nextInt();
                if (i <= manufacturerService.getAllManufacturers().size() && i > 0) {
                    manufacturer = manufacturerService.searchManufacturerByName(manufacturerService.getAllManufacturers().get(i - 1).getName());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect value");
            }

            souvenirToAdd.setName(name);
            souvenirToAdd.setDetailsOfTheManufacturer(manufacturer);
            souvenirToAdd.setReleaseDate(releaseDate);
            souvenirToAdd.setPrice(price);
            souvenirService.updateSouvenir(souvenirToAdd);
        } else {
            System.out.println("No souvenir with such data");
        }
        show();
    }

    private void souvenirList() {
        if (souvenirService.getAllSouvenirs().isEmpty()) {
            System.out.println("----No souvenirs----");
        } else {
            int increase = 3;
            int page = 0;
            int[] pageIndex = pageIndex(souvenirService.getAllSouvenirs(), increase, page);
            boolean exitz = false;
            while (!exitz) {
                System.out.println("Souvenir list:");
                souvenirService.getAllSouvenirs().subList(pageIndex[1], pageIndex[2]).forEach(x -> System.out.println("\t" + x));
                if (pageIndex[0] == 0 && increase >= souvenirService.getAllSouvenirs().size()) {
                    System.out.println("        /         / 3. Exit");
                } else if ((pageIndex[0] * increase) + increase >= souvenirService.getAllSouvenirs().size()) {
                    System.out.println("1. Prev /         / 3. Exit");
                } else if (pageIndex[0] == 0 && increase < souvenirService.getAllSouvenirs().size()) {
                    System.out.println("        / 2. Next / 3. Exit");
                } else {
                    System.out.println("1. Prev / 2. Next / 3. Exit");
                }

                int userChoice = -1;
                try {
                    userChoice = scanner.nextInt();
                } catch (InputMismatchException ignored) {
                }

                switch (userChoice) {
                    case 1 -> pageIndex = pageIndex(souvenirService.getAllSouvenirs(), increase, --pageIndex[0]);
                    case 2 -> pageIndex = pageIndex(souvenirService.getAllSouvenirs(), increase, ++pageIndex[0]);
                    case 3 -> exitz = true;
                }

            }
        }
        show();
    }

    public int[] pageIndex(List items, int increase, int page) {
        int pageOut;
        int start;
        int end;
        int[] index = new int[3];
        int maxPages = items.size() / increase;

        if (page < 0) {
            pageOut = 0;
            start = pageOut;
            end = increase;
        } else if (page >= maxPages) {
            pageOut = maxPages;
            start = pageOut * increase;
            end = items.size();
        } else {
            pageOut = page;
            start = page * increase;
            end = start + increase;
        }

        index[0] = pageOut;
        index[1] = start;
        index[2] = end;
        return index;
    }

    @Override
    public void exit() {
        if (CurrentUser.user.getRole() == User.UserRole.USER) {
            new UserMainMenu().show();
        } else {
            new AdminMainMenu().show();
        }
    }
}