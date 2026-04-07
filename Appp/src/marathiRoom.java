import java.util.*;

// Reservation class
class Reserv{
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reserv(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        this.isActive = false;
    }
}

// Inventory Service
class InventoryServi {
    private Map<String, Integer> inventory;

    public InventoryServi() {
        inventory = new HashMap<>();
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
    }

    public void incrementInventory(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistor {
    private Map<String, Reservation> reservations;

    public BookingHistor() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }
}

// Cancellation Service
class CancellationService {
    private BookingHistory history;
    private InventoryService inventory;
    private Stack<String> rollbackStack;

    public CancellationService(BookingHistory history, InventoryService inventory) {
        this.history = history;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        Reservation r = history.getReservation(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation FAILED: Reservation does not exist.");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation FAILED: Already cancelled.");
            return;
        }

        // Step 1: Push room ID to rollback stack
        rollbackStack.push(r.getRoomId());

        // Step 2: Restore inventory
        inventory.incrementInventory(r.getRoomType());

        // Step 3: Mark reservation cancelled
        r.cancel();

        System.out.println("Cancellation SUCCESS for " + r.getGuestName() +
                " | Room ID released: " + r.getRoomId());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// Main class
public class marathiRoom {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings (from UC6)
        Reservation r1 = new Reservation("RES-101", "Alice", "Deluxe", "DEL-111");
        Reservation r2 = new Reservation("RES-102", "Bob", "Standard", "STD-222");

        history.addReservation(r1);
        history.addReservation(r2);

        CancellationService cancellationService = new CancellationService(history, inventory);

        // Valid cancellation
        cancellationService.cancelBooking("RES-101");

        // Invalid cases
        cancellationService.cancelBooking("RES-101"); // already cancelled
        cancellationService.cancelBooking("RES-999"); // does not exist

        // Another valid cancellation
        cancellationService.cancelBooking("RES-102");

        // Show rollback stack
        cancellationService.displayRollbackStack();

        // Show inventory
        inventory.displayInventory();
    }
}