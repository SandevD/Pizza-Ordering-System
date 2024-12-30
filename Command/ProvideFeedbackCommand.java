package Command;

import Core.Order.Feedback;
import Core.Order.Order;

public class ProvideFeedbackCommand implements FeedbackCommand {
    private Order order;
    private String comment;
    private int rating;

    public ProvideFeedbackCommand(Order order, String comment, int rating) {
        this.order = order;
        this.comment = comment;
        this.rating = rating;
    }

    @Override
    public boolean canProvideFeedback() {
        return !order.hasFeedback();
    }

    @Override
    public void execute() {
        if (canProvideFeedback()) {
            order.addFeedback(new Feedback(order.getOrderId(), comment, rating));
            System.out.println("Feedback submitted successfully!");
        } else {
            System.out.println("Feedback already provided for this order.");
        }
    }
}
