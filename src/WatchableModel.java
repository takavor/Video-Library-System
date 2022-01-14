import java.util.ArrayList;

/**
 * This class represents the Model in the Observer pattern
 * It will send a notification to the observers (the watchlist) when a new element is watched
 */

public class WatchableModel {

    private Watchable aRecentlyWatched;
    private static ArrayList<WatchableObserver> aObservers = new ArrayList<>();


    /**
     * Method to get the most recently watched Watchable element
     *
     * @return
     *          the most recently watched Watchable element
     */
    public Watchable getRecentlyWatched() {
        return aRecentlyWatched;
    }

    /**
     * Method to watch the Watchable element. Some subclasses extending WatchableModel (Movie) make a call to this method in their watch method.
     * The aRecentlyWatched field is updated when watch() is called in the subclasses. As such, this method simply watches this field.
     */
    public void watch() {
        System.out.println("Now watching " + aRecentlyWatched.getTitle());
    }

    /**
     * Method to set the most recently watched Watchable element
     *
     * @param pRecentlyWatched
     *          Watchable element which was recently watched
     *
     * @pre pRecentlyWatched != null;
     */
    public void setRecentlyWatched(Watchable pRecentlyWatched) {
        assert pRecentlyWatched != null;
        aRecentlyWatched = pRecentlyWatched;
        notifyObservers();
    }

    /**
     * Method to add an observer to aObservers
     *
     * @param pObserver
     *          observer to add to the list
     * @pre pObserver != null;
     */
    public void addObserver(WatchableObserver pObserver) {
        assert pObserver != null;
        aObservers.add(pObserver);
    }

    /**
     * Method to remove an observer from aObservers
     *
     * @param pObserver
     *          observer to remove from the list
     * @pre pObserver != null;
     */
    public void removeObserver(WatchableObserver pObserver) {
        assert pObserver != null;
        aObservers.remove(pObserver);
    }


    /**
     * Method to notify observers of a new watched object
     */
    private void notifyObservers() {

        for(WatchableObserver aObserver: aObservers) {
            aObserver.newWatchedElement(aRecentlyWatched);
        }

    }


}
