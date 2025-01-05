package pl.edu.agh.kis.pz1.model;

import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.commands.*;

import java.util.Scanner;

/**
 * The HotelSystem class provides a command-line interface for managing a hotel system.
 * It allows users to execute various commands such as viewing room details, checking guests in/out,
 * listing available rooms, saving data, and exiting the system.
 *
 * This class uses a custom MyMap implementation to store and retrieve command instances,
 * optimizing memory usage by creating each command only once.
 */
public class HotelSystem {
    private Hotel hotel;
    private final MyMap<String, Command> commands = new MyMap<>();

    /**
     * Constructs a HotelSystem with the specified Hotel object.
     * Initializes all available commands and stores them in a MyMap,
     * associating each command with a keyword for efficient retrieval.
     *
     * @param hotel the Hotel instance that will be managed by the system
     */
    public HotelSystem(Hotel hotel) {
        this.hotel = hotel;
        commands.put("prices", new PricesCommand(this.hotel));
        commands.put("view", new ViewCommand(this.hotel));
        commands.put("checkin", new CheckinCommand(this.hotel));
        commands.put("checkout", new CheckOutCommand(this.hotel));
        commands.put("list", new ListCommand(this.hotel));
        commands.put("save", new SaveCommand(this.hotel));
        commands.put("exit", new ExitCommand());
    }

    /**
     * Retrieves the current Hotel instance.
     *
     * @return the hotel managed by this system
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Updates the Hotel instance managed by this system.
     *
     * @param hotel the new Hotel instance to be managed by the system
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Starts the command-line interface for interacting with the hotel system.
     * Continuously prompts the user for commands, processes each command,
     * and executes the corresponding action.
     *
     * Commands are retrieved from the MyMap of commands based on user input.
     * If an invalid command is entered, an error message is displayed.
     *
     * Available commands include:
     * - "prices": Displays room prices.
     * - "view": Shows room details.
     * - "checkin": Checks a guest into a room.
     * - "checkout": Checks a guest out of a room.
     * - "list": Lists all available rooms.
     * - "save": Saves the current hotel data.
     * - "exit": Exits the system.
     */
    public void system() {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("Enter command: ");
                String command = sc.nextLine().trim().toLowerCase();
                Command cmd = commands.get(command);
                if (cmd != null) {
                    cmd.execute();
                    if (command.equals("exit")) {
                        isRunning = false;
                    }
                } else {
                    System.out.println("Invalid command");
                }
            } catch (Exception e) {
                System.out.println("An error occurred while processing the command.");
            }
        }
    }
}
