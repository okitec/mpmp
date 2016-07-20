package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A thing.
 *
 * Specifically, this is the client/small model containing data that can be updated.
 * The game logic resides only in the server parts.
 */
public class Model {
	public enum GameState {
		Pregame, Running;
	}

	private GameState gs;

	private Map<String, Player> players;    /* players, no spectators */
	private Player currentPlayer;

	private static Map<Integer, Plot> plots;
	private static ArrayList<PlotGroup> pgroups;

	public Model() {
		players = new HashMap<>();
		currentPlayer = null;
		plots = new HashMap<>();
		pgroups = new ArrayList<>();
	}

	public void addPlayer(Player p) {
		if(gs == GameState.Running)
			return;

		players.put(p.getName(), p);
	}

	public Player getPlayer(String name) {
		return players.get(name);
	}

	public Iterable<Player> getPlayers() {
		return players.values();
	}

	/**
	 * Sets the current player. Null is allowed.
	 */
	public void setCurrentPlayer(Player p) {
		currentPlayer = p;
	}

	public Plot getPlot(int pos) {
		pos %= Field.Nfields;
		return plots.get(new Integer(pos));
	}

	public boolean running() {
		return gs == GameState.Running;
	}

	public void startGame() {
		gs = GameState.Running;
	}

	/* STATIC */

	public static void init() throws IOException {
		initPlots();
		Card.init();
	}

	private static void initPlots() {
	}

	private static void addPlot(int pos, Plot plot) {
		plots.put(new Integer(pos), plot);
	}
}
