import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Room Inventory Management
 * Introduces HashMap to manage room availability state.
 * @version 3.0
 */

// The Inventory Manager - Encapsulates the HashMap logic
class RoomInventory {
    // HashMap<RoomType, Count> provides O(1) lookup and updates
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    // Initialize or Update room counts
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Retrieve availability for a specific type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Controlled update: Reduce count when a booking happens (simplified)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }

    // Display the full state of the inventory
    public void displayInventory() {
        System.out.println("Current Inventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

public class Hotel {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       BOOK MY STAY APP - VERSION 3.0");
        System.out.println("=================================================");

        // 1. Initialize the Centralized Inventory
        RoomInventory hotelInventory = new RoomInventory();

        // 2. Register Room Types (Populating the HashMap)
        hotelInventory.addRoomType("Single Room", 10);
        hotelInventory.addRoomType("Double Room", 7);
        hotelInventory.addRoomType("Suite Room", 3);

        // 3. Display Initial State
        hotelInventory.displayInventory();
        System.out.println("-------------------------------------------------");

        // 4. Demonstrate a controlled update
        System.out.println("Updating Suite Room availability to 2...");
        hotelInventory.updateAvailability("Suite Room", 2);

        // 5. Final Check
        System.out.println("Verification: Suite Rooms left = " +
                hotelInventory.getAvailability("Suite Room"));

        System.out.println("=================================================");
    }
}