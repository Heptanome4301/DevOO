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
import modele.Plan;
import modele.Troncon;
import util.Constants;

public class VueGraphique extends JPanel implements Observer {
	/**
	 * 
	 */
	private double echelle = 1;

	private int maxLargeur;
	private int maxHauteur;

	private ArrayList<AdresseVue> adressesVue;
	private AdresseVue adresseSelectionne;
	
	private Plan plan;

	public VueGraphique(Plan plan, Fenetre fenetre) {
		super();
		this.plan = plan;
		plan.addObserver(this);
		adressesVue = new ArrayList();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof Plan) {
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		for (Adresse a : this.plan.getAdresses()) {
			g2D.setColor(Color.gray);
			for (Troncon t : a.getTroncons()) {
				g2D.drawLine(
						(int) (t.getDepart().getX() * echelle + Constants.MARGIN_VUE_GRAPHE),
						(int) (t.getDepart().getY() * echelle + Constants.MARGIN_VUE_GRAPHE),
						(int) (t.getArrivee().getX() * echelle + Constants.MARGIN_VUE_GRAPHE),
						(int) (t.getArrivee().getY() * echelle + Constants.MARGIN_VUE_GRAPHE));
			}

			AdresseVue adresse = new AdresseVue(
					(int) ((a.getX() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE),
					(int) ((a.getY() - Constants.RAYON_NOEUD) * echelle + Constants.MARGIN_VUE_GRAPHE),
					(int) (Constants.RAYON_NOEUD * 2 * echelle),
					(int) (Constants.RAYON_NOEUD * 2 * echelle),
					a.getId());

			adressesVue.add(adresse);

			g2D.fill(adresse);
			g2D.setColor(Color.black);
			g2D.draw(adresse);
			
			if (adresseSelectionne != null)
			{
				g2D.setColor(Color.yellow);
				g2D.fill(adresseSelectionne);				
			}
			

		}

		// this.getParent().setPreferredSize(new Dimension(this.maxLargeur,
		// this.maxHauteur));

	}

	public int getAdresseByXY(int x, int y) {
		for (AdresseVue adresse : adressesVue) {
			if ( adresse.containsClick(x, y)) {
				adresseSelectionne = adresse;
				this.repaint();
				return adresse.getId();
			}
		}
		return -1;
	}

	public void setEchelle(double value) {
		this.echelle = value;
	}
}
