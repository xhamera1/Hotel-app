package pl.edu.agh.kis.pz1;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import pl.edu.agh.kis.pz1.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The CsvReader class provides functionality to read hotel data from a CSV file
 * and populate a Hotel instance with room and reservation information.
 */
public class CsvReader {

    private CsvReader() {
    }

    /**
     * Reads hotel data from a CSV file located at "/hotel-data.csv" and populates the provided Hotel instance.
     *
     * @param hotel The Hotel instance to be populated with data from the CSV file.
     * @return true if the data was successfully read and parsed, false otherwise.
     */
    public static boolean readCSV(Hotel hotel) {
        InputStream is = CsvReader.class.getResourceAsStream("/hotel-data.csv");
        if (is == null) {
            System.out.println("File not found.");
            return false;
        }
        return readCSV(hotel, is);
    }

    /**
     * Overloaded method to read hotel data from a provided InputStream and populate the Hotel instance.
     *
     * @param hotel The Hotel instance to be populated.
     * @param is    The InputStream containing CSV data.
     * @return true if the data was successfully read and parsed, false otherwise.
     */
    public static boolean readCSV(Hotel hotel, InputStream is) {
        if (is == null) {
            System.out.println("InputStream is null.");
            return false;
        }

        try (Reader reader = new InputStreamReader(is);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] values;
            int lineNumber = 0;
            while ((values = csvReader.readNext()) != null) {
                lineNumber++;
                // Skip header
                if (lineNumber == 1) {
                    continue;
                }
                processLine(values, lineNumber, hotel);
            }
            System.out.println("Data successfully read");
            return true;
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading data");
            return false;
        }
    }

    /**
     * Processes a single line from the CSV file.
     *
     * @param values     The array of string values from the CSV line.
     * @param lineNumber The current line number being processed.
     * @param hotel      The Hotel instance to which the room and reservations will be added.
     */
    private static void processLine(String[] values, int lineNumber, Hotel hotel) {
        if (values.length < 5) {
            System.out.println("Invalid number of fields in line " + lineNumber + ": " + String.join(",", values));
            return;
        }
        try {
            Room room = parseRoom(values);
            if (values.length >= 8) {
                processReservations(values, lineNumber, room);
            }
            hotel.addRoom(room);
        } catch (Exception e) {
            System.out.println("Error parsing data in line " + lineNumber + ": " + String.join(",", values));
        }
    }

    /**
     * Parses room information from the CSV line values.
     *
     * @param values The array of string values from the CSV line.
     * @return A Room object populated with data from the CSV line.
     * @throws NumberFormatException If there is an error parsing numeric values.
     */
    private static Room parseRoom(String[] values) throws NumberFormatException {
        int roomNumber = Integer.parseInt(values[0]);
        BigDecimal pricePerNight = new BigDecimal(values[1]);
        int capacity = Integer.parseInt(values[2]);
        String description = values[3];

        return new Room(roomNumber, pricePerNight, capacity, description);
    }

    /**
     * Processes reservation information associated with a room.
     *
     * @param values     The array of string values from the CSV line.
     * @param lineNumber The current line number being processed.
     * @param room       The Room object to which reservations will be added.
     */
    private static void processReservations(String[] values, int lineNumber, Room room) {
        String checkInDatesStr = values[5];
        String checkOutDatesStr = values[6];
        String guestsStr = values[7];

        if (!checkInDatesStr.isEmpty() && !checkOutDatesStr.isEmpty() && !guestsStr.isEmpty()) {
            String[] checkInDates = checkInDatesStr.split(";");
            String[] checkOutDates = checkOutDatesStr.split(";");
            String[] guestGroups = guestsStr.split(";");

            // Ensure the number of reservations matches
            if (checkInDates.length != checkOutDates.length || checkInDates.length != guestGroups.length) {
                System.out.println("Mismatch in the number of check-in dates, check-out dates, and guest groups in line " + lineNumber);
                return;
            }

            for (int i = 0; i < checkInDates.length; i++) {
                Reservation reservation = parseReservation(checkInDates[i], checkOutDates[i], guestGroups[i]);
                room.getReservations().add(reservation);
            }
        }
    }

    /**
     * Parses a single reservation from the provided data.
     *
     * @param checkInDateStr  The check-in date as a string.
     * @param checkOutDateStr The check-out date as a string.
     * @param guestGroupStr   The guest group information as a string.
     * @return A Reservation object populated with the provided data.
     */
    private static Reservation parseReservation(String checkInDateStr, String checkOutDateStr, String guestGroupStr) {
        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

        List<Guest> guests = parseGuests(guestGroupStr);
        ReservationPeriod period = new ReservationPeriod(checkInDate, checkOutDate);
        return new Reservation(guests, period);
    }

    /**
     * Parses guest information from a guest group string.
     *
     * @param guestGroupStr The guest group information as a string.
     * @return A list of Guest objects parsed from the guest group string.
     */
    private static List<Guest> parseGuests(String guestGroupStr) {
        List<Guest> guests = new ArrayList<>();
        String[] guestNames = guestGroupStr.replace("<", "").replace(">", "").split("-");

        for (String guestName : guestNames) {
            String[] nameParts = guestName.trim().split(" ");
            if (nameParts.length >= 2) {
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                guests.add(new Guest(firstName, lastName, false));
            }
        }
        return guests;
    }
}
