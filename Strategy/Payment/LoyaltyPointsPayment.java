package Strategy.Payment;

import Core.User.User;

public class LoyaltyPointsPayment implements PaymentStrategy {
    private User user;

    public LoyaltyPointsPayment(User user) {
        this.user = user;
    }

    @Override
    public void pay(double amount) {
        int requiredPoints = (int) (amount);
        if (user.getLoyaltyPoints() >= requiredPoints) {
            user.deductLoyaltyPoints(requiredPoints);
            System.out.println("Paid with " + requiredPoints + " loyalty points");
        } else {
            throw new IllegalStateException("Insufficient loyalty points");
        }
    }
}
