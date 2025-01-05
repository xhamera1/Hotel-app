package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.model.Guest;
import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Reservation;
import pl.edu.agh.kis.pz1.model.Room;

/**
 * The ListCommand class implements the logic for listing all rooms in a hotel.
 * When executed, it displays detailed information about each room, including
 * its number, description, price, capacity, and reservation details (if any).
 */
public class ListCommand extends Command {
    private Hotel hotel;

    /**
     * Constructs a ListCommand with the specified hotel.
     * The hotel object contains all rooms to be listed when the command is executed.
     *
     * @param hotel The hotel instance from which room details are retrieved.
     */
    public ListCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Executes the list command, displaying all rooms and their details.
     */
    @Override
    public void execute() {
        for (Room room : hotel.getRooms().getValues()) {
            // Display the room's number, description, price, and capacity
            System.out.println("\nRoom number: " + room.getNumber());
            System.out.println("Room description: " + room.getDescription());
            System.out.println("Room price per night: " + room.getPricePerNight());
            System.out.println("Room capacity: " + room.getCapacity());
            // sprawdzma czy w tym pokoju sa jakies rezerwacje
            checkOccupied(room);
        }
    }

    /**
     * Checks if a room is occupied and displays the reservation details if it is.
     * If the room is not occupied, it displays a message indicating the room's availability.
     *
     * @param room The room to check for occupancy and display details.
     */
    public void checkOccupied(Room room) {
        if (room.getReservations().isEmpty()) {
            System.out.println("The room is not occupied at the moment.");
        } else {
            System.out.println("Reservation details:");
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
    }
}
