package view;

/**
 * Displayer provides an interface for some kind of display, e.g. the chat box or the player list.
 * The caller can't assume much; show somehow displays something; the reset method clears the current
 * contents if that makes sense (it is sometimes a nil function).
 *
 * @author oki
 */
public interface Displayer {
	public void show(Object... args);
	public void reset();
}
