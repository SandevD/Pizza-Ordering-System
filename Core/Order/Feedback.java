package Core.Order;

public class Feedback {
    private String orderId;
    private String comment;
    private int rating;

    public Feedback(String orderId, String comment, int rating) {
        this.orderId = orderId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
