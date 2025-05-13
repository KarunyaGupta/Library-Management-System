import java.util.ArrayList;
import java.time.LocalDate;

public class Library {
    private static final int MAX_BOOKS_PER_MEMBER = 5;

    public Library() {
        // Empty constructor
    }

    public void addBook(Book book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty() || 
            findBook(book.getIsbn()) != null) {
            return;
        }
        DataManager.addBook(book);
    }

    public void addMember(Member member) {
        if (member == null || member.getMemberId() == null || member.getMemberId().trim().isEmpty() || 
            findMember(member.getMemberId()) != null) {
            return;
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

        if (book == null || member == null || 
            member.getBorrowedBooks().size() >= MAX_BOOKS_PER_MEMBER ||
            member.getFineAmount() > 0 ||
            !book.isAvailable() ||
            (book.getReservedBy() != null && !book.getReservedBy().equals(member))) {
            return false;
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