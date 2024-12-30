import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import Chain.CustomizationManager;
import Command.AddToFavoritesCommand;
import Command.FeedbackCommand;
import Command.OrderCommand;
import Command.PlaceOrderCommand;
import Command.ProvideFeedbackCommand;
import Core.Order.Feedback;
import Core.Order.Order;
import Core.Pizza.Pizza;
import Core.Pizza.Builder.PizzaBuilder;
import Core.User.User;
import Observer.CustomerNotifier;
import Strategy.Discount.DiscountStrategy;
import Strategy.Discount.FlatDiscount;
import Strategy.Discount.PercentageDiscount;
import Strategy.Payment.CreditCardPayment;
import Strategy.Payment.DigitalWalletPayment;
import Strategy.Payment.LoyaltyPointsPayment;
import Strategy.Payment.PaymentStrategy;
import Utils.ColorCodes;

public class PizzaOrderingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static List<Order> orders = new ArrayList<>();
    private static List<DiscountStrategy> discounts = new ArrayList<>();
    private static CustomizationManager customizationManager = new CustomizationManager();

    public static void main(String[] args) {
        // Initialize discounts
        discounts.add(new PercentageDiscount("SAVE20", 20));
        discounts.add(new FlatDiscount("FLAT500", 500));

        login();
        while (true) {
            displayMainMenu();
            int choice = getValidChoice(1, 8);

            switch (choice) {
                case 1:
                    orderPizza();
                    break;
                case 2:
                    viewAllOrders();
                    break;
                case 3:
                    trackOrder();
                    break;
                case 4:
                    viewFavorites();
                    break;
                case 5:
                    provideFeedback();
                    break;
                case 6:
                    viewFeedbacks();
                    break;
                case 7:
                    viewLoyaltyPoints();
                    break;
                case 8:
                    ColorCodes.printBannerTitle("Goodbye!", 50);
                    return;
                default:
                    ColorCodes.printErrorMessage("Invalid choice. Please try again.", false);
            }
        }
    }

    private static void login() {
        ColorCodes.printBannerTitle("Initialization", 50);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        currentUser = new User(UUID.randomUUID().toString(), name);
        System.out.println("Welcome, " + ColorCodes.GREEN + name + ColorCodes.RESET + "!");
    }

    private static void displayMainMenu() {
        ColorCodes.printBannerTitle("Pizza Odering System", 50);
        System.out.println("1. Order Pizza");
        System.out.println("2. View All Orders");
        System.out.println("3. Track Order");
        System.out.println("4. View Favorites");
        System.out.println("5. Provide Feedback");
        System.out.println("6. View All Feedbacks");
        System.out.println("7. View Loyalty Points");
        System.out.println("8. Exit\n");
        System.out.print("Enter your choice: ");
    }

    private static Pizza buildPizza() {
        PizzaBuilder builder = new PizzaBuilder();

        // Step 1: Pizza Name
        ColorCodes.printBannerTitle("Order Your Pizza - Base Cost LKR 1000.00", 50);
        ColorCodes.printStep(1, "Enter pizza name:", false);
        String name = scanner.nextLine();
        builder.withName(name);

        Pizza pizza = builder.build();

        // Step 2: Select Crust
        ColorCodes.divider(50);
        ColorCodes.printStep(2, "Select Your Crust", true);
        System.out.println("\n1. Thin Crust");
        System.out.println("2. Thick Crust");
        System.out.println("3. Stuffed Crust");
        System.out.print("Enter your choice (1-3): ");
        int crustChoice = getValidChoice(1, 3);
        String crustType;
        switch (crustChoice) {
            case 1:
                crustType = "Thin";
                break;
            case 2:
                crustType = "Thick";
                break;
            case 3:
                crustType = "Stuffed";
                break;
            default:
                crustType = "Thin";
                break;
        }
        pizza = customizationManager.processCustomization(pizza, "crust:" + crustType);

        // Step 3: Select Sauce
        ColorCodes.divider(50);
        ColorCodes.printStep(3, "Select Your Sauce", true);
        System.out.println("\n1. Tomato Sauce");
        System.out.println("2. BBQ Sauce");
        System.out.println("3. Alfredo Sauce");
        System.out.print("Enter your choice (1-3): ");
        int sauceChoice = getValidChoice(1, 3);
        String sauceType;
        switch (sauceChoice) {
            case 1:
                sauceType = "Tomato";
                break;
            case 2:
                sauceType = "BBQ";
                break;
            case 3:
                sauceType = "Alfredo";
                break;
            default:
                sauceType = "Tomato";
                break;
        }
        pizza = customizationManager.processCustomization(pizza, "sauce:" + sauceType);

        // Step 4: Select Toppings
        ColorCodes.divider(50);
        ColorCodes.printStep(4, "Select Your Toppings (Per Topping LKR 150.00)", true);
        String[] availableToppings = {
                "Pepperoni", "Mushrooms", "Onions", "Sausage",
                "Bell Peppers", "Black Olives", "Ham", "Pineapple"
        };

        boolean selectingToppings = true;
        Set<String> selectedToppings = new HashSet<>(); // To prevent duplicate toppings

        while (selectingToppings) {
            System.out.println(ColorCodes.YELLOW + "\nCurrent Toppings: " + ColorCodes.RESET +
                    (selectedToppings.isEmpty() ? "None" : String.join(", ", selectedToppings)));
            System.out.println("\nAvailable Toppings:");
            for (int i = 0; i < availableToppings.length; i++) {
                if (!selectedToppings.contains(availableToppings[i])) {
                    System.out.println((i + 1) + ". " + availableToppings[i]);
                }
            }
            System.out.println((availableToppings.length + 1) + ". Done adding toppings");

            System.out.print("Enter your choice (1-" + (availableToppings.length + 1) + "): ");
            int toppingChoice = getValidChoice(1, availableToppings.length + 1);

            if (toppingChoice == availableToppings.length + 1) {
                selectingToppings = false;
            } else {
                String selectedTopping = availableToppings[toppingChoice - 1];
                if (!selectedToppings.contains(selectedTopping)) {
                    selectedToppings.add(selectedTopping);
                    pizza = customizationManager.processCustomization(
                            pizza,
                            "topping:add:" + selectedTopping);
                }
            }
        }

        // Step 5: Extra Cheese Option
        ColorCodes.divider(50);
        ColorCodes.printStep(5, "Would you like extra cheese? (Additional LKR 250.00 fee)", true);
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter your choice (1-2): ");
        int cheeseChoice = getValidChoice(1, 2);

        if (cheeseChoice == 1) {
            pizza = customizationManager.processCustomization(pizza, "extra cheese");
        }

        return pizza;
    }

    private static void orderPizza() {
        Pizza pizza = buildPizza();
        Order order = new Order(UUID.randomUUID().toString(), currentUser);
        order.addPizza(pizza);
        orders.add(order);

        double cost = pizza.calculateCost();
        ColorCodes.printWarningMessage("Pizza cost: LKR " + cost, true);

        // Step 6 - Add delivery option selection
        ColorCodes.divider(50);
        ColorCodes.printStep(6, "Would you like delivery? (Additional LKR 300.00 fee)", true);
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter your choice (1-2): ");
        int deliveryChoice = getValidChoice(1, 2);

        boolean isDelivery = (deliveryChoice == 1);
        order.setDelivery(isDelivery);
        if (isDelivery) {
            cost += order.getDeliveryFee();
            ColorCodes.printSuccessMessage("\nDelivery fee added: LKR " + order.getDeliveryFee(), true);
            ColorCodes.printWarningMessage("Total Amount: LKR " + cost, true);
        }

        order.setTotalAmount(cost);

        // Step 7 and Step 8 ( Apply discount and proccess payment )
        processPayment(cost);
        currentUser.addLoyaltyPoints(cost);

        order.addObserver(new CustomerNotifier(currentUser.getId()));
        OrderCommand placeOrder = new PlaceOrderCommand(order);
        placeOrder.execute();

        // Transition the order to Preparing state
        order.getState().next(order);

        // Finale - Print receipt
        printReceipt(order);

        System.out.println("\nSave to favorites? (y/n)");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            OrderCommand addToFavorites = new AddToFavoritesCommand(currentUser, pizza);
            addToFavorites.execute();
            System.out.println("Added to favorites!");
        }
    }

    private static void processPayment(double cost) {
        // Step 7 - Apply discount if available
        cost = applyDiscount(cost);

        // Step 8 - Payment method selection
        ColorCodes.divider(50);
        ColorCodes.printStep(8, "Select payment method:", true);
        System.out.println("\n1. Credit Card");
        System.out.println("2. Digital Wallet");
        System.out.println("3. Loyalty Points");

        System.out.print("Enter your choice (1-3): ");
        int paymentChoice = getValidChoice(1, 3);

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
            processPayment(cost);
        }
    }

    // Step 7 - Apply discount if available
    private static double applyDiscount(double cost) {
        ColorCodes.divider(50);
        ColorCodes.printStep(7, "Enter discount code (or press enter to skip): ", false);
        String code = scanner.nextLine().trim();

        if (!code.isEmpty()) {
            for (DiscountStrategy discount : discounts) {
                if (discount.isValid(code)) {
                    double originalCost = cost;
                    double discountedCost = discount.applyDiscount(cost);
                    double discountAmount = originalCost - discountedCost;
                    ColorCodes.printSuccessMessage("\nDiscount Applied: LKR " + discountAmount, true);
                    ColorCodes.printWarningMessage("New Total: LKR " + discountedCost, true);

                    // Store discount information in the current order
                    Order currentOrder = orders.get(orders.size() - 1);
                    currentOrder.setDiscount(code, discountAmount);

                    return discountedCost;
                }
            }
            ColorCodes.printErrorMessage("\nInvalid discount code!", true);
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
            System.out.println("\nPizza cost: LKR " + cost);
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
            System.out.println("Order Status: " + order.getState().getStatus());
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
            System.out.println("Delivery: " + (order.isDelivery() ? "Yes" : "No"));

            System.out.println("\nOrdered Items:");
            for (Pizza pizza : order.getPizzas()) {
                System.out.println("\nPizza: " + pizza.getName());
                System.out.println("- Crust: " + pizza.getCrust());
                System.out.println("- Sauce: " + pizza.getSauce());
                System.out.println("- Toppings: " + String.join(", ", pizza.getToppings()));
                if (pizza.hasExtraCheese()) {
                    System.out.println("- Extra Cheese: Yes");
                }
            }

            // Display discount information
            if (order.getDiscountCode() != null) {
                System.out.println("\nDiscount Applied:");
                System.out.println("- Code: " + order.getDiscountCode());
                System.out.printf("- Amount: LKR %.2f%n", order.getDiscountAmount());
            }

            System.out.printf("Total Amount: LKR %.2f%n", order.getTotalAmount());

            if (order.hasFeedback()) {
                Feedback feedback = order.getFeedback();
                System.out.println("\nFeedback:");
                System.out.println("Rating: " + feedback.getRating() + "/5");
                System.out.println("Comment: " + feedback.getComment());
            }
            System.out.println("------------------------");
        }
    }

    private static int getValidChoice(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                ColorCodes.printErrorMessage("Please enter a number between " + min + " and " + max + ": ", false);
            } catch (Exception e) {
                scanner.nextLine();
                ColorCodes.printErrorMessage("Please enter a valid number between " + min + " and " + max + ": ", false);
            }
        }
    }

    private static void printReceipt(Order order) {
        ColorCodes.printReceiptTitle("Order Reciept", 50);
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + currentUser.getName());

        for (Pizza pizza : order.getPizzas()) {
            System.out.println("\nPizza Name ( Base Cost LKR 1000): " + pizza.getName());
            System.out.println("- Crust: " + pizza.getCrust());
            System.out.println("- Sauce: " + pizza.getSauce());
            System.out.println("- Toppings ( + LKR 150 per topping ): " + String.join(", ", pizza.getToppings()));
            if (pizza.hasExtraCheese()) {
                System.out.println("- Extra Cheese: Yes ( + LKR 250.00 )");
            }
            System.out.printf("Item Cost: LKR %.2f%n", pizza.calculateCost());
        }

        if (order.isDelivery()) {
            System.out.println("Delivery: Yes ( + LKR " + order.getDeliveryFee() + " )");
        } else {
            System.out.println("Delivery: No");
        }

        if (order.getDiscountCode() != null) {
            System.out.println("\nDiscount Applied:");
            System.out.println("Code: " + order.getDiscountCode());
            System.out.printf("Discount Amount: -LKR %.2f%n", order.getDiscountAmount());
        }

        System.out.printf("\nTotal Amount: LKR %.2f%n", order.getTotalAmount());
        ColorCodes.printReceiptTitle("Thank You!", 50);
    }
}
