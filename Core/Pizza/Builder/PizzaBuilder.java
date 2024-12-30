package Core.Pizza.Builder;

import Core.Pizza.CustomPizza;
import Core.Pizza.Pizza;

public class PizzaBuilder {
    private CustomPizza pizza;

    public PizzaBuilder() {
        pizza = new CustomPizza();
    }

    public PizzaBuilder withName(String name) {
        pizza.name = name.trim();
        return this;
    }

    public PizzaBuilder withCrust(String crust) {
        pizza.crust = crust;
        return this;
    }

    public PizzaBuilder withSauce(String sauce) {
        pizza.sauce = sauce;
        return this;
    }

    public PizzaBuilder addTopping(String topping) {
        pizza.toppings.add(topping);
        return this;
    }

    public Pizza build() {
        return pizza;
    }
}
