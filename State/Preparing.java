package State;

import Core.Order.Order;

public class Preparing implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new OutForDelivery());
    }

    @Override
    public String getStatus() {
        return "Preparing";
    }
}
