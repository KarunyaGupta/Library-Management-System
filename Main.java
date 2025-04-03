import java.util.Scanner;
import java.util.List;

public class Main {
    private Library library;
    private Scanner scanner;

    public Main() {
        library = new Library();
        scanner = new Scanner(System.in);
        initializeLibrary();
    }

    private void initializeLibrary() {
        // Add some initial books and members for testing
        library.addBook(new Book("1234", "Java Programming", "John Doe", "Programming"));
        library.addBook(new Book("5678", "Python Basics", "Jane Smith", "Programming"));
        library.addMember(new Member("M001", "Alice"));
    }

    public void start() {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: borrowBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: reserveBook(); break;
                case 7: payFine(); break;
                case 8: System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Books");
        System.out.println("6. Reserve Book");
        System.out.println("7. Pay Fine");
        System.out.println("8. Exit");
    }

    private void addBook() {
        System.out.println("\n=== Add Book ===");
        String isbn = getStringInput("Enter ISBN: ");
        String title = getStringInput("Enter Title: ");
        String author = getStringInput("Enter Author: ");
        String category = getStringInput("Enter Category: ");
        
        library.addBook(new Book(isbn, title, author, category));
        System.out.println("Book added successfully!");
    }

    private void addMember() {
        System.out.println("\n=== Add Member ===");
        String id = getStringInput("Enter Member ID: ");
        String name = getStringInput("Enter Name: ");
        
        library.addMember(new Member(id, name));
        System.out.println("Member added successfully!");
    }

    private void borrowBook() {
        System.out.println("\n=== Borrow Book ===");
        String isbn = getStringInput("Enter Book ISBN: ");
        String memberId = getStringInput("Enter Member ID: ");
        
        boolean success = library.borrowBook(isbn, memberId);
        System.out.println(success ? "Book borrowed successfully!" : "Failed to borrow book!");
    }

    private void returnBook() {
        System.out.println("\n=== Return Book ===");
        String isbn = getStringInput("Enter Book ISBN: ");
        String memberId = getStringInput("Enter Member ID: ");
        
        boolean success = library.returnBook(isbn, memberId);
        System.out.println(success ? "Book returned successfully!" : "Failed to return book!");
    }

    private void searchBooks() {
        System.out.println("\n=== Search Books ===");
        String query = getStringInput("Enter search term: ");
        
        List<Book> results = library.searchBooks(query);
        if (results.isEmpty()) {
            System.out.println("No books found!");
        } else {
            System.out.println("Found books:");
            results.forEach(b -> System.out.println(b.getTitle() + " by " + b.getAuthor() + 
                " [" + (b.isAvailable() ? "Available" : "Not Available") + "]"));
        }
    }

    private void reserveBook() {
        System.out.println("\n=== Reserve Book ===");
        String isbn = getStringInput("Enter Book ISBN: ");
        String memberId = getStringInput("Enter Member ID: ");
        
        boolean success = library.reserveBook(isbn, memberId);
        System.out.println(success ? "Book reserved successfully!" : "Failed to reserve book!");
    }

    private void payFine() {
        System.out.println("\n=== Pay Fine ===");
        String memberId = getStringInput("Enter Member ID: ");
        Member member = library.findMember(memberId);
        
        if (member != null) {
            System.out.println("Current fine: $" + member.getFineAmount());
            double amount = getDoubleInput("Enter amount to pay: ");
            member.payFine(amount);
            System.out.println("Remaining fine: $" + member.getFineAmount());
        } else {
            System.out.println("Member not found!");
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount!");
            }
        }
    }

    public static void main(String[] args) {
        Main librarySystem = new Main();
        librarySystem.start();
    }
}
