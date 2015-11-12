package vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import modele.Adresse;
import modele.Chemin;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;
import modele.Visitable;
import modele.Visiteur;
import util.Constants;
import vue.Fenetre;

public class VueGraphique extends JPanel implements Observer, Visiteur {

	/**
	 * 
	 */
	public double echelle = 1;

	private Plan plan;

	private ArrayList<Shape> listeAdresses;
	private ArrayList<Shape> listeTroncons;
	private Shape adresseSelectionne;
	private ArrayList<Shape> listeAdressesTournee;
	private ArrayList<Shape> listeTronconsTournee;

	private Shape adresseEntrepot;

	private int translationX;

	private int translationY;

	public VueGraphique(Plan plan, Fenetre fenetre) {
		super();
		this.setBackground(Color.white);
		listeAdresses = new ArrayList<Shape>();
		listeTroncons = new ArrayList<Shape>();
		listeAdressesTournee = new ArrayList<Shape>();
		listeTronconsTournee = new ArrayList<Shape>();
		this.plan = plan;
		plan.addObserver(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;

		RenderingHints hints = g2D.getRenderingHints();
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2D.setRenderingHints(hints);

		Stroke strokeDefault = new BasicStroke(0.01f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 10.0f);
		Stroke strokeLivraisons = new BasicStroke(0.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 10.0f);
		g2D.setStroke(strokeDefault);

		g2D.translate(translationX, translationY);
		g2D.scale(echelle, echelle);

		for (Shape troncon : listeTroncons) {

			g2D.setColor(Color.black);
			g2D.draw(troncon);
		}

		for (Shape troncon : listeTronconsTournee) {
			g2D.setStroke(strokeLivraisons);
			g2D.setColor(Color.green);
			g2D.draw(troncon);
		}

		for (Shape adresse : listeAdresses) {
			g2D.setColor(Color.black);
			g2D.draw(adresse);
			g2D.fill(adresse);
		}

		for (Shape adresse : listeAdressesTournee) {
			g2D.setColor(Color.blue);
			g2D.fill(adresse);
		}

		if (adresseEntrepot != null) {
			g2D.setColor(Color.red);
			g2D.fill(adresseEntrepot);
		}

		if (adresseSelectionne != null) {
			g2D.setColor(Color.yellow);
			g2D.fill(adresseSelectionne);
		}
	}

	private void dessinPlan(Adresse a) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD),
				(a.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		listeAdresses.add(adresse);
	}

	private void dessinPlan(Troncon t) {
		Point tete = new Point(t.getArrivee().getX(), t.getArrivee().getY());
		Point queue = new Point(t.getDepart().getX(), t.getDepart().getY());
		double dy = tete.y - queue.y;
		double dx = tete.x - queue.x;
		double theta = Math.atan2(dy, dx);

		tete.setLocation(tete.x - Constants.RAYON_NOEUD * Math.cos(theta),
				tete.y - Constants.RAYON_NOEUD * Math.sin(theta));

		int x1, x2, y1, y2;
		double phi = theta + Math.toRadians(40);

		phi = theta + Math.toRadians(40);
		x1 = (int) (tete.x - 5 * Math.cos(phi));
		y1 = (int) (tete.y - 5 * Math.sin(phi));
		phi = theta - Math.toRadians(40);
		x2 = (int) (tete.x - 5 * Math.cos(phi));
		y2 = (int) (tete.y - 5 * Math.sin(phi));

		Line2D ligne1 = new Line2D.Double(queue.x, queue.y, tete.x, tete.y);
		Line2D ligne2 = new Line2D.Double(tete.x, tete.y, x1, y1);
		Line2D ligne3 = new Line2D.Double(tete.x, tete.y, x2, y2);

		listeTroncons.add(ligne1);
		listeTroncons.add(ligne2);
		listeTroncons.add(ligne3);
	}

