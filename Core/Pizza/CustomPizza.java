package Core.Pizza;

public class CustomPizza extends Pizza {
    public void setCrust(String crust) {
        this.crust = crust;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }


    @Override
    public double calculateCost() {
        double cost = 10.0; // Base cost
        cost += toppings.size() * 1.5;
        return cost;
    }
}