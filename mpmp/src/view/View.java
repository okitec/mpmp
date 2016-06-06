/**
 * View part of the MVC model
 */
package view;

import java.awt.EventQueue;

import model.Model;

/**
 * Main View class; it instantiates the needed frames and passes the requests to them
 * instead of handling them itself.
 *
 * @author Leander, oki
 */
public class View {
	private Model m;

	public View(Model m){
		this.m = m;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainframe frame = new Mainframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
