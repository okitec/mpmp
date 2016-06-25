package view;

/**
 * Displayer provides an interface for a text box (e.g. the chat box or the player list).
 */
public interface Displayer {
	public void show(String s);
	public void reset();
}
