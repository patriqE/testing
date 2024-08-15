package org.example;

import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadersManagement {

    private Scanner scanner = new Scanner(System.in);
    private Random rand = new Random();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/libary_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";


    public void addReader() {
        System.out.println("Enter Full name of Reader: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter your address: ");
        String address = scanner.nextLine();
        System.out.println("Enter your State of Origin: ");
        String stateOfOrigin = scanner.nextLine();
//           Auto generate a new ID
        int id = rand.nextInt(10000);
        String libaryNo = "RDR/" + id;
        System.out.println("This is your Unique ID: " + libaryNo);

        String phoneNumber;
        do {
            System.out.println("Enter phoneNumber number (maximum 11 digits):");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.length() > 11) {
                System.out.println("Contact number exceeds 11 digits. Please enter again.");
            }
        } while (phoneNumber.length() > 11);

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO libary_readers (libaryNo, full_Name, address, stateOfOrigin, status, phone_number) VALUES (?,?,?,?,?,?)");

            stmt.setString(1, libaryNo);
            stmt.setString(2, fullName);
            stmt.setString(3, address);
            stmt.setString(4, stateOfOrigin);
            stmt.setString(5, "Active");
            stmt.setString(6, phoneNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Reader added successfully!");
    }

    public void displayReaders() {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM libary_readers");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String libaryNo = rs.getString("libaryNo");
                String fullName = rs.getString("full_Name");
                String address = rs.getString("address");
                String stateOfOrign = rs.getString("stateOfOrigin");
                String status = rs.getString("status");
                String phoneNumber = rs.getString("phone_number");

                System.out.println("Libary No: " + libaryNo);
                System.out.println("Full Name: " + fullName);
                System.out.println("Address: " + address);
                System.out.println("State of Origin: " + stateOfOrign);
                System.out.println("Status: " + status);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchReader() {
        System.out.println("Enter the ID of the reader you want to search: ");
        String readerID = scanner.nextLine();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM libary_readers WHERE libaryNo = ?");
            stmt.setString(1, readerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String libaryNo = rs.getString("libaryNo");
                String fullName = rs.getString("full_Name");
                String address = rs.getString("address");
                String stateOfOrign = rs.getString("stateOfOrigin");
                String status = rs.getString("status");
                String phoneNumber = rs.getString("phone_number");

                System.out.println("Libary No: " + libaryNo);
                System.out.println("Full Name: " + fullName);
                System.out.println("Address: " + address);
                System.out.println("State of Origin: " + stateOfOrign);
                System.out.println("Status: " + status);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertReaderToLibrarian(){
        System.out.println("Enter full name or ID of the reader you want to convert to librarian: ");
        String searchKey = scanner.nextLine();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            String searchQuery = "SELECT * FROM libary_readers WHERE libaryNo = ? OR full_Name = ?";
            PreparedStatement stmt = con.prepareStatement(searchQuery);
            stmt.setString(1, searchKey);
            stmt.setString(2, searchKey);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String libaryNo = rs.getString("libaryNo");
                String fullName = rs.getString("full_Name");
                String address = rs.getString("address");
                String stateOfOrign = rs.getString("stateOfOrigin");
                String status = rs.getString("status");
                String phoneNumber = rs.getString("phone_number");

                System.out.println("Reader found!");
                System.out.println("Libary No: " + libaryNo);
                System.out.println("Full Name: " + fullName);
                System.out.println("Address: " + address);
                System.out.println("State of Origin: " + stateOfOrign);
                System.out.println("Status: " + status);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println();

                System.out.println("Are you sure you want to convert this reader to librarian? (y/n)");
                String confirmation = scanner.nextLine();
                if(confirmation.equalsIgnoreCase("y")) {
                    int id = rand.nextInt(100);
                    String newLibaryNo = "LBR/" + id;
                    System.out.println("This is your new librarian ID: " + newLibaryNo);
                    System.out.println("Converting reader to librarian...");
                    String insertLibrarianQuery = "INSERT INTO libary_libarians (libaryNo, full_Name, address, stateOfOrigin, status, phone_number) VALUES (?,?,?,?,?,?)";
                    PreparedStatement insertStmt = con.prepareStatement(insertLibrarianQuery);
                    insertStmt.setString(1, newLibaryNo);
                    insertStmt.setString(2, fullName);
                    insertStmt.setString(3, address);
                    insertStmt.setString(4, stateOfOrign);
                    insertStmt.setString(5, "Converted");
                    insertStmt.setString(6, phoneNumber);
                    insertStmt.executeUpdate();

                    String updateReaderQuery = "UPDATE libary_readers SET status = ? WHERE libaryNo = ?";
                    PreparedStatement updateStmt = con.prepareStatement(updateReaderQuery);
                    updateStmt.setString(1, "Deactivated");
                    updateStmt.setString(2, libaryNo);
                    updateStmt.executeUpdate();

                    System.out.println("Reader converted to librarian successfully!");
                } else {
                    System.out.println("Operation cancelled.");
                }
            } else {
                System.out.println("Reader not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}