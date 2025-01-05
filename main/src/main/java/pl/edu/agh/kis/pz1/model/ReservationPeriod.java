package pl.edu.agh.kis.pz1.model;

import java.time.LocalDate;

/**
 * The ReservationPeriod class represents a period of reservation with check-in and check-out dates.
 * Provides functionality to determine if two reservation periods overlap.
 */
public class ReservationPeriod {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    /**
     * Creates a new ReservationPeriod with specified check-in and check-out dates.
     *
     * @param checkInDate  the date of check-in
     * @param checkOutDate the date of check-out
     */
    public ReservationPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new NullPointerException("Check-in and check-out dates cannot be null");
        }
        if (!checkInDate.isBefore(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Returns the check-in date of the reservation period.
     *
     * @return the check-in date
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * Returns the check-out date of the reservation period.
     *
     * @return the check-out date
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Checks if this reservation period overlaps with another reservation period.
     * Overlap occurs if the check-in date is before the other period's check-out date
     * and the check-out date is after the other period's check-in date.
     *
     * @param other the other reservation period to check against
     * @return true if the periods overlap, false otherwise
     */
    public boolean overlapsWith(ReservationPeriod other) {
        return (checkInDate.isBefore(other.getCheckOutDate()) && checkOutDate.isAfter(other.getCheckInDate()));
    }

    /**
     * Compares this reservation period with another object for equality.
     * Two ReservationPeriod objects are considered equal if both their check-in
     * and check-out dates are the same.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationPeriod that = (ReservationPeriod) o;

        if (!checkInDate.equals(that.checkInDate)) return false;
        return checkOutDate.equals(that.checkOutDate);
    }

    /**
     * Generates a hash code for this reservation period based on the check-in and check-out dates.
     * The hash code is computed using the hash codes of the dates and a prime multiplier.
     *
     * @return the hash code for this reservation period
     */
    @Override
    public int hashCode() {
        int result = checkInDate.hashCode();
        result = 31 * result + checkOutDate.hashCode();
        return result;
    }

    /**
     * Returns a string representation of the reservation period.
     * The format is "from [check-in date] to [check-out date]".
     *
     * @return the string representation of this reservation period
     */
    @Override
    public String toString() {
        return "from " + checkInDate.toString() + " to " + checkOutDate.toString();
    }
}
