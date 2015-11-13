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

/**
 * La vue graphique charge d'afficher le plan de la ville ainsi que les livraisons et les tournees chargees.
 */
public class VueGraphique extends JPanel implements Observer, Visiteur {

	/**
	 * Echelle du plan affiche.
	 */
	public double echelle = 1;
	/**
	 * Le plan a afficher.
	 */
	private Plan plan;
	/**
	 * la liste des adresses a afficher.
	 */
	private ArrayList<Shape> listeAdresses;
	/**
	 * La liste de troncons a afficher
	 */
	private ArrayList<Shape> listeTroncons;
	/**
	 * La representation graphique de l'adresse selectionnee.
	 */
	private Shape adresseSelectionne;
	/**
	 * La liste d'adresses composant la tournee a afficher.
	 */
	private ArrayList<Shape> listeAdressesTournee;
	/**
	 * La liste des troncons composant la tournee.
	 */
	private ArrayList<Shape> listeTronconsTournee;
	/**
	 * La representation graphique de l'entrepot.
	 */
	private Shape adresseEntrepot;
	/**
	 * Le decalage a effecteur sur l'affichage du plan sur l'axe x.
	 */
	private int translationX;
	/**
	 * Le decalage a effecteur sur l'affichage du plan sur l'axe y.
	 */
	private int translationY;

	/**
	 * Le constructeur de la vue graphique.
	 * @param plan le plan a afficher.
	 * @param fenetre la fenetre dans laquelle afficher.
	 */
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

	/**
	 * Surcharge de la methode d'affichage.
	 * @param g les Graphics a afficher.
	 */
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
	
	/**
	 * Dessine une adresse dans le plan.
	 * @param a l'adresse a afficher.
	 */
	private void dessinPlan(Adresse a) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD),
				(a.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		listeAdresses.add(adresse);
	}
	
	/**
	 * Dessine un troncon dans le plan.
	 * @param t le troncon a afficher.
	 */
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

	/**
	 * Dessine une adresse et la definie comme adresse selectionnee.
	 * @param a l'adresse a definir en tant qu'adresse selectionnee.
	 */
	private void dessinSelection(Adresse a) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD),
				(a.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		adresseSelectionne = adresse;
	}

	/**
	 * Dessine un troncon appartenant a la tournee en cours de modification.
	 * @param tr le troncon a dessiner.
	 */
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

	/**
	 * Dessine une adresse appartenant a la tournee en cours de livraison.
	 * @param a l'adresse a dessiner.
	 */
	private void dessinTournee(Adresse a) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(a.getX() - Constants.RAYON_NOEUD),
				(a.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		listeAdressesTournee.add(adresse);
	}
	
	/**
	 * Dessine l'entrepot de la tournee en cours de modification.
	 * @param l'adresse de l'entrepot.
	 */
	private void dessinEntrepot(Adresse e) {
		Ellipse2D adresse = new Ellipse2D.Double(
				(e.getX() - Constants.RAYON_NOEUD),
				(e.getY() - Constants.RAYON_NOEUD), Constants.RAYON_NOEUD * 2,
				Constants.RAYON_NOEUD * 2);
		adresseEntrepot = adresse;
	}
	
	/**
	 * Efface l'affichage du plan en cours.
	 */
	private void clearPlan() {
		listeAdresses.clear();
		listeTroncons.clear();
		adresseSelectionne = null;
	}
	
	/**
	 * Efface l'affichage de la tournee en cours de modification.
	 */
	private void clearTournee() {
		listeAdressesTournee.clear();
		listeTronconsTournee.clear();
		adresseEntrepot = null;
	}
	
	/**
	 * Surcharge de la methode update necessaire au fonctionnement du design pattern observable.
	 * @param o l'objet observe.
	 * @param arg l'objet a l'origine de la modification.
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (arg != null) {
			((Visitable) arg).accepte(this);
		}

	}

	/**
	 * Mutateur de l'attribut echelle.
	 * @param value la valeur a donner a echelle.
	 */
	public void setEchelle(double value) {
		this.echelle = value;
	}

	/**
	 * Met une valeur a l'echelle.
	 * @param x la valeur a mettre a l'echelle.
	 * @return la valeur mise a l'echelle.
	 */
	public int changerRepere(int x) {
		return (int) (x / echelle);
	}
	
	/**
	 * Reinitialise l'adresse selectionnee.
	 */
	public void deselection() {
		this.adresseSelectionne = null;

	}
	
	/**
	 * Modifie l'adresse selectionnee.
	 * @param idAdresseSelectionne l'id de l'adresse a definir comme selectionnee.
	 */
	public void selection(int idAdresseSelectionne) {
		Adresse adresseSelectionne = plan.getAdresse(idAdresseSelectionne);
		dessinSelection(adresseSelectionne);
	}


	/**
	 * Surcharge de la methode necessaire au fonctionnement du design pattern visiteur pour le plan.
	 * @param p le plan a visiter.
	 */
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
	
	/**
	 * Surcharge de la methode necessaire au fonctionnement du design pattern visiteur pour la tournee.
	 * @param t la tournee a visiter.
	 */
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
	
	/**
	 * Deplace l'affichage du plan.
	 * @param dx la translation sur l'axe x.
	 * @param dy la translation sur l'axe y.
	 */
	public void moveEcran(int dx, int dy) {
		translationX = dx;
		translationY = dy;
	}
}