	private void dessinSelection(Adresse a) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD),
				(a.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		adresseSelectionne = adresse;
	}

	private void dessinTournee(Troncon tr) {
		Point tete = new Point(tr.getArrivee().getX(), tr.getArrivee().getY());
		Point queue = new Point(tr.getDepart().getX(), tr.getDepart().getY());
		double dy = tete.y - queue.y;
		double dx = tete.x - queue.x;
		double theta = Math.atan2(dy, dx);

		tete.setLocation(tete.x - Constants.RAYON_NOEUD * Math.cos(theta),
				tete.y - Constants.RAYON_NOEUD * Math.sin(theta));

		int x1, x2, y1, y2;
		double phi = theta + Math.toRadians(40);

		phi = theta + Math.toRadians(40);
		x1 = (int) (tete.x - 10 * Math.cos(phi));
		y1 = (int) (tete.y - 10 * Math.sin(phi));
		phi = theta - Math.toRadians(40);
		x2 = (int) (tete.x - 10 * Math.cos(phi));
		y2 = (int) (tete.y - 10 * Math.sin(phi));

		Line2D ligne1 = new Line2D.Double(queue.x, queue.y, tete.x, tete.y);
		Line2D ligne2 = new Line2D.Double(tete.x, tete.y, x1, y1);
		Line2D ligne3 = new Line2D.Double(tete.x, tete.y, x2, y2);

		listeTronconsTournee.add(ligne1);
		listeTronconsTournee.add(ligne2);
		listeTronconsTournee.add(ligne3);
	}

	private void dessinTournee(Adresse a) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD),
				(a.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		listeAdressesTournee.add(adresse);
	}

	private void dessinEntrepot(Adresse e) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(e.getX() - Constants.RAYON_NOEUD),
				(e.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		adresseEntrepot = adresse;
	}
	
	private void clearPlan() {
		listeAdresses.clear();
		listeTroncons.clear();
		adresseSelectionne = null;
	}
	
	private void clearTournee() {
		listeAdressesTournee.clear();
		listeTronconsTournee.clear();
		adresseEntrepot = null;
	}
	
	@Override
	public void update(Observable o, Object arg) {

		if (arg != null) {
			((Visitable) arg).accepte(this);
		}

	}

	public void setEchelle(double value) {
		this.echelle = value;
	}

	// @Override
	// public void visite(Adresse a, boolean estEntrepot) {
	// Graphics2D g2D = (Graphics2D) g;
	//
	// Ellipse2D adresse = new Ellipse2D.Double(
	// (a.getX() - Constants.RAYON_NOEUD) * echelle +
	// Constants.MARGIN_VUE_GRAPHE,
	// (a.getY() - Constants.RAYON_NOEUD) * echelle +
	// Constants.MARGIN_VUE_GRAPHE,
	// Constants.RAYON_NOEUD * 2 * echelle,
	// Constants.RAYON_NOEUD * 2 * echelle);
	// g2D.fill(adresse);
	// g2D.setColor(Color.black);
	// g2D.draw(adresse);
	//
	//
	// if (a.estAssocierAvecLivraison()) {
	// g2D.setColor(Color.blue);
	// g2D.fill(adresse);
	//
	// }
	// if (estEntrepot) {
	// g2D.setColor(Color.red);
	// g2D.fill(adresse);
	// }
	//
	// if (idAdresseSelectionne != null) {
	// a = plan.getAdresse(idAdresseSelectionne);
	// Ellipse2D adresseSelectionne = new Ellipse2D.Double(
	// (a.getX() - Constants.RAYON_NOEUD) * echelle +
	// Constants.MARGIN_VUE_GRAPHE,
	// (a.getY() - Constants.RAYON_NOEUD) * echelle +
	// Constants.MARGIN_VUE_GRAPHE,
	// Constants.RAYON_NOEUD * 2 * echelle,
	// Constants.RAYON_NOEUD * 2 * echelle);
	//
	// g2D.setColor(Color.yellow);
	// g2D.fill(adresseSelectionne);
	// }
	//
	// }
	//
	// @Override
	// public void visite(Troncon t, boolean isDansTournee) {
	// Graphics2D g2D = (Graphics2D) g;
	// if (isDansTournee) {
	// g2D.setColor(Color.blue);
	// } else {
	// g2D.setColor(Color.gray);
	// }
	// g2D.drawLine(
	// (int) (t.getDepart().getX() * echelle + Constants.MARGIN_VUE_GRAPHE),
	// (int) (t.getDepart().getY() * echelle + Constants.MARGIN_VUE_GRAPHE),
	// (int) (t.getArrivee().getX() * echelle + Constants.MARGIN_VUE_GRAPHE),
	// (int) (t.getArrivee().getY() * echelle + Constants.MARGIN_VUE_GRAPHE));
	//
	// double dy = t.getArrivee().getY() - t.getDepart().getY();
	// double dx = t.getArrivee().getX() - t.getDepart().getX();
	// double theta = Math.atan2(dy, dx);
	//
	// double x, y;
	// double phi = theta + Math.toRadians(40);
	//
	// for(int j = 0; j < 2; j++)
	// {
	// x = t.getArrivee().getX() - 10 * Math.cos(phi);
	// y = t.getArrivee().getY() - 10 * Math.sin(phi);
	// g2D.draw(new Line2D.Double(t.getArrivee().getX() * echelle +
	// Constants.MARGIN_VUE_GRAPHE,
	// t.getArrivee().getY() * echelle + Constants.MARGIN_VUE_GRAPHE,
	// x * echelle + Constants.MARGIN_VUE_GRAPHE,
	// y * echelle + Constants.MARGIN_VUE_GRAPHE));
	// phi = theta - Math.toRadians(40);
	// }
	// }

	public int changerRepere(int x) {
		return (int) (x / echelle);
	}

	public void deselection() {
		this.adresseSelectionne = null;

	}

	public void selection(int idAdresseSelectionne) {
		Adresse adresseSelectionne = plan.getAdresse(idAdresseSelectionne);
		dessinSelection(adresseSelectionne);
	}

//	@Override
//	public void visite(Adresse a) {
//		dessinPlan(a);
//		for (Troncon t : a.getTroncons()) {
//			dessinPlan(t);
//		}
//	}

	@Override
	public void visite(Plan p) {
		clearPlan();
		
		for (Adresse a : p.getAdresses()) {
			dessinPlan(a);
			for (Troncon t : a.getTroncons()) {
				dessinPlan(t);
			}
		}

		Tournee t = p.getTournee();

		if (t != null) {
			t.addObserver(this);
			
			dessinEntrepot(t.getEntrepot());
			for (Livraison l : t.getLivraisons()) {
				Adresse a = l.getAdresse();
				dessinTournee(a);
			}

			for (Chemin c : t.getItineraire()) {
				for (Troncon tr : c.getTroncons()) {
					dessinTournee(tr);
				}
			}
		}
	}

	@Override
	public void visite(Tournee t) {
		clearTournee();
		dessinEntrepot(t.getEntrepot());
		for (Livraison l : t.getLivraisons()) {
			Adresse a = l.getAdresse();
			dessinTournee(a);
		}

		for (Chemin c : t.getItineraire()) {
			for (Troncon tr : c.getTroncons()) {
				dessinTournee(tr);
			}
		}

	}

	public void moveEcran(int dx, int dy) {
		translationX = dx;
		translationY = dy;
	}

//	@Override
//	public void visite(Chemin c) {
//		for (Troncon tr : c.getTroncons()) {
//			dessinTournee(tr);
//		}
//	}
}
