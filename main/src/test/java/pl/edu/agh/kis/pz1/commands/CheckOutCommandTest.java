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
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckOutCommandTest {

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
    void testSuccessfulCheckout() {
        Room room = hotel.getRooms().get(101);
        room.addReservation(Arrays.asList(new Guest("John", "Doe", true)), LocalDate.now().minusDays(2), LocalDate.now().plusDays(2));

        String input = "101\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckOutCommand command = new CheckOutCommand(hotel);
        command.execute();

        String output = testOut.toString();
        assertTrue(output.contains("Successfully checked out."));
        assertTrue(output.contains("You have stayed in this room for"));

        assertEquals(0, room.getReservations().size());
        assertFalse(room.isOccupied());
        assertNull(room.getCheckInDate());
        assertNull(room.getPlannedCheckOutDate());
        assertEquals(0, room.getGuests().size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCheckoutInputs")
    void testInvalidCheckouts(String input, String expectedOutput) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        CheckOutCommand command = new CheckOutCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains(expectedOutput));
    }

    private static Stream<Arguments> provideInvalidCheckoutInputs() {
        return Stream.of(
                Arguments.of("101\n", "No reservation period contains the current date."),
                Arguments.of("abc\n", "An unexpected error occurred"),
                Arguments.of("\n", "An unexpected error occurred")
        );
    }
}
