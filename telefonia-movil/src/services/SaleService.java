package services;

import context.SaleOrder;
import data.PlanCatalog;
import models.Customer;
import models.MobilePlan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service: SaleService
 * Business logic layer that manages sale orders.
 * Coordinates the interaction between Context, States, Models and Data.
 */
public class SaleService {

    private final List<SaleOrder> orders = new ArrayList<>();
    private static final AtomicInteger customerCounter = new AtomicInteger(1);

    /**
     * Creates a new sale order for a given plan.
     */
    public SaleOrder createOrder(String customerName, String idNumber,
                                  String phoneNumber, String email, String planId) {
        MobilePlan plan = PlanCatalog.findPlanById(planId);
        if (plan == null) {
            throw new IllegalArgumentException("Plan not found: " + planId);
        }

        String customerId = "CUST-" + String.format("%04d", customerCounter.getAndIncrement());
        Customer customer = new Customer(customerId, customerName, idNumber, phoneNumber, email);
        SaleOrder order = new SaleOrder(customer, plan);
        orders.add(order);
        return order;
    }

    /**
     * Advances the order to the next logical state.
     */
    public void advanceOrder(SaleOrder order) {
        String currentState = order.getCurrentState().getStateName();
        switch (currentState) {
            case "PENDING":
                order.validateCustomer();
                break;
            case "VALIDATED":
                order.processPayment();
                break;
            case "PAYMENT_PROCESSED":
                order.activatePlan();
                break;
            default:
                order.addLog("INFO", "Order is in terminal state: " + currentState);
        }
    }

    /**
     * Cancels an order.
     */
    public void cancelOrder(SaleOrder order) {
        order.cancelSale();
    }

    /**
     * Returns all orders registered in the current session.
     */
    public List<SaleOrder> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Returns all orders in JSON format for the web frontend.
     */
    public String getAllOrdersAsJson() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < orders.size(); i++) {
            SaleOrder o = orders.get(i);
            sb.append(orderToJson(o));
            if (i < orders.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns a specific order index for web operations.
     */
    public SaleOrder getOrderByIndex(int index) {
        if (index < 0 || index >= orders.size()) return null;
        return orders.get(index);
    }

    private String orderToJson(SaleOrder o) {
        StringBuilder logs = new StringBuilder("[");
        List<models.SaleLog> logList = o.getLogs();
        for (int i = 0; i < logList.size(); i++) {
            models.SaleLog log = logList.get(i);
            logs.append("{\"type\":\"").append(log.getType()).append("\",")
                .append("\"message\":\"").append(escapeJson(log.getMessage())).append("\",")
                .append("\"time\":\"").append(log.getFormattedTimestamp()).append("\"}");
            if (i < logList.size() - 1) logs.append(",");
        }
        logs.append("]");

        return "{" +
                "\"orderId\":\"" + o.getOrderId() + "\"," +
                "\"state\":\"" + o.getCurrentState().getStateName() + "\"," +
                "\"stateDesc\":\"" + o.getCurrentState().getStateDescription() + "\"," +
                "\"customerName\":\"" + escapeJson(o.getCustomer().getFullName()) + "\"," +
                "\"customerPhone\":\"" + o.getCustomer().getPhoneNumber() + "\"," +
                "\"planId\":\"" + o.getPlan().getId() + "\"," +
                "\"planName\":\"" + escapeJson(o.getPlan().getName()) + "\"," +
                "\"planDays\":" + o.getPlan().getDurationDays() + "," +
                "\"planPrice\":" + o.getPlan().getPriceInCOP() + "," +
                "\"operatorId\":\"" + o.getPlan().getOperatorId() + "\"," +
                "\"createdAt\":\"" + o.getCreatedAtFormatted() + "\"," +
                "\"logs\":" + logs +
                "}";
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
