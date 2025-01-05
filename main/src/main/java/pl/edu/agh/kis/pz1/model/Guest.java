package pl.edu.agh.kis.pz1.model;

import java.util.Objects;

/**
 * The Guest class represents a hotel guest, including personal details, contact information,
 * and their status as the main guest.
 */
public class Guest {
    private String firstName;
    private String lastName;
    private boolean isMainGuest;

    /**
     * Constructs a Guest object with the specified details.
     *
     * @param firstName   The first name of the guest.
     * @param lastName    The last name of the guest.
     * @param isMainGuest Whether this guest is the main guest.
     */
    public Guest(String firstName, String lastName, boolean isMainGuest) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isMainGuest = isMainGuest;
    }

    /**
     * Gets the first name of the guest.
     *
     * @return The first name of the guest.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the guest.
     *
     * @param firstName The new first name of the guest.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the guest.
     *
     * @return The last name of the guest.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the guest.
     *
     * @param lastName The new last name of the guest.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns whether the guest is the main guest.
     *
     * @return true if the guest is the main guest, false otherwise.
     */
    public boolean isMainGuest() {
        return isMainGuest;
    }

    /**
     * Sets whether the guest is the main guest.
     *
     * @param mainGuest The new status of whether this guest is the main guest.
     */
    public void setMainGuest(boolean mainGuest) {
        isMainGuest = mainGuest;
    }

    @Override
    /**
     * Compares this Guest object with another object for equality. Two guests are considered equal
     * if they have the same first name, last name, and main guest status.
     *
     * @param o The object to compare with this Guest.
     * @return true if the objects are equal based on first name, last name, and main guest status; false otherwise.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return isMainGuest == guest.isMainGuest &&
                Objects.equals(firstName, guest.firstName) &&
                Objects.equals(lastName, guest.lastName);
    }

    @Override
    /**
     * Returns a hash code value for this Guest, based on the first name, last name, and main guest status.
     * This ensures that two equal Guest objects (according to equals method) will have the same hash code.
     *
     * @return The hash code value for this Guest.
     */
    public int hashCode() {
        return Objects.hash(firstName, lastName, isMainGuest);
    }


}
