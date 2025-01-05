package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.*;
import pl.edu.agh.kis.pz1.model.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheckinCommandTest {

    private Hotel hotel;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        Room room101 = new Room(101, new BigDecimal("100.0"), 2, "Standard Room");
        hotel.addRoom(room101);
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(System.in);
    }

    @Test
    void testSuccessfulCheckin() {
        String input = """
            101
            John
            Doe
            
            2
            no
            """;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckinCommand command = new CheckinCommand(hotel);
        command.execute();

        String output = testOut.toString();
        assertTrue(output.contains("Successfully checked in."));

        Room room = hotel.getRooms().get(101);
        assertNotNull(room);
        assertEquals(1, room.getReservations().size());
        Reservation reservation = room.getReservations().get(0);
        assertEquals(1, reservation.getGuests().size());
        assertEquals("John", reservation.getGuests().get(0).getFirstName());
        assertEquals("Doe", reservation.getGuests().get(0).getLastName());
    }

    @Test
    void testInvalidRoomNumber() {
        String input = "abc\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckinCommand command = new CheckinCommand(hotel);
        command.execute();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid room number."));
    }

    @Test
    void testRoomNotFound() {
        String input = "999\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckinCommand command = new CheckinCommand(hotel);
        command.execute();

        String output = testOut.toString();
        assertTrue(output.contains("Room not found."));
    }

    @Test
    void testRoomAlreadyBooked() {
        Room room = hotel.getRooms().get(101);
        room.addReservation(Arrays.asList(new Guest("Existing", "Guest", true)), LocalDate.now(), LocalDate.now().plusDays(5));

        String input = """
        101
        John
        Doe
        
        2
        no
        """;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckinCommand command = new CheckinCommand(hotel);
        command.execute();

        String output = testOut.toString();
        assertTrue(output.contains("The room is already booked for the requested dates."));
    }

    @Test
    void testInvalidDateOrDuration() {
        String input = """
            101
            John
            Doe
            invalid-date
            abc
            """;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckinCommand command = new CheckinCommand(hotel);
        command.execute();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid date or duration of stay."));
    }

    @Test
    void testExceedingRoomCapacity() {
        String input = """
            101
            John
            Doe
            
            2
            yes
            Jane
            Smith
            yes
            Bob
            Brown
            no
            """;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckinCommand command = new CheckinCommand(hotel);
        command.execute();

        Room room = hotel.getRooms().get(101);
        Reservation reservation = room.getReservations().get(0);
        assertEquals(2, reservation.getGuests().size());
    }
}
