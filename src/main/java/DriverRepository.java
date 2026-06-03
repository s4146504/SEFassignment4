import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private final String FILE_PATH = "drivers.txt";

    private List<Driver> loadDrivers() {
        List<Driver> drivers = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return drivers;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

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

    private void saveDrivers(List<Driver> drivers) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));

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

    public boolean addDriver(Driver driver) {
        List<Driver> drivers = loadDrivers();

        if (!driver.validSubmission()) return false;

        //check duplicate
        for (Driver d : drivers) {
            if (d.getDriverID().equals(driver.getDriverID())) {
                return false;
            }
        }

        drivers.add(driver);
        saveDrivers(drivers);
        return true;
    }

    public Driver getDriver(String driverID) {
        List<Driver> drivers = loadDrivers();

        for (Driver d : drivers) {
            if (d.getDriverID().equals(driverID)) {
                return d;
            }
        }

        return null;
    }

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

        return false;
    }

    public int countDrivers() {
        return loadDrivers().size();
    }
}