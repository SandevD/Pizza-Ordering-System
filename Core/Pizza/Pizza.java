package Core.Pizza;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    public String name;
    public String crust;
    public String sauce;
    public List<String> toppings;
    protected boolean hasExtraCheese;
    protected double price;

    public Pizza() {
        toppings = new ArrayList<>();
        hasExtraCheese = false;
    }

    public abstract double calculateCost();

    public void setName(String name) {
        this.name = name;
    }

    public void setHasExtraCheese(boolean hasExtraCheese) {
        this.hasExtraCheese = hasExtraCheese;
    }

    public String getName() {
        return name != null ? name : "Unnamed Pizza";
    }

    public String getCrust() {
        return crust;
    }

    public String getSauce() {
        return sauce;
    }

    public List<String> getToppings() {
        return new ArrayList<>(toppings);
    }

    public boolean hasExtraCheese() {
        return hasExtraCheese;
    }
}
