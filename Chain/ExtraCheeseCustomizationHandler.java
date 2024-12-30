package Chain;

import Core.Pizza.Pizza;
import Decorator.ExtraCheeseDecorator;

public class ExtraCheeseCustomizationHandler extends CustomizationHandler {
    @Override
    public Pizza handleRequest(Pizza pizza, String customization) {
        if (customization.toLowerCase().contains("extra cheese")) {
            Pizza decoratedPizza = new ExtraCheeseDecorator(pizza);
            System.out.println("Added extra cheese");
            return decoratedPizza;
        } else {
            return passToNextHandler(pizza, customization);
        }
    }
}
