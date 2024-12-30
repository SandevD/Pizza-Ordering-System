package Chain;

import Core.Pizza.Pizza;

public class CustomizationManager {
    private CustomizationHandler chain;

    public CustomizationManager() {
        chain = new CrustCustomizationHandler();
        CustomizationHandler sauceHandler = new SauceCustomizationHandler();
        CustomizationHandler toppingHandler = new ToppingCustomizationHandler();
        CustomizationHandler cheeseHandler = new ExtraCheeseCustomizationHandler();

        chain.setNext(sauceHandler);
        sauceHandler.setNext(toppingHandler);
        toppingHandler.setNext(cheeseHandler);
    }

    public Pizza processCustomization(Pizza pizza, String customization) {
        return chain.handleRequest(pizza, customization);
    }
}
