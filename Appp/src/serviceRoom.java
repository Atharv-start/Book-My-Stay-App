import java.util.*;

// Reservation class (simplified with ID)
class Reservati {
    private String reservationId;
    private String guestName;

    public Reservati(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Add-On Service class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service +
                " to Reservation ID: " + reservationId);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (Service s : getServices(reservationId)) {
            total += s.getCost();
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main class
public class serviceRoom
{
    public static void main(String[] args) {

        // Sample reservations (already confirmed in UC6)
        Reservation r1 = new Reservation("RES-101", "Alice");
        Reservation r2 = new Reservation("RES-102", "Bob");

        // Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(r1.getReservationId(), new Service("Breakfast", 500));
        manager.addService(r1.getReservationId(), new Service("Airport Pickup", 1200));

        manager.addService(r2.getReservationId(), new Service("Extra Bed", 800));

        // Display services
        manager.displayServices(r1.getReservationId());
        manager.displayServices(r2.getReservationId());
    }
}