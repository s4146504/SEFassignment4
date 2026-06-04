import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BusRepository
{
    private Map<String, Bus> database = new HashMap<>();
    private final String filePath;

    public BusRepository()
    {
        this.filePath = "buses.txt";
        loadFromFile();
    }

    public BusRepository(String filePath)
    {
        this.filePath = filePath;
        loadFromFile();
    }

    public boolean addBus(Bus bus)
    {
        if (bus == null) return false;

        if (bus.getBusID() == null || bus.getBusID().length() != 8)
        {
            return false;
        }

        for (int i = 0; i < bus.getBusID().length(); i++)
        {
            char c = bus.getBusID().charAt(i);
            if (!Character.isDigit(c))
            {
                return false;
            }
        }

        if (database.containsKey(bus.getBusID()))
        {
            return false;
        }

        database.put(bus.getBusID(), bus);
        saveToFile();
        return true;
    }

    public Bus getBus(String busID)
    {
        loadFromFile();
        return database.get(busID);
    }

    public boolean updateBus(String busID, int newCapacity, double newFuel, String newFuelType, Driver driver) {
        loadFromFile();
        Bus bus = database.get(busID);
        if (bus == null || driver == null)
            return false;

        // Condition B2
        if (newCapacity > bus.getCapacity())
        {
            return false;
        }

        int driverAge = calculateAge(driver.getBirthdate());

        // Condition B3
        if (driverAge > 50 && newCapacity >= 50)
        {
            return false;
        }

        // Condition B4
        if (newFuelType.equalsIgnoreCase("Electricity") && driver.getExperienceYears() < 5)
        {
            return false;
        }

        // Condition B5
        if ((newFuelType.equalsIgnoreCase("Electricity") || newFuelType.equalsIgnoreCase("Hybrid")))
        {
            String license = driver.getLicenseType();

            if (!license.equalsIgnoreCase("Heavy") && !license.equalsIgnoreCase("PublicTransport"))
            {
                return false;
            }
        }

        bus.setCapacity(newCapacity);
        bus.setFuelLevel(newFuel);
        bus.setFuelType(newFuelType);

        saveToFile();
        return true;
    }

    public int countBuses() {
        loadFromFile();
        return database.size();
    }

    private int calculateAge(String birthdate) {
        if (birthdate == null || birthdate.length() != 10) {
            return 0;
        }

        if (birthdate.charAt(2) != '-' || birthdate.charAt(5) != '-') {
            return 0;
        }

        for (int i = 0; i < birthdate.length(); i++) {
            if (i == 2 || i == 5) {
                continue;
            }
            if (!Character.isDigit(birthdate.charAt(i))) {
                return 0;
            }
        }

        try {
            int birthYear = Integer.parseInt(birthdate.substring(6, 10));
            return 2026 - birthYear;
        } catch (Exception e) {
            return 0;
        }
    }


    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Bus bus : database.values()) {
                writer.write(bus.getBusID() + "|" +
                        bus.getCapacity() + "|" +
                        bus.getFuelLevel() + "|" +
                        bus.getFuelType());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving system data to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        database.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String busID = parts[0];
                    int capacity = Integer.parseInt(parts[1]);
                    double fuelLevel = Double.parseDouble(parts[2]);
                    String fuelType = parts[3];

                    Bus bus = new Bus(busID, capacity, fuelLevel, fuelType);
                    database.put(busID, bus);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading system data from file: " + e.getMessage());
        }
    }
}