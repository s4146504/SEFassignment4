import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Driver class, covers all driver related conditions

public class DriverUnitTest {

    // Valid if satisfies 10 characters, first two digits between 2-9, at least 2 special characters in the middle and ends with 2 uppercase letters

    @Test
    void validDriverID_shouldPass() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        assertTrue(d.validDriverID()
        );
    }

    // Should fail because driver ID is not 10 characters long

    @Test
    void driverID_wrongLength_shouldFail() {
        Driver d = new Driver(
                "23@@!!56A",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        assertFalse(d.validDriverID()
        );
    }

    // Should fail because driver ID does not contain enough special characters

    @Test
    void driverID_notEnoughSpecialChars_shouldFail() {
        Driver d = new Driver(
                "23456789AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        assertFalse(d.validDriverID()
        );
    }


    @Test
    void validAddress_shouldPass() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        assertTrue(d.validAddress()
        );
    }

    // Should fail because address does not contain enough arguments

    @Test
    void address_missingParts_shouldFail() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC",
                "01-01-2000"
        );

        assertFalse(d.validAddress()
        );
    }

    // Should fail because address does not contain any information

    @Test
    void address_emptyField_shouldFail() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1||Melbourne|VIC|Australia",
                "01-01-2000"
        );

        assertFalse(d.validAddress()
        );
    }

    @Test
    void validBirthdate_shouldPass() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        assertTrue(d.validBirthdate()
        );
    }

    // Should fail due to incorrect date formatting

    @Test
    void birthdate_wrongFormat_shouldFail() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "2000-01-01"
        );

        assertFalse(d.validBirthdate()
        );
    }

    // Should fail because date is invalid

    @Test
    void birthdate_invalidDay_shouldFail() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "32-01-2000"
        );

        assertFalse(d.validBirthdate()
        );
    }

    @Test
    void updateLicense_under10Years_shouldPass() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        boolean result = d.updateDetails(
                5,
                "Medium",
                "2|New|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        assertTrue(result);
    }

    // Should fail because experience is greater than 10 years

    @Test
    void updateLicense_over10Years_changeLicense_shouldFail() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                15,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        boolean result = d.updateDetails(
                15,
                "Medium",
                "2|New|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        assertFalse(result);
    }

    @Test
    void updateLicense_over10Years_sameLicense_shouldPass() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                15,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        boolean result = d.updateDetails(
                15,
                "Light",
                "2|New|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        assertTrue(result);
    }

    // Driver ID should remain unchanged after update

    @Test
    void driverID_shouldNotChange() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        d.updateDetails(
                6,
                "Light",
                "2|New|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        assertEquals(
                "23@@!!56AB", d.getDriverID()
        );
    }

    // Name should remain unchanged after update

    @Test
    void name_shouldNotChange() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        d.updateDetails(
                6,
                "Light",
                "2|New|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        assertEquals("Harry", d.getName()
        );
    }

    @Test
    void otherFields_shouldUpdateCorrectly() {
        Driver d = new Driver(
                "23@@!!56AB",
                "Harry",
                5,
                "Light",
                "1|Main|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        d.updateDetails(
                10,
                "Light",
                "2|New|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        assertEquals(
                10,
                d.getExperienceYears()
        );
        assertEquals(
                "2|New|Melbourne|VIC|Australia",
                d.getAddress()
        );
    }

}