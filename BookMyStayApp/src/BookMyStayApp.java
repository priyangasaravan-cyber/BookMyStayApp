import java.util.ArrayList;
import java.util.List;

class ConfirmedBooking {
    private String guestName;
    private String roomType;
    private String roomId;

    public ConfirmedBooking(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
}

class BookingHistory {
    private List<ConfirmedBooking> history;

    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    public void addRecord(ConfirmedBooking booking) {
        history.add(booking);
    }

    public List<ConfirmedBooking> getAllRecords() {
        return history;
    }
}

class BookingReportService {

    public void generateReport(BookingHistory history) {
        List<ConfirmedBooking> records = history.getAllRecords();

        if (records.isEmpty()) {
            System.out.println("No booking records found.");
            return;
        }

        System.out.println("Booking History Report");
        System.out.println("----------------------");
        for (ConfirmedBooking record : records) {
            System.out.println("Guest: " + record.getGuestName() +
                    ", Room Type: " + record.getRoomType() +
                    ", Room ID: " + record.getRoomId());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addRecord(new ConfirmedBooking("Abhi", "Single", "Single-1"));
        history.addRecord(new ConfirmedBooking("Subha", "Double", "Double-1"));
        history.addRecord(new ConfirmedBooking("Vanmathi", "Suite", "Suite-1"));

        System.out.println("Booking History and Reporting Status");
        System.out.println("====================================");

        reportService.generateReport(history);
    }
}