import java.util.*;

// Reservation class (same as UC5)
class Reservatio {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservatio(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> roomInventory;

    public InventoryService() {
        roomInventory = new HashMap<>();
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementInventory(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " -> " + roomInventory.get(type));
        }
    }
}

// Booking Service
class BookingService {
    private Queue<Reservation> requestQueue;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomAllocations;

    public BookingService(Queue<Reservation> queue) {
        this.requestQueue = queue;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 3).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    // Process booking requests
    public void processBookings(InventoryService inventory) {
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            String roomType = request.getRoomType();

            System.out.println("\nProcessing request for: " + request.getGuestName());

            if (inventory.isAvailable(roomType)) {
                String roomId = generateRoomId(roomType);

                // Store allocated ID
                allocatedRoomIds.add(roomId);

                // Map room type to allocated IDs
                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                // Update inventory immediately
                inventory.decrementInventory(roomType);

                System.out.println("Booking CONFIRMED for " + request.getGuestName() +
                        " | Room Type: " + roomType +
                        " | Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " + request.getGuestName() +
                        " | No rooms available for type: " + roomType);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}

// Main Class
public class nightRoom {
    public static void main(String[] args) {

        // Step 1: Create booking queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Alice", "Deluxe", 2));
        queue.offer(new Reservation("Bob", "Standard", 1));
        queue.offer(new Reservation("Charlie", "Suite", 1));
        queue.offer(new Reservation("David", "Suite", 2)); // Should fail

        // Step 2: Initialize services
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(queue);

        // Step 3: Process bookings
        bookingService.processBookings(inventory);

        // Step 4: Show results
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}