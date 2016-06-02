package model;

import main.Client;

public class Msgbuf { // Do we need a class for that..
	public static void send(String msg){
		for (Client c : main.Main.getClients())
			c.send(msg);
	}
}
