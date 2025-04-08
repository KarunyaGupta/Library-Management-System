import java.util.ArrayList;

public class LibraryReport {
    // Show all books in library
    public static void showAllBooks() {
        ArrayList<Book> books = DataManager.getBooks();
        System.out.println("\n=== Library Book Inventory ===");
        System.out.println("Total Books: " + books.size());
        System.out.println("----------------------------------------");
        
        for (Book book : books) {
            System.out.printf("ISBN: %s\nTitle: %s\nAuthor: %s\nCategory: %s\nStatus: %s\n",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.isAvailable() ? "Available" : "Borrowed"
            );
            if (!book.isAvailable()) {
                System.out.println("Due Date: " + book.getDueDate());
            }
            System.out.println("----------------------------------------");
        }
    }

    // Show all members and their borrowed books
    public static void showAllMembers() {
        ArrayList<Member> members = DataManager.getMembers();
        System.out.println("\n=== Library Members ===");
        System.out.println("Total Members: " + members.size());
        System.out.println("----------------------------------------");
        
        for (Member member : members) {
            System.out.printf("ID: %s\nName: %s\nFine Amount: $%.2f\n",
                member.getMemberId(),
                member.getName(),
                member.getFineAmount()
            );
            
            ArrayList<Book> borrowed = (ArrayList<Book>) member.getBorrowedBooks();
            if (!borrowed.isEmpty()) {
                System.out.println("Borrowed Books:");
                for (Book book : borrowed) {
                    System.out.printf("- %s (Due: %s)\n", 
                        book.getTitle(), 
                        book.getDueDate()
                    );
                }
            }
            System.out.println("----------------------------------------");
        }
    }

    // Show overdue books
    public static void showOverdueBooks() {
        ArrayList<Member> members = DataManager.getMembers();
        System.out.println("\n=== Overdue Books ===");
        
        boolean hasOverdue = false;
        for (Member member : members) {
            for (Book book : member.getBorrowedBooks()) {
                double fine = member.calculateFine(book);
                if (fine > 0) {
                    hasOverdue = true;
                    System.out.printf("Book: %s\nBorrowed by: %s\nDue Date: %s\nFine: $%.2f\n",
                        book.getTitle(),
                        member.getName(),
                        book.getDueDate(),
                        fine
                    );
                    System.out.println("----------------------------------------");
                }
            }
        }
        
        if (!hasOverdue) {
            System.out.println("No overdue books found.");
        }
    }
}
