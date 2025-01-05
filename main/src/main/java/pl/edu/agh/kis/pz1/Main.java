package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.HotelSystem;

/**
 * The Main class is the entry point of the hotel management application.
 * It initializes the hotel system, loads hotel data from a CSV file,
 * and starts the command-line interface for user interaction.
 */
public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        CsvReader.readCSV(hotel);
        HotelSystem hotelSystem = new HotelSystem(hotel);
        hotelSystem.system();
    }
}