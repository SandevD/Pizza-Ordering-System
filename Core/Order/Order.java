package Core.Order;

import java.util.ArrayList;
import java.util.List;

import Core.Pizza.Pizza;
import Core.User.User;
import Observer.OrderObserver;
import State.OrderPlaced;
import State.OrderState;

public class Order {
    private String orderId;
    private User user;
    private List<Pizza> pizzas;
    private OrderState state;
    private List<OrderObserver> observers;
    private double totalAmount;
    private double discountAmount;
    private String discountCode;
    private Feedback feedback;
    private boolean isDelivery;
    private static final double DELIVERY_FEE = 300.00;

    public Order(String orderId, User user) {
        this.orderId = orderId;
        this.user = user;
        this.pizzas = new ArrayList<>();
        this.state = new OrderPlaced();
        this.observers = new ArrayList<>();
    }

    public void setDelivery(boolean delivery) {
        this.isDelivery = delivery;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public double getDeliveryFee() {
        return isDelivery ? DELIVERY_FEE : 0.00;
    }

    public String getOrderId() {
        return orderId;
    }

    public boolean hasFeedback() {
        return feedback != null;
    }

    public void addFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
        notifyObservers();
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(state.getStatus());
        }
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setDiscount(String discountCode, double discountAmount) {
        this.discountCode = discountCode;
        this.discountAmount = discountAmount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
