package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Reservation;
import pl.edu.agh.kis.pz1.model.ReservationPeriod;
import pl.edu.agh.kis.pz1.model.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The CheckOutCommand class handles the check-out process for a room.
 * It calculates the total cost based on the stay duration and updates the room's status.
 */
public class CheckOutCommand extends Command {
    private Hotel hotel;

    /**
     * Constructs a CheckOutCommand with the specified Hotel instance.
     *
     * @param hotel the Hotel instance where the check-out will be processed
     */
    public CheckOutCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Executes the check-out command by processing the check-out for a specified room.
     * The command prompts the user to input the room number, retrieves the current reservation
     * for the room, calculates the total cost of the stay based on the duration, and updates the room status.
     * If no reservation matches the current date, an error message is displayed.
     */
    @Override
    public void execute() {
        System.out.print("Enter room number: ");
        Scanner sc = new Scanner(System.in);
        try {
            int roomNr = sc.nextInt();
            sc.nextLine();

            Room room = hotel.getRooms().get(roomNr);
            if (room == null) {
                System.out.println("Room not found.");
                return;
            }

            LocalDate currentDate = LocalDate.now();
            Reservation currentReservation = null;


            // iterujemuy po rezerwacjach by dostac rezerwacje ktora jest teraz
            Iterator<Reservation> iterator = room.getReservations().iterator();
            while (iterator.hasNext()) {
                Reservation reservation = iterator.next();
                ReservationPeriod period = reservation.getPeriod();

                if (!currentDate.isBefore(period.getCheckInDate()) && !currentDate.isAfter(period.getCheckOutDate())) {
                    currentReservation = reservation;
                    iterator.remove();
                    break;
                }
            }

            if (currentReservation == null) {
                System.out.println("No reservation period contains the current date.");
                return;
            }

            LocalDate checkInDate = currentReservation.getPeriod().getCheckInDate();
            long daysBetween = ChronoUnit.DAYS.between(checkInDate, currentDate);
            if (daysBetween < 1) {
                daysBetween = 0;
            }

            BigDecimal pricePerNight = room.getPricePerNight();
            BigDecimal totalCost = pricePerNight.multiply(BigDecimal.valueOf(daysBetween));

            System.out.println("This room costs " + pricePerNight + "$. You have stayed in this room for " +
                    daysBetween + " days. The total cost is " + totalCost + "$.");

            room.setOccupied(false);
            room.setCheckInDate(null);
            room.setPlannedCheckOutDate(null);
            room.setGuests(new ArrayList<>());

            System.out.println("Successfully checked out.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
