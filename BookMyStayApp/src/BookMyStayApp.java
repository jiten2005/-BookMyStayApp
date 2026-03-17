import java.util.HashMap;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author Jiten
 * @version 3.1
 */

// Inventory class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to display inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType));
        }
    }

    // Method to get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Version 3.1");
        System.out.println(" Centralized Inventory System");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println();

        // Example update
        inventory.updateAvailability("Single Room", 4);

        System.out.println("After Updating Single Room Availability:");
        inventory.displayInventory();
    }
}