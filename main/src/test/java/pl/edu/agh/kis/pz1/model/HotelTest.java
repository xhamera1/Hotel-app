package pl.edu.agh.kis.pz1.model;

import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.MyMap;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Hotel class.
 * Verifies the correct behavior of constructors, getters, setters,
 * and the addRoom method.
 */
class HotelTest {

    @Test
    void testDefaultConstructor() {
        Hotel hotel = new Hotel();

        assertEquals(0, hotel.getFloorsCount(), "Floors count should be initialized to 0");
        assertEquals(0, hotel.getRoomsCount(), "Rooms count should be initialized to 0");
        assertNotNull(hotel.getRooms(), "Rooms map should be initialized");
        assertTrue(hotel.getRooms().isEmpty(), "Rooms map should be empty");
    }

    @Test
    void testParameterizedConstructor() {
        MyMap<Integer, Room> rooms = new MyMap<>();
        rooms.put(101, new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room"));

        Hotel hotel = new Hotel(1, 1, rooms);

        assertEquals(1, hotel.getFloorsCount(), "Floors count should be set to 1");
        assertEquals(1, hotel.getRoomsCount(), "Rooms count should be set to 1");
        assertEquals(rooms, hotel.getRooms(), "Rooms map should be the one provided");
    }

    @Test
    void testCopyConstructor() {
        MyMap<Integer, Room> rooms = new MyMap<>();
        rooms.put(101, new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room"));

        Hotel originalHotel = new Hotel(1, 1, rooms);
        Hotel copiedHotel = new Hotel(originalHotel);

        assertEquals(originalHotel.getFloorsCount(), copiedHotel.getFloorsCount(), "Floors count should be the same");
        assertEquals(originalHotel.getRoomsCount(), copiedHotel.getRoomsCount(), "Rooms count should be the same");
        assertEquals(originalHotel.getRooms(), copiedHotel.getRooms(), "Rooms map should be the same");
    }

    @Test
    void testSettersAndGetters() {
        Hotel hotel = new Hotel();

        hotel.setFloorsCount(5);
        hotel.setRoomsCount(50);
        MyMap<Integer, Room> rooms = new MyMap<>();
        hotel.setRooms(rooms);

        assertEquals(5, hotel.getFloorsCount(), "Floors count should be updated to 5");
        assertEquals(50, hotel.getRoomsCount(), "Rooms count should be updated to 50");
        assertEquals(rooms, hotel.getRooms(), "Rooms map should be updated");
    }

    @Test
    void testAddRoom() {
        Hotel hotel = new Hotel();

        Room room101 = new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room");
        hotel.addRoom(room101);

        assertEquals(1, hotel.getRoomsCount(), "Rooms count should be 1 after adding a room");
        assertEquals(room101, hotel.getRooms().get(101), "Room 101 should be added to the rooms map");
        assertEquals(1, hotel.getFloorsCount(), "Floors count should be updated based on room level");

        // Adding a room on a higher floor
        Room room201 = new Room(201, BigDecimal.valueOf(150.0), 2, "Deluxe Room");
        hotel.addRoom(room201);

        assertEquals(2, hotel.getRoomsCount(), "Rooms count should be 2 after adding another room");
        assertEquals(2, hotel.getFloorsCount(), "Floors count should be updated to 2");

        // Attempt to add a room with the same number
        hotel.addRoom(room101);

        assertEquals(2, hotel.getRoomsCount(), "Rooms count should remain 2 after attempting to add duplicate room");
    }

    @Test
    void testAddRoomDoesNotAddDuplicate() {
        Hotel hotel = new Hotel();

        Room room101 = new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room");
        hotel.addRoom(room101);

        Room duplicateRoom101 = new Room(101, BigDecimal.valueOf(120.0), 3, "Suite Room");
        hotel.addRoom(duplicateRoom101);

        assertEquals(1, hotel.getRoomsCount(), "Rooms count should not increase when adding a duplicate room");
        assertEquals(room101, hotel.getRooms().get(101), "Original room should remain unchanged");
    }

    @Test
    void testFloorsCountUpdate() {
        Hotel hotel = new Hotel();

        Room room101 = new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room");
        Room room301 = new Room(301, BigDecimal.valueOf(200.0), 2, "Executive Suite");
        hotel.addRoom(room101);
        hotel.addRoom(room301);

        assertEquals(3, hotel.getFloorsCount(), "Floors count should be updated to the highest floor number");
    }

    @Test
    void testRoomsMapIsMutable() {
        Hotel hotel = new Hotel();

        MyMap<Integer, Room> rooms = hotel.getRooms();
        Room room101 = new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room");
        rooms.put(101, room101);

        assertEquals(0, hotel.getRoomsCount(), "Rooms count should not be affected by external modifications");
    }
}
