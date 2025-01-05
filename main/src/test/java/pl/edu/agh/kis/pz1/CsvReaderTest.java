package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.model.Guest;
import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Reservation;
import pl.edu.agh.kis.pz1.model.Room;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
    }

    /**
     * Test reading CSV data with multiple guests per reservation and multiple rooms.
     */
    @Test
    void testReadCSV_WithMultipleGuestsPerReservation() {
        String csvContent = """
            roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests
            101,100.0,2,Standard Room,1,2024-11-15;2024-11-05,2024-11-19;2024-11-09,<Jan Kowalski>;<Iga Lis-Marta Rusin>
            102,150.0,2,Deluxe Room,1,,,
            103,120.0,2,Standard Room,1,,,
            """;

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        assertEquals(3, hotel.getRooms().keys().size(), "There should be 3 rooms in the hotel");

        Room room101 = hotel.getRoomByNumber(101);
        assertNotNull(room101, "Room 101 should exist in the hotel");
        assertEquals(new BigDecimal("100.0"), room101.getPricePerNight(), "Room 101 price should be 100.0");
        assertEquals(2, room101.getCapacity(), "Room 101 capacity should be 2");
        assertEquals("Standard Room", room101.getDescription(), "Room 101 description should be 'Standard Room'");
        assertEquals(2, room101.getReservations().size(), "Room 101 should have 2 reservations");

        Reservation reservation1 = room101.getReservations().get(0);
        assertEquals(LocalDate.of(2024, 11, 15), reservation1.getPeriod().getCheckInDate(), "Check-in date should be 2024-11-15");
        assertEquals(LocalDate.of(2024, 11, 19), reservation1.getPeriod().getCheckOutDate(), "Check-out date should be 2024-11-19");
        List<Guest> guests1 = reservation1.getGuests();
        assertEquals(1, guests1.size(), "There should be 1 guest in the first reservation");
        assertEquals("Kowalski", guests1.get(0).getLastName(), "First guest's last name should be Kowalski");

        Reservation reservation2 = room101.getReservations().get(1);
        assertEquals(LocalDate.of(2024, 11, 5), reservation2.getPeriod().getCheckInDate(), "Check-in date should be 2024-11-05");
        assertEquals(LocalDate.of(2024, 11, 9), reservation2.getPeriod().getCheckOutDate(), "Check-out date should be 2024-11-09");
        List<Guest> guests2 = reservation2.getGuests();
        assertEquals(2, guests2.size(), "There should be 2 guests in the second reservation");

        assertEquals("Marta", guests2.get(1).getFirstName(), "Second guest's first name should be Marta");
        assertEquals("Rusin", guests2.get(1).getLastName(), "Second guest's last name should be Rusin");

        Room room102 = hotel.getRoomByNumber(102);
        assertNotNull(room102, "Room 102 should exist in the hotel");
        assertEquals(new BigDecimal("150.0"), room102.getPricePerNight(), "Room 102 price should be 150.0");
        assertEquals("Deluxe Room", room102.getDescription(), "Room 102 description should be 'Deluxe Room'");
        assertTrue(room102.getReservations().isEmpty(), "Room 102 should have no reservations");

        Room room103 = hotel.getRoomByNumber(103);
        assertNotNull(room103, "Room 103 should exist in the hotel");
        assertEquals(new BigDecimal("120.0"), room103.getPricePerNight(), "Room 103 price should be 120.0");
        assertEquals(2, room103.getCapacity(), "Room 103 capacity should be 2");

    }

    /**
     * Test reading CSV data with missing fields in a line.
     */
    @Test
    void testReadCSV_WithMissingFields() {
        String csvContent = """
        roomNumber,pricePerNight,capacity,description,level
        104,200.0,3,Luxury Room
        """;

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        Room room104 = hotel.getRoomByNumber(104);
        assertNull(room104, "Room 104 should not exist due to missing fields");
    }

    /**
     * Test reading CSV data with invalid numeric values.
     */
    @Test
    void testReadCSV_WithInvalidNumericValues() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level\n" +
                "ABC,XYZ,2,Standard Room,1";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        assertEquals(0, hotel.getRooms().keys().size(), "No rooms should be added due to invalid numeric values");
    }

    /**
     * Test reading CSV data with mismatched reservation data.
     */
    @Test
    void testReadCSV_WithMismatchedReservationData() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests\n" +
                "105,180.0,2,Standard Room,1,2024-11-15,2024-11-19,<Jan Kowalski>;<Anna Nowak>";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        Room room105 = hotel.getRoomByNumber(105);
        assertNotNull(room105, "Room 105 should exist in the hotel");
        assertTrue(room105.getReservations().isEmpty(), "Room 105 should have no reservations due to mismatched data");
    }


    /**
     * Test reading CSV data with incomplete guest information.
     */
    @Test
    void testReadCSV_WithIncompleteGuestInfo() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests\n" +
                "107,250.0,2,Premium Room,1,2024-11-15,2024-11-19,<OnlyFirstName>";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        Room room107 = hotel.getRoomByNumber(107);
        assertNotNull(room107, "Room 107 should exist in the hotel");
        assertEquals(1, room107.getReservations().size(), "Room 107 should have 1 reservation");

        Reservation reservation = room107.getReservations().get(0);
        assertNotNull(reservation, "Reservation should not be null");
        assertEquals(0, reservation.getGuests().size(), "No guests should be added due to incomplete guest information");
    }

    /**
     * Test reading CSV data with extra fields.
     */
    @Test
    void testReadCSV_WithExtraFields() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level,extraField\n" +
                "108,300.0,3,Executive Room,1,ExtraData";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        Room room108 = hotel.getRoomByNumber(108);
        assertNotNull(room108, "Room 108 should exist in the hotel");
        assertEquals(new BigDecimal("300.0"), room108.getPricePerNight(), "Room 108 price should be 300.0");
    }

    /**
     * Test reading CSV data where InputStream is null.
     */
    @Test
    void testReadCSV_WithNullInputStream() {
        boolean result = CsvReader.readCSV(hotel, null);
        assertFalse(result, "The readCSV method should return false when InputStream is null");
    }


    /**
     * Test reading CSV data with empty content.
     */
    @Test
    void testReadCSV_WithEmptyData() {
        String csvContent = "";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        assertEquals(0, hotel.getRooms().keys().size(), "No rooms should be added with empty CSV content");
    }

    /**
     * Test reading CSV data with only headers.
     */
    @Test
    void testReadCSV_WithOnlyHeaders() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests\n";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        assertEquals(0, hotel.getRooms().keys().size(), "No rooms should be added with only headers in CSV content");
    }

    /**
     * Test reading CSV data with multiple reservations for a room.
     */
    @Test
    void testReadCSV_WithMultipleReservations() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests\n" +
                "110,180.0,2,Standard Room,1,2024-11-15;2024-11-20;2024-11-25,2024-11-16;2024-11-21;2024-11-26,<Guest A>;<Guest B>;<Guest C>";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        Room room110 = hotel.getRoomByNumber(110);
        assertNotNull(room110, "Room 110 should exist in the hotel");
        assertEquals(3, room110.getReservations().size(), "Room 110 should have 3 reservations");

        for (int i = 0; i < 3; i++) {
            Reservation reservation = room110.getReservations().get(i);
            assertNotNull(reservation, "Reservation should not be null");
            assertEquals(1, reservation.getGuests().size(), "Each reservation should have 1 guest");
            Guest guest = reservation.getGuests().get(0);
            assertNotNull(guest, "Guest should not be null");
            assertEquals("Guest", guest.getFirstName(), "Guest's first name should be 'Guest'");
            assertEquals(String.valueOf((char) ('A' + i)), guest.getLastName(), "Guest's last name should be 'A', 'B', or 'C'");
        }
    }

    /**
     * Test reading CSV data with special characters in guest names.
     */
    @Test
    void testReadCSV_WithSpecialCharactersInGuestNames() {
        String csvContent = "roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests\n" +
                "111,200.0,2,Standard Room,1,2024-11-15,2024-11-19,<Jose García>";

        InputStream testInputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        boolean result = CsvReader.readCSV(hotel, testInputStream);
        assertTrue(result, "The readCSV method should return true");

        Room room111 = hotel.getRoomByNumber(111);
        assertNotNull(room111, "Room 111 should exist in the hotel");
        assertEquals(1, room111.getReservations().size(), "Room 111 should have 1 reservation");

        Reservation reservation = room111.getReservations().get(0);
        assertNotNull(reservation, "Reservation should not be null");

    }
}
