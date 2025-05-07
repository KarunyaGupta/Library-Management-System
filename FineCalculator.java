import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {
    private static final double DAILY_FINE_RATE = 0.50; // $0.50 per day

    public static double calculateFine(Book book) {
        if (book.getDueDate() == null) {
            return 0.0;
        }
        
        LocalDate today = LocalDate.now();
        LocalDate dueDate = book.getDueDate();
        
        if (today.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
            return daysOverdue * DAILY_FINE_RATE;
        }
        return 0.0;
    }
}
