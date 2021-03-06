package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import srv.Interpreter;

/**
 * Event cards.
 */
public class Card {
	private static Random randomno;
	private static ArrayList<Card> eventCards;
	private static ArrayList<Card> communityCards;
	private final String text;
	private final String[] actions;

	public Card(String text, String code) {
		this.text = text;
		actions = code.split(";");
	}

	public void run(SrvPlayer sp) {
		Interpreter.run(sp, actions);
	}

	public String toString() {
		return text;
	}

	public static Card getRandomCard(boolean eventcards) {
		if (eventcards)
			return eventCards.get(randomno.nextInt(eventCards.size()));
		else
			return communityCards.get(randomno.nextInt(communityCards.size()));
	}

	public static void init() throws IOException {
		communityCards = loadCards("community-cards");
		eventCards = loadCards("event-cards");
		randomno = new Random();
	}

	/**
	 * Load the cards from the file. The format is:
	 *     The text of the card.|command arg; command arg; command arg; ...
	 * Available commands:
	 *     prison, collect, add-money, add-unjail, tp, set-money
	 */
	public static ArrayList<Card> loadCards(String filename) throws FileNotFoundException, IOException {
		String line;
		ArrayList<Card> cards;

		cards = new ArrayList<>();
		try(InputStream is = new FileInputStream(filename)) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
			while ((line = br.readLine()) != null) {
				// split into text and command part; pipe as separator
				String[] parts = line.split("[|]");
				cards.add(new Card(parts[0], parts[1]));
			}

			return cards;
		}
	}
}
