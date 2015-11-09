package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import modele.Adresse;
import modele.Chemin;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;
import modele.Visiteur;
import util.Constants;

public class VueGraphique extends JPanel implements Observer, Visiteur {
	/**
	 * 
	 */
	private double echelle = 1;

	private int maxLargeur;
	private int maxHauteur;

	private ArrayList<AdresseVue> adressesVue;
	private AdresseVue adresseSelectionne;

	private Plan plan;

	private Graphics g;

	public VueGraphique(Plan plan, Fenetre fenetre) {
		super();
		this.plan = plan;
		plan.addObserver(this);
		adressesVue = new ArrayList<AdresseVue>();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof Plan) {
			if (arg != null && arg instanceof Tournee) {
				Tournee t = (Tournee) arg;
				t.addObserver(this);
			}
		}

		this.repaint();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g = g;

		for (Adresse a : this.plan.getAdresses()) {
			a.accept(this,false);
		}
		if (plan.getTournee() != null) {
			plan.getTournee().getEntrepot().accept(this,true);
			for (Chemin ch : plan.getTournee().getItineraire()) {
				ch.accept(this);
			}
		}

		// this.getParent().setPreferredSize(new Dimension(this.maxLargeur,
		// this.maxHauteur));

	}

	public int getAdresseByXY(int x, int y) {
		int res = -1;
		for (AdresseVue adresse : adressesVue) {
			if (adresse.containsClick(x, y)) {
				if (adresse == adresseSelectionne)
					adresseSelectionne = null;
				else {
					adresseSelectionne = adresse;
					res = adresse.getId();
				}
				this.repaint();
				break;
			}
		}
		return res;
	}

	public void setEchelle(double value) {
		this.echelle = value;
	}



	@Override
	public void visite(Adresse a, boolean estEntrepot) {
		Graphics2D g2D = (Graphics2D) g;

		AdresseVue adresse = new AdresseVue(
				(int) ((a.getX() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE),
				(int) ((a.getY() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE),
				(int) (Constants.RAYON_NOEUD * 2 * echelle),
				(int) (Constants.RAYON_NOEUD * 2 * echelle), a.getId());
		g2D.fill(adresse);
		g2D.setColor(Color.black);
		g2D.draw(adresse);

		adressesVue.add(adresse);


		if (a.estAssocierAvecLivraison()) {
			g2D.setColor(Color.blue);
			g2D.fill(adresse);

		}
		if (estEntrepot) {
			g2D.setColor(Color.red);
			g2D.fill(adresse);
		}

		if (adresseSelectionne != null) {
			g2D.setColor(Color.yellow);
			g2D.fill(adresseSelectionne);
		}
		
	}

	@Override
	public void visite(Troncon t, boolean isDansTouree) {
		Graphics2D g2D = (Graphics2D) g;
		if (isDansTouree) {
			g2D.setColor(Color.blue);
		} else {
			g2D.setColor(Color.gray);
		}
		g2D.drawLine(
				(int) (t.getDepart().getX() * echelle + Constants.MARGIN_VUE_GRAPHE),
				(int) (t.getDepart().getY() * echelle + Constants.MARGIN_VUE_GRAPHE),
				(int) (t.getArrivee().getX() * echelle + Constants.MARGIN_VUE_GRAPHE),
				(int) (t.getArrivee().getY() * echelle + Constants.MARGIN_VUE_GRAPHE));
	}
}
