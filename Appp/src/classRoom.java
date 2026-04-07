import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reserva {
    private String guestName;
    private String roomType;

    public Reserva(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service with validation
class InventoryServic{
    private Map<String, Integer> inventory;

    public InventoryServic() {
        inventory = new HashMap<>();
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void decrementInventory(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("Cannot decrement. Inventory already zero for: " + roomType);
        }
        inventory.put(roomType, count - 1);
    }
}

// Booking Service with validation + error handling
class BookingServic {

    private InventoryService inventory;

    public BookingServic(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {
        try {
            // Fail-fast validations
            if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            if (reservation.getRoomType() == null || reservation.getRoomType().isEmpty()) {
                throw new InvalidBookingException("Room type cannot be empty");
            }

            // Validate room type
            inventory.validateRoomType(reservation.getRoomType());

            // Check availability
            inventory.checkAvailability(reservation.getRoomType());

            // Allocate (update inventory)
            inventory.decrementInventory(reservation.getRoomType());

            System.out.println("Booking SUCCESS for " + reservation.getGuestName() +
                    " | Room Type: " + reservation.getRoomType());

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking FAILED: " + e.getMessage());
        }
    }
}

// Main class
public class classRoom {
    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService((Queue<Reservation>) inventory);

        // Test cases
        Reservation r1 = new Reservation("Alice", "Deluxe");   // valid
        Reservation r2 = new Reservation("", "Standard");      // invalid name
        Reservation r3 = new Reservation("Bob", "Suite");      // invalid type
        Reservation r4 = new Reservation("Charlie", "Standard"); // valid
        Reservation r5 = new Reservation("David", "Standard");   // should fail (no inventory)

        bookingService.processBooking(r1);
        bookingService.processBooking(r2);
        bookingService.processBooking(r3);
        bookingService.processBooking(r4);
        bookingService.processBooking(r5);
    }
}