package basic;

/**
 * This class represents a Counter used by various classes to determine score, lives, etc.
 */
public class Counter {
    private int counter;

    /**
     * constructor.
     *
     * @param val - A numeric value to assign to the counter.
     */
    public Counter(int val) {
        this.counter = val;
    }

    /**
     * This method increases the counter's value.
     *
     * @param number - A number to increase the counter with.
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * This method decreases the counter's value.
     *
     * @param number - A number to decrease the counter with.
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * getter.
     *
     * @return counter - the counter's value to return.
     */
    public int getValue() {
        return this.counter;
    }
}
