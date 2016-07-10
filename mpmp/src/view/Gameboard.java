package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;

import model.Player;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;

/**
 * The gameboard holds the gameboard SVG and draws the players upon it.
 */
public class Gameboard extends JSVGCanvas {

	private Frame f;
	private Converter converter;

	public void setDocument(){
	}
	
	public Gameboard(Frame f) {
		this.f = f;
		converter = new Converter(304, 506);	  // XXX magic: original unresized wfld, hfld

		try {
			setFont(Font.createFont(Font.TRUETYPE_FONT, new File("graphics/font/SourceSansPro-Light.ttf")));
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory SVGDF = new SAXSVGDocumentFactory(parser);
			setDocument(SVGDF.createDocument(new File("graphics/svg/gameboard.svg").toURI().toString()));

			setBackground(new Color(0, 0, 0, 0));
			setRecenterOnResize(false);
			setEnableRotateInteractor(false);
			setEnableResetTransformInteractor(true);

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
				f.setTitle("MPMP - Loading...");
			}

			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				f.setTitle("MPMP");
				System.out.println("Resized");
				paintComponent(getGraphics());
				repaint();
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Player p : Player.getRealPlayers()) {
			drawPlayer(p);
		}
	}

	/**
	 * Draw a player piece.
	 */
	private void drawPlayer(Player p) {
		double scale;
		int rOuter = 15;
		int rInner = 12;

		AffineTransform aT = getViewBoxTransform();
		scale = getSVGDocument().getRootElement().getCurrentScale();
		Graphics g = getGraphics();

		Point pt = converter.middleRelPx(p.getPos());
		// 0.258: scale transform set in the gameboard SVG internally
		pt.x *= 0.258 * scale;
		pt.y *= 0.258 * scale;
		pt.x += aT.getTranslateX();
		pt.y += aT.getTranslateY();
		rOuter *= scale;
		rInner *= scale;

		g.setColor(Color.BLACK);
		g.fillOval(pt.x - rOuter, pt.y - rOuter, 2 * rOuter, 2 * rOuter);
		g.setColor(p.getColor());
		g.fillOval(pt.x - rInner, pt.y - rInner, 2 * rInner, 2 * rInner);
	}

	public double getCurrentZoom() {
		return getSVGDocument().getRootElement().getCurrentScale();
	}

	public double getCurrentRotation() {
		return getSVGDocument().getRootElement().getZoomAndPan();
	}
}
