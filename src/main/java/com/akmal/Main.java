package com.akmal;

import com.akmal.model.InventoryItem;
import com.akmal.model.Product;
import com.akmal.observer.AlertService;
import com.akmal.service.Warehouse;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Warehouse warehouse;
    private static Scanner scanner;

    public static void main(String[] args) {
        warehouse = new Warehouse();
        scanner = new Scanner(System.in);
        AlertService alertService = new AlertService();

        warehouse.registerObserver(alertService);

        Product laptopProduct = new Product("L001", "Laptop");
        Product mouseProduct = new Product("M002", "Mouse");

        InventoryItem laptopItem = new InventoryItem(laptopProduct, 20, 5);
        InventoryItem mouseItem = new InventoryItem(mouseProduct, 50, 10);

        warehouse.addInventoryItem(laptopItem);
        warehouse.addInventoryItem(mouseItem);

        System.out.println("--- Warehouse System Initialized ---");
        System.out.println("Pre-loaded: Laptop (ID: L001) and Mouse (ID: M002)");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = -1;

            try {
                System.out.print("Enter your choice (1-6): ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("ERROR: Please enter a valid number.");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    receiveShipment();
                    break;
                case 3:
                    fulfillOrder();
                    break;
                case 4:
                    checkStock();
                    break;
                case 5:
                    checkAllStock();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.err.println("ERROR: Invalid choice. Please select from 1-6.");
            }
        }

        System.out.println("\n--- Exiting System. Goodbye! ---");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add New Product");
        System.out.println("2. Receive Shipment (Add Stock)");
        System.out.println("3. Fulfill Order (Remove Stock)");
        System.out.println("4. Check Stock for a Product");
        System.out.println("5. Check All Stock");
        System.out.println("6. Exit");
    }

    private static void addNewProduct() {
        System.out.println("\n--- Add New Product ---");
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        Product newProduct = new Product(id, name);

        int threshold = getIntInput("Enter Reorder Threshold: ");
        int quantity = getIntInput("Enter Initial Quantity: ");

        InventoryItem newItem = new InventoryItem(newProduct, quantity, threshold);

        warehouse.addInventoryItem(newItem);
    }

    private static void receiveShipment() {
        System.out.println("\n--- Receive Shipment ---");
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        int amount = getIntInput("Enter Quantity to Add: ");
        warehouse.receiveShipment(id, amount);
    }

    private static void fulfillOrder() {
        System.out.println("\n--- Fulfill Order ---");
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        int amount = getIntInput("Enter Quantity to Fulfill: ");
        warehouse.fulfillOrder(id, amount);
    }

    private static void checkStock() {
        System.out.println("\n--- Check Stock ---");
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        warehouse.printStockStatus(id);
    }

    private static void checkAllStock() {
        warehouse.printAllStock();
    }


    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value < 0) {
                    System.err.println("ERROR: Number cannot be negative.");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.err.println("ERROR: Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}