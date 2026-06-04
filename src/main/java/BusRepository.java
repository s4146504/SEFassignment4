import java.util.HashMap;
import java.util.Map;

public class BusRepository {
    private Map<String, Bus> database = new HashMap<>();


    public boolean addBus(Bus bus) {
        if (bus == null) return false;

        if (bus.getBusID() == null || bus.getBusID().length() != 8) {
            return false;
        }

        for (int i = 0; i < bus.getBusID().length(); i++) {
            char c = bus.getBusID().charAt(i);

            if (!Character.isDigit(c)) {
                return false;
            }
        }

        if (database.containsKey(bus.getBusID())) {
            return false;
        }

        database.put(bus.getBusID(), bus);
        return true;
    }

    public Bus getBus(String busID) {
        return database.get(busID);
    }


    public boolean updateBus(String busID, int newCapacity, double newFuel, String newFuelType, Driver driver) {
        Bus bus = database.get(busID);
        if (bus == null || driver == null) return false;

        if (newCapacity > bus.getCapacity()) {
            return false;
        }

        int driverAge = calculateAge(driver.getBirthdate());

        if (driverAge > 50 && newCapacity >= 50) {
            return false;
        }

        if (newFuelType.equalsIgnoreCase("Electricity") && driver.getExperienceYears() < 5) {
            return false;
        }

        if ((newFuelType.equalsIgnoreCase("Electricity") || newFuelType.equalsIgnoreCase("Hybrid")))
        {
            String license = driver.getLicenseType();
            if (!license.equalsIgnoreCase("Heavy") && !license.equalsIgnoreCase("Public Transport")) {
                return false;
            }
        }

        bus.setCapacity(newCapacity);
        bus.setFuelLevel(newFuel);
        bus.setFuelType(newFuelType);
        return true;
    }

    public int countBuses() {
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
}