package Strategy.Payment;

import Core.User.User;
import Utils.ColorCodes;

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
            ColorCodes.printSuccessMessage("\nPaid with Loyalty Points!", true);
            ColorCodes.printSuccessMessage("Redeemed " + requiredPoints + " Loyalty Points!", true);
        } else {
            throw new IllegalStateException("\nInsufficient loyalty points");
        }
    }
}
