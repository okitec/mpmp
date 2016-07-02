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


/**
 * The gameboard holds the gameboard SVG and draws the players upon it.
 */
public class Gameboard extends JSVGCanvas {
	private Frame f;
	private org.w3c.dom.Document doc;
	private Converter converter;

	public Gameboard(Frame f) {
		this.f = f;
		converter = new Converter(304, 506);	  // XXX magic: original unresized wfld, hfld
		try {
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/font/SourceSansPro-Light.ttf"));
			setFont(fnt);

			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory SVGDF = new SAXSVGDocumentFactory(parser);
			org.w3c.dom.Document doc = SVGDF.createDocument(new File("graphics/svg/gameboard.svg").toURI().toString());
			setDocument(doc);

			setBackground(new Color(0, 0, 0, 0));
			setRecenterOnResize(false);
			setEnableRotateInteractor(false);
			setEnableResetTransformInteractor(true);

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
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
}
