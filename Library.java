import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        DataManager.saveBooks(books);
    }

    public void addMember(Member member) {
        members.add(member);
        DataManager.saveMembers(members);
    }

    public boolean borrowBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book != null && member != null && book.isAvailable() && 
            (book.getReservedBy() == null || book.getReservedBy() == member)) {
            book.setAvailable(false);
            book.setDueDate(LocalDate.now().plusDays(14)); // 14-day loan period
            member.borrowBook(book);
            if (book.getReservedBy() == member) {
                member.cancelReservation(book);
            }
            DataManager.saveBooks(books);
            DataManager.saveMembers(members);
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book != null && member != null && !book.isAvailable()) {
            double fine = member.calculateFine(book);
            if (fine > 0) {
                member.addFine(fine);
            }
            book.setAvailable(true);
            book.setDueDate(null);
            member.returnBook(book);
            DataManager.saveBooks(books);
            DataManager.saveMembers(members);
            return true;
        }
        return false;
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        query = query.toLowerCase();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) ||
                book.getAuthor().toLowerCase().contains(query) ||
                book.getCategory().toLowerCase().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    public boolean reserveBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book != null && member != null && book.getReservedBy() == null) {
            member.reserveBook(book);
            return true;
        }
        return false;
    }

    public Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public Member findMember(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}
