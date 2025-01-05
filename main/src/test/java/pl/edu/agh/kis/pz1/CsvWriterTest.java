package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CsvWriterTest {

    private Hotel hotel;
    private String filePath = "main/src/main/resources/hotel-data.csv";

    @BeforeEach
    void setUp() {
        hotel = new Hotel();

        Room room101 = new Room(101, BigDecimal.valueOf(100.0), 2, "Standard Room");
        room101.addReservation(
                Arrays.asList(new Guest("Jan", "Kowalski", true)),
                LocalDate.of(2024, 11, 15),
                LocalDate.of(2024, 11, 20)
        );
        hotel.addRoom(room101);

        Room room102 = new Room(102, BigDecimal.valueOf(150.0), 2, "Deluxe Room");
        hotel.addRoom(room102);
    }

    @Test
    void testWriteCSV_Success() throws IOException {
        boolean result = CsvWriter.writeCSV(hotel);
        assertTrue(result, "Metoda writeCSV powinna zwrócić true");

        assertTrue(Files.exists(Paths.get(filePath)), "Plik CSV powinien zostać utworzony");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            assertEquals("roomNumber,pricePerNight,capacity,description,level,checkInDates,checkOutDates,guests", headerLine);

            String room101Line = reader.readLine();
            assertNotNull(room101Line);
            String[] room101Data = room101Line.split(",");
            assertEquals("101", room101Data[0]);
            assertEquals("100.0", room101Data[1]);
            assertEquals("2", room101Data[2]);
            assertEquals("Standard Room", room101Data[3]);
            assertEquals("1", room101Data[4]);
            assertEquals("2024-11-15", room101Data[5]);
            assertEquals("2024-11-20", room101Data[6]);
            assertEquals("<Jan Kowalski>", room101Data[7]);

            String room102Line = reader.readLine();
            assertNotNull(room102Line);
            String[] room102Data = room102Line.split(",");
            assertEquals("102", room102Data[0]);
            assertEquals("150.0", room102Data[1]);
            assertEquals("2", room102Data[2]);
            assertEquals("Deluxe Room", room102Data[3]);
            assertEquals("1", room102Data[4]);

            assertNull(reader.readLine(), "Plik CSV nie powinien zawierać więcej linii");
        }

        Files.deleteIfExists(Paths.get(filePath));
    }
}
