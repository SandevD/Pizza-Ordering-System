// Main System Class

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PizzaOrderingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static List<Order> orders = new ArrayList<>();
    private static List<DiscountStrategy> discounts = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize discounts
        discounts.add(new PercentageDiscount("SAVE20", 20));
        discounts.add(new FlatDiscount("FLAT10", 10));

        login();
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    orderPizza();
                    break;
                case 2:
                    viewFavorites();
                    break;
                case 3:
                    trackOrder();
                    break;
                case 4:
                    viewLoyaltyPoints();
                    break;
                case 5:
                    provideFeedback();
                    break;
                case 6:
                    viewFeedbacks();
                    break;
                case 7:
                    viewAllOrders();
                    break;
                case 8:
                    System.out.println("Thank you for using our service!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        currentUser = new User(UUID.randomUUID().toString(), name);
        System.out.println("Welcome, " + name + "!");
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Pizza Ordering System ===");
        System.out.println("1. Order Pizza");
        System.out.println("2. View Favorites");
        System.out.println("3. Track Order");
        System.out.println("4. View Loyalty Points");
        System.out.println("5. Provide Feedback");
        System.out.println("6. View All Feedbacks");
        System.out.println("7. View All Orders");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static Pizza buildPizza() {
        PizzaBuilder builder = new PizzaBuilder();

        System.out.print("Enter pizza name: ");
        String name = scanner.nextLine();
        builder.withName(name);

        System.out.println("\nSelect crust:");
        System.out.println("1. Thin 2. Thick 3. Stuffed");
        int crustChoice = scanner.nextInt();
        scanner.nextLine();
        String crust = crustChoice == 1 ? "Thin" : crustChoice == 2 ? "Thick" : "Stuffed";
        builder.withCrust(crust);

        System.out.println("\nSelect sauce:");
        System.out.println("1. Tomato 2. BBQ 3. Alfredo");
        int sauceChoice = scanner.nextInt();
        scanner.nextLine();
        String sauce = sauceChoice == 1 ? "Tomato" : sauceChoice == 2 ? "BBQ" : "Alfredo";
        builder.withSauce(sauce);

        while (true) {
            System.out.println("\nAdd toppings:");
            System.out.println("1. Pepperoni 2. Mushrooms 3. Onions 4. Done");
            int toppingChoice = scanner.nextInt();
            scanner.nextLine();

            if (toppingChoice == 4)
                break;

            String topping;

            switch (toppingChoice) {
                case 1:
                    topping = "Pepperoni";
                    break;
                case 2:
                    topping = "Mushrooms";
                    break;
                case 3:
                    topping = "Onions";
                    break;
                default:
                    topping = "Unknown topping"; // Handle the default case
                    break;
            }
            builder.addTopping(topping);
        }

        Pizza pizza = builder.build();

        System.out.println("Add extra cheese? (y/n)");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            pizza = new ExtraCheeseDecorator(pizza);
        }

        return pizza;
    }

    private static void orderPizza() {
        Pizza pizza = buildPizza();
        Order order = new Order(UUID.randomUUID().toString(), currentUser);
        order.addPizza(pizza);
        orders.add(order); // Store the order

        double cost = pizza.calculateCost();
        System.out.println("\nPizza cost: $" + cost);

        processPayment(cost);
        currentUser.addLoyaltyPoints(cost);

        order.addObserver(new CustomerNotifier(currentUser.getId()));
        OrderCommand placeOrder = new PlaceOrderCommand(order);
        placeOrder.execute();

        System.out.println("Save to favorites? (y/n)");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            OrderCommand addToFavorites = new AddToFavoritesCommand(currentUser, pizza);
            addToFavorites.execute();
            System.out.println("Added to favorites!");
        }
    }

    private static void processPayment(double cost) {
        // Apply discount if available
        cost = applyDiscount(cost);

        System.out.println("\nSelect payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Digital Wallet");
        System.out.println("3. Loyalty Points");

        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        PaymentStrategy payment;
        try {
            switch (paymentChoice) {
                case 1:
                    System.out.print("Enter card number: ");
                    payment = new CreditCardPayment(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter wallet ID: ");
                    payment = new DigitalWalletPayment(scanner.nextLine());
                    break;
                case 3:
                    payment = new LoyaltyPointsPayment(currentUser);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid payment method");
            }
            payment.pay(cost);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            processPayment(cost); // Retry payment
        }
    }

    private static double applyDiscount(double cost) {
        System.out.print("Enter discount code (or press enter to skip): ");
        String code = scanner.nextLine().trim();

        if (!code.isEmpty()) {
            for (DiscountStrategy discount : discounts) {
                if (discount.isValid(code)) {
                    double discountedCost = discount.applyDiscount(cost);
                    System.out.println("Discount applied! New total: $" + discountedCost);
                    return discountedCost;
                }
            }
            System.out.println("Invalid discount code!");
        }
        return cost;
    }

    private static void viewFavorites() {
        List<Pizza> favorites = currentUser.getFavorites();
        if (favorites.isEmpty()) {
            System.out.println("No favorites yet!");
            return;
        }

        System.out.println("\nYour Favorite Pizzas:");
        for (int i = 0; i < favorites.size(); i++) {
            System.out.println((i + 1) + ". " + favorites.get(i).getName());
        }

        System.out.println("\nWould you like to reorder any? (Enter number or 0 to go back)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= favorites.size()) {
            Pizza selectedPizza = favorites.get(choice - 1);
            double cost = selectedPizza.calculateCost();
            System.out.println("\nPizza cost: $" + cost);
            processPayment(cost);
            currentUser.addLoyaltyPoints(cost);

            Order order = new Order(UUID.randomUUID().toString(), currentUser);
            orders.add(order);
            order.addObserver(new CustomerNotifier(currentUser.getId()));
            OrderCommand placeOrder = new PlaceOrderCommand(order);
            placeOrder.execute();
        }
    }

    private static void provideFeedback() {
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();

        Order order = findOrder(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        FeedbackCommand feedbackCmd = new ProvideFeedbackCommand(order,
                getCommentFromUser(), getRatingFromUser());

        if (feedbackCmd.canProvideFeedback()) {
            feedbackCmd.execute();
        }
    }

    private static void viewFeedbacks() {
        System.out.println("\nOrder Feedbacks:");
        orders.stream()
                .filter(Order::hasFeedback)
                .forEach(order -> {
                    Feedback feedback = order.getFeedback();
                    System.out.println("Order: " + order.getOrderId());
                    System.out.println("Rating: " + feedback.getRating());
                    System.out.println("Comment: " + feedback.getComment());
                    System.out.println();
                });
    }

    private static void trackOrder() {
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine();
        Order order = findOrder(orderId);
        if (order != null) {
            System.out.println("Order Status: " + order.getState());
        } else {
            System.out.println("Order not found!");
        }
    }

    private static void viewLoyaltyPoints() {
        System.out.println("Your current loyalty points: " + currentUser.getLoyaltyPoints());
    }

    private static Order findOrder(String orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    private static String getCommentFromUser() {
        System.out.print("Enter your feedback comment: ");
        return scanner.nextLine();
    }

    private static int getRatingFromUser() {
        System.out.print("Enter rating (1-5): ");
        return scanner.nextInt();
    }

    private static void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders found!");
            return;
        }

        System.out.println("\n=== Your Orders ===");
        for (Order order : orders) {
            System.out.println("\nOrder ID: " + order.getOrderId());
            System.out.println("Status: " + order.getState().getStatus());
            System.out.println("Pizzas:");
            for (Pizza pizza : order.getPizzas()) {
                System.out.println("- " + pizza.getName());
            }
            if (order.hasFeedback()) {
                Feedback feedback = order.getFeedback();
                System.out.println("Rating: " + feedback.getRating() + "/5");
                System.out.println("Comment: " + feedback.getComment());
            }
            System.out.println("------------------------");
        }
    }
}
