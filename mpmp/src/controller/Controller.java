/**
 * Controller part of the MVC model
 */
package controller;

import model.Model;
import view.View;

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
	public Controller() {
		Model m = new Model();
		View v = new View(m);
	}
}
