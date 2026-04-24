package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model: SaleLog
 * Represents a log entry for a sale order event.
 */
public class SaleLog {

    public enum LogType { INFO, SUCCESS, ERROR }

    private final LogType type;
    private final String message;
    private final LocalDateTime timestamp;

    public SaleLog(String type, String message) {
        this.type = LogType.valueOf(type);
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public LogType getType() { return type; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("[%s] [%s] %s", getFormattedTimestamp(), type, message);
    }
}
