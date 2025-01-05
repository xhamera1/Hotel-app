package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.CsvWriter;
import pl.edu.agh.kis.pz1.model.Hotel;


/**
 * The SaveCommand class saves the current state of all rooms in a hotel to a CSV file.
 * It utilizes the CsvWriter utility class to perform the actual file writing operation.
 * This command is part of the command pattern design, enabling the application to save data
 * consistently by executing a standardized command.
 */
public class SaveCommand extends Command {
    Hotel hotel;


    /**
     * Constructs a SaveCommand with the specified hotel instance.
     * The hotel object contains all the rooms whose information will be saved to the CSV file.
     *
     * @param hotel The hotel instance containing room data to be saved.
     */
    public SaveCommand(Hotel hotel){
        this.hotel = hotel;
    }


    /**
     * Executes the save command, invoking the CsvWriter to write all room information to a CSV file.
     * Displays a success message upon successful completion of the save operation, or an error message
     * if the CSV writing fails.
     */
    @Override
    public void execute() {
        boolean result = CsvWriter.writeCSV(hotel);
        if(result){
            System.out.println("Csv save successfully completed");
        }
        else{
            System.out.println("Error while writing csv file");
        }
    }
}
