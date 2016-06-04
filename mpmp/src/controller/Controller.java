/**
 * Controller part of the MVC model
 */
package controller;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import model.Model;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import view.View;

/**
 * Controller class of the MVC model; currently not used correctly. The main
 * method is also located here.
 *
 * @author Leander, oki, Klaus
 */
public class Controller {

	public static void main(String[] args) {
		Model m = new Model();
		View v = new View(m);




//		
//		JSVGCanvas canvas = new JSVGCanvas();
//		canvas.setURI(new File("E:/mpmp/graphics/svg/cubes/cube_1.svg").toURI()
//				.toString());
//
//		
//		v.getContentPane().add(canvas);


	}
}
