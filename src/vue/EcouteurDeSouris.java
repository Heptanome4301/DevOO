package vue;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;

public class EcouteurDeSouris extends MouseAdapter {

	private Controleur controleur;
	private VueGraphique vue;

	private int x;
	private int y;

	public EcouteurDeSouris(Controleur c, VueGraphique v) {
		this.controleur = c;
		this.vue = v;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			Point p = new Point(vue.changerRepere(e.getX()),
					vue.changerRepere(e.getY()));
			controleur.clicNoeud(p);
			break;
		case MouseEvent.BUTTON2:
			break;
		case MouseEvent.BUTTON3:
			controleur.clicDroit();
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int dx = e.getX() - x;
		int dy = e.getY() - y;
		
		controleur.moveEcran(dx, dy);
		
		x += dx;
		y += dy;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
	}

}
