package model;

public enum GameState {
	Pregame, RunningGame;

	private static GameState state = Pregame;

	public static void startGame() {
		state = RunningGame;
		System.out.println("The game is now running");
	}

	public static boolean running() {
		return state == RunningGame;
	}
}
