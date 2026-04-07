import java.util.*;

// Reservation class
class Reservat {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservat(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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

    @Override
    public String toString() {
        return "ReservationID=" + reservationId +
                ", Guest=" + guestName +
                ", RoomType=" + roomType;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary Report ---");

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + " bookings: " + roomTypeCount.get(type));
        }

        System.out.println("Total bookings: " + reservations.size());
    }
}

// Main class
public class partRoom {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings (from UC6)
        history.addReservation(new Reservation("RES-101", "Alice", "Deluxe"));
        history.addReservation(new Reservation("RES-102", "Bob", "Standard"));
        history.addReservation(new Reservation("RES-103", "Charlie", "Suite"));
        history.addReservation(new Reservation("RES-104", "David", "Deluxe"));

        // Display history
        reportService.displayAllBookings(history.getAllReservations());

        // Generate report
        reportService.generateSummary(history.getAllReservations());
    }
}