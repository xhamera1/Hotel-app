package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.edu.agh.kis.pz1.model.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ViewCommandTest {

    private Hotel hotel;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Room room101 = new Room(101, new BigDecimal("100.0"), 2, "Standard Room");
        Guest guest1 = new Guest("John", "Doe", true);
        Guest guest2 = new Guest("Jane", "Doe", false);
        room101.addReservation(
                List.of(guest1, guest2),
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        hotel.addRoom(room101);

        Room room102 = new Room(102, new BigDecimal("150.0"), 2, "Deluxe Room");
        hotel.addRoom(room102);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(System.in);
    }

    /**
     * Test viewing an existing room with active reservations.
     */
    @Test
    void testViewExistingRoomWithReservations() {
        String input = "101\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        ViewCommand command = new ViewCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room number: 101"), "Output should contain room number.");
        assertTrue(output.contains("Room description: Standard Room"), "Output should contain room description.");
        assertTrue(output.contains("Room price per night: 100.0"), "Output should contain room price.");
        assertTrue(output.contains("Room capacity: 2"), "Output should contain room capacity.");
        assertTrue(output.contains("Reservations:"), "Output should indicate reservations.");
        assertTrue(output.contains("Guests in the reservation:"), "Output should list guests.");
        assertTrue(output.contains("Main guest: John Doe"), "Output should list main guest.");
        assertTrue(output.contains("Guest: Jane Doe"), "Output should list additional guest.");
        assertTrue(output.contains("Reservation period:"), "Output should show reservation period.");
    }

    /**
     * Test viewing an existing room without any reservations.
     */
    @Test
    void testViewExistingRoomWithoutReservations() {
        String input = "102\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        ViewCommand command = new ViewCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room number: 102"), "Output should contain room number.");
        assertTrue(output.contains("Room description: Deluxe Room"), "Output should contain room description.");
        assertTrue(output.contains("Room price per night: 150.0"), "Output should contain room price.");
        assertTrue(output.contains("Room capacity: 2"), "Output should contain room capacity.");
        assertTrue(output.contains("No reservations found for this room."), "Output should indicate no reservations.");
    }

    /**
     * Parameterized test for invalid view commands.
     * This replaces the tests: testViewNonExistingRoom, testInvalidRoomNumberInput, and testExceptionHandling.
     *
     * @param input          The simulated user input.
     * @param expectedOutput The expected output message.
     */
    @ParameterizedTest
    @MethodSource("provideInvalidViewCommandInputs")
    void testInvalidViewCommandInputs(String input, String expectedOutput) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        ViewCommand command = new ViewCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains(expectedOutput), "Output should contain expected message.");
    }

    /**
     * Provides test arguments for invalid view command scenarios.
     *
     * @return A stream of arguments: simulated input and expected output.
     */
    private static Stream<Arguments> provideInvalidViewCommandInputs() {
        return Stream.of(
                Arguments.of("999\n", "Room not found."),

                Arguments.of("abc\n", "Invalid room number"),

                Arguments.of("\n", "Invalid room number")
        );
    }
}
