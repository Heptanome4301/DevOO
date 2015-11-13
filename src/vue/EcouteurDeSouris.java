package vue;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import controleur.Controleur;

/**
 * L'ectouteur de souris qui definit les actions a accomplir lorsqu'un clic est detecte.
 */
public class EcouteurDeSouris extends MouseAdapter {
	
	/**
	 * Le controleur qui devra effectuer les actions.
	 */
	private Controleur controleur;
	/**
	 * La vue graphique qui capte les evenements de la souris et qui affichera les resultats.
	 */
	private VueGraphique vue;
	/**
	 * La position x de la souris lors d'un clic.
	 */
	private int x;
	/**
	 * La position y de la souris lors d'un clic.
	 */
	private int y;

	/**
	 * Le constructeur de l'ecouteur.
	 * @param c le controleur a notifier.
	 * @param v la vue graphique a actualiser.
	 */
	public EcouteurDeSouris(Controleur c, VueGraphique v) {
		this.controleur = c;
		this.vue = v;
	}

	/**
	 *  Surcharge de la methode invoquee lors d'un clic a la souris.
	 *  @param e l'evenement declenche par le clic.
	 */
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
	
	/**
	 * Surcharge de la methode declenchee par l'appui d'un bouton de la souris.
	 * @param e l'evenement declenche par l'appui d'un bouton de la souris.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		x = e.getX();
		y = e.getY();
	}

	/**
	 * Surcharge de la methode declenchee par le deplacement de la souris lorsqu'un bouton est enfonce.
	 * @param e l'evenement declenche par le deplacement de la souris avec un bouton enfonce.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		int dx = e.getX() - x;
		int dy = e.getY() - y;

		controleur.moveEcran(dx, dy);

		x += dx;
		y += dy;

		System.out.println(" MouseDragged : " + x + " " + y);
	}

	/**
	 * Surcharge de la methode declenchee lors de l'utilistaion de la molette de la souris
	 * @param e l'evenement declenche lors de l'utilisation de la molette.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		Point click = new Point(vue.changerRepere(e.getX()),
				vue.changerRepere(e.getY()));
		float amount = e.getWheelRotation() * 5f;
		controleur.zoom(amount, click);
		System.out.println("Wheel : " + amount);
		
	}

	/**
	 * Surcharge de la methode declenchee lors du relachement d'un bouton de la souris.
	 * @param e l'evenement declenche par le relachement d'un bouton de la souris.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
	}
}
