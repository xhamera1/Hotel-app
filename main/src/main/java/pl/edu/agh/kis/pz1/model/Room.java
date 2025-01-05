package pl.edu.agh.kis.pz1.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Room class represents a hotel room, including information about room number,
 * price per night, capacity, occupancy status, and guest details.
 */
public class Room {
    private int number;
    private BigDecimal pricePerNight;
    private int capacity;
    private boolean isOccupied;
    private List<Guest> guests;
    private LocalDate checkInDate;
    private LocalDate plannedCheckOutDate;
    private String description;
    private List<Reservation> reservations;

    /**
     * Constructor initializing all attributes of the room.
     *
     * @param number              Room number
     * @param pricePerNight       Price per night
     * @param capacity            Room capacity (number of guests)
     * @param checkInDate         Check-in date
     * @param isOccupied          Whether the room is occupied
     * @param guests              List of guests checked into the room
     * @param plannedCheckOutDate Planned check-out date
     * @param description         Room description
     */
    public Room(int number, BigDecimal pricePerNight, int capacity, LocalDate checkInDate, boolean isOccupied,
                List<Guest> guests, LocalDate plannedCheckOutDate, String description) {
        this.number = number;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.checkInDate = checkInDate;
        this.isOccupied = isOccupied;
        this.guests = guests;
        this.plannedCheckOutDate = plannedCheckOutDate;
        this.description = description;
        this.reservations = new ArrayList<>();
    }

    /**
     * Constructor initializing basic room attributes for loading from CSV.
     *
     * @param number        Room number
     * @param pricePerNight Price per night
     * @param capacity      Room capacity (number of guests)
     * @param description   Room description
     */
    public Room(int number, BigDecimal pricePerNight, int capacity, String description) {
        this.number = number;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.description = description;
        this.isOccupied = false;
        this.guests = new ArrayList<>();
        this.checkInDate = null;
        this.plannedCheckOutDate = null;
        this.reservations = new ArrayList<>();
    }

    /**
     * @return Room number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return Price per night.
     */
    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    /**
     * @return Room capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return Whether the room is occupied.
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * @return List of guests checked into the room.
     */
    public List<Guest> getGuests() {
        return guests;
    }

    /**
     * @return Check-in date.
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * @return Planned check-out date.
     */
    public LocalDate getPlannedCheckOutDate() {
        return plannedCheckOutDate;
    }

    /**
     * @return Room description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return List of reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the room's occupancy status.
     *
     * @param occupied New occupancy status.
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Sets the list of guests checked into the room.
     *
     * @param guests New guest list.
     */
    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    /**
     * Sets the check-in date.
     *
     * @param checkInDate New check-in date.
     */
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Sets the planned check-out date.
     *
     * @param plannedCheckOutDate New planned check-out date.
     */
    public void setPlannedCheckOutDate(LocalDate plannedCheckOutDate) {
        this.plannedCheckOutDate = plannedCheckOutDate;
    }

    /**
     * Adds a guest to the room if capacity allows.
     *
     * @param guest the Guest to be added to the room
     * @return true if the guest was successfully added; false if the room is at full capacity
     */
    public boolean addGuest(Guest guest) {
        if (guests == null) {
            throw new NullPointerException("Guests list is null");
        }
        if (guests.size() >= this.getCapacity()) {
            return false;
        }
        guests.add(guest);
        return true;
    }

    /**
     * Adds a new reservation to the room if it does not overlap with existing reservations.
     *
     * @param guests        the list of guests for the reservation
     * @param checkInDate   the check-in date for the new reservation
     * @param checkOutDate  the check-out date for the new reservation
     * @return true if the reservation was successfully added, false if there was a conflict
     */
    public boolean addReservation(List<Guest> guests, LocalDate checkInDate, LocalDate checkOutDate) {
        ReservationPeriod newPeriod = new ReservationPeriod(checkInDate, checkOutDate);

        for (Reservation reservation : reservations) {
            if (reservation.getPeriod().overlapsWith(newPeriod)) {
                return false;
            }
        }

        reservations.add(new Reservation(guests, newPeriod));
        return true;
    }

    /**
     * Returns the floor on which the room is located.
     *
     * @return Floor number for this room.
     */
    public int level() {
        return this.number / 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return number == room.number;
    }



    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
