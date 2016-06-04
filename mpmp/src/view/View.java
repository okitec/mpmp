/**
 * View part of the MVC model
 */
package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import model.Model;

/**
 * Main View class; it instantiates the needed frames and passes the requests to them
 * instead of handling them itself.
 *
 * @author Leander, oki, Klaus
 */
public class View {
	private Model m;
	private Mainframe f;

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
