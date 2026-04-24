package context;

import models.Customer;
import models.MobilePlan;
import models.SaleLog;
import states.PendingState;
import states.SaleState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Context: SaleOrder
 * The main context class in the State Pattern.
 * Maintains a reference to the current state and delegates behavior to it.
 */
public class SaleOrder {

    private final String orderId;
    private final Customer customer;
    private final MobilePlan plan;
    private SaleState currentState;
    private final List<SaleLog> logs;
    private final LocalDateTime createdAt;

    public SaleOrder(Customer customer, MobilePlan plan) {
        this.orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.customer = customer;
        this.plan = plan;
        this.logs = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.currentState = new PendingState();
        addLog("INFO", "Sale order created for plan: " + plan.getName()
                + " | Operator: " + plan.getOperatorId()
                + " | Customer: " + customer.getFullName());
    }

    // ─── State delegation methods ──────────────────────────────────────────────

    public void requestSale() {
        currentState.requestSale(this);
    }

    public void validateCustomer() {
        currentState.validateCustomer(this);
    }

    public void processPayment() {
        currentState.processPayment(this);
    }

    public void activatePlan() {
        currentState.activatePlan(this);
    }

    public void cancelSale() {
        currentState.cancelSale(this);
    }

    // ─── State management ─────────────────────────────────────────────────────

    public void setState(SaleState newState) {
        addLog("INFO", "State transition: " + currentState.getStateName()
                + " → " + newState.getStateName());
        this.currentState = newState;
    }

    // ─── Logging ──────────────────────────────────────────────────────────────

    public void addLog(String type, String message) {
        logs.add(new SaleLog(type, message));
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public MobilePlan getPlan() { return plan; }
    public SaleState getCurrentState() { return currentState; }
    public List<SaleLog> getLogs() { return new ArrayList<>(logs); }

    public String getCreatedAtFormatted() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("Orden: %s | Estado: %s | Plan: %s | Cliente: %s",
                orderId, currentState.getStateName(),
                plan.getName(), customer.getFullName());
    }
}
