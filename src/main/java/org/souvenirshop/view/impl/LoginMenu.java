package org.souvenirshop.view.impl;

import org.souvenirshop.model.User;
import org.souvenirshop.service.UserService;
import org.souvenirshop.service.UserServiceImpl;
import org.souvenirshop.util.CurrentUser;
import org.souvenirshop.view.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginMenu implements Menu {
    private final UserService userService = new UserServiceImpl();
    private final String[] items = {"1.Login", "2.User registration", "3.Administrator registration", "0. Exit"};
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
                case 1 -> loginSubMenu();
                case 2 -> registerUser();
                case 3 -> registerAdmin();
                case 0 -> exit();
            }
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void loginSubMenu() {
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();
        User user = userService.login(login, password);
        if (user != null) {
            CurrentUser.user = user;
            System.out.println("Successfully authorization");
            if (user.getRole().equals(User.UserRole.USER)) {
                new UserMainMenu().show();
            } else {
                new AdminMainMenu().show();
            }
        } else {
            System.out.println("Wrong username/password");
            show();
        }
    }

    private void registerUser() {
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();

        User user = new User(login, password, User.UserRole.USER);
        userService.register(user);
        System.out.println("User was registered successfully ");
        show();
    }

    private void registerAdmin() {
        System.out.println("Input login:");
        scanner.nextLine();
        String login = scanner.nextLine();

        System.out.println("Input password:");
        String password = scanner.nextLine();

        User user = new User(login, password, User.UserRole.ADMIN);
        userService.register(user);

        System.out.println("Admin was registered successfully");
        show();
    }
}