/**
 * Use Case 2: Basic Room Types & Static Availability
 * This version introduces Abstract Classes, Inheritance, and Polymorphism.
 * @version 2.0
 */

// 1. Abstract Class - Defines the "General Concept" of a Room
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }

    // Abstract method: Each subclass MUST define its own features
    public abstract void displayFeatures();
}

// 2. Inheritance - Concrete specialized Room types
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 100.0); }
    @Override
    public void displayFeatures() {
        System.out.println("Features: 1 Single Bed, High-speed WiFi, Work Desk");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 150.0); }
    @Override
    public void displayFeatures() {
        System.out.println("Features: 2 Double Beds, High-speed WiFi, Smart TV");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 300.0); }
    @Override
    public void displayFeatures() {
        System.out.println("Features: 1 King Bed, Living Area, Mini Bar, City View");
    }
}

// 3. Main Application Entry Point
public class Hotel {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       BOOK MY STAY APP - VERSION 2.0");
        System.out.println("=================================================");

        // Polymorphism: Handling different room objects through the 'Room' reference
        Room sRoom = new SingleRoom();
        Room dRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability: Representing stock with simple variables
        int singleAvailable = 10;
        int doubleAvailable = 7;
        int suiteAvailable = 3;

        // Displaying Room Details and Availability
        printRoomDetails(sRoom, singleAvailable);
        printRoomDetails(dRoom, doubleAvailable);
        printRoomDetails(suite, suiteAvailable);

        System.out.println("=================================================");
    }

    /**
     * Helper method to demonstrate uniform handling of Room objects
     */
    private static void printRoomDetails(Room room, int count) {
        System.out.println("Room Type: " + room.getType());
        System.out.println("Price per Night: $" + room.getPrice());
        room.displayFeatures();
        System.out.println("Current Availability: " + count + " units");
        System.out.println("-------------------------------------------------");
    }
}