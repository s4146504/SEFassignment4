
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class BusUnitTest {
    private BusRepository repo;
    private Driver youngExpertDriver;
    private Driver oldDriver;
    private Driver inexperiencedDriver;

    @BeforeEach
    public void setUp() {
        File file = new File("buses.txt");
        if (file.exists()) {
            file.delete();
        }

        repo = new BusRepository();

        youngExpertDriver = new Driver("23@@ABCDEF", "Jorge", 12, "Heavy", "1|St|Melb|VIC|Aus", "01-01-1990");
        oldDriver = new Driver("24@@ABCDEF", "Potato", 20, "PublicTransport", "2|St|Melb|VIC|Aus", "01-01-1970");
        inexperiencedDriver = new Driver("25##XYZABC", "Ironman", 2, "Light", "3|St|Melb|VIC|Aus", "01-01-2000");
    }

    // B1: BUS ID RULES (3 Tests)
    @Test
    public void testB1_NormalValidBusID() {
        Bus bus = new Bus("12345678", 40, 100.0, "Diesel");
        assertTrue(repo.addBus(bus));
    }

    @Test
    public void testB1_InvalidBusIDTooShort() {
        Bus bus = new Bus("12345", 40, 100.0, "Diesel");
        assertFalse(repo.addBus(bus));
    }

    @Test
    public void testB1_InvalidBusIDContainsLetters() {
        Bus bus = new Bus("1234567A", 40, 100.0, "Diesel");
        assertFalse(repo.addBus(bus));
    }

    // B2: CAPACITY UPDATE RESTRICTION (3 Tests)
    @Test
    public void testB2_NormalDecreaseCapacity() {
        Bus bus = new Bus("88888888", 40, 100.0, "Diesel");
        repo.addBus(bus);
        assertTrue(repo.updateBus("88888888", 30, 100.0, "Diesel", youngExpertDriver));
    }

    @Test
    public void testB2_InvalidIncreaseCapacity() {
        Bus bus = new Bus("88888888", 40, 100.0, "Diesel");
        repo.addBus(bus);
        assertFalse(repo.updateBus("88888888", 45, 100.0, "Diesel", youngExpertDriver));
    }

    @Test
    public void testB2_EdgeMaintainSameCapacity() {
        Bus bus = new Bus("88888888", 40, 100.0, "Diesel");
        repo.addBus(bus);
        assertTrue(repo.updateBus("88888888", 40, 100.0, "Diesel", youngExpertDriver));
    }

    // B3: DRIVER AGE RESTRICTION (3 Tests)
    @Test
    public void testB3_NormalYoungDriverHighCapacity() {
        Bus bus = new Bus("11112222", 60, 100.0, "Diesel");
        assertTrue(repo.addBus(bus));
        assertTrue(repo.updateBus("11112222", 60, 100.0, "Diesel", youngExpertDriver));
    }

    @Test
    public void testB3_InvalidOldDriverHighCapacity() {
        Bus bus = new Bus("11112222", 60, 100.0, "Diesel");
        assertTrue(repo.addBus(bus));
        assertFalse(repo.updateBus("11112222", 55, 100.0, "Diesel", oldDriver));
    }

    @Test
    public void testB3_EdgeOldDriverJustBelowCapacityLimit() {
        Bus bus = new Bus("11112222", 49, 100.0, "Diesel");
        assertTrue(repo.addBus(bus));
        assertTrue(repo.updateBus("11112222", 49, 100.0, "Diesel", oldDriver));
    }

    // B4: ELECTRIC BUS RESTRICTION (3 Tests)
    @Test
    public void testB4_NormalElectricWithExperiencedDriver() {
        Bus bus = new Bus("33334444", 30, 100.0, "Diesel");
        repo.addBus(bus);
        assertTrue(repo.updateBus("33334444", 30, 100.0, "Electricity", youngExpertDriver));
    }

    @Test
    public void testB4_InvalidElectricWithInexperiencedDriver() {
        Bus bus = new Bus("33334444", 30, 100.0, "Diesel");
        repo.addBus(bus);
        assertFalse(repo.updateBus("33334444", 30, 100.0, "Electricity", inexperiencedDriver));
    }

    @Test
    public void testB4_EdgeElectricWithExactFiveYearsExperience() {
        Bus bus = new Bus("33334444", 30, 100.0, "Diesel");
        repo.addBus(bus);
        Driver edgeDriver = new Driver("26@@AAAAAA", "Edge", 5, "Heavy", "4|St|Melb", "01-01-1995");
        assertTrue(repo.updateBus("33334444", 30, 100.0, "Electricity", edgeDriver));
    }

    // B5: DRIVER LICENSE RESTRICTION (3 Tests)
    @Test
    public void testB5_NormalHybridWithPublicTransportLicense() {
        Bus bus = new Bus("55556666", 30, 100.0, "Diesel");
        repo.addBus(bus);
        assertTrue(repo.updateBus("55556666", 30, 100.0, "Hybrid", oldDriver));
    }

    @Test
    public void testB5_InvalidElectricWithLightLicense() {
        Bus bus = new Bus("55556666", 30, 100.0, "Diesel");
        repo.addBus(bus);
        Driver wrongLicenseDriver = new Driver("27@@BBBBBB", "Wrong", 8, "Light", "5|St|Melb", "01-01-1992");
        assertFalse(repo.updateBus("55556666", 30, 100.0, "Electricity", wrongLicenseDriver));
    }

    @Test
    public void testB5_NormalElectricWithHeavyLicense() {
        Bus bus = new Bus("55556666", 30, 100.0, "Diesel");
        repo.addBus(bus);
        assertTrue(repo.updateBus("55556666", 30, 100.0, "Electricity", youngExpertDriver));
    }
}