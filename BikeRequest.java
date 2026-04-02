import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BikeRequest {
    private String userEmail;
    private String location;
    private LocalDateTime requestTime;

    public BikeRequest(String userEmail, String location, LocalDateTime requestTime) {
        this.userEmail = userEmail;
        this.location = location;
        this.requestTime = requestTime;
    }

    public String getUserEmail() { return userEmail; }
    public String getLocation() { return location; }
    public LocalDateTime getRequestTime() { return requestTime; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "User: " + userEmail + ", Location: " + location + ", Request Time: " + requestTime.format(formatter);
    }
}