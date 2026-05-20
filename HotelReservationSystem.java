import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    String category;
    boolean isBooked;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }
}

class Booking {
    String customerName;
    int roomNumber;

    Booking(String customerName, int roomNumber) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Add rooms
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Deluxe"));
        rooms.add(new Room(103, "Suite"));
        rooms.add(new Room(104, "Standard"));
        rooms.add(new Room(105, "Deluxe"));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom(sc);
                    break;

                case 3:
                    cancelReservation(sc);
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }

    // View Rooms
    static void viewRooms() {

        System.out.println("\n===== ROOM DETAILS =====");

        for (Room room : rooms) {

            String status = room.isBooked ? "Booked" : "Available";

            System.out.println(
                    "Room " + room.roomNumber +
                    " | " + room.category +
                    " | " + status);
        }
    }

    // Book Room
    static void bookRoom(Scanner sc) {

        System.out.print("Enter your name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNo = sc.nextInt();

        for (Room room : rooms) {

            if (room.roomNumber == roomNo) {

                if (!room.isBooked) {

                    room.isBooked = true;

                    bookings.add(new Booking(name, roomNo));

                    saveBookingToFile(name, roomNo);

                    System.out.println("Payment Successful!");
                    System.out.println("Room booked successfully.");

                } else {
                    System.out.println("Room already booked!");
                }

                return;
            }
        }

        System.out.println("Room not found!");
    }

    // Cancel Reservation
    static void cancelReservation(Scanner sc) {

        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();

        for (Room room : rooms) {

            if (room.roomNumber == roomNo && room.isBooked) {

                room.isBooked = false;

                for (Booking booking : bookings) {

                    if (booking.roomNumber == roomNo) {
                        bookings.remove(booking);
                        break;
                    }
                }

                System.out.println("Reservation cancelled successfully.");
                return;
            }
        }

        System.out.println("Booking not found!");
    }

    // View Bookings
    static void viewBookings() {

        System.out.println("\n===== BOOKING DETAILS =====");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking booking : bookings) {

            System.out.println(
                    "Customer: " + booking.customerName +
                    " | Room Number: " + booking.roomNumber);
        }
    }

    // Save Booking to File
    static void saveBookingToFile(String name, int roomNo) {

        try {

            FileWriter writer = new FileWriter("bookings.txt", true);

            writer.write(
                    "Customer: " + name +
                    " | Room Number: " + roomNo + "\n");

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving booking.");
        }
    }
}