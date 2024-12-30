import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    protected String name;
    protected String crust;
    protected String sauce;
    protected List<String> toppings;
    protected double price;

    public Pizza() {
        toppings = new ArrayList<>();
    }

    public abstract double calculateCost();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name != null ? name : "Unnamed Pizza";
    }
}

// Concrete Pizza class
class CustomPizza extends Pizza {
    @Override
    public double calculateCost() {
        double cost = 10.0; // Base cost
        cost += toppings.size() * 1.5;
        return cost;
    }
}

// Builder Pattern
class PizzaBuilder {
    private CustomPizza pizza;

    public PizzaBuilder() {
        pizza = new CustomPizza();
    }

    public PizzaBuilder withName(String name) {
        pizza.name = name.trim();
        return this;
    }

    public PizzaBuilder withCrust(String crust) {
        pizza.crust = crust;
        return this;
    }

    public PizzaBuilder withSauce(String sauce) {
        pizza.sauce = sauce;
        return this;
    }

    public PizzaBuilder addTopping(String topping) {
        pizza.toppings.add(topping);
        return this;
    }

    public Pizza build() {
        return pizza;
    }
}

// Decorator Pattern
abstract class ToppingDecorator extends Pizza {
    protected Pizza pizza;

    public ToppingDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}

class ExtraCheeseDecorator extends ToppingDecorator {
    public ExtraCheeseDecorator(Pizza pizza) {
        super(pizza);
        this.name = pizza.getName();
    }

    @Override
    public double calculateCost() {
        return pizza.calculateCost() + 2.0;
    }
    
    @Override
    public String getName() {
        return pizza.getName();
    }
}

// Feedback System
class Feedback {
    private String orderId;
    private String comment;
    private int rating;

    public Feedback(String orderId, String comment, int rating) {
        this.orderId = orderId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getOrderId() { return orderId; }
    public String getComment() { return comment; }
    public int getRating() { return rating; }
}

// User Profile
class User {
    private String id;
    private String name;
    private List<Pizza> favorites;
    private int loyaltyPoints;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.favorites = new ArrayList<>();
        this.loyaltyPoints = 0;
    }

    public void addFavorite(Pizza pizza) {
        favorites.add(pizza);
    }

    public void addLoyaltyPoints(double amount) {
        this.loyaltyPoints += (int)(amount / 5) * 3;
    }

    public void deductLoyaltyPoints(int points) {
        this.loyaltyPoints -= points;
    }

    public String getId() { return id; }
    public List<Pizza> getFavorites() { return favorites; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public String getName() { return name; }
}

// Order States
interface OrderState {
    void next(Order order);
    String getStatus();
}

class OrderPlaced implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new Preparing());
    }

    @Override
    public String getStatus() {
        return "Order Placed";
    }
}

class Preparing implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new OutForDelivery());
    }

    @Override
    public String getStatus() {
        return "Preparing";
    }
}

class OutForDelivery implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new Delivered());
    }

    @Override
    public String getStatus() {
        return "Out for Delivery";
    }
}

class Delivered implements OrderState {
    @Override
    public void next(Order order) {
        // Final state
    }

    @Override
    public String getStatus() {
        return "Delivered";
    }
}

// Order class
class Order {
    private String orderId;
    private User user;
    private List<Pizza> pizzas;
    private OrderState state;
    private List<OrderObserver> observers;
    private double totalAmount;
    private Feedback feedback;

    public Order(String orderId, User user) {
        this.orderId = orderId;
        this.user = user;
        this.pizzas = new ArrayList<>();
        this.state = new OrderPlaced();
        this.observers = new ArrayList<>();
    }

    public String getOrderId() { return orderId; }
    public boolean hasFeedback() { return feedback != null; }
    public void addFeedback(Feedback feedback) { this.feedback = feedback; }
    public Feedback getFeedback() { return feedback; }
    public void setTotalAmount(double amount) { this.totalAmount = amount; }
    public double getTotalAmount() { return totalAmount; }
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
}

// Observer Pattern
interface OrderObserver {
    void update(String status);
}

class CustomerNotifier implements OrderObserver {
    private String customerId;

    public CustomerNotifier(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void update(String status) {
        System.out.println("Notification for customer " + customerId + ": Order status - " + status);
    }
}

// Command Pattern
interface OrderCommand {
    void execute();
}

interface FeedbackCommand extends OrderCommand {
    boolean canProvideFeedback();
}

class PlaceOrderCommand implements OrderCommand {
    private Order order;

    public PlaceOrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute() {
        System.out.println("Order placed successfully");
    }
}

class AddToFavoritesCommand implements OrderCommand {
    private User user;
    private Pizza pizza;

    public AddToFavoritesCommand(User user, Pizza pizza) {
        this.user = user;
        this.pizza = pizza;
    }

    @Override
    public void execute() {
        user.addFavorite(pizza);
    }
}

class ProvideFeedbackCommand implements FeedbackCommand {
    private Order order;
    private String comment;
    private int rating;

    public ProvideFeedbackCommand(Order order, String comment, int rating) {
        this.order = order;
        this.comment = comment;
        this.rating = rating;
    }

    @Override
    public boolean canProvideFeedback() {
        return !order.hasFeedback();
    }

    @Override
    public void execute() {
        if (canProvideFeedback()) {
            order.addFeedback(new Feedback(order.getOrderId(), comment, rating));
            System.out.println("Feedback submitted successfully!");
        } else {
            System.out.println("Feedback already provided for this order.");
        }
    }
}

// Payment Strategy Pattern
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with credit card " + cardNumber);
    }
}

class DigitalWalletPayment implements PaymentStrategy {
    private String walletId;

    public DigitalWalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with digital wallet " + walletId);
    }
}

class LoyaltyPointsPayment implements PaymentStrategy {
    private User user;
    
    public LoyaltyPointsPayment(User user) {
        this.user = user;
    }

    @Override
    public void pay(double amount) {
        int requiredPoints = (int)(amount * 10); // 10 points = 1 currency unit
        if (user.getLoyaltyPoints() >= requiredPoints) {
            user.deductLoyaltyPoints(requiredPoints);
            System.out.println("Paid with " + requiredPoints + " loyalty points");
        } else {
            throw new IllegalStateException("Insufficient loyalty points");
        }
    }
}

// Discount Strategy Pattern
interface DiscountStrategy {
    double applyDiscount(double amount);
    boolean isValid(String code);
}

class PercentageDiscount implements DiscountStrategy {
    private String code;
    private double percentage;

    public PercentageDiscount(String code, double percentage) {
        this.code = code;
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount * (1 - percentage/100);
    }

    @Override
    public boolean isValid(String code) {
        return this.code.equals(code);
    }
}

class FlatDiscount implements DiscountStrategy {
    private String code;
    private double amount;

    public FlatDiscount(String code, double amount) {
        this.code = code;
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double amount) {
        return Math.max(0, amount - this.amount);
    }

    @Override
    public boolean isValid(String code) {
        return this.code.equals(code);
    }
}