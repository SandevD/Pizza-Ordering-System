package Command;

public interface FeedbackCommand extends OrderCommand {
    boolean canProvideFeedback();
}
