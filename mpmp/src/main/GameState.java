package main;

/**
 *
 * @author Leander, oki, Klaus
 */
public enum GameState {
	Pregame, RunningGame;

	private static GameState state;

	public static void startGame() {
		state = RunningGame;
	}

	public static boolean running() {
		if (state == Pregame)
			return false;
		return true;
	}
}
