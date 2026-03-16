import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        roomAvailability.put(roomType, newCount);
    }
}

class RoomAllocationService {
    private Map<String, Integer> roomCounters = new HashMap<>();

    public RoomAllocationService() {
        roomCounters.put("Single", 1);
        roomCounters.put("Double", 1);
        roomCounters.put("Suite", 1);
    }

    public void processAllocation(Reservation request, RoomInventory inventory) {
        String type = request.getRoomType();
        int available = inventory.getAvailability(type);

        if (available > 0) {
            String roomId = generateRoomId(type);

            inventory.updateAvailability(type, available - 1);

            System.out.println("Booking confirmed for Guest: " + request.getGuestName() +
                    ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " + request.getGuestName() +
                    " - No " + type + " rooms available.");
        }
    }

    private String generateRoomId(String roomType) {
        int currentCount = roomCounters.get(roomType);
        String id = roomType + "-" + currentCount;
        roomCounters.put(roomType, currentCount + 1);
        return id;
    }
}

public class BookMyStayApp{
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.offer(new Reservation("Abhi", "Single"));
        requestQueue.offer(new Reservation("Subha", "Single"));
        requestQueue.offer(new Reservation("Vanmathi", "Suite"));

        System.out.println("Room Allocation Processing");
        System.out.println("--------------------------");

        while (!requestQueue.isEmpty()) {
            Reservation nextRequest = requestQueue.poll();
            allocationService.processAllocation(nextRequest, inventory);
        }
    }
}