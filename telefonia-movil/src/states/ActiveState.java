package states;

import context.SaleOrder;

/**
 * Concrete State: ACTIVE
 * The plan has been activated. Process is complete.
 */
public class ActiveState implements SaleState {

    @Override
    public void requestSale(SaleOrder order) {
        order.addLog("INFO", "Plan is already active. Start a new order to purchase again.");
    }

    @Override
    public void validateCustomer(SaleOrder order) {
        order.addLog("INFO", "Plan is already active. No further validation needed.");
    }

    @Override
    public void processPayment(SaleOrder order) {
        order.addLog("INFO", "Plan is already active and paid. Process is complete.");
    }

    @Override
    public void activatePlan(SaleOrder order) {
        order.addLog("INFO", "Plan is already active and running.");
    }

    @Override
    public void cancelSale(SaleOrder order) {
        order.addLog("ERROR", "Cannot cancel an active plan. Contact customer support for deactivation.");
    }

    @Override
    public String getStateName() {
        return "ACTIVE";
    }

    @Override
    public String getStateDescription() {
        return "Plan activated and running. Sale process completed successfully.";
    }
}
