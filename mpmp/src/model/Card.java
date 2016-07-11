package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

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

		if (code.contains(";")) {
			actions = code.split(";");
		} else {
			actions = new String[1];
			actions[0] = code;
		}
	}

	public void run(Player p) {
		String command;

		for (String a : actions) {
			//split the string into command and value
			if (a.contains(" ")) {
				String[] parts = a.split(" ");
				command = parts[0];
				String parameter = parts[1];
				//executes the comands with the read parameters
				if (command.equals("move")) {
					p.teleport(Integer.parseInt(parameter), true);
				}
				if (command.equals("collect")) {
					p.collect(Integer.parseInt(parameter));
				}
				if (command.equals("addMoney")) {
					p.addMoney(Integer.parseInt(parameter));
				}

			} else {
				command = actions[0];
			}
			if (command.equals("prison")) {
				p.teleport(Field.Prison, false);
			}
			if (command.equals("addUnjailCard")) {
				p.addUnjailCard();
			}
		}
	}

	public static Card getRandomCard(boolean eventcards) {
		if (eventcards) {
			return eventCards.get(randomno.nextInt(eventCards.size()));
		} else {
			return communityCards.get(randomno.nextInt(communityCards.size()));
		}
	}

	public static void init() throws IOException {
		communityCards = loadCards("community-cards");
		eventCards = loadCards("event-cards");
		randomno = new Random();
	}

	public static ArrayList<Card> loadCards(String filename) throws FileNotFoundException, IOException {
		String line;
		ArrayList<Card> cards;

		cards = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(filename))));
		while ((line = br.readLine()) != null) {
			//splits the string into text and command part
			String[] parts = line.split("[|]");
			cards.add(new Card(parts[0], parts[1]));
		}

		return cards;
	}
}
