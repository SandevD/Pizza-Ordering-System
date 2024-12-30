package State;

import Core.Order.Order;

public class Delivered implements OrderState {
    @Override
    public void next(Order order) {
        // Final state
    }

    @Override
    public String getStatus() {
        return "Delivered";
    }
}
