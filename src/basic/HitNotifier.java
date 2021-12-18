package basic;

/**
 * An interface that represents notifiers.
 */
public interface HitNotifier {

    /**
     * This method adds a listener to objects.
     *
     * @param hl - A listener to be added.
     */
    void addHitListener(HitListener hl);

    /**
     * This method removes a listener off the list (of listeners).
     *
     * @param hl - the listener to be removed.
     */
    void removeHitListener(HitListener hl);
}
