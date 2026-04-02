import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ERyderLog {
    private String logId;
    private String event;
    private LocalDateTime timestamp;

    public ERyderLog(String logId, String event, LocalDateTime timestamp) {
        this.logId = logId;
        this.event = event;
        this.timestamp = timestamp;
    }

    public String getLogId() { return logId; }
    public String getEvent() { return event; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return logId + " - " + event + " - " + timestamp.format(formatter);
    }
}