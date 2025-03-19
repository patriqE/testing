package org.example;

import java.sql.*;
import java.util.Scanner;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;

public class tomShoeOrderer {
    static String remoteIp = "10.100.80.112";
    static String dbName = "tomShoes";

    private static final String dbURL = "jdbc:mysql://" + remoteIp + ":3306/"+dbName;
    private static final String dbUser = "The-PatriQ";
    private static final String dbPassword = "12345";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            System.out.println("Welcome to the Shoe Order Bot!");
            System.out.print("Please enter your name: ");
            String customerName = scanner.nextLine();

            System.out.print("Please enter your phone number: ");
            String phoneNumber = scanner.nextLine();

            System.out.println("Please enter your email address: ");
            String emailAddress = scanner.nextLine();

            System.out.print("Please enter your delivery address: ");
            String deliveryAddress = scanner.nextLine();

            System.out.println("what category are you shopping for?: ");
            System.out.println(" Male / female/ toddler ");
            String category = scanner.nextLine().toLowerCase();

            // Connect to table
            String tableName;
            switch (category) {
                case "male":
                    tableName = "mensShoe";
                    break;
                case "female":
                    tableName = "womenShoes";
                    break;
                case "toddler":
                    tableName = "toddlerShoes";
                    break;
                default:
                    System.out.println("Invalid category. Please try again.");
                    return;
            }


            String brandQuery = "SELECT DISTINCT brand FROM " + tableName;
            PreparedStatement brandStmt = conn.prepareStatement(brandQuery);
            ResultSet brandRs = brandStmt.executeQuery();

            List<String> brands = new ArrayList<>();
            System.out.println("Available Brands:");
            while (brandRs.next()) {
                String brand = brandRs.getString("brand");
                brands.add(brand);
                System.out.println(" - " + brand);
            }
            System.out.print("Please select a brand: ");
            String selectedBrand = scanner.nextLine();

            String styleQuery = "SELECT DISTINCT style FROM " + tableName + " WHERE brand = ?";
            PreparedStatement styleStmt = conn.prepareStatement(styleQuery);
            styleStmt.setString(1, selectedBrand);
            ResultSet styleRs = styleStmt.executeQuery();

            List<String> styles = new ArrayList<>();
            System.out.println("Available Styles for " + selectedBrand + ":");

            while (styleRs.next()) {
                String style = styleRs.getString("style");
                styles.add(style);
                System.out.println("- " + style);
            }
            System.out.print("Please select a style: ");
            String selectedStyle = scanner.nextLine();

            String sizeQuery = "SELECT DISTINCT shoe_size FROM " + tableName + " WHERE brand = ? AND style = ?";
            PreparedStatement sizeStmt = conn.prepareStatement(sizeQuery);
            sizeStmt.setString(1, selectedBrand);
            sizeStmt.setString(2, selectedStyle);
            ResultSet sizeRs = sizeStmt.executeQuery();

            List<String> sizes = new ArrayList<>();
            System.out.println("Available Sizes for " + selectedBrand + " " + selectedStyle + ":");
            while (sizeRs.next()) {
                int size = Integer.parseInt(sizeRs.getString("shoe_size"));
                sizes.add(String.valueOf(size));
                System.out.println("- " + size);
            }
            System.out.print("Please select a size: ");
            int selectedSize = scanner.nextInt();

            System.out.println("please enter the quantity of pairs: ");
            int quantity = scanner.nextInt();

            // Fetch quantity and price from database
            String quantityPriceQuery = "SELECT quantity, price FROM " + tableName + " WHERE brand = ? AND style = ? AND shoe_size = ?";

            PreparedStatement quantityPriceStmt = conn.prepareStatement(quantityPriceQuery);
            quantityPriceStmt.setString(1, selectedBrand);
            quantityPriceStmt.setString(2, selectedStyle);
            quantityPriceStmt.setString(3, String.valueOf(selectedSize));
            ResultSet quantityPriceRs = quantityPriceStmt.executeQuery();

