import java.util.HashMap;

/**
 * UseCase4RoomSearch
 *
 * Demonstrates room search using centralized inventory
 * without modifying system state.
 *
 * @author Jiten
 * @version 4.0
 */

// Abstract Room class
abstract class Room {
    protected double price;

    public Room(double price) {
        this.price = price;
    }

    abstract String getRoomType();

    public void displayDetails() {
        System.out.println("Room Type : " + getRoomType());
        System.out.println("Price     : ₹" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super(2000);
    }

    String getRoomType() {
        return "Single Room";
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(3500);
    }

    String getRoomType() {
        return "Double Room";
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(6000);
    }

    String getRoomType() {
        return "Suite Room";
    }
}

// Inventory class
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Room Search");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Read-only search
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println();
            }
        }
    }
}