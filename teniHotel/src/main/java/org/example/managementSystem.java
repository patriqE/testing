package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class managementSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/hotel?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String roomType = null;
        boolean validSelection = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        while (true) {
            System.out.println("Welcome to newLine Hotel");
            System.out.println("Enter customer's customerName:");
            String customerName = scan.nextLine();

            String phoneNumber;
            do {
                System.out.println("Enter customer's phoneNumber number (maximum 11 digits):");
                phoneNumber = scan.nextLine();
                if (phoneNumber.length() > 11) {
                    System.out.println("Contact number exceeds 11 digits. Please enter again.");
                }
            } while (phoneNumber.length() > 11);

            while (!validSelection) {
                System.out.println("Room type: ");
                System.out.println("1. Standard");
                System.out.println("2. Deluxe");
                System.out.println("3. Suite");
                System.out.println("Enter your choice: ");
                int roomTypeOption = scan.nextInt();

                roomType = getRoomType(roomTypeOption);

                if (roomType == null) {
                    System.out.println("Invalid room type selection. Please try again.");
                    continue;
                }
                validSelection = true;
            }

            System.out.println("Enter room number: ");
            int roomNumber = scan.nextInt();
            scan.nextLine(); // Consume newline

            System.out.print("Enter length of stay (in days): ");
            int lengthOfStay = scan.nextInt();
            scan.nextLine(); // Consume newline

            System.out.print("Enter check-in date (DD/MM/YY): ");
            String checkInDateStr = scan.nextLine();

            System.out.print("Enter check-out date (DD/MM/YY): ");
            String checkOutDateStr = scan.nextLine();

            try {
                Date checkInDate = dateFormat.parse(checkInDateStr);
                Date checkOutDate = dateFormat.parse(checkOutDateStr);
                saveHotelDetails(customerName, phoneNumber, roomNumber, roomType, lengthOfStay, checkInDate, checkOutDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use DD/MM/YY.");
            }

            System.out.print("Do you want to enter details for another customer? (yes/no): ");
            String response = scan.nextLine();

            if (response.equalsIgnoreCase("no")) {
                break;
            }
        }

        System.out.println("All customer details have been saved.");
    }


    private static String getRoomType(int roomTypeOption) {
        switch (roomTypeOption) {
            case 1:
                return "Standard";
            case 2:
                return "Deluxe";
            case 3:
                return "Suite";
            default:
                return null;
        }
    }

    private static void saveHotelDetails(String customerName, String phoneNumber, int roomNumber, String roomType, int lengthOfStay, Date checkInDate, Date checkOutDate) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO hotel_details (customer_name, phone_number, room_number, room_type, length_of_stay, check_in_date, check_out_date) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, customerName);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.setInt(3, roomNumber);
                preparedStatement.setString(4, roomType);
                preparedStatement.setInt(5, lengthOfStay);
                preparedStatement.setDate(6, new java.sql.Date(checkInDate.getTime()));
                preparedStatement.setDate(7, new java.sql.Date(checkOutDate.getTime()));
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Hotel details saved successfully.");
                }
            }

            }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}