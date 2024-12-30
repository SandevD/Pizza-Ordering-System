package Decorator;

import Core.Pizza.Pizza;

public abstract class ToppingDecorator extends Pizza {
    protected Pizza pizza;

    public ToppingDecorator(Pizza pizza) {
        this.pizza = pizza;
    }
}
