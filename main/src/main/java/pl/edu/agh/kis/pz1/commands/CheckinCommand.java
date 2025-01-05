package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.model.Guest;
import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The CheckinCommand class implements the functionality to check in a guest into a specified room.
 * It records the check-in date and planned check-out date, and allows the user to specify the duration of stay.
 * Additional guests can be added if the room's capacity allows.
 */
public class CheckinCommand extends Command {

    private Hotel hotel;

    /**
     * Constructs a CheckinCommand with the specified Hotel instance.
     *
     * @param hotel the Hotel instance where guests will be checked in
     */
    public CheckinCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Executes the check-in command by registering a guest into a specific room.
     * Prompts the user to input a room number, verifies the room's availability for the desired period,
     * and collects guest information to register the main guest. If the room has additional capacity,
     * it further prompts to add additional guests until the room reaches its full capacity or the user
     * decides not to add more guests.
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

            System.out.println("Enter data of main guest");

            System.out.print("First name: ");
            String firstName = sc.nextLine().trim();

            System.out.print("Last name: ");
            String lastName = sc.nextLine().trim();

            try {
                System.out.print("Enter check-in date (YYYY-MM-DD) or leave blank for today: ");
                String checkInDateInput = sc.nextLine();
                LocalDate checkInDate = checkInDateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(checkInDateInput);

                System.out.print("Enter duration of stay in days: ");
                int durationOfStay = sc.nextInt();
                sc.nextLine();
                LocalDate plannedCheckOutDate = checkInDate.plusDays(durationOfStay);

                List<Guest> guests = new ArrayList<>();
                Guest mainGuest = new Guest(firstName, lastName, true);
                guests.add(mainGuest);

                int roomCapacity = room.getCapacity();
                System.out.println("This room has a capacity of " + roomCapacity);

                if (roomCapacity > 1) {
                    int currentGuests = 1;

                    while (currentGuests < roomCapacity) {
                        System.out.print("Do you want to add an additional guest to this room? yes/no: ");
                        String response = sc.nextLine().trim().toLowerCase();

                        if (response.equals("no")) {
                            break;
                        } else if (response.equals("yes")) {
                            System.out.println("Enter data of additional guest");
                            System.out.print("First name: ");
                            String additionalFirstName = sc.nextLine().trim();
                            System.out.print("Last name: ");
                            String additionalLastName = sc.nextLine().trim();

                            Guest additionalGuest = new Guest(additionalFirstName, additionalLastName, false);
                            guests.add(additionalGuest);
                            currentGuests++;

                            System.out.println("Guest added. Current number of guests: " + currentGuests);
                        } else {
                            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
                        }
                    }
                }

                boolean reservationAdded = room.addReservation(guests, checkInDate, plannedCheckOutDate);
                if (!reservationAdded) {
                    System.out.println("The room is already booked for the requested dates.");
                    return;
                }

                room.setGuests(guests);
                room.setCheckInDate(checkInDate);
                room.setPlannedCheckOutDate(plannedCheckOutDate);

                if (checkInDate.equals(LocalDate.now()) || checkInDate.isBefore(LocalDate.now())) {
                    room.setOccupied(true);
                }

                System.out.println("Successfully checked in.");
            } catch (Exception e) {
                System.out.println("Invalid date or duration of stay.");
            }
        } catch (Exception e) {
            System.out.println("Invalid room number.");
        }
    }

}


