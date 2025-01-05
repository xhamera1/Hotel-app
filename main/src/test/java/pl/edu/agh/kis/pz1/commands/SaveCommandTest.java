package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.*;
import pl.edu.agh.kis.pz1.model.Hotel;
import pl.edu.agh.kis.pz1.model.Room;

import java.io.File;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaveCommandTest {

    private Hotel hotel;
    private final String filePath = "main/src/main/resources/hotel-data.csv";
    private File file;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        Room room101 = new Room(101, new BigDecimal("100.0"), 2, "Standard Room");
        hotel.addRoom(room101);

        file = new File(filePath);

        if (file.exists()) {
            File backupFile = new File(filePath + ".backup");
            if (backupFile.exists()) {
                assertTrue(backupFile.delete(), "Failed to delete existing backup file");
            }
            assertTrue(file.renameTo(backupFile), "Failed to backup existing CSV file");
        }
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) {
            assertTrue(file.delete(), "Failed to delete test CSV file");
        }

        File backupFile = new File(filePath + ".backup");
        if (backupFile.exists()) {
            assertTrue(backupFile.renameTo(file), "Failed to restore backup CSV file");
        }
    }

    @Test
    void testExecuteSuccess() {
        SaveCommand command = new SaveCommand(hotel);
        command.execute();

        assertTrue(file.exists(), "CSV file was not created");
    }


}
