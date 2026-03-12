import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 4: Room Search & Availability Check
 * Demonstrates Read-Only access and Defensive Programming.
 * @version 4.0
 */

// --- Domain Layer ---
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
}

class SingleRoom extends Room { public SingleRoom() { super("Single Room", 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double Room", 150.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super("Suite Room", 300.0); } }

// --- Inventory Layer (State Holder) ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) { inventory.put(type, count); }

    // Read-only access to availability
    public int getCount(String type) { return inventory.getOrDefault(type, 0); }

    public Map<String, Integer> getAllInventory() {
        // Returning a copy or the map itself for search purposes
        return inventory;
    }
}

// --- Service Layer (Search Logic) ---
class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomDetails = new HashMap<>();

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
        // Mapping types to objects to get pricing/details
        roomDetails.put("Single Room", new SingleRoom());
        roomDetails.put("Double Room", new DoubleRoom());
        roomDetails.put("Suite Room", new SuiteRoom());
    }

    public void performSearch() {
        System.out.println("Searching for available rooms...");
        boolean found = false;

        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {
            String type = entry.getKey();
            int availableCount = entry.getValue();

            // Validation Logic: Only show if availability > 0
            if (availableCount > 0) {
                Room details = roomDetails.get(type);
                System.out.println("-> " + type + " | Price: $" + details.getPrice() +
                        " | Available: " + availableCount);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Sorry, no rooms are currently available.");
        }
    }
}

// --- Execution Layer ---
public class Hotel {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       BOOK MY STAY APP - VERSION 4.0");
        System.out.println("=================================================");

        // 1. Setup Inventory
        RoomInventory hotelInventory = new RoomInventory();
        hotelInventory.addRoomType("Single Room", 5);
        hotelInventory.addRoomType("Double Room", 0); // This should be filtered out
        hotelInventory.addRoomType("Suite Room", 2);

        // 2. Initialize Search Service
        SearchService searchService = new SearchService(hotelInventory);

        // 3. Perform Search
        searchService.performSearch();

        System.out.println("-------------------------------------------------");
        System.out.println("Search Complete. System state remains unchanged.");
        System.out.println("=================================================");
    }
}