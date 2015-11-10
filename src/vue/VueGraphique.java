package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
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


	private Integer idAdresseSelectionne;

	private Plan plan;

	private Graphics g;

	public VueGraphique(Plan plan, Fenetre fenetre) {
		super();
		this.setBackground(Color.white);
		this.plan = plan;
		plan.addObserver(this);
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

	public void setEchelle(double value) {
		this.echelle = value;
	}



	@Override
	public void visite(Adresse a, boolean estEntrepot) {
		Graphics2D g2D = (Graphics2D) g;

		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE,
				(a.getY() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE,
				Constants.RAYON_NOEUD * 2 * echelle,
				Constants.RAYON_NOEUD * 2 * echelle);
		g2D.fill(adresse);
		g2D.setColor(Color.black);
		g2D.draw(adresse);


		if (a.estAssocierAvecLivraison()) {
			g2D.setColor(Color.blue);
			g2D.fill(adresse);

		}
		if (estEntrepot) {
			g2D.setColor(Color.red);
			g2D.fill(adresse);
		}

		if (idAdresseSelectionne != null) {
			a = plan.getAdresse(idAdresseSelectionne);
			Ellipse2D adresseSelectionne = new Ellipse2D.Double(
					(a.getX() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE,
					(a.getY() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE,
					Constants.RAYON_NOEUD * 2 * echelle,
					Constants.RAYON_NOEUD * 2 * echelle);
			
			g2D.setColor(Color.yellow);
			g2D.fill(adresseSelectionne);
		}
		
	}

	@Override
	public void visite(Troncon t, boolean isDansTournee) {
		Graphics2D g2D = (Graphics2D) g;
		if (isDansTournee) {
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

	public int changerRepere(int x) {
        return (int) ((x - Constants.MARGIN_VUE_GRAPHE) / echelle);
	}

	public void deselection() {
		this.idAdresseSelectionne = null;
		this.repaint();
	}
	
	public void selection(int idAdresseSelectionne) {
		this.idAdresseSelectionne = idAdresseSelectionne;
		this.repaint();
	}

}
