package org.souvenirshop.view.impl;

import org.souvenirshop.model.Manufacturer;
import org.souvenirshop.model.Souvenir;
import org.souvenirshop.model.User;
import org.souvenirshop.service.ManufacturerService;
import org.souvenirshop.service.ManufacturerServiceImpl;
import org.souvenirshop.util.CurrentUser;
import org.souvenirshop.view.Menu;

import java.util.*;

public class ManufacturerMenu implements Menu {
    private final ManufacturerService manufacturerService = new ManufacturerServiceImpl();
    private final User.UserRole currentRole = CurrentUser.user.getRole();

    private final String[] items = currentRole == User.UserRole.USER
            ? new String[]{"1. Manufacturer list", "2. Search manufacturer",
            "3. Show all manufacturers with their souvenirs", "0. Exit"}
            : new String[]{"1. Manufacturer list", "2. Edit manufacturer", "3. Add manufacturer",
            "4. Delete manufacturer and his souvenirs", "0. Exit"};

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
                        case 1 -> manufacturerList();
                        case 2 -> new SearchManufacturerMenu().show();
                        case 3 -> manufacturerListWithTheirSouvenirs();
                        case 0 -> exit();
                    }
                }
                case ADMIN -> {
                    switch (choice) {
                        case 1 -> manufacturerList();
                        case 2 -> editManufacturer();
                        case 3 -> addManufacturer();
                        case 4 -> removeManufacturer();
                        case 0 -> exit();
                    }
                }
            }
        }
    }

    private void removeManufacturer() {
        System.out.println("Enter manufacturer name: ");
        scanner.nextLine();
        String manufacturerName = scanner.nextLine().toUpperCase();
        Manufacturer manufacturerToRemove = manufacturerService.searchManufacturerByName(manufacturerName);

        if (manufacturerToRemove != null) {
            manufacturerService.removeManufacturerWithHisSouvenirs(manufacturerName);
        } else {
            System.out.println("No manufacturer with such name");
        }
        show();
    }

    private void addManufacturer() {
        System.out.println("Enter manufacturer name: ");
        scanner.nextLine();
        String name = scanner.nextLine().toUpperCase();

        System.out.println("Enter manufacturer's country: ");
        //scanner.nextLine();
        String country = scanner.nextLine();

        Manufacturer manufacturer = new Manufacturer(name, country);

        manufacturerService.saveManufacturer(manufacturer);

        show();
    }

    private void editManufacturer() {
        System.out.println("Enter manufacturer name: ");
        //scanner.nextLine();
        String manufacturerName = scanner.nextLine().toUpperCase();

        Manufacturer manufacturerToAdd = manufacturerService.searchManufacturerByName(manufacturerName);

        if (manufacturerToAdd != null) {

            System.out.println("Enter manufacturer name: ");
            scanner.nextLine();

            String name = scanner.nextLine().toUpperCase();

            System.out.println("Enter manufacturer country: ");
            //scanner.nextLine();

            String country = scanner.nextLine();

            manufacturerToAdd.setName(name);
            manufacturerToAdd.setCountry(country);

            manufacturerService.updateManufacturer(manufacturerToAdd);
        } else {
            System.out.println("No manufacturer with such name");
        }
        show();
    }

    private void manufacturerList() {
        if (manufacturerService.getAllManufacturers().isEmpty()) {
            System.out.println("----No manufacturers----");
        } else {
            int increase = 3;
            int page = 0;
            int[] pageIndex = pageIndex(manufacturerService.getAllManufacturers(), increase, page);
            boolean exitz = false;
            while (!exitz) {
                System.out.println("Manufacturer list:");
                manufacturerService.getAllManufacturers().subList(pageIndex[1], pageIndex[2]).forEach(x -> System.out.println("\t" + x));
                if (pageIndex[0] == 0 && increase >= manufacturerService.getAllManufacturers().size()) {
                    System.out.println("        /         / 3. Exit");
                } else if ((pageIndex[0] * increase) + increase >= manufacturerService.getAllManufacturers().size()) {
                    System.out.println("1. Prev /         / 3. Exit");
                } else if (pageIndex[0] == 0 && increase < manufacturerService.getAllManufacturers().size()) {
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
                    case 1 ->
                            pageIndex = pageIndex(manufacturerService.getAllManufacturers(), increase, --pageIndex[0]);
                    case 2 ->
                            pageIndex = pageIndex(manufacturerService.getAllManufacturers(), increase, ++pageIndex[0]);
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

    private void manufacturerListWithTheirSouvenirs() {
        Map<Manufacturer, List<Souvenir>> manufacturersWithAllTheirSouvenirs =
                manufacturerService.getAllManufacturersWithTheirSouvenirs();
        if (!manufacturersWithAllTheirSouvenirs.isEmpty()) {
            System.out.println(manufacturersWithAllTheirSouvenirs.values());
        } else {
            System.out.println("Unfortunately, at the moment, the storage of manufacturers and souvenirs is empty.");
        }
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