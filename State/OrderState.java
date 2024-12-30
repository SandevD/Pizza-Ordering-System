package State;

import Core.Order.Order;

public interface OrderState {
    void next(Order order);

    String getStatus();
}
