package states;

import context.SaleOrder;

/**
 * Concrete State: PAYMENT_PROCESSED
 * Payment has been confirmed. Ready for plan activation.
 */
public class PaymentProcessedState implements SaleState {

    @Override
    public void requestSale(SaleOrder order) {
        order.addLog("INFO", "Sale already paid. Waiting for plan activation.");
    }

    @Override
    public void validateCustomer(SaleOrder order) {
        order.addLog("INFO", "Customer already validated and payment confirmed.");
    }

    @Override
    public void processPayment(SaleOrder order) {
        order.addLog("INFO", "Payment is already confirmed for this order.");
    }

    @Override
    public void activatePlan(SaleOrder order) {
        order.addLog("SUCCESS", "Plan activated successfully! Customer is now connected.");
        order.setState(new ActiveState());
    }

    @Override
    public void cancelSale(SaleOrder order) {
        order.addLog("INFO", "Sale cancelled after payment. Refund process initiated.");
        order.setState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "PAYMENT_PROCESSED";
    }

    @Override
    public String getStateDescription() {
        return "Payment confirmed. Ready for plan activation.";
    }
}
