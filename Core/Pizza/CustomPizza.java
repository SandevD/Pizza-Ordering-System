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
        double cost = 1000.00; // Base cost of a pizza
        cost += toppings.size() * 150.00;
        return cost;
    }
}
