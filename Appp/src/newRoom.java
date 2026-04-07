import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Resimplements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public void Res(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Wrapper class for system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state SAVED successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state LOADED successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Corrupted data. Starting with clean state.");
        }

        return null;
    }
}

// Main class
public class newRoom {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Try loading previous state
        SystemState state = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> history;

        if (state != null) {
            inventory = state.inventory;
            history = state.bookingHistory;
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);

            history = new ArrayList<>();
        }

        // Simulate new booking
        Reservation r1 = new Reservation("RES-201", "Alice", "Deluxe");
        history.add(r1);

        inventory.put("Deluxe", inventory.get("Deluxe") - 1);

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : history) {
            System.out.println(r);
        }

        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, history);
        persistence.save(newState);
    }
}