package vue;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;

public class EcouteurDeSouris extends MouseAdapter {
	private Controleur controleur;
	private VueGraphique vue;
	private Fenetre fenetre;

	public EcouteurDeSouris(Controleur c, VueGraphique v, Fenetre f) {
		this.controleur = c;
		this.vue = v;
		this.fenetre = f;
		System.out.println(v);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: 
			Point p = new Point(vue.changerRepere(e.getX()),
			vue.changerRepere(e.getY()));
			controleur.clicNoeud(p); // le mettre là
			break;
		case MouseEvent.BUTTON2:			
			break;
		case MouseEvent.BUTTON3:
                        controleur.clicDroit();
			break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
	}
	
	
}
