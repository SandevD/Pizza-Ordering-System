package Chain;

import Core.Pizza.CustomPizza;
import Core.Pizza.Pizza;
import Utils.ColorCodes;

public class SauceCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("sauce")) {
            String sauceType = customization.split(":")[1].trim();
            ((CustomPizza) pizza).setSauce(sauceType);
            ColorCodes.printSuccessMessage("\nSauce customized to: " + sauceType, true);
        }
        return passToNextHandler(pizza, customization);
    }
}
