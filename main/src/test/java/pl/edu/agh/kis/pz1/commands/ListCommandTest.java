package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.*;
import pl.edu.agh.kis.pz1.model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ListCommandTest {

    private Hotel hotel;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Room room101 = new Room(101, new BigDecimal("100.0"), 2, "Standard Room");
        Room room102 = new Room(102, new BigDecimal("150.0"), 2, "Deluxe Room");

        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("Jane", "Doe", false);

        room101.addReservation(Arrays.asList(guest1, guest2), LocalDate.now(), LocalDate.now().plusDays(3));

        hotel.addRoom(room101);
        hotel.addRoom(room102);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testListCommandOutput() {
        ListCommand command = new ListCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room number: 101"));
        assertTrue(output.contains("Room description: Standard Room"));
        assertTrue(output.contains("Room price per night: 100.0"));
        assertTrue(output.contains("Room capacity: 2"));
        assertTrue(output.contains("Reservation details:"));
        assertTrue(output.contains("Guests in the reservation:"));
        assertTrue(output.contains("Main guest: John Doe"));
        assertTrue(output.contains("Guest: Jane Doe"));
        assertTrue(output.contains("Reservation period:"));

        assertTrue(output.contains("Room number: 102"));
        assertTrue(output.contains("Room description: Deluxe Room"));
        assertTrue(output.contains("Room price per night: 150.0"));
        assertTrue(output.contains("Room capacity: 2"));
        assertTrue(output.contains("The room is not occupied at the moment."));
    }

    @Test
    void testListCommandWithNoRooms() {
        Hotel emptyHotel = new Hotel();
        ListCommand command = new ListCommand(emptyHotel);
        command.execute();

        String output = testOut.toString().trim();
        assertEquals("", output);
    }

    @Test
    void testListCommandWithMultipleReservations() {
        Room room = hotel.getRooms().get(101);
        Guest guest3 = new Guest("Alice", "Smith", true);
        room.addReservation(Arrays.asList(guest3), LocalDate.now().plusDays(5), LocalDate.now().plusDays(7));

        ListCommand command = new ListCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room number: 101"));
        assertTrue(output.contains("Reservation details:"));
        assertTrue(output.contains("Main guest: Alice Smith"));
    }

    @Test
    void testListCommandNoReservations() {
        Hotel hotelWithNoReservations = new Hotel();
        Room room = new Room(103, new BigDecimal("120.0"), 2, "Standard Room");
        hotelWithNoReservations.addRoom(room);

        ListCommand command = new ListCommand(hotelWithNoReservations);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room number: 103"));
        assertTrue(output.contains("The room is not occupied at the moment."));
    }
}
