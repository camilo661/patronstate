package models;

/**
 * Model: Customer
 * Represents a customer purchasing a mobile plan.
 */
public class Customer {

    private final String id;
    private final String fullName;
    private final String idNumber;
    private final String phoneNumber;
    private final String email;

    public Customer(String id, String fullName, String idNumber,
                    String phoneNumber, String email) {
        this.id = id;
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getIdNumber() { return idNumber; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Cliente: %s | CC: %s | Tel: %s | Email: %s",
                fullName, idNumber, phoneNumber, email);
    }
}
