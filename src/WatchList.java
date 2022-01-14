import java.io.File;
import java.util.*;

/**
 * Represents a sequence of watchables to watch in FIFO order.
 */
public class WatchList implements Bingeable<Watchable>, WatchableObserver {
	
	private final List<Watchable> aList = new LinkedList<>();
	private String aName;
	private int aNext;

	private Watchable aRecentlyWatched;

	// stacks to keep track of commands
	private Stack<Command> aUndoStack = new Stack<>();
	private Stack<Command> aRedoStack = new Stack<>();
	
	/**
	 * Creates a new empty watchlist.
	 * 
	 * @param pName
	 *            the name of the list
	 * @pre pName!=null;
	 */
	public WatchList(String pName) {
		assert pName != null;
		aName = pName;
		aNext = 0;
	}

	/**
	 * Notification/update method for the watchlist (observer) for when a new watchable is watched.
	 * @param pWatched
	 * 			watchable element which has been watched
	 * @pre pWatched != null;
	 */
	public void newWatchedElement(Watchable pWatched) {
		assert pWatched != null;
		aRecentlyWatched = pWatched;
	}

	/**
	 *	Method to return the last Watchable object which was watched.
	 *
	 * @return
	 * 			a reference to the last Watchable object that has been watched from the watchlist
	 */
	public Watchable lastWatched() {
		return aRecentlyWatched;
	}




	/**
	 * Command to undo the state of the WatchList.
	 */
	public void undo() {
		if(aUndoStack.size() != 0) {
			Command command = aUndoStack.pop();
			aRedoStack.push(command);
			command.undo();
		}
	}

	/**
	 * Command to redo the state of the WatchList.
	 */
	public void redo() {
		if(aRedoStack.size() != 0) {
			Command command = aRedoStack.pop();
			command.execute();
			aUndoStack.push(command);
		}
	}
	

	/**
	 * Changes the name of this watchlist.
	 * 
	 * @param pName
	 *            the new name
	 * @pre pName!=null;
	 */
	public void setName(String pName) {

		assert pName != null;

		Command setNameCommand = createSetNameCommand(pName);
		setNameCommand.execute();

		aUndoStack.push(setNameCommand);

		aRedoStack.clear();

		//aName = pName;

	}

	/**
	 * Method to create a set name command. This is called by the setName method.
	 * @param pName
	 * 			The new name of the WatchList.
	 * @return
	 * 			A Command which holds the initial and new states of the name.
	 */
	private Command createSetNameCommand(String pName) {
		return new Command() {

			private String aPreviousName = aName;

			@Override
			public void execute() {
				aName = pName;
			}

			@Override
			public void undo() {
				aName = aPreviousName;
			}
		};
	}


	
	/**
	 * Adds a watchable at the end of this watchlist.
	 * 
	 * @param pWatchable
	 *            the watchable to add
	 * @pre pWatchable!=null;
	 */
	public void addWatchable(Watchable pWatchable) {
		assert pWatchable != null;

		Command addWatchableCommand = createAddWatchableCommand(pWatchable);
		addWatchableCommand.execute();

		aUndoStack.push(addWatchableCommand);

		aRedoStack.clear();

		//aList.add(pWatchable);
	}


	/**
	 * Method to create a set name command. This is called by the addWatchable method.
	 * @param pWatchable
	 * 			The Watchable to be added.
	 * @return
	 * 			A Command which corresponds to adding a Watchable to the WatchList (and undoing).
	 */
	private Command createAddWatchableCommand(Watchable pWatchable) {
		return new Command() {

			@Override
			public void execute() {
				aList.add(pWatchable);
			}

			@Override
			public void undo() {
				aList.remove(pWatchable);
			}
		};
	}

	/**
	 * Retrieves and removes the Watchable at the specified index.
	 * 
	 * @param pIndex
	 *            the position of the Watchable to remove
	 * @pre pIndex < getTotalCount() && pIndex >= 0
	 */
	public Watchable removeWatchable(int pIndex) {
		assert pIndex < aList.size() && pIndex >= 0;
		if (aNext > pIndex) {
			aNext--;
		}

		Watchable toRemove = aList.get(pIndex);

		Command removeWatchableCommand = createRemoveWatchableCommand(pIndex);
		removeWatchableCommand.execute();

		aUndoStack.push(removeWatchableCommand);

		aRedoStack.clear();

		return toRemove;

		//return aList.remove(pIndex);
	}

	/**
	 * Method to create a remove watchable command. This is called by the removeWatchable method.
	 * @param pIndex
	 * 			The index of the element to remove from the list.
	 * @return
	 * 			A Command which corresponds to removing a Watchable from the WatchList (and undoing).
	 */
	private Command createRemoveWatchableCommand(int pIndex) {
		return new Command() {

			Watchable aRemovedWatchable = null;

			@Override
			public void execute() {
				aRemovedWatchable = aList.remove(pIndex);
			}

			@Override
			public void undo() {
				aList.add(pIndex, aRemovedWatchable);
				if(aNext > pIndex) {
					aNext++;
				}
				// not setting aRemovedWatchable back to null since an actual reference is being stored in the LinkedList
			}

			public Watchable getRemovedWatchable() {
				return aRemovedWatchable;
			}
		};
	}
	
	/**
	 * @return the total number of valid watchable elements
	 */
	public int getValidCount() {
		int count = 0;
		for (Watchable item : aList) {
			if (item.isValid()) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public int getTotalCount() {
		return aList.size();
	}
	
	@Override
	public int getRemainingCount() {
		return aList.size() - aNext;
	}
	
	@Override
	public Watchable next() {
		assert getRemainingCount() > 0;
		Watchable next = aList.get(aNext);

		Command nextCommand = createNextCommand();
		nextCommand.execute();

		aUndoStack.push(nextCommand);

		aRedoStack.clear();

		//aNext++;
//		if (aNext >= aList.size()) {
//			aNext = 0;
//		}

		return next;
	}


	/**
	 * Method to create a next watchable command. This is called by the next() method.
	 * @return
	 * 			A Command which corresponds to incrementing the value of aNext. The undoing action is decrementing the value.
	 */
	private Command createNextCommand() {
		return new Command() {
			@Override
			public void execute() {
				aNext++;
				if (aNext >= aList.size()) {
					aNext = 0;
				}
			}

			@Override
			public void undo() {
				aNext--;
				if(aNext < 0) {
					aNext = 0;
				}
			}
		};
	}
	
	@Override
	public void reset() {

		Command resetCommand = createResetCommand();
		resetCommand.execute();

		aUndoStack.push(resetCommand);

		aRedoStack.clear();

	}


	/**
	 * Method to create a reset  command. This is called by the reset() method.
	 * @return
	 * 			A Command which corresponds to incrementing the value of aNext. The undoing action is decrementing the value.
	 */
	private Command createResetCommand() {
		return new Command() {

			private int aPreviousIndex = 0;

			@Override
			public void execute() {
				aPreviousIndex = aNext;
				aNext = 0;
			}

			@Override
			public void undo() {
				aNext = aPreviousIndex;
				aPreviousIndex = 0;
			}
		};
	}

	
	@Override
	public Iterator<Watchable> iterator() {
		return Collections.unmodifiableList(aList).iterator();
	}


	public String getName() {
		return aName;
	}




}
