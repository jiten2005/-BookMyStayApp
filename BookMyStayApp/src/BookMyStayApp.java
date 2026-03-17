/**
 * UseCase2RoomInitialization
 *
 * Demonstrates abstract class, inheritance, polymorphism,
 * and static room availability in Hotel Booking System.
 *
 * @author Jiten
 * @version 2.1
 */

// Abstract class
abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Abstract method
    abstract String getRoomType();

    // Common method
    public void displayRoomDetails() {
        System.out.println("Room Type : " + getRoomType());
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : ₹" + price);
    }
}

// Single Room class
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 120, 2000);
    }

    String getRoomType() {
        return "Single Room";
    }
}

// Double Room class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 180, 3500);
    }

    String getRoomType() {
        return "Double Room";
    }
}

// Suite Room class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 300, 6000);
    }

    String getRoomType() {
        return "Suite Room";
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Version 2.1");
        System.out.println(" Room Availability System");
        System.out.println("=================================");

        // Polymorphism
        Room room1 = new SingleRoom();
        Room room2 = new DoubleRoom();
        Room room3 = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        room1.displayRoomDetails();
        System.out.println("Available : " + singleAvailable);
        System.out.println();

        room2.displayRoomDetails();
        System.out.println("Available : " + doubleAvailable);
        System.out.println();

        room3.displayRoomDetails();
        System.out.println("Available : " + suiteAvailable);
    }
}