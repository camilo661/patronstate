package states;

import context.SaleOrder;

/**
 * Concrete State: CANCELLED
 * The sale has been cancelled. No further actions allowed.
 */
public class CancelledState implements SaleState {

    @Override
    public void requestSale(SaleOrder order) {
        order.addLog("ERROR", "This order has been cancelled. Please create a new order.");
    }

    @Override
    public void validateCustomer(SaleOrder order) {
        order.addLog("ERROR", "Cannot validate: this order has been cancelled.");
    }

    @Override
    public void processPayment(SaleOrder order) {
        order.addLog("ERROR", "Cannot process payment: this order has been cancelled.");
    }

    @Override
    public void activatePlan(SaleOrder order) {
        order.addLog("ERROR", "Cannot activate plan: this order has been cancelled.");
    }

    @Override
    public void cancelSale(SaleOrder order) {
        order.addLog("INFO", "This order is already cancelled.");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }

    @Override
    public String getStateDescription() {
        return "Order cancelled. No further actions available.";
    }
}
