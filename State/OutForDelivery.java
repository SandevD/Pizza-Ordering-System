package State;

import Core.Order.Order;

public class OutForDelivery implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new Delivered());
    }

    @Override
    public String getStatus() {
        return "Out for Delivery";
    }
}
