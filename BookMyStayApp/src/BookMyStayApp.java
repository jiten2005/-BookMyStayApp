import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Reservation Model
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        isActive = false;
    }

    @Override
    public String toString() {
        return reservationId + " | " + roomType + " | RoomID: " + roomId + " | Active: " + isActive;
    }
}

// Inventory Management
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    private int roomCounter = 1;

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    public String allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            return null;
        }

        inventory.put(roomType, available - 1);
        return roomType.substring(0, 1) + (roomCounter++);
    }

    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory Status:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Reservation> reservations;
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Reservation> reservations) {
        this.reservations = reservations;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId, RoomInventory inventory)
            throws CancellationException {

        if (!reservations.containsKey(reservationId)) {
            throw new CancellationException("Reservation does not exist.");
        }

        Reservation res = reservations.get(reservationId);

        if (!res.isActive()) {
            throw new CancellationException("Reservation already cancelled.");
        }

        // Step 1: Push room ID to rollback stack (LIFO tracking)
        rollbackStack.push(res.getRoomId());

        // Step 2: Restore inventory
        inventory.releaseRoom(res.getRoomType());

        // Step 3: Mark reservation as cancelled
        res.cancel();

        System.out.println("Cancellation successful for Reservation: " + reservationId);
        System.out.println("Rolled back Room ID: " + rollbackStack.peek());
    }
}

// Main Class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        Map<String, Reservation> reservations = new HashMap<>();
        CancellationService cancellationService = new CancellationService(reservations);

        int reservationCounter = 1;

        while (true) {
            try {
                System.out.println("\n--- Booking System ---");
                System.out.println("1. Book Room");
                System.out.println("2. Cancel Booking");
                System.out.println("3. View Reservations");
                System.out.println("4. View Inventory");
                System.out.println("5. Exit");

                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Enter Room Type (Single/Double): ");
                        String roomType = scanner.nextLine();

                        String roomId = inventory.allocateRoom(roomType);

                        if (roomId == null) {
                            System.out.println("No rooms available.");
                            break;
                        }

                        String reservationId = "R" + (reservationCounter++);
                        Reservation res = new Reservation(reservationId, roomType, roomId);
                        reservations.put(reservationId, res);

                        System.out.println("Booking Confirmed: " + res);
                        break;

                    case 2:
                        System.out.print("Enter Reservation ID to cancel: ");
                        String cancelId = scanner.nextLine();

                        cancellationService.cancelBooking(cancelId, inventory);
                        break;

                    case 3:
                        System.out.println("\nAll Reservations:");
                        for (Reservation r : reservations.values()) {
                            System.out.println(r);
                        }
                        break;

                    case 4:
                        inventory.displayInventory();
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (CancellationException e) {
                System.out.println("Cancellation Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }
        }
    }
}