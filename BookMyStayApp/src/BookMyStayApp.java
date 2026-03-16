import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get("Single Room") > 0) {
            displayRoomDetails(singleRoom, availability.get("Single Room"));
        }

        if (availability.get("Double Room") > 0) {
            displayRoomDetails(doubleRoom, availability.get("Double Room"));
        }

        if (availability.get("Suite Room") > 0) {
            displayRoomDetails(suiteRoom, availability.get("Suite Room"));
        }
    }

    private void displayRoomDetails(Room room, int count) {
        System.out.println(room.getType() + ":");
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSize() + " sqft");
        System.out.println("Price per night: " + room.getPrice());
        System.out.println("Available: " + count);
        System.out.println();
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Room single = new Room("Single Room", 1, 250, 1500.0);
        Room doubleRoom = new Room("Double Room", 2, 400, 2500.0);
        Room suite = new Room("Suite Room", 3, 750, 5000.0);

        RoomSearchService searchService = new RoomSearchService();

        System.out.println("Use Case 4: Room Search & Availability Check");
        System.out.println("-------------------------------------------");
        searchService.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}