package Strategy.Discount;

public class PercentageDiscount implements DiscountStrategy {
    private String code;
    private double percentage;

    public PercentageDiscount(String code, double percentage) {
        this.code = code;
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount * (1 - percentage / 100);
    }

    @Override
    public boolean isValid(String code) {
        return this.code.equals(code);
    }
}
