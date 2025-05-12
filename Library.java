import java.util.ArrayList;
import java.time.LocalDate;

public class Library {
    private static final int MAX_BOOKS_PER_MEMBER = 5;

    public Library() {
        // Empty constructor
    }

    public void addBook(Book book) throws IllegalArgumentException {
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid book data");
        }
        if (findBook(book.getIsbn()) != null) {
            throw new IllegalArgumentException("Book with ISBN already exists");
        }
        DataManager.addBook(book);
    }

    public void addMember(Member member) throws IllegalArgumentException {
        if (member == null || member.getMemberId() == null || member.getMemberId().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid member data");
        }
        if (findMember(member.getMemberId()) != null) {
            throw new IllegalArgumentException("Member ID already exists");
        }
        DataManager.addMember(member);
    }

    // Update search to use DataManager
    public ArrayList<Book> searchBooks(String query) {
        ArrayList<Book> results = new ArrayList<>();
        query = query.toLowerCase();
        
        for (Book book : DataManager.getBooks()) {
            String title = book.getTitle().toLowerCase();
            String author = book.getAuthor().toLowerCase();
            String category = book.getCategory().toLowerCase();
            
            if (title.contains(query) || author.contains(query) || category.contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    public boolean borrowBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book == null || member == null) {
            return false;
        }

        if (member.getBorrowedBooks().size() >= MAX_BOOKS_PER_MEMBER) {
            throw new IllegalStateException("Member has reached maximum borrowing limit");
        }

        if (member.getFineAmount() > 0) {
            throw new IllegalStateException("Member has unpaid fines");
        }

        if (!book.isAvailable()) {
            return false;
        }

        if (book.getReservedBy() != null && !book.getReservedBy().equals(member)) {
            throw new IllegalStateException("Book is reserved by another member");
        }

        book.setAvailable(false);
        book.setDueDate(LocalDate.now().plusDays(14));
        member.borrowBook(book);
        return true;
    }

    public boolean returnBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book != null && member != null && !book.isAvailable()) {
            double fine = FineCalculator.calculateFine(book);
            if (fine > 0) {
                member.addFine(fine);
            }
            book.setAvailable(true);
            book.setDueDate(null);
            member.returnBook(book);
            return true;
        }
        return false;
    }

    public Book findBook(String isbn) {
        return DataManager.findBook(isbn);
    }

    public Member findMember(String memberId) {
        return DataManager.findMember(memberId);
    }
}
