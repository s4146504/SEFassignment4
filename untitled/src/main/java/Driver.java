public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {

        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    public boolean validDriverID() {
        if (driverID.length() != 10) return false;

        String firstTwo = driverID.substring(0, 2);
        if (!firstTwo.matches("[2-9]{2}")) return false;

        String middle = driverID.substring(2, 8);
        int charCount = 0;

        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                charCount++;
            }
        }

        if (charCount <2) return false;

        String lastTwo = driverID.substring(8);
        if (!lastTwo.matches("[A-Z]{2}")) {
            return false;
        }

        return true;
    }

    public boolean validAddress() {
        String[] parts = address.split("\\|");
        return parts.length == 5;
    }

    public boolean validBirthdate() {
        boolean matches = birthdate.matches("\\d{2}-\\d{2}-\\d{4}");
        return matches;
    }

    public boolean validSubmission() {
        return validDriverID() && validAddress()  && validBirthdate();
    }

    public boolean updateDetails(int newExpiry, String newLicense, String newAddress, String newBirthdate) {

        if (this.experienceYears > 10 && !this.licenseType.equals(newLicense)) return false;

        if (!newAddress.matches(".+\\|.+\\|.+\\|.+\\|.+")) return false;
        if (!newBirthdate.matches("\\d{2}-\\d{2}-\\d{4}")) return false;

        this.experienceYears = newExpiry;
        this.licenseType = newLicense;
        this.address = newAddress;
        this.birthdate = newBirthdate;

        return true;
    }

    private boolean validLicense(String license) {
        return license.equals("Light") ||
                license.equals("Medium") ||
                license.equals("Heavy") ||
                license.equals("PublicTransport");
    }

    public String getDriverID() { return driverID; }
    public String getName() { return name; }
    public int getExperienceYears() { return experienceYears; }
    public String getLicenseType() { return licenseType; }
    public String getAddress() { return address; }
    public String getBirthdate() { return birthdate; }
}