package org.example;

import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryManagement {

    private Scanner scanner = new Scanner(System.in);
    private Random rand1 = new Random();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/libary_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";



    public void addLibrarian(){
        System.out.println("Enter Fullname: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter Address: ");
        String address = scanner.nextLine();
        System.out.println("Enter State of orign: ");
        String stateOfOrign = scanner.nextLine();
//           Auto generate a new ID
        int id = rand1.nextInt(100);
        String libaryNo = "LIB/"+id;
        System.out.println("Your Unique ID: " + libaryNo);
        String phoneNumber;
        do {
            System.out.println("Enter phoneNumber number (maximum 11 digits):");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.length() > 11) {
                System.out.println("Contact number exceeds 11 digits. Please enter again.");
            }
        } while (phoneNumber.length() > 11);

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO libary_libarians (libaryNo, full_Name, address, stateOfOrigin, status, phone_number) VALUES (?,?,?,?,?,?)");

            stmt.setString(1, libaryNo);
            stmt.setString(2, fullName);
            stmt.setString(3, address);
            stmt.setString(4, stateOfOrign);
            stmt.setString(5, "Active");
            stmt.setString(6, phoneNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Librarian added successfully!");
    }

    public static void addLibrarian(Librarian lib) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO libary_libarians (libaryNo, full_Name, address, stateOfOrigin, status, phone_number) VALUES (?,?,?,?,?,?)");


            stmt.setString(1, lib.getLibaryNo());
            stmt.setString(2, lib.getFullName());
            stmt.setString(3, lib.getAddress());
            stmt.setString(4, lib.getStateOfOrigin());
            stmt.setString(5, "Converted");
            stmt.setString(6, lib.getPhoneNumber());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewLibrarians() {

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM libary_libarians");
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

    public void searchLibrarian() {
        System.out.println("Enter the ID of the librarian you want to search: ");
        String libarianID = scanner.nextLine();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM libary_libarians WHERE libaryNo = ?");
            stmt.setString(1, libarianID);
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



}

