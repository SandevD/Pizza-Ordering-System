package Strategy.Discount;

public class FlatDiscount implements DiscountStrategy {
    private String code;
    private double amount;

    public FlatDiscount(String code, double amount) {
        this.code = code;
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double amount) {
        return Math.max(0, amount - this.amount);
    }

    @Override
    public boolean isValid(String code) {
        return this.code.equals(code);
    }
}
