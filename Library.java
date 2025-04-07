import java.util.ArrayList;
import java.time.LocalDate;

public class Library {
    // Store books and members in simple ArrayLists
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    
    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    // Add a new book to library
    public void addBook(Book book) {
        books.add(book);
    }

    // Add a new member to library
    public void addMember(Member member) {
        members.add(member);
    }

    // Search books by title, author or category
    public ArrayList<Book> searchBooks(String query) {
        ArrayList<Book> results = new ArrayList<>();
        // Convert query to lowercase to make search case-insensitive
        query = query.toLowerCase();
        
        // Check each book if it matches the search query
        for (Book book : books) {
            String title = book.getTitle().toLowerCase();
            String author = book.getAuthor().toLowerCase();
            String category = book.getCategory().toLowerCase();
            
            // If book matches, add it to results
            if (title.contains(query) || author.contains(query) || category.contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    // Borrow a book using ISBN and member ID
    public boolean borrowBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        // Check if book can be borrowed
        if (book != null && member != null && book.isAvailable()) {
            book.setAvailable(false);
            // Set due date to 14 days from now
            book.setDueDate(LocalDate.now().plusDays(14));
            member.borrowBook(book);
            return true;
        }
        return false;
    }

    // Return a book and calculate any fines
    public boolean returnBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        // Check if book can be returned
        if (book != null && member != null && !book.isAvailable()) {
            // Calculate and add fine if book is late
            double fine = member.calculateFine(book);
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

    // Find a book by its ISBN
    public Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    // Find a member by their ID
    public Member findMember(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}
