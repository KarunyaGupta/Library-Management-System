import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
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
            return true;
        }
        return false;
    }

    public List<Book> searchBooks(String query) {
        return books.stream()
            .filter(book -> 
                book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                book.getCategory().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
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

    public Member findMember(String memberId) {
        return members.stream()
            .filter(member -> member.getMemberId().equals(memberId))
            .findFirst()
            .orElse(null);
    }

    private Book findBook(String isbn) {
        return books.stream()
            .filter(book -> book.getIsbn().equals(isbn))
            .findFirst()
            .orElse(null);
    }
}
