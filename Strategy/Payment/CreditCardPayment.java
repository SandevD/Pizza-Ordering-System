package Strategy.Payment;

import Utils.ColorCodes;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        ColorCodes.printSuccessMessage("\nPaid LKR " + amount, true);
        ColorCodes.printSuccessMessage("Credit Card Number: " + cardNumber, true);
    }
}
