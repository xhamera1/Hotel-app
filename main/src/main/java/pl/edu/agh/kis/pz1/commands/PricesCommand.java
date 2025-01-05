package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Room;

/**
 * The PricesCommand class lists all rooms in the hotel along with their descriptions,
 * room numbers, and prices per night.
 */
public class PricesCommand extends Command {
    private Hotel hotel;

    /**
     * Constructs a PricesCommand with the specified Hotel instance.
     *
     * @param hotel the Hotel instance containing rooms whose prices will be displayed
     */
    public PricesCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Executes the PricesCommand, iterating over each room in the hotel
     * and printing out its description, number, and price per night.
     */
    @Override
    public void execute() {
        for (int index : hotel.getRooms().keys()) {
            Room room = hotel.getRooms().get(index);
            StringBuilder sB = new StringBuilder("Room ");
            sB.append(room.getDescription());
            sB.append(" number ");
            sB.append(room.getNumber());
            sB.append(" costs ");
            sB.append(room.getPricePerNight());
            sB.append("$ per night.");

            String str = sB.toString();
            System.out.println(str);
        }
    }
}
