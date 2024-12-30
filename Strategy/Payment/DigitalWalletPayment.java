package Strategy.Payment;

import Utils.ColorCodes;

public class DigitalWalletPayment implements PaymentStrategy {
    private String walletId;

    public DigitalWalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void pay(double amount) {
        ColorCodes.printSuccessMessage("\nPaid LKR " + amount, true);
        ColorCodes.printSuccessMessage("Digital Wallet ID: " + walletId, true);
    }
}
