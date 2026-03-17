import java.util.LinkedList;
import java.util.Queue;

import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and safe room allocation.
 *
 * @author Jiten
 * @version 6.0
 */

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
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

// Inventory class
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Room Allocation");
        System.out.println("=================================");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Amit", "Single Room"));
        bookingQueue.add(new Reservation("Priya", "Suite Room"));
        bookingQueue.add(new Reservation("Rahul", "Single Room"));

        RoomInventory inventory = new RoomInventory();

        // Track allocated room IDs
        Set<String> allocatedRooms = new HashSet<>();

        // Map room type to allocated room IDs
        HashMap<String, Set<String>> roomAllocationMap = new HashMap<>();

        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();
            String roomType = r.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = roomType.substring(0, 1) + (allocatedRooms.size() + 1);

                if (!allocatedRooms.contains(roomId)) {
                    allocatedRooms.add(roomId);

                    roomAllocationMap.putIfAbsent(roomType, new HashSet<>());
                    roomAllocationMap.get(roomType).add(roomId);

                    inventory.updateAvailability(roomType);

                    System.out.println("Reservation Confirmed");
                    System.out.println("Guest Name : " + r.getGuestName());
                    System.out.println("Room Type  : " + roomType);
                    System.out.println("Room ID    : " + roomId);
                    System.out.println();
                }

            } else {
                System.out.println("No rooms available for " + r.getGuestName()
                        + " (" + roomType + ")");
                System.out.println();
            }
        }
    }
}