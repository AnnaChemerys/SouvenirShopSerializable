package org.souvenirshop.view.impl;

import org.souvenirshop.view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMainMenu implements Menu {
    private final String[] items = {"1. Manufacturers menu", "2. Souvenirs menu", "0. Exit"};

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
                case 1 -> new ManufacturerMenu().show();
                case 2 -> new SouvenirMenu().show();
                case 0 -> exit();
            }
        }
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}