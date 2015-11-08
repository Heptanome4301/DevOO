package vue;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import util.Constants;

public class AdresseVue extends Ellipse2D.Double {
	private int id;

	public AdresseVue(int x, int y, int width, int height, int id) {
		super(x, y, width, height);
		this.id = id;
	}

	public boolean containsClick(double x, double y) {
		Rectangle2D rectangle = new Rectangle2D.Double(this.x, this.y, width + Constants.AREA_CLICK_NOEUD, height + Constants.AREA_CLICK_NOEUD); 
		return rectangle.contains(x, y);
	}
		
	public int getId() {
		return id;
	}

}
