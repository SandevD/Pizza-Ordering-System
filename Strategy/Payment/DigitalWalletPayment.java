package Strategy.Payment;

public class DigitalWalletPayment implements PaymentStrategy {
    private String walletId;

    public DigitalWalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with digital wallet " + walletId);
    }
}
