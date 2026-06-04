import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Handles the storage and retrieval of Driver objects using JSON handling
public class DriverRepository {

    // File path for storage
    private final String FILE_PATH = "drivers.txt";

    // Loads existing drivers
    private List<Driver> loadDrivers() {
        List<Driver> drivers = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return drivers;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            // Read file line by line to gather information
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Double checks correct amount of items exist
                if (parts.length == 6) {
                    Driver d = new Driver(
                            parts[0], // id
                            parts[1], // name
                            Integer.parseInt(parts[2]),
                            parts[3],
                            parts[4],
                            parts[5]
                    );
                    drivers.add(d);
                }
            }

            reader.close();

        } catch (Exception e) {  //ignored for simplicity
        }

        return drivers;
    }

    // Saves drivers to a file, and overwrites existing data with updated data
    private void saveDrivers(List<Driver> drivers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));

            // Loops through lines
            for (Driver d : drivers) {
                String line = d.getDriverID() + "," +
                        d.getName() + "," +
                        d.getExperienceYears() + "," +
                        d.getLicenseType() + "," +
                        d.getAddress() + "," +
                        d.getBirthdate();

                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (Exception e) { //ignored for simplicity
        }
    }

    // Adds a driver to the repository
    public boolean addDriver(Driver driver) {
        List<Driver> drivers = loadDrivers();

        // Validates data before adding
        if (!driver.validSubmission()) return false;

        // Check for duplicate driver IDs
        for (Driver d : drivers) {
            if (d.getDriverID().equals(driver.getDriverID())) {
                return false;
            }
        }

        drivers.add(driver);
        saveDrivers(drivers);
        return true;
    }

    // Retrieval of driver by driverID
    public Driver getDriver(String driverID) {
        List<Driver> drivers = loadDrivers();

        for (Driver d : drivers) {
            if (d.getDriverID().equals(driverID)) {
                return d;
            }
        }

        return null;
    }

    // Update an existing drivers details
    public boolean updateDriver(String driverID, int exp, String license, String address, String birthdate) {
        List<Driver> drivers = loadDrivers();

        for (Driver d : drivers) {
            if (d.getDriverID().equals(driverID)) {

                boolean success = d.updateDetails(exp, license, address, birthdate);

                if (success) {
                    saveDrivers(drivers);
                }

                return success;
            }
        }

        // Driver not found
        return false;
    }

    // Returns count of stored drivers
    public int countDrivers() {
        return loadDrivers().size();
    }
}