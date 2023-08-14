package org.souvenirshop.view.impl;

import org.souvenirshop.view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserMainMenu implements Menu {

    private final String[] items = {"1. Souvenir menu", "2. Manufacturer menu", "0. Exit"};

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
                case 1 -> new SouvenirMenu().show();
                case 2 -> new ManufacturerMenu().show();
                case 0 -> exit();
            }
        }
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}