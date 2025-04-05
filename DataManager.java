import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class DataManager {
    private static final String DATA_DIR = "library_data";
    private static final String BOOKS_FILE = DATA_DIR + "/books.txt";
    private static final String MEMBERS_FILE = DATA_DIR + "/members.txt";

    public static void initialize() {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                // Format: ISBN,Title,Author,Category,IsAvailable,DueDate
                writer.println(String.format("%s,%s,%s,%s,%b,%s",
                    book.getIsbn(),
                    book.getTitle().replace(",", ";"),
                    book.getAuthor().replace(",", ";"),
                    book.getCategory(),
                    book.isAvailable(),
                    book.getDueDate() != null ? book.getDueDate().toString() : "null"
                ));
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public static void saveMembers(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                // Format: MemberID,Name,FineAmount,BorrowedBooks
                StringBuilder borrowedBooks = new StringBuilder();
                for (Book book : member.getBorrowedBooks()) {
                    borrowedBooks.append(book.getIsbn()).append(";");
                }
                
                writer.println(String.format("%s,%s,%.2f,%s",
                    member.getMemberId(),
                    member.getName().replace(",", ";"),
                    member.getFineAmount(),
                    borrowedBooks.toString()
                ));
            }
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }
}
