import java.util.*;
public class Main {
    private static Library library;
    private static Scanner scanner;

    public static void main(String[] args) {
        library = new Library();
        scanner = new Scanner(System.in);

        // Add sample data
        initializeSampleData();

        while (true) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: 
                    searchBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    showMemberDetails();
                    break;
                case 5:
                    addNewBook();
                    break;
                case 6:
                    addNewMember();
                    break;
                case 7: 
                    LibraryReport.showAllBooks();
                    break;
                case 8:
                    LibraryReport.showAllMembers();
                    break;
                case 9: 
                    LibraryReport.showOverdueBooks();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default: 
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Search Books");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. Member Details");
        System.out.println("5. Add New Book");
        System.out.println("6. Add New Member");
        System.out.println("7. Show All Books");
        System.out.println("8. Show All Members");
        System.out.println("9. Show Overdue Books");
        System.out.println("0. Exit");
    }

    private static void searchBooks() {
        String query = getStringInput("Enter search term: ");
        ArrayList<Book> results = library.searchBooks(query);
        
        System.out.println("\nSearch Results:");
        for (Book book : results) {
            System.out.printf("ISBN: %s, Title: %s, Author: %s, Available: %s\n",
                book.getIsbn(), book.getTitle(), book.getAuthor(), 
                book.isAvailable() ? "Yes" : "No");
        }
    }

    private static void borrowBook() {
        String isbn = getStringInput("Enter book ISBN: ");
        String memberId = getStringInput("Enter member ID: ");
        
        if (library.borrowBook(isbn, memberId)) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Failed to borrow book. Book may not be available.");
        }
    }

    private static void returnBook() {
        String isbn = getStringInput("Enter book ISBN: ");
        String memberId = getStringInput("Enter member ID: ");
        
        if (library.returnBook(isbn, memberId)) {
            System.out.println("Book returned successfully!");
            Member member = library.findMember(memberId);
            if (member != null) {
                System.out.printf("Fine amount: $%.2f\n", member.getFineAmount());
            }
        } else {
            System.out.println("Failed to return book. Please check ISBN and member ID.");
        }
    }

    private static void showMemberDetails() {
        String memberId = getStringInput("Enter member ID: ");
        Member member = library.findMember(memberId);
        
        if (member != null) {
            System.out.println("\nMember Details:");
            System.out.println("ID: " + member.getMemberId());
            System.out.println("Name: " + member.getName());
            System.out.println("Fine Amount: $" + member.getFineAmount());
            System.out.println("Borrowed Books:");
            for (Book book : member.getBorrowedBooks()) {
                System.out.printf("- %s by %s (Due: %s)\n", 
                    book.getTitle(), book.getAuthor(), 
                    book.getDueDate());
            }
        } else {
            System.out.println("Member not found!");
        }
    }

    private static void addNewBook() {
        String isbn = getStringInput("Enter ISBN: ");
        if (!isbn.matches("^[0-9]{4}$")) {
            System.out.println("Invalid ISBN format. Please use 4 digits.");
            return;
        }
        
        String title = getStringInput("Enter title: ");
        String author = getStringInput("Enter author: ");
        String category = getStringInput("Enter category: ");
        
        Book book = new Book(isbn, title, author, category);
        library.addBook(book);
        System.out.println("Book added successfully!");
    }

    private static void addNewMember() {
        String memberId = getStringInput("Enter member ID: ");
        String name = getStringInput("Enter name: ");
        
        Member member = new Member(memberId, name);
        library.addMember(member);
        System.out.println("Member added successfully!");
    }

    private static void initializeSampleData() {
        Book book1 = new Book("0001", "Java Programming", "John Doe", "Programming");
        Book book2 = new Book("0002", "Python Basics", "Jane Smith", "Programming");
        Book book3 = new Book("0003", "Data Structures", "Alice Johnson", "Computer Science");
        Book book4 = new Book("0004", "Algorithms", "Bob Brown", "Computer Science");
        Book book5 = new Book("0005", "Database Systems", "Charlie White", "Database");
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        Member member1 = new Member("1", "Karunya");
        Member member2 = new Member("2", "Navya");
        Member member3 = new Member("3", "Kartik");
        Member member4 = new Member("4", "Manvir");
        library.addMember(member1);
        library.addMember(member2);
        library.addMember(member3);
        library.addMember(member4);
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }
}
