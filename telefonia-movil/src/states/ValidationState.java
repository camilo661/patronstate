package states;

import context.SaleOrder;

/**
 * Concrete State: VALIDATION
 * The customer has been validated. Awaiting payment.
 */
public class ValidationState implements SaleState {

    @Override
    public void requestSale(SaleOrder order) {
        order.addLog("INFO", "Sale already in validation stage. Customer has been verified.");
    }

    @Override
    public void validateCustomer(SaleOrder order) {
        order.addLog("INFO", "Customer is already validated. Proceed with payment.");
    }

    @Override
    public void processPayment(SaleOrder order) {
        order.addLog("SUCCESS", "Payment processed successfully. Order moving to activation.");
        order.setState(new PaymentProcessedState());
    }

    @Override
    public void activatePlan(SaleOrder order) {
        order.addLog("ERROR", "Cannot activate plan: payment has not been processed yet.");
    }

    @Override
    public void cancelSale(SaleOrder order) {
        order.addLog("INFO", "Sale cancelled during validation stage.");
        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "VALIDATED";
    }

    @Override
    public String getStateDescription() {
        return "Customer validated. Awaiting payment processing.";
    }
}
