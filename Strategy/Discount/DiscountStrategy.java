package Strategy.Discount;

public interface DiscountStrategy {
    double applyDiscount(double amount);

    boolean isValid(String code);
}
