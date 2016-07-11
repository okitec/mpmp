package view;

/**
 * Displayer provides an interface for some kind of display, e.g. the chat box or the player list.
 * The caller can't assume much; show somehow displays a message; the reset method clears the current
 * contents if that makes sense (is is often a nil function).
 */
public interface Displayer {
	public void show(String s);
	public void reset();
}
