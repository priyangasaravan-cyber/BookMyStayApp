import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
}

class AddOnServiceManager {
    private Map<String, List<Service>> selectedServices;

    public AddOnServiceManager() {
        this.selectedServices = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        // If the ID isn't in the map, create a new list for it
        selectedServices.putIfAbsent(reservationId, new ArrayList<>());
        selectedServices.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = selectedServices.get(reservationId);
        if (services == null) return 0.0;

        double total = 0.0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public List<Service> getServicesForReservation(String reservationId) {
        return selectedServices.getOrDefault(reservationId, new ArrayList<>());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        String resId1 = "Single-1";
        String resId2 = "Suite-1";

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        Service breakfast = new Service("Breakfast", 250.0);
        Service wifi = new Service("High-Speed WiFi", 150.0);
        Service gym = new Service("Gym Access", 300.0);

        serviceManager.addService(resId1, breakfast);
        serviceManager.addService(resId1, wifi);

        serviceManager.addService(resId2, breakfast);
        serviceManager.addService(resId2, gym);

        System.out.println("Add-On Service Selection");
        System.out.println("------------------------");

        displaySummary(resId1, serviceManager);
        displaySummary(resId2, serviceManager);
    }

    private static void displaySummary(String id, AddOnServiceManager manager) {
        System.out.println("Reservation ID: " + id);
        System.out.println("Selected Services:");
        for (Service s : manager.getServicesForReservation(id)) {
            System.out.println("- " + s.getServiceName() + ": " + s.getPrice());
        }
        System.out.println("Total Add-On Cost: " + manager.calculateTotalServiceCost(id));
        System.out.println();
    }
}