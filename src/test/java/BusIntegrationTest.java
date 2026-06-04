import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class BusIntegrationTest {
    private BusRepository integrationRepo;
    private final String TEST_FILE = "integration_buses.txt";
    private Driver validDriver;

    @BeforeEach
    public void setUp() {
        integrationRepo = new BusRepository(TEST_FILE);

        validDriver = new Driver("23@@ABCDEF", "Jorge", 10, "Heavy", "1|St|Melb|VIC|Aus", "01-01-1990");
    }

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }


    // 1. Verify valid buses are stored correctly
    @Test
    public void testIntegration_ValidBusesStoredCorrectly() {
        Bus validBus = new Bus("12345678", 40, 100.0, "Diesel");

        assertTrue(integrationRepo.addBus(validBus));

        BusRepository secondaryRepo = new BusRepository(TEST_FILE);
        Bus recoveredBus = secondaryRepo.getBus("12345678");

        assertNotNull(recoveredBus);
        assertEquals(40, recoveredBus.getCapacity());
        assertEquals("Diesel", recoveredBus.getFuelType());
    }

    // 2. Verify invalid buses are rejected
    @Test
    public void testIntegration_InvalidBusesAreRejected() {
        Bus brokenBus = new Bus("123", 40, 100.0, "Diesel");

        assertFalse(integrationRepo.addBus(brokenBus));

        BusRepository secondaryRepo = new BusRepository(TEST_FILE);
        assertNull(secondaryRepo.getBus("123"));
        assertEquals(0, secondaryRepo.countBuses());
    }

    // 3. Verify updates are successful
    @Test
    public void testIntegration_UpdatesArePersistedCorrectly() {
        Bus bus = new Bus("99999999", 50, 100.0, "Diesel");
        integrationRepo.addBus(bus);

        assertTrue(integrationRepo.updateBus("99999999", 40, 85.5, "Diesel", validDriver));

        BusRepository secondaryRepo = new BusRepository(TEST_FILE);
        Bus updatedBus = secondaryRepo.getBus("99999999");

        assertNotNull(updatedBus);
        assertEquals(40, updatedBus.getCapacity());
        assertEquals(85.5, updatedBus.getFuelLevel());
    }

    // 4. Verify record counts
    @Test
    public void testIntegration_RecordCountsUpdatedCorrectly() {
        assertEquals(0, integrationRepo.countBuses());

        Bus bus1 = new Bus("11111111", 30, 100.0, "Diesel");
        Bus bus2 = new Bus("22222222", 35, 100.0, "Diesel");

        integrationRepo.addBus(bus1);
        integrationRepo.addBus(bus2);

        BusRepository secondaryRepo = new BusRepository(TEST_FILE);
        assertEquals(2, secondaryRepo.countBuses());
    }
}