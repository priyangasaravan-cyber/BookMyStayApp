import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public RoomInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
    }


    public synchronized boolean allocateRoom(String roomType) {
        int available = roomAvailability.getOrDefault(roomType, 0);
        if (available > 0) {
            try { Thread.sleep(10); } catch (InterruptedException e) {}

            roomAvailability.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public Map<String, Integer> getFinalInventory() {
        return roomAvailability;
    }
}


class BookingRequestQueue {
    private Queue<String> queue = new LinkedList<>();

    public synchronized void addRequest(String guestName) {
        queue.offer(guestName);
    }

    public synchronized String getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}


class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue queue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(BookingRequestQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            String guest;
            synchronized (queue) {
                if (queue.isEmpty()) break;
                guest = queue.getNextRequest();
            }

            if (guest != null) {
                if (inventory.allocateRoom("Single")) {
                    System.out.println(Thread.currentThread().getName() +
                            " confirmed booking for: " + guest);
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " failed booking for: " + guest + " (Sold Out)");
                }
            }
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest("Abhi");
        queue.addRequest("Subha");
        queue.addRequest("Vanmathi");
        queue.addRequest("Karthik");
        queue.addRequest("Priya");
        queue.addRequest("Anbu");

        System.out.println("Concurrent Booking Simulation Started...");
        System.out.println("---------------------------------------");

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory), "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("\nRemaining Inventory:");
        inventory.getFinalInventory().forEach((type, count) ->
                System.out.println(type + ": " + count));
    }
}