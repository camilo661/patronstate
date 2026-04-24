package states;

import context.SaleOrder;

/**
 * State Interface - Patrón State
 * Defines the actions that can be performed on a sale order in any state.
 */
public interface SaleState {
    void requestSale(SaleOrder order);
    void validateCustomer(SaleOrder order);
    void processPayment(SaleOrder order);
    void activatePlan(SaleOrder order);
    void cancelSale(SaleOrder order);
    String getStateName();
    String getStateDescription();
}
