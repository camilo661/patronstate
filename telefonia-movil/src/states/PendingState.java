package states;

import context.SaleOrder;

/**
 * Concrete State: PENDING
 * The sale order has been created but not yet validated.
 */
public class PendingState implements SaleState {

    @Override
    public void requestSale(SaleOrder order) {
        order.addLog("INFO", "Sale request already registered. Waiting for customer validation.");
    }

    @Override
    public void validateCustomer(SaleOrder order) {
        order.addLog("SUCCESS", "Customer validated successfully. Proceeding to payment.");
        order.setState(new ValidationState());
    }

    @Override
    public void processPayment(SaleOrder order) {
        order.addLog("ERROR", "Cannot process payment: customer has not been validated yet.");
    }

    @Override
    public void activatePlan(SaleOrder order) {
        order.addLog("ERROR", "Cannot activate plan: sale has not been paid yet.");
    }

    @Override
    public void cancelSale(SaleOrder order) {
        order.addLog("INFO", "Sale cancelled by customer request.");
        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }

    @Override
    public String getStateDescription() {
        return "Order created. Awaiting customer validation.";
    }
}
