package Chain;

import Core.Pizza.Pizza;

public abstract class CustomizationHandler {
    protected CustomizationHandler nextHandler;

    public void setNext(CustomizationHandler handler) {
        this.nextHandler = handler;
    }

    public abstract Pizza handleRequest(Pizza pizza, String customization);

    protected Pizza passToNextHandler(Pizza pizza, String customization) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(pizza, customization);
        }
        return pizza;
    }
}
