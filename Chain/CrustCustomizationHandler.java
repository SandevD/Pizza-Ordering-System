package Chain;

import Core.Pizza.CustomPizza;
import Core.Pizza.Pizza;
import Utils.ColorCodes;

public class CrustCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("crust")) {
            String crustType = customization.split(":")[1].trim();
            ((CustomPizza) pizza).setCrust(crustType);
            ColorCodes.printSuccessMessage("\nCrust customized to: " + crustType, true);
        }
        return passToNextHandler(pizza, customization);
    }
}
