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

import model.Model;
import model.Player;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;

/**
 * The gameboard holds the gameboard SVG and draws the players upon it.
 */
public class Gameboard extends JSVGCanvas {
	// 0.258: scale transform set in the gameboard SVG internally
	private static final double SVGScale = 0.258;

	// Height and width of original unresized portrait field.
	private static final int WFLD = 304;
	private static final int HFLD = 506;

	private static final int MarkedPlotAlpha = 100; // 0-255; here: less than 50%

	private Frame f;
	private Model m;
	private Converter converter;

	public Gameboard(Frame f, Model m) {
		this.f = f;
		this.m = m;
		converter = new Converter(WFLD, HFLD);
	}

	public void init() {
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
				paintComponent(getGraphics());
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Player p : m.getPlayers()) {
			drawPlayer(p);
		}

		// debug draws
		markPlot(9, Color.RED);
		markPlot(11, Color.GREEN);
		markPlot(24, Color.BLUE);
		markPlot(39, Color.YELLOW);
	}

	/**
	 * Draw a scaled player piece in the middle of a field.
	 */
	private void drawPlayer(Player p) {
		double scale;
		int rOuter = 15;
		int rInner = 12;

		AffineTransform at = getViewBoxTransform();
		scale = getScale();
		Graphics g = getGraphics();

		Point pt = converter.middleRelPx(p.getPos());
		pt.x *= SVGScale * scale;
		pt.y *= SVGScale * scale;
		pt.x += at.getTranslateX();
		pt.y += at.getTranslateY();
		rOuter *= scale;
		rInner *= scale;

		g.setColor(Color.BLACK);
		g.fillOval(pt.x - rOuter, pt.y - rOuter, 2 * rOuter, 2 * rOuter);
		g.setColor(p.getColor());
		g.fillOval(pt.x - rInner, pt.y - rInner, 2 * rInner, 2 * rInner);
	}

	/**
	 * Mark a plot with a transparent shade.
	 */
	private void markPlot(int pos, Color col) {
		double scale;
		int dx, dy;

		AffineTransform at = getViewBoxTransform();
		Graphics g = getGraphics();
		scale = getScale();

		Point pt = converter.cornerRelPx(pos); // left upper corner
		if(converter.isPortrait(pos)) {
			dx = WFLD;
			dy = HFLD;
		} else if(converter.isLandscape(pos)) {
			// Reverse height and width
			dx = HFLD;
			dy = WFLD;
		} else {
			return;  // corner fields are never plots
		}

		pt.x *= SVGScale * scale;
		pt.y *= SVGScale * scale;
		pt.x += at.getTranslateX();
		pt.y += at.getTranslateY();
		dx *= SVGScale * scale;
		dy *= SVGScale * scale;

		Color shade = new Color(col.getRed(), col.getGreen(), col.getBlue(), MarkedPlotAlpha);
		g.setColor(shade);
		g.fillRect(pt.x, pt.y, dx, dy);
	}

	public double getScale() {
		return getSVGDocument().getRootElement().getCurrentScale();
	}

	public double getRotation() {
		return getSVGDocument().getRootElement().getZoomAndPan();
	}
}
