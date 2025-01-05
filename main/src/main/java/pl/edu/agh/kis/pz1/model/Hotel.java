package pl.edu.agh.kis.pz1.model;

import pl.edu.agh.kis.pz1.MyMap;

import java.util.Collection;

/**
 * The Hotel class represents a hotel with a specific number of floors and rooms.
 * It maintains a map of room numbers to Room objects.
 */
public class Hotel {
    private int floorsCount;
    private int roomsCount;
    private MyMap<Integer, Room> rooms;

    /**
     * Default constructor for the Hotel class.
     * Initializes the hotel with zero floors, zero rooms, and no room mappings.
     *
     * - floorsCount: Initially set to 0, indicating no floors are defined.
     * - roomsCount: Initially set to 0, indicating no rooms are defined.
     * - rooms: Initially set empty, indicating no room data or structure has been assigned.
     */
    public Hotel() {
        this.floorsCount = 0;
        this.roomsCount = 0;
        this.rooms = new MyMap<>();
    }

    /**
     * Copy constructor that creates a new Hotel object based on an existing one.
     *
     * @param hotel the Hotel object from which field values are copied.
     */
    public Hotel(Hotel hotel){
        this.floorsCount = hotel.floorsCount;
        this.roomsCount = hotel.roomsCount;
        this.rooms = hotel.rooms;
    }

    /**
     * Constructs a Hotel with the specified number of floors and rooms.
     *
     * @param floorsCount The number of floors in the hotel.
     * @param roomsCount  The number of rooms in the hotel.
     * @param rooms       A map of room numbers to Room objects.
     */
    public Hotel(int floorsCount, int roomsCount, MyMap<Integer, Room> rooms) {
        this.floorsCount = floorsCount;
        this.roomsCount = roomsCount;
        this.rooms = rooms;
    }

    /**
     * Returns the map of room numbers to Room objects.
     *
     * @return A MyMap object containing the room mappings.
     */
    public MyMap<Integer, Room> getRooms() {
        return rooms;
    }


    /**
     * Returns the collection of all rooms
     *
     * @return collection of all rooms.
     */
    public Collection<Room> getAllRooms() {
        return rooms.getValues();
    }

    /**
     * Sets the map of room numbers to Room objects.
     *
     * @param rooms A new MyMap object containing the room mappings.
     */
    public void setRooms(MyMap<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Returns the total number of rooms in the hotel.
     *
     * @return The number of rooms.
     */
    public int getRoomsCount() {
        return roomsCount;
    }

    /**
     * Sets the total number of rooms in the hotel.
     *
     * @param roomsCount The new number of rooms.
     */
    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }


    /**
     * Returns the room by the number.
     *
     * @param roomNumber The number of room.
     * @return The room by the number.
     */
    public Room getRoomByNumber(int roomNumber) {
        return rooms.get(roomNumber);
    }

    /**
     * Returns the number of floors in the hotel.
     *
     * @return The number of floors.
     */
    public int getFloorsCount() {
        return floorsCount;
    }

    /**
     * Sets the number of floors in the hotel.
     *
     * @param floorsCount The new number of floors.
     */
    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    /**
     * Adds a new room to the hotel. If a room with the same number already exists, the method does nothing.
     * The room is added to the map of rooms, and the total count of rooms is incremented.
     * Additionally, the total number of floors (floorsCount) is updated if the new room is on a higher floor.
     *
     * @param room The room to be added to the hotel.
     */
    public void addRoom(Room room) {
        int roomNr = room.getNumber();
        if (rooms.get(roomNr) != null) {
            return;
        }
        rooms.put(roomNr, room);
        roomsCount++;
        int roomLevel = room.level();
        if (roomLevel > this.floorsCount) {
            this.floorsCount = roomLevel;
        }
    }
}
