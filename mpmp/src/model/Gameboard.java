package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import view.Frame;

public class Gameboard extends JSVGCanvas {

	private Frame f;
	private org.w3c.dom.Document doc;

	public Gameboard(Frame f) {
		this.f = f;
		try {
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/font/SourceSansPro-Light.ttf"));
			setFont(fnt);

			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory SVGDF = new SAXSVGDocumentFactory(parser);
			doc = SVGDF.createDocument(new File("graphics/svg/gameboard.svg").toURI().toString());
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
	public void paint(Graphics grphcs) {
		invalidate();
		validate();
		repaint();
		super.paint(grphcs);
		f.redrawPlayers();
	}

}
