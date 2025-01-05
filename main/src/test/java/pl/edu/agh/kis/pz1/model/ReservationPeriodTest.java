package pl.edu.agh.kis.pz1.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the ReservationPeriod class.
 * Verifies the correct behavior of constructors, getters, overlapsWith method, equals, hashCode, and toString.
 */
class ReservationPeriodTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate checkIn = LocalDate.of(2023, 10, 1);
        LocalDate checkOut = LocalDate.of(2023, 10, 5);

        ReservationPeriod period = new ReservationPeriod(checkIn, checkOut);

        assertEquals(checkIn, period.getCheckInDate(), "Check-in date should match the one provided");
        assertEquals(checkOut, period.getCheckOutDate(), "Check-out date should match the one provided");
    }

    @Test
    void testConstructorWithInvalidDates() {
        LocalDate checkIn = LocalDate.of(2023, 10, 5);
        LocalDate checkOut = LocalDate.of(2023, 10, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReservationPeriod(checkIn, checkOut);
        });

        assertEquals("Check-in date must be before check-out date", exception.getMessage());
    }

    @Test
    void testOverlapsWithOverlappingPeriods() {
        ReservationPeriod period1 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5));
        ReservationPeriod period2 = new ReservationPeriod(LocalDate.of(2023, 10, 4), LocalDate.of(2023, 10, 8));

        assertTrue(period1.overlapsWith(period2), "Periods should overlap");
        assertTrue(period2.overlapsWith(period1), "Periods should overlap");
    }

    @Test
    void testOverlapsWithNonOverlappingPeriods() {
        ReservationPeriod period1 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 3));
        ReservationPeriod period2 = new ReservationPeriod(LocalDate.of(2023, 10, 4), LocalDate.of(2023, 10, 6));

        assertFalse(period1.overlapsWith(period2), "Periods should not overlap");
        assertFalse(period2.overlapsWith(period1), "Periods should not overlap");
    }

    @Test
    void testOverlapsWithAdjacentPeriods() {
        ReservationPeriod period1 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 3));
        ReservationPeriod period2 = new ReservationPeriod(LocalDate.of(2023, 10, 3), LocalDate.of(2023, 10, 5));

        assertFalse(period1.overlapsWith(period2), "Periods should not overlap (adjacent)");
        assertFalse(period2.overlapsWith(period1), "Periods should not overlap (adjacent)");
    }

    @Test
    void testOverlapsWithSamePeriods() {
        ReservationPeriod period1 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5));
        ReservationPeriod period2 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5));

        assertTrue(period1.overlapsWith(period2), "Identical periods should overlap");
    }

    @Test
    void testEqualsAndHashCode() {
        ReservationPeriod period1 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5));
        ReservationPeriod period2 = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5));
        ReservationPeriod period3 = new ReservationPeriod(LocalDate.of(2023, 10, 2), LocalDate.of(2023, 10, 6));

        assertEquals(period1, period2, "Periods with same dates should be equal");
        assertEquals(period1.hashCode(), period2.hashCode(), "Hash codes should be equal for equal periods");

        assertNotEquals(period1, period3, "Periods with different dates should not be equal");
    }

    @Test
    void testToString() {
        ReservationPeriod period = new ReservationPeriod(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5));
        String expectedString = "from 2023-10-01 to 2023-10-05";

        assertEquals(expectedString, period.toString(), "toString should return the correct format");
    }
}
