import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Member {
    private String memberId;
    private String name;
    private ArrayList<Book> borrowedBooks;
    private double fineAmount;
    private ArrayList<Book> reservedBooks;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
        this.fineAmount = 0.0;
        this.reservedBooks = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public ArrayList<Book> getBorrowedBooks() { return borrowedBooks; }
    public double getFineAmount() { return fineAmount; }
    
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void addFine(double amount){
        this.fineAmount += amount;
    }
    public void payFine(double amount){
        this.fineAmount = Math.max(0, this.fineAmount - amount);
    }
    
    public void reserveBook(Book book) {
        reservedBooks.add(book);
        book.setReservedBy(this);
    }
    
    public void cancelReservation(Book book) {
        reservedBooks.remove(book);
        if (book.getReservedBy() == this) {
            book.setReservedBy(null);
        }
    }
    
    public double calculateFine(Book book) {
        if (book.getDueDate() != null && LocalDate.now().isAfter(book.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), LocalDate.now());
            return daysOverdue * 0.50; // $0.50 per day
        }
        return 0.0;
    }
}
