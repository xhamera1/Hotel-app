package pl.edu.agh.kis.pz1.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Room class.
 * Verifies the correct behavior of constructors, getters, setters,
 * and methods like addGuest, addReservation, level, equals, and hashCode.
 */
class RoomTest {

    @Test
    void testConstructorWithAllAttributes() {
        int number = 101;
        BigDecimal pricePerNight = BigDecimal.valueOf(100.0);
        int capacity = 2;
        LocalDate checkInDate = LocalDate.of(2023, 10, 1);
        boolean isOccupied = true;
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("John", "Doe", true));
        LocalDate plannedCheckOutDate = LocalDate.of(2023, 10, 5);
        String description = "Standard Room";

        Room room = new Room(number, pricePerNight, capacity, checkInDate, isOccupied,
                guests, plannedCheckOutDate, description);

        assertEquals(number, room.getNumber(), "Room number should match");
        assertEquals(pricePerNight, room.getPricePerNight(), "Price per night should match");
        assertEquals(capacity, room.getCapacity(), "Capacity should match");
        assertEquals(checkInDate, room.getCheckInDate(), "Check-in date should match");
        assertEquals(isOccupied, room.isOccupied(), "Occupancy status should match");
        assertEquals(guests, room.getGuests(), "Guests list should match");
        assertEquals(plannedCheckOutDate, room.getPlannedCheckOutDate(), "Planned check-out date should match");
        assertEquals(description, room.getDescription(), "Description should match");
        assertNotNull(room.getReservations(), "Reservations list should be initialized");
        assertTrue(room.getReservations().isEmpty(), "Reservations list should be empty initially");
    }

    @Test
    void testConstructorForCSVLoading() {
        int number = 102;
        BigDecimal pricePerNight = BigDecimal.valueOf(150.0);
        int capacity = 3;
        String description = "Deluxe Room";

        Room room = new Room(number, pricePerNight, capacity, description);

        assertEquals(number, room.getNumber(), "Room number should match");
        assertEquals(pricePerNight, room.getPricePerNight(), "Price per night should match");
        assertEquals(capacity, room.getCapacity(), "Capacity should match");
        assertEquals(description, room.getDescription(), "Description should match");
        assertFalse(room.isOccupied(), "Room should not be occupied initially");
        assertNotNull(room.getGuests(), "Guests list should be initialized");
        assertTrue(room.getGuests().isEmpty(), "Guests list should be empty initially");
        assertNull(room.getCheckInDate(), "Check-in date should be null");
        assertNull(room.getPlannedCheckOutDate(), "Planned check-out date should be null");
        assertNotNull(room.getReservations(), "Reservations list should be initialized");
        assertTrue(room.getReservations().isEmpty(), "Reservations list should be empty initially");
    }

    @Test
    void testGettersAndSetters() {
        Room room = new Room(103, BigDecimal.valueOf(200.0), 4, "Suite");

        room.setOccupied(true);
        assertTrue(room.isOccupied(), "Occupancy status should be updated");

        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Alice", "Smith", true));
        room.setGuests(guests);
        assertEquals(guests, room.getGuests(), "Guests list should be updated");

        LocalDate checkInDate = LocalDate.of(2023, 11, 1);
        room.setCheckInDate(checkInDate);
        assertEquals(checkInDate, room.getCheckInDate(), "Check-in date should be updated");

        LocalDate plannedCheckOutDate = LocalDate.of(2023, 11, 10);
        room.setPlannedCheckOutDate(plannedCheckOutDate);
        assertEquals(plannedCheckOutDate, room.getPlannedCheckOutDate(), "Planned check-out date should be updated");
    }

    @Test
    void testAddGuestSuccessful() {
        Room room = new Room(104, BigDecimal.valueOf(120.0), 2, "Double Room");
        Guest guest1 = new Guest("Bob", "Johnson", true);
        Guest guest2 = new Guest("Carol", "Johnson", false);

        assertTrue(room.addGuest(guest1), "Should successfully add first guest");
        assertTrue(room.addGuest(guest2), "Should successfully add second guest");
        assertEquals(2, room.getGuests().size(), "Guests list should have 2 guests");
    }

    @Test
    void testAddGuestWhenFull() {
        Room room = new Room(105, BigDecimal.valueOf(80.0), 1, "Single Room");
        Guest guest1 = new Guest("Dave", "Lee", true);
        Guest guest2 = new Guest("Eve", "Lee", false);

        assertTrue(room.addGuest(guest1), "Should successfully add first guest");
        assertFalse(room.addGuest(guest2), "Should not add guest when room is at full capacity");
        assertEquals(1, room.getGuests().size(), "Guests list should have 1 guest");
    }

    @Test
    void testAddReservationSuccessful() {
        Room room = new Room(106, BigDecimal.valueOf(180.0), 2, "Queen Room");
        List<Guest> guests = List.of(new Guest("Frank", "Miller", true));
        LocalDate checkInDate = LocalDate.of(2023, 12, 1);
        LocalDate checkOutDate = LocalDate.of(2023, 12, 5);

        boolean result = room.addReservation(guests, checkInDate, checkOutDate);
        assertTrue(result, "Should successfully add reservation");
        assertEquals(1, room.getReservations().size(), "Reservations list should have 1 reservation");
    }

    @Test
    void testAddReservationWithOverlap() {
        Room room = new Room(107, BigDecimal.valueOf(220.0), 2, "King Room");
        List<Guest> guests1 = List.of(new Guest("Grace", "Hopper", true));
        LocalDate checkInDate1 = LocalDate.of(2023, 12, 10);
        LocalDate checkOutDate1 = LocalDate.of(2023, 12, 15);

        List<Guest> guests2 = List.of(new Guest("Heidi", "Lamarr", true));
        LocalDate checkInDate2 = LocalDate.of(2023, 12, 14);
        LocalDate checkOutDate2 = LocalDate.of(2023, 12, 20);

        assertTrue(room.addReservation(guests1, checkInDate1, checkOutDate1), "First reservation should be added");
        assertFalse(room.addReservation(guests2, checkInDate2, checkOutDate2), "Second reservation should fail due to overlap");
        assertEquals(1, room.getReservations().size(), "Reservations list should still have 1 reservation");
    }

    @Test
    void testLevel() {
        Room room101 = new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room");
        Room room202 = new Room(202, BigDecimal.valueOf(150.0), 2, "Deluxe Room");
        Room room305 = new Room(305, BigDecimal.valueOf(200.0), 2, "Suite");

        assertEquals(1, room101.level(), "Room 101 should be on level 1");
        assertEquals(2, room202.level(), "Room 202 should be on level 2");
        assertEquals(3, room305.level(), "Room 305 should be on level 3");
    }

    @Test
    void testEqualsAndHashCode() {
        Room room1 = new Room(108, BigDecimal.valueOf(100.0), 2, "Standard Room");
        Room room2 = new Room(108, BigDecimal.valueOf(150.0), 3, "Deluxe Room");
        Room room3 = new Room(109, BigDecimal.valueOf(100.0), 2, "Standard Room");

        assertEquals(room1, room2, "Rooms with same number should be equal");
        assertEquals(room1.hashCode(), room2.hashCode(), "Hash codes should be equal for rooms with same number");

        assertNotEquals(room1, room3, "Rooms with different numbers should not be equal");
        assertNotEquals(room1.hashCode(), room3.hashCode(), "Hash codes should differ for rooms with different numbers");
    }

    @Test
    void testSettersNullValues() {
        Room room = new Room(110, BigDecimal.valueOf(120.0), 2, "Double Room");

        room.setCheckInDate(null);
        assertNull(room.getCheckInDate(), "Check-in date should be null");

        room.setPlannedCheckOutDate(null);
        assertNull(room.getPlannedCheckOutDate(), "Planned check-out date should be null");

        room.setGuests(null);
        assertNull(room.getGuests(), "Guests list should be null");
    }

    @Test
    void testAddGuestToNullGuestList() {
        Room room = new Room(111, BigDecimal.valueOf(130.0), 2, "Twin Room");
        room.setGuests(null);

        Guest guest = new Guest("Ivy", "Taylor", true);

        // Check that a NullPointerException is thrown
        assertThrows(NullPointerException.class, () -> room.addGuest(guest));
    }

    @Test
    void testAddReservationWithInvalidDates() {
        Room room = new Room(112, BigDecimal.valueOf(140.0), 2, "Family Room");
        List<Guest> guests = List.of(new Guest("Jack", "Wilson", true));
        LocalDate checkInDate = LocalDate.of(2023, 12, 20);
        LocalDate checkOutDate = LocalDate.of(2023, 12, 15); // Invalid dates

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            room.addReservation(guests, checkInDate, checkOutDate);
        });

        assertEquals("Check-in date must be before check-out date", exception.getMessage(), "Exception message should indicate invalid dates");
    }
}
