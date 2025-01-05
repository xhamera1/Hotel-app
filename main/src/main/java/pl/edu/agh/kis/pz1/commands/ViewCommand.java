package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.model.Guest;
import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Reservation;
import pl.edu.agh.kis.pz1.model.Room;

import java.util.Scanner;

/**
 * The ViewCommand class implements the functionality to view the details of a specified room.
 * It allows users to enter a room number and retrieve information such as room description,
 * price, capacity, occupancy status, guest details, and check-in/check-out dates if applicable.
 */
public class ViewCommand extends Command {
    private Hotel hotel;

    /**
     * Constructs a ViewCommand with the specified Hotel instance.
     *
     * @param hotel the Hotel instance from which room details will be retrieved
     */
    public ViewCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Executes the ViewCommand by prompting the user for a room number,
     * then retrieving and displaying details of the specified room.
     * If the room is not found, an error message is displayed.
     * If the room has reservations, it lists the guests and reservation periods.
     */
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter room number: ");
        try {
            int roomNr = sc.nextInt();
            sc.nextLine();
            Room room = hotel.getRooms().get(roomNr);
            if (room == null) {
                System.out.println("Room not found.");
                return;
            }

            System.out.println("Room number: " + room.getNumber());
            System.out.println("Room description: " + room.getDescription());
            System.out.println("Room price per night: " + room.getPricePerNight());
            System.out.println("Room capacity: " + room.getCapacity());

            if (room.getReservations().isEmpty()) {
                System.out.println("No reservations found for this room.");
            } else {
                System.out.println("Reservations:");
                for (Reservation reservation : room.getReservations()) {
                    System.out.println("Guests in the reservation:");
                    for (Guest guest : reservation.getGuests()) {
                        String guestType = guest.isMainGuest() ? "Main guest" : "Guest";
                        System.out.println(guestType + ": " + guest.getFirstName() + " " + guest.getLastName());
                    }
                    System.out.println("Reservation period: " +
                            reservation.getPeriod().getCheckInDate() + " to " +
                            reservation.getPeriod().getCheckOutDate());
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid room number");
        }
    }
}
