import java.io.*;
import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void setRoomAvailability(Map<String, Integer> availability) {
        this.roomAvailability = availability;
    }
}


class PersistenceService {


    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            System.out.println("Inventory saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        Map<String, Integer> loadedMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    loadedMap.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
            inventory.setRoomAvailability(loadedMap);
            System.out.println("Inventory restored successfully from " + filePath);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        PersistenceService persistence = new PersistenceService();
        String storageFile = "inventory_state.txt";

        System.out.println("System Recovery Process Started");
        System.out.println("------------------------------");

        persistence.loadInventory(inventory, storageFile);

        System.out.println("\nCurrent Inventory:");
        inventory.getRoomAvailability().forEach((type, count) ->
                System.out.println(type + ": " + count));

        System.out.println("\nProcessing a sample booking for 'Single' room...");
        int currentCount = inventory.getRoomAvailability().get("Single");
        if (currentCount > 0) {
            inventory.getRoomAvailability().put("Single", currentCount - 1);
        }

        persistence.saveInventory(inventory, storageFile);

        System.out.println("\nSystem operation complete. Check " + storageFile + " for saved state.");
    }
}