package model;

/**
 * Represents a visitor interface for visiting messages.
 */
public interface MessageVisitor {
    /**
     * Visits a message and performs operations based on the message content.
     *
     * @param message the message to visit
     */
    void visitMessage(String message);
}

/**
 * Represents a concrete visitor class for checking positivity in messages.
 */
public class PositivityVisitor implements MessageVisitor {
    private int positiveCount;
    private String[] positiveWords = {"good", "great", "excellent", "incredible", "cool"};

    /**
     * Constructs a new PositivityVisitor object with initial positive count set to zero.
     */
    public PositivityVisitor() {
        positiveCount = 0;
    }

    /**
     * Visits a message and checks for positive words to update the positive count.
     *
     * @param message the message to visit
     */
    @Override
    public void visitMessage(String message) {
        for (String word : positiveWords) {
            if (message.toLowerCase().contains(word)) {
                positiveCount++;
                break; // exit loop if any positive word is found
            }
        }
    }

    /**
     * Gets the positive count accumulated during message visits.
     *
     * @return the positive count
     */
    public int getPositiveCount() {
        return positiveCount;
    }
}
