package pl.edu.agh.kis.pz1;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import pl.edu.agh.kis.pz1.model.Guest;
import pl.edu.agh.kis.pz1.model.Reservation;
import pl.edu.agh.kis.pz1.model.Room;
import pl.edu.agh.kis.pz1.model.Hotel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CsvWriter class provides functionality to write hotel data to a CSV file.
 * It saves room details, including reservations, guests, and reservation periods.
 */
public class CsvWriter {

    private CsvWriter() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Writes hotel data to a CSV file located at "main/src/main/resources/hotel-data.csv".
     * The method creates directories if they do not exist and saves room information,
     * including reservations, to the file.
     *
     * @param hotel The Hotel instance containing data to be written to the CSV file.
     * @return true if the data was successfully written, false otherwise.
     */
    public static boolean writeCSV(Hotel hotel) {
        File file = new File("main/src/main/resources/hotel-data.csv");

        try {
            File directory = new File("main/src/main/resources/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile,
                    ICSVWriter.DEFAULT_SEPARATOR,
                    ICSVWriter.NO_QUOTE_CHARACTER,
                    ICSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    ICSVWriter.DEFAULT_LINE_END);

            // CSV file header
            String[] header = {"roomNumber", "pricePerNight", "capacity", "description", "level", "checkInDates", "checkOutDates", "guests"};
            writer.writeNext(header);

            for (Room room : hotel.getRooms().getValues()) {
                List<String> data = new ArrayList<>();
                data.add(String.valueOf(room.getNumber()));
                data.add(String.valueOf(room.getPricePerNight()));
                data.add(String.valueOf(room.getCapacity()));
                data.add(room.getDescription());
                data.add(String.valueOf(room.level()));

                List<String> checkInDates = new ArrayList<>();
                List<String> checkOutDates = new ArrayList<>();
                List<String> guestGroups = new ArrayList<>();

                for (Reservation reservation : room.getReservations()) {
                    checkInDates.add(reservation.getPeriod().getCheckInDate().toString());
                    checkOutDates.add(reservation.getPeriod().getCheckOutDate().toString());

                    StringBuilder guestGroup = new StringBuilder("<");
                    for (Guest guest : reservation.getGuests()) {
                        guestGroup.append(guest.getFirstName()).append(" ").append(guest.getLastName()).append("-");
                    }
                    if (guestGroup.length() > 1) {
                        guestGroup.setLength(guestGroup.length() - 1); // Remove the last hyphen
                    }
                    guestGroup.append(">");
                    guestGroups.add(guestGroup.toString());
                }

                data.add(String.join(";", checkInDates));
                data.add(String.join(";", checkOutDates));
                data.add(String.join(";", guestGroups));

                writer.writeNext(data.toArray(new String[0]));
            }

            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error in CSV writer: " + e.getMessage());
            return false;
        }
    }
}
