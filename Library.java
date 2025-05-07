import java.util.ArrayList;
import java.time.LocalDate;

public class Library {
    // Remove local ArrayLists since we'll use DataManager
    
    public Library() {
        // Empty constructor
    }

    public void addBook(Book book) {
        DataManager.addBook(book);
    }

    public void addMember(Member member) {
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

        if (book != null && member != null && book.isAvailable()) {
            book.setAvailable(false);
            book.setDueDate(LocalDate.now().plusDays(14));
            member.borrowBook(book);
            return true;
        }
        return false;
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
