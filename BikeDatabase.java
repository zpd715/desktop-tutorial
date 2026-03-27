import java.time.LocalDateTime;
import java.util.*;

class BikeDatabase {

    static ArrayList<Bike> bikes = new ArrayList<>();

    static {
        bikes.add(new Bike("B101", true, 100, LocalDateTime.parse("2024-01-01T10:00"), "Campus Gate 5"));
        bikes.add(new Bike("B102", false, 80, LocalDateTime.parse("2024-01-01T09:00"), "NA"));
        bikes.add(new Bike("B103", true, 90, LocalDateTime.parse("2024-01-01T08:30"), "Campus Gate 3"));
        bikes.add(new Bike("B104", false, 70, LocalDateTime.parse("2024-01-01T07:45"), "NA"));
        bikes.add(new Bike("B105", true, 60, LocalDateTime.parse("2024-01-01T11:15"), "North Canteen Lot"));
        bikes.add(new Bike("B106", true, 50, LocalDateTime.parse("2024-01-01T12:00"), "Campus Gate 2"));
        bikes.add(new Bike("B107", false, 40, LocalDateTime.parse("2024-01-01T13:30"), "NA"));
        bikes.add(new Bike("B108", true, 30, LocalDateTime.parse("2024-01-01T14:45"), "East Canteen Lot"));
        bikes.add(new Bike("B109", false, 20, LocalDateTime.parse("2024-01-01T15:00"), "NA"));
        bikes.add(new Bike("B110", true, 10, LocalDateTime.parse("2024-01-01T16:30"), "Campus Gate 1"));    

    }
}