package Observer;

public class CustomerNotifier implements OrderObserver {
    private String customerId;

    public CustomerNotifier(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void update(String status) {
        System.out.println("Notification : Order status - " + status);
    }
}
