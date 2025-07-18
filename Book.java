import java.time.LocalDate;
public class Book implements Comparable<Book> {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;
    private String category;
    private LocalDate dueDate;
    private Member reservedBy;

    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.category = category;
        this.dueDate = null;
        this.reservedBy = null;
    }

    // Getters and setters
    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void setAvailable(boolean available){
        isAvailable = available;
    }
    public String getCategory(){
        return category;
    }
    public LocalDate getDueDate(){
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }
    public Member getReservedBy(){
        return reservedBy;
    }
    public void setReservedBy(Member member){
        this.reservedBy = member;
    }

    public boolean matches(String query) {
        return title.toLowerCase().contains(query) ||
               author.toLowerCase().contains(query) ||
               category.toLowerCase().contains(query);
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }
}
