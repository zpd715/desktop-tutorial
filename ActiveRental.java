import java.time.LocalDateTime;

public class ActiveRental {
    private String rentalId;
    private RegisteredUser user;      // 关联用户对象
    private String bikeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ActiveRental(String rentalId, RegisteredUser user, String bikeId, LocalDateTime startTime) {
        this.rentalId = rentalId;
        this.user = user;
        this.bikeId = bikeId;
        this.startTime = startTime;
        this.endTime = null;
    }

    // Getters and Setters
    public String getRentalId() { return rentalId; }
    public RegisteredUser getUser() { return user; }
    public String getBikeId() { return bikeId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "ActiveRental{" + "rentalId='" + rentalId + '\'' + ", user=" + user.getFullName() +
               ", bikeId='" + bikeId + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
}