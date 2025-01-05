package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.*;
import pl.edu.agh.kis.pz1.model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PricesCommandTest {

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

        hotel.addRoom(room101);
        hotel.addRoom(room102);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testPricesCommandOutput() {
        PricesCommand command = new PricesCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room Standard Room number 101 costs 100.0$ per night."));
        assertTrue(output.contains("Room Deluxe Room number 102 costs 150.0$ per night."));
    }

    @Test
    void testPricesCommandWithNoRooms() {
        Hotel emptyHotel = new Hotel();
        PricesCommand command = new PricesCommand(emptyHotel);
        command.execute();

        String output = testOut.toString().trim();
        assertEquals("", output);
    }

    @Test
    void testPricesCommandWithMultipleRooms() {
        Room room103 = new Room(103, new BigDecimal("200.0"), 3, "Suite");
        hotel.addRoom(room103);

        PricesCommand command = new PricesCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room Standard Room number 101 costs 100.0$ per night."));
        assertTrue(output.contains("Room Deluxe Room number 102 costs 150.0$ per night."));
        assertTrue(output.contains("Room Suite number 103 costs 200.0$ per night."));
    }

    @Test
    void testPricesCommandHandlesNullDescription() {
        Room room104 = new Room(104, new BigDecimal("250.0"), 2, null);
        hotel.addRoom(room104);

        PricesCommand command = new PricesCommand(hotel);
        command.execute();

        String output = testOut.toString();

        assertTrue(output.contains("Room null number 104 costs 250.0$ per night."));
    }

    @Test
    void testPricesCommandHandlesEmptyHotel() {
        hotel = new Hotel();
        PricesCommand command = new PricesCommand(hotel);
        command.execute();

        String output = testOut.toString().trim();
        assertEquals("", output);
    }
}
