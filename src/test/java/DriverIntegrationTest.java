import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DriverIntegrationTest {

    private void clearDriverFile() {
        File file = new File("drivers.txt");

        if (file.exists()) {
            file.delete();
        }
}

@Test
    void shouldStoreValidDriver() {
        clearDriverFile();

        DriverRepository repository =
                new DriverRepository();

        Driver driver = new Driver(
                "23@@ABCDXY",
                "Andrea Stella",
                5,
                "Heavy",
                "500|Swanston Street|Melbourne|VIC|Australia",
                "04-06-1990"
        );

        boolean result =
                repository.addDriver(driver);

        assertTrue(result);

        Driver storedDriver =
                repository.getDriver("23@@ABCDXY");

        assertNotNull(storedDriver);

        assertEquals("23@@ABCDXY",
                storedDriver.getDriverID());

        assertEquals("Andrea Stella",
                storedDriver.getName());

        assertEquals(1,
                repository.countDrivers());

        clearDriverFile();
    }

    @Test
    void shouldNotStoreInvalidDriver() {
        clearDriverFile();

        DriverRepository repository =
                new DriverRepository();

        Driver driver = new Driver(
                "12ABC",
                "Invalid Driver",
                3,
                "Light",
                "30|Bourke Street|Melbourne|VIC|Australia",
                "04-01-2000"
        );

        boolean result =
                repository.addDriver(driver);

        assertFalse(result);

        assertEquals(0,
                repository.countDrivers());

        clearDriverFile();
    }

    @Test
    void shouldUpdateDriverDetailsCorrectly() {
        clearDriverFile();

        DriverRepository repository =
                new DriverRepository();

        Driver driver = new Driver(
                "24@@ABCDYZ",
                "Alice Brown",
                5,
                "Medium",
                "50|Swanston Street|Melbourne|VIC|Australia",
                "10-04-1998"
        );

        repository.addDriver(driver);

        boolean result =
                repository.updateDriver(
                        "24@@ABCDYZ",
                        5,
                        "Heavy",
                        "60|Bourke Street|Melbourne|VIC|Australia",
                        "10-04-1998"
                );

        assertTrue(result);

        Driver updatedDriver =
                repository.getDriver("24@@ABCDYZ");

        assertNotNull(updatedDriver);

        assertEquals("Heavy",
                updatedDriver.getLicenseType());

        assertEquals("60|Bourke Street|Melbourne|VIC|Australia",
                updatedDriver.getAddress());

        assertEquals("Alice Brown",
                updatedDriver.getName());

        clearDriverFile();
    }

    @Test
    void shouldUpdateDriverCountCorrectly() {
        clearDriverFile();

        DriverRepository repository =
                new DriverRepository();

        Driver driver1 = new Driver(
                "32@@DCBAZZ",
                "Carlos Sainz",
                7,
                "Heavy",
                "100|Collins Street|Melbourne|VIC|Australia",
                "20-02-1989"
        );

        Driver driver2 = new Driver(
                "35##ABCDYY",
                "Lando Norris",
                4,
                "PublicTransport",
                "200|Elizabeth Street|Melbourne|VIC|Australia",
                "13-11-1999"
        );

        repository.addDriver(driver1);
        repository.addDriver(driver2);

        assertEquals(2,
                repository.countDrivers());

        clearDriverFile();
    }
}