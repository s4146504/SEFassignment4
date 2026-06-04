// Driver class containing all validation logic/ conditions
public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    // Constructor
    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {

        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    // Validates driver ID according to condition D1
    public boolean validDriverID() {

        // Must be 10 characters
        if (driverID.length() != 10) return false;

        // First 2 characters must be between digits 2 and 9
        String firstTwo = driverID.substring(0, 2);
        if (!firstTwo.matches("[2-9]{2}")) return false;

        // Two special characters between characters 3 and 8
        String middle = driverID.substring(2, 8);
        int charCount = 0;

        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                charCount++;
            }
        }

        if (charCount <2) return false;

        // Last 2 characters must be uppercase letters A-Z
        String lastTwo = driverID.substring(8);
        if (!lastTwo.matches("[A-Z]{2}")) {
            return false;
        }

        return true;
    }

    // Validates address according to condition D2
    // Must follow format X|X|X|X|X
    public boolean validAddress() {
        String[] parts = address.split("\\|");

        if (parts.length != 5) return false;

        for (String part : parts) {
            if (part.trim().isEmpty()) return false;
        }

        return true;
    }

    // Validates birthdate according to condition D3
    // Must follow format DD-MM-YYYY
    public boolean validBirthdate() {
        return birthdate.matches("(0[1-9]|[12][0-9]|3[01])-" +
                "(0[1-9]|1[0-2])-" +
                "\\d{4}");
    }

    // Validates driver submission
    public boolean validSubmission() {
        return validDriverID() && validAddress()  && validBirthdate();
    }

    // Updates details whilst enforcing conditions
    public boolean updateDetails(int newExpiry, String newLicense, String newAddress, String newBirthdate) {

        // Cannot change if experience > 10 years
        if (this.experienceYears > 10 && !this.licenseType.equals(newLicense)) return false;

        // Address and birthdate must remain valid
        if (!newAddress.matches(".+\\|.+\\|.+\\|.+\\|.+")) return false;
        if (!newBirthdate.matches("\\d{2}-\\d{2}-\\d{4}")) return false;

        // Validates that driverID and name are not modified according to condition D5
        this.experienceYears = newExpiry;
        this.licenseType = newLicense;
        this.address = newAddress;
        this.birthdate = newBirthdate;

        return true;
    }

    // Validates license type against allowed vehicle types
    private boolean validLicense(String license) {
        return license.equals("Light") ||
                license.equals("Medium") ||
                license.equals("Heavy") ||
                license.equals("PublicTransport");
    }

    // Getters
    public String getDriverID() { return driverID; }
    public String getName() { return name; }
    public int getExperienceYears() { return experienceYears; }
    public String getLicenseType() { return licenseType; }
    public String getAddress() { return address; }
    public String getBirthdate() { return birthdate; }
}