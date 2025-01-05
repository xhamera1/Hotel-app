package pl.edu.agh.kis.pz1.commands;

/**
 * Abstract class that defines a generic command in the system.
 * All specific commands will inherit from this class.
 */
public abstract class Command {

    /**
     * Executes the command. All subclasses must implement this method.
     */
    public abstract void execute();
}