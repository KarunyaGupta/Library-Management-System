import java.util.ArrayList;

public class DataManager {
    // Keep data in memory using ArrayLists
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();

    // Methods to access data
    public static ArrayList<Book> getBooks() {
        return bookList;
    }

    public static ArrayList<Member> getMembers() {
        return memberList;
    }

    // Add new book to memory
    public static void addBook(Book book) {
        bookList.add(book);
    }

    // Add new member to memory
    public static void addMember(Member member) {
        memberList.add(member);
    }

    // Find book by ISBN
    public static Book findBook(String isbn) {
        for (Book book : bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    // Find member by ID
    public static Member findMember(String memberId) {
        for (Member member : memberList) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    // Clear all data (for testing)
    public static void clearData() {
        bookList.clear();
        memberList.clear();
    }
}
