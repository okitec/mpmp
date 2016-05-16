/**
 * 
 */
package controller;

import model.Model;
import view.View;

/**
 * @author leander.dreier
 *
 */
public class Controller {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Model m = new Model();
		View v = new View(m);
	}
}
