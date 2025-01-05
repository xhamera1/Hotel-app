package pl.edu.agh.kis.pz1.model;

import java.util.List;

/**
 * The Reservation class represents a reservation for a hotel room.
 * It includes a list of guests and a reservation period.
 */
public class Reservation {
    private final List<Guest> guests;
    private final ReservationPeriod period;

    public Reservation(List<Guest> guests, ReservationPeriod period) {
        if (guests == null || period == null) {
            throw new NullPointerException("Guests and period cannot be null");
        }
        this.guests = List.copyOf(guests);
        this.period = period;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public ReservationPeriod getPeriod() {
        return period;
    }
}

