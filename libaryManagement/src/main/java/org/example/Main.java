package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManagement library = new LibraryManagement();
        ReadersManagement read = new ReadersManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add New Reader");
            System.out.println("2. Add New Librarian");
            System.out.println("3. Display Reader(s)");
            System.out.println("4. Display Librarian(s)");
            System.out.println("5. Search for a reader");
            System.out.println("6. Search for a librarian");
            System.out.println("7. Convert Reader to Librarian");
            System.out.println("8. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    read.addReader();
                    break;
                case 2:
                    library.addLibrarian();
                    break;
                case 3:
                    read.displayReaders();
                    break;
                case 4:
                    library.viewLibrarians();
                    break;

                case 5:
                    read.searchReader();
                    break;

                case 6:
                    library.searchLibrarian();
                    break;

                case 7: {
                    read.convertReaderToLibrarian();
                    break;
                }
                case 8:
                    System.out.println("Goodbye...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, pls choose from the available  options");
            }
        }
    }
}