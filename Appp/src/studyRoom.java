import java.util.LinkedList;
import java.util.Queue;

// Class representing a booking request
class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
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

    @Override
    public String toString() {
        return "Reservation [Guest=" + guestName +
                ", RoomType=" + roomType +
                ", Nights=" + nights + "]";
    }
}

// Booking Request Queue Manager
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    // View next request without removing
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    // Process request (dequeue)
    public Reservation processRequest() {
        return requestQueue.poll();
    }

    // Display all pending requests
    public void displayQueue() {
        if (requestQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        System.out.println("\nPending Booking Requests:");
        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }
}

// Main class
public class studyRoom {
    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulating incoming booking requests
        queue.addRequest(new Reservation("Alice", "Deluxe", 2));
        queue.addRequest(new Reservation("Bob", "Standard", 3));
        queue.addRequest(new Reservation("Charlie", "Suite", 1));

        // Display all queued requests
        queue.displayQueue();

        // Peek at next request
        System.out.println("\nNext request to process: " + queue.peekNextRequest());

        // Process requests in FIFO order
        System.out.println("\nProcessing Requests:");
        while (queue.peekNextRequest() != null) {
            Reservation processed = queue.processRequest();
            System.out.println("Processed: " + processed);
        }

        // Final state
        queue.displayQueue();
    }
}