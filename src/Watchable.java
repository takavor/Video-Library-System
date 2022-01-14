/**
 * Represents a video object that can be watched
 */
interface Watchable {
	
	/**
	 * Plays the video to the user
	 * @pre isValid()
	 */
	public void watch();

	/**
	 * Returns the title of the Watchable element
	 * @return the title field of the object
	 */
	public String getTitle();

	/**
	 * Indicates whether the Watchable element is ready to be played.
	 * 
	 * @return true if the file path points to an existing location that can be read and is not a directory
	 */
	public boolean isValid();
	
}