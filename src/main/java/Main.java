public class Main {
    public static void main(String[] args) {

        DriverRepository repo = new DriverRepository();

        Driver d1 = new Driver(       //test case
                "2824@!!!JS",   // valid ID
                "Harry",
                5,
                "Light",
                "1|Yes Avenue|Melbourne|VIC|Australia",
                "01-01-2000"
        );

        boolean added = repo.addDriver(d1);
        System.out.println("Added: " + added);

        Driver found = repo.getDriver("2824@!!!JS");
        if (found != null) {
            System.out.println("Found: " + found.getName());
        }

        boolean updated = repo.updateDriver(
                "2824@!!!JS",
                12,
                "Medium",
                "2|Okay St|Melbourne|VIC|Australia",
                "02-02-2000"
        );

        System.out.println("Updated: " + updated);

        System.out.println("Total drivers: " + repo.countDrivers());
    }
}