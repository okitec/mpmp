/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.Controller;

/**
 *
 * @author oki, Leander
 */
public class ClientMain {
	public static void clientmain(String[] args) {
		Controller c;

		if(args.length < 5)
			usage();

		try {
			c = new Controller(args[1], 1918, args[2], args[3], args[4]); // XXX hardcoded 1918 -oki
		} catch (UnknownHostException uhe) {
			System.err.println("mpmp client: unknown host '" + args[1] + "'");
			System.exit(1);
		} catch (IOException ioe) {
			System.err.println("mpmp client: IO error in connection to '" + args[1] + "`");
			System.exit(1);
		}
	}

	private static void usage() {
		System.err.println("Usage: java -jar mpmp.jar client <server address> <color> <gamemode> <username>");
		System.exit(1);
	}
}
