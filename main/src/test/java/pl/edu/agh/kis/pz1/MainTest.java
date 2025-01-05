package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    /**
     * Provides simulated user input to System.in.
     *
     * @param data The input string to simulate.
     */
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * Retrieves the output from System.out.
     *
     * @return The output string captured during the test.
     */
    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Test the main method by simulating user input and capturing output.
     */
    @Test
    void testMainMethod() {
        String simulatedInput = "exit\n";
        provideInput(simulatedInput);

        Main.main(new String[]{});

        String output = getOutput();

        assertTrue(output.contains("Enter command:"), "The output should prompt for command input");
        assertFalse(output.contains("Invalid command"), "The output should not contain 'Invalid command' for 'exit'");
    }

    /**
     * Test the main method when an invalid command is entered.
     */
    @Test
    void testMainMethodWithInvalidCommand() {
        String simulatedInput = "invalid\nexit\n";
        provideInput(simulatedInput);

        Main.main(new String[]{});

        String output = getOutput();

        assertTrue(output.contains("Enter command:"), "The output should prompt for command input");
        assertTrue(output.contains("Invalid command"), "The output should indicate an invalid command");
    }

    /**
     * Test the main method when 'save' command is entered.
     */
    @Test
    void testMainMethodWithSaveCommand() {
        String simulatedInput = "save\nexit\n";
        provideInput(simulatedInput);

        Main.main(new String[]{});

        String output = getOutput();

        assertTrue(output.contains("Enter command:"), "The output should prompt for command input");
    }
}
