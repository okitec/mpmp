/**
 * 
 */
package view;

import java.awt.EventQueue;

import model.Model;

/**
 * @author leander.dreier
 *
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
