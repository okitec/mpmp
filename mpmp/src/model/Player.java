package model;

import java.awt.Color;

/**
 *
 * @author Leander
 */
public class Player {
	private boolean inPrison;
	private int money;
	private Color color;

	public Player(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
