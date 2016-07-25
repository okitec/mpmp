package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Server's model: this contains actual game logic and envelops a client model.
 * It is a singleton: there's only one instance of it, accessable via the static
 * attribute SrvModel.self;
 */
public class SrvModel {
	public static SrvModel self;
	public Model m;                          /* don't modify this model; use game logic functions */
	private Map<String, SrvPlayer> players;  /* only active players */
	private ArrayList<SrvPlayer>   order;    /* players in turn order */

	public SrvModel() {
		m = new Model();
		players = new HashMap<>();
		order = new ArrayList<>();
		self = this;
	}

	public SrvPlayer getSrvPlayer(Player p) {
		return players.get(p.getName());
	}

	public SrvPlayer getSrvPlayer(String name) {
		return players.get(name);
	}

	public void addSrvPlayer(SrvPlayer sp) {
		players.put(sp.p.getName(), sp);
		order.add(sp);
	}

	public void rmSrvPlayer(SrvPlayer sp) {
		players.remove(sp.p.getName());
		order.remove(order.indexOf(sp));
	}

	public ArrayList<SrvPlayer> getSrvPlayers() {
		return order;
	}
}