            String colorQuery = "SELECT DISTINCT color FROM" + tableName + " WHERE brand = ? AND style = ? AND shoe_size = ? AND quantity > ?";
            PreparedStatement colorStmt = conn.prepareStatement(colorQuery);
            colorStmt.setString(1, selectedBrand);
            colorStmt.setString(2, selectedStyle);
            colorStmt.setString(3, String.valueOf(selectedSize));
            colorStmt.setInt(4, quantity);
            ResultSet colorRs = colorStmt.executeQuery();

            List<String> colours = new ArrayList<>();
            System.out.println("Available Colors for " + selectedBrand + " " + selectedStyle + " " + selectedSize + ":");
            while (colorRs.next()) {
                String colour = colorRs.getString("color");
                colours.add(colour);
                System.out.println("- " + colour);
            }
            System.out.print("Please select a color: ");
            String selectedColor = scanner.nextLine();

            String priceQuery = "SELECT price FROM " + tableName + " WHERE brand = ? AND style = ? AND shoe_size = ? AND color = ?";
            PreparedStatement priceStmt = conn.prepareStatement(priceQuery);
            priceStmt.setString(1, selectedBrand);
            priceStmt.setString(2, selectedStyle);
            priceStmt.setString(3, String.valueOf(selectedSize));
            priceStmt.setString(4, selectedColor);
            ResultSet priceRs = priceStmt.executeQuery();




            double totalPrice = 0;
            if (quantityPriceRs.next()) {
                int availableQuantity = quantityPriceRs.getInt("quantity");
                double price = quantityPriceRs.getDouble("price");
                int remainingQuantity = availableQuantity - quantity;
                if (remainingQuantity >= 0) {
                    String updateQuery = "UPDATE " + tableName + " SET quantity = ? WHERE brand = ? AND style = ? AND shoe_size = ? AND color = ?";

                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);

                    updateStmt.setInt(1, remainingQuantity);
                    updateStmt.setString(2, selectedBrand);
                    updateStmt.setString(3, selectedStyle);
                    updateStmt.setString(4, String.valueOf(selectedSize));
                    updateStmt.setString(5, selectedColor);

                    updateStmt.executeUpdate();

                    totalPrice = price * quantity;

                    // Final Output: Show order summary
                    System.out.println("\nOrder Summary:");
                    System.out.println("Order ID: " + UUID.randomUUID().toString());
                    System.out.println("Customer Name: " + customerName);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Email Address: " + emailAddress);
                    System.out.println("Delivery Address: " + deliveryAddress);
                    System.out.println("Selected Brand: " + selectedBrand);
                    System.out.println("Selected Style: " + selectedStyle);
                    System.out.println("Selected Size: " + selectedSize);
                    System.out.println("Selected Color: " + selectedColor);
                    System.out.println("Total Price: $" + totalPrice);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Order Placed!");

                } else {
                    System.out.println("Sorry, there are not enough " + selectedBrand + " " + selectedStyle + " " + selectedSize + " " + selectedColor + " in stock.");
                }

            } else {
                System.out.println("Item not found in database.");
            }


            // Insert order into database
            String insertQuery = "INSERT INTO customerOrder (order_id, customer_name, customer_phone_number, customer_email_address, + " +
                    "address, brand, price, quantity, color, shoe_size, order_status, style ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, UUID.randomUUID().toString());
            insertStmt.setString(2, customerName);
            insertStmt.setString(3, phoneNumber);
            insertStmt.setString(4, emailAddress);
            insertStmt.setString(5, deliveryAddress);
            insertStmt.setString(6, selectedBrand);
            insertStmt.setDouble(7, totalPrice);
            insertStmt.setInt(8, quantity);
            insertStmt.setString(9, selectedColor);
            insertStmt.setString(10, String.valueOf(selectedSize));
            insertStmt.setString(11, "Order Placed");
            insertStmt.setString(12, selectedStyle);
            insertStmt.executeUpdate();


            // Close database connection
            conn.close();

            System.out.println("Thank you for your order, " + customerName + "!");
            System.out.println("Your order will be shipped to " + deliveryAddress + " and will cost $" + totalPrice + ".");


        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
