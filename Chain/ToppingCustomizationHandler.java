package Chain;

import Core.Pizza.Pizza;

class ToppingCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("topping")) {
            String[] parts = customization.split(":");
            if (parts.length >= 3) {
                String action = parts[1];
                String topping = parts[2].trim();

                if (action.equals("add")) {
                    pizza.toppings.add(topping);
                    System.out.println("Added topping: " + topping);
                } else if (action.equals("remove")) {
                    pizza.toppings.remove(topping);
                    System.out.println("Removed topping: " + topping);
                }
            }
        }
        return passToNextHandler(pizza, customization);
    }
}
