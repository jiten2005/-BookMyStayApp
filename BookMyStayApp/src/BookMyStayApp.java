import java.io.*;
import java.util.*;

// Reservation Model (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State Wrapper
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> reservations;

    public SystemState(Map<String, Integer> inventory, List<Reservation> reservations) {
        this.inventory = inventory;
        this.reservations = reservations;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Corrupted data. Starting with safe defaults.");
        }

        return null;
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Load previous state if available
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> reservations;

        if (state != null) {
            inventory = state.inventory;
            reservations = state.reservations;
        } else {
            // Initialize fresh state
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 2);

            reservations = new ArrayList<>();
        }

        int counter = reservations.size() + 1;

        while (true) {
            System.out.println("\n--- Booking System ---");
            System.out.println("1. Book Room");
            System.out.println("2. View Reservations");
            System.out.println("3. View Inventory");
            System.out.println("4. Save & Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Room Type (Single/Double): ");
                    String roomType = scanner.nextLine();

                    int available = inventory.getOrDefault(roomType, 0);

                    if (available <= 0) {
                        System.out.println("No rooms available.");
                        break;
                    }

                    inventory.put(roomType, available - 1);

                    String id = "R" + counter++;
                    Reservation res = new Reservation(id, name, roomType);
                    reservations.add(res);

                    System.out.println("Booking confirmed: " + res);
                    break;

                case 2:
                    System.out.println("\nReservations:");
                    for (Reservation r : reservations) {
                        System.out.println(r);
                    }
                    break;

                case 3:
                    System.out.println("\nInventory:");
                    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                        System.out.println(entry.getKey() + " -> " + entry.getValue());
                    }
                    break;

                case 4:
                    // Save state before exit
                    PersistenceService.save(new SystemState(inventory, reservations));
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}