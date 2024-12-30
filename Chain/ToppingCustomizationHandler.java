package Chain;

import Core.Pizza.Pizza;
import Utils.ColorCodes;

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
                    ColorCodes.printSuccessMessage("\nAdded topping: " + topping, true);
                    ColorCodes.divider(50);
                } else if (action.equals("remove")) {
                    pizza.toppings.remove(topping);
                    ColorCodes.printSuccessMessage("\nRemoved topping: " + topping, true);
                    ColorCodes.divider(50);
                }
            }
        }
        return passToNextHandler(pizza, customization);
    }
}
