package State;

import Core.Order.Order;

public class OrderPlaced implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new Preparing());
    }

    @Override
    public String getStatus() {
        return "Order Placed";
    }
}
