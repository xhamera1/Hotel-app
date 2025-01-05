package pl.edu.agh.kis.pz1.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Reservation class.
 * Verifies the correct behavior of constructors and getters.
 */
class ReservationTest {

    @Test
    void testConstructorAndGetters() {
        // Create test data
        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("Jane", "Doe", false);
        List<Guest> guests = List.of(guest1, guest2);

        ReservationPeriod period = new ReservationPeriod(
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 5)
        );

        // Create Reservation instance
        Reservation reservation = new Reservation(guests, period);

        // Verify guests
        assertEquals(guests, reservation.getGuests(), "Guests list should match the one provided");
        assertEquals(2, reservation.getGuests().size(), "Guests list should contain 2 guests");
        assertTrue(reservation.getGuests().contains(guest1), "Guests list should contain guest1");
        assertTrue(reservation.getGuests().contains(guest2), "Guests list should contain guest2");

        // Verify reservation period
        assertEquals(period, reservation.getPeriod(), "Reservation period should match the one provided");
        assertEquals(LocalDate.of(2023, 10, 1), reservation.getPeriod().getCheckInDate(), "Check-in date should be 2023-10-01");
        assertEquals(LocalDate.of(2023, 10, 5), reservation.getPeriod().getCheckOutDate(), "Check-out date should be 2023-10-05");
    }

    @Test
    void testConstructorWithEmptyGuestsList() {
        List<Guest> guests = List.of();
        ReservationPeriod period = new ReservationPeriod(
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 5)
        );

        Reservation reservation = new Reservation(guests, period);

        // Verify that guests list is empty
        assertTrue(reservation.getGuests().isEmpty(), "Guests list should be empty");
    }

    @Test
    void testConstructorWithNullGuestsList() {
        ReservationPeriod period = new ReservationPeriod(
                LocalDate.of(2023, 10, 1),
                LocalDate.of(2023, 10, 5)
        );

        // Expect NullPointerException when guests list is null
        assertThrows(NullPointerException.class, () -> {
            new Reservation(null, period);
        });
    }

    @Test
    void testConstructorWithNullPeriod() {
        Guest guest = new Guest("John", "Doe", true);
        List<Guest> guests = List.of(guest);

        // Expect NullPointerException when period is null
        assertThrows(NullPointerException.class, () -> {
            new Reservation(guests, null);
        });
    }

}
