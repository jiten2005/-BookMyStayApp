import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request handling using Queue (FIFO).
 *
 * @author Jiten
 * @version 5.0
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

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Booking Queue");
        System.out.println("=================================");

        // Queue for booking requests
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Add booking requests
        bookingQueue.add(new Reservation("Amit", "Single Room"));
        bookingQueue.add(new Reservation("Priya", "Suite Room"));
        bookingQueue.add(new Reservation("Rahul", "Double Room"));

        System.out.println("Booking Requests in Arrival Order:");
        System.out.println();

        // Display queue without removing
        for (Reservation r : bookingQueue) {
            r.displayReservation();
            System.out.println();
        }
    }
}