import java.io.File;

/**
 * Driver class to test and implement code
 */

public class Driver {

    public static void main(String[] args) {

        ////////////// Q1 DEMO //////////////
        // Please see the end of the q3 demo for the q1 demo

        ////////////// Q3 DEMO //////////////
        // Please feel free to use the debugger to verify the states of the watchlist
        System.out.println("\n------------------ Q3 DEMO ------------------");
        WatchList list = new WatchList("Initial name");
        System.out.println("Creating list\nInitial name of list: " + list.getName());


        list.setName("Second name");
        System.out.println("\nSetting list name\nName of list after setting: " + list.getName());


        list.undo();
        System.out.println("\nUndoing\nName of list after undo: " + list.getName());


        list.redo();
        System.out.println("\nRedoing\nName of list after redo: " + list.getName());


        Watchable movie1 = new Movie(new File(""), "movie 1", Language.ENGLISH, "studio 1" );
        Watchable movie2 = new Movie(new File(""), "movie 2", Language.FRENCH, "studio 2" );
        Watchable movie3 = new Movie(new File(""), "movie 3", Language.SPANISH, "studio 3" );


        TVShow show1 = new TVShow("show 1", Language.ENGLISH, "tv show studio 1");
        show1.createAndAddEpisode(new File(""), "show 1 episode 1");
        show1.createAndAddEpisode(new File(""), "show 1 episode 2");
        show1.createAndAddEpisode(new File(""), "show 1 episode 3");


        System.out.println("\nAdding movie1, movie2, movie3, and show1");
        list.addWatchable(movie1);
        list.addWatchable(movie2);
        list.addWatchable(movie3);
        list.addWatchable(show1);


        list.undo();
        System.out.println("\nUndoing last action (the adding of show1)\nSize of list: " + list.getTotalCount());


        list.redo();
        System.out.println("\nRedoing last action\nSize of list: " + list.getTotalCount());


        System.out.println("\nRemoving Watchable at index 2 (movie3)");
        list.removeWatchable(2);


        System.out.println("Elements in the list:");
        for(Watchable element: list) {
            System.out.println(element.getTitle());
        }


        list.undo();
        System.out.println("\nUndoing last action\nElements in the list:");
        for(Watchable element: list) {
            System.out.println(element.getTitle());
        }


        Watchable next = list.next();
        System.out.println("\nGetting next element in list\nTitle of this element: " + next.getTitle());
        System.out.println("Value of remaining count: " + list.getRemainingCount());


        Watchable next2 = list.next();
        System.out.println("\nGetting next element in list\nTitle of this element: " + next2.getTitle());
        System.out.println("Value of remaining count: " + list.getRemainingCount());


        list.undo();
        System.out.println("\nUndoing last action\nValue of remaining count: " + list.getRemainingCount());


        list.reset();
        System.out.println("\nResetting list count\nValue of remaining count: " + list.getRemainingCount());


        list.undo();
        System.out.println("\nUndoing reset\nValue of remaining count: " + list.getRemainingCount());


        ////////////// Q1 DEMO //////////////
        System.out.println("\n------------------ Q1 DEMO ------------------");

        WatchableModel model = new WatchableModel();
        model.addObserver(list);

        movie1.watch();

        Watchable last = list.lastWatched();
        System.out.println("Title of element returned from calling lastWatched(): " + last.getTitle() + "\n");

        movie2.watch();
        last = list.lastWatched();
        System.out.println("Title of element returned from calling lastWatched(): " + last.getTitle());


    }

}
