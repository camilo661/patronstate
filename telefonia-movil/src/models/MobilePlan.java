package models;

/**
 * Model: MobilePlan
 * Represents a mobile phone plan offered by an operator.
 */
public class MobilePlan {

    private final String id;
    private final String operatorId;
    private final String name;
    private final int durationDays;
    private final int priceInCOP;
    private final String dataIncluded;
    private final String minutesIncluded;
    private final String smsIncluded;
    private final String badge;

    public MobilePlan(String id, String operatorId, String name, int durationDays,
                      int priceInCOP, String dataIncluded, String minutesIncluded,
                      String smsIncluded, String badge) {
        this.id = id;
        this.operatorId = operatorId;
        this.name = name;
        this.durationDays = durationDays;
        this.priceInCOP = priceInCOP;
        this.dataIncluded = dataIncluded;
        this.minutesIncluded = minutesIncluded;
        this.smsIncluded = smsIncluded;
        this.badge = badge;
    }

    public String getId() { return id; }
    public String getOperatorId() { return operatorId; }
    public String getName() { return name; }
    public int getDurationDays() { return durationDays; }
    public int getPriceInCOP() { return priceInCOP; }
    public String getDataIncluded() { return dataIncluded; }
    public String getMinutesIncluded() { return minutesIncluded; }
    public String getSmsIncluded() { return smsIncluded; }
    public String getBadge() { return badge; }

    @Override
    public String toString() {
        return String.format("[%s] %s - %d días - $%,d COP | Data: %s | Minutos: %s | SMS: %s",
                id, name, durationDays, priceInCOP, dataIncluded, minutesIncluded, smsIncluded);
    }
}
