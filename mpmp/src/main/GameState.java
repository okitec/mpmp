package main;

public enum GameState {
	Pregame, RunningGame;

	private static GameState state = Pregame;

	public static void startGame() {
		state = RunningGame;
	}

	public static boolean running() {
		if (state == RunningGame)
			return true;
		return false;
	}
}
