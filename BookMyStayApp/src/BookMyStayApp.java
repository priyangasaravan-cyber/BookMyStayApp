import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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


class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public RoomInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public void restoreInventory(String roomType) {
        int currentCount = roomAvailability.getOrDefault(roomType, 0);
        roomAvailability.put(roomType, currentCount + 1);
    }
}

class CancellationService {
    private Stack<String> rollbackHistory = new Stack<>();

    public void cancelBooking(ConfirmedBooking booking, RoomInventory inventory) {
        inventory.restoreInventory(booking.getRoomType());

        rollbackHistory.push(booking.getRoomId());

        System.out.println("Booking cancelled successfully. Inventory restored for room type: "
                + booking.getRoomType());
    }


    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        while (!rollbackHistory.isEmpty()) {
            System.out.println("Released Reservation ID: " + rollbackHistory.pop());
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        ConfirmedBooking b1 = new ConfirmedBooking("Abhi", "Single", "Single-1");
        ConfirmedBooking b2 = new ConfirmedBooking("Subha", "Double", "Double-1");

        System.out.println("Booking Cancellation Processing");
        System.out.println("------------------------------");

        cancellationService.cancelBooking(b1, inventory);
        cancellationService.cancelBooking(b2, inventory);

        cancellationService.showRollbackHistory();
    }
}