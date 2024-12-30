package Decorator;

import java.util.ArrayList;

import Core.Pizza.Pizza;

public class ExtraCheeseDecorator extends ToppingDecorator {
    public ExtraCheeseDecorator(Pizza pizza) {
        super(pizza);
        this.name = pizza.getName();
        this.crust = pizza.getCrust();
        this.sauce = pizza.getSauce();
        this.toppings = new ArrayList<>(pizza.getToppings());
        this.hasExtraCheese = true;
    }

    @Override
    public double calculateCost() {
        return pizza.calculateCost() + 2.0;
    }

    @Override
    public String getName() {
        return pizza.getName();
    }
}
