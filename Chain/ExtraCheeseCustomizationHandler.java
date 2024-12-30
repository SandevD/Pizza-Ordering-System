package Chain;

import Core.Pizza.Pizza;
import Decorator.ExtraCheeseDecorator;
import Utils.ColorCodes;

public class ExtraCheeseCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("extra cheese")) {
            Pizza decoratedPizza = new ExtraCheeseDecorator(pizza);
            ColorCodes.printSuccessMessage("\nAdded extra cheese!", true);
            return decoratedPizza;
        } else {
            return passToNextHandler(pizza, customization);
        }
    }
}
