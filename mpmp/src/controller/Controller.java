/**
 * Controller part of the MVC model
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Model;
import view.View;
import main.Conn;

/**
 * Controller class of the MVC model; currently not used correctly.
 * The main method is also located here.
 *
 * @author Leander, oki
 */
public class Controller {
	/**
	 * @param args
	 */
	public Controller(String addr, int port) throws UnknownHostException, IOException {
		Model m = new Model();
		View v = new View(m);
		Conn srv = new Conn(new Socket(addr, port));

		srv.handle();
	}
}
