package Chain;

import Core.Pizza.CustomPizza;
import Core.Pizza.Pizza;

public class CrustCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("crust")) {
            String crustType = customization.split(":")[1].trim();
            ((CustomPizza) pizza).setCrust(crustType);
            System.out.println("Crust customized to: " + crustType);
        }
        return passToNextHandler(pizza, customization);
    }
}