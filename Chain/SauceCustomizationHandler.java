package Chain;

import Core.Pizza.CustomPizza;
import Core.Pizza.Pizza;

public class SauceCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("sauce")) {
            String sauceType = customization.split(":")[1].trim();
            ((CustomPizza) pizza).setSauce(sauceType);
            System.out.println("Sauce customized to: " + sauceType);
        }
        return passToNextHandler(pizza, customization);
    }
}
