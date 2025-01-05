package pl.edu.agh.kis.pz1.commands;

/**
 * The ExitCommand class terminates the program when executed.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command, which terminates the program.
     */
    @Override
    public void execute() {
        System.out.println("Exiting the application...");
    }
}