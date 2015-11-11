package modele;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import util.Constants;
import xml.ExceptionXML;
import xml.XMLParser;

/**
 * Cette classe repr�sente le plan d'une ville sous forme d'adresses et de
 * tron�ons entre ces adresses. Elle peut �tre apparent�e � un graphe.
 */
public class Plan extends Observable {
	/**
	 * La liste des adresses qui composent le plan.
	 */
	private Collection<Adresse> adresses;
	/**
	 * La tourn�e qui passent par toutes les livraisons command�es.
	 */
	private Tournee tournee;
	/**
	 * Cordonn�e extr�me des adresses sur l'axe Est/Ouest.
	 */
	private int XMax;
	/**
	 * Coordonn�e extr�me des adresses sur l'axe Nord/Sud
	 */
	private int YMax;

	/**
	 * Le constructeur de la classe Plan. Il ne prend pas de param�tres. Il est
	 * appel� par le controleur
	 */
	public Plan() {
		adresses = new ArrayList<Adresse>();
		this.tournee = null;
	}

	/**
	 * Ajoute une adresse � la liste d'adresse
	 * 
	 * @param a
	 *            l'adresse � ajouter
	 */
	public void ajouterAdresse(Adresse a) {
		adresses.add(a);
		// TODO Appliquer le pattern obervable avec la vue
		// this.setChanged();
		// this.notifyObservers(a); // Pour l'instant on n'utilise pas "a"
	}

	/**
	 * Appel� par le controleur. Le nom du fichier � charger est d�j� d�termin�
	 * par l'OuvreurDeFichier dans le controleur. C'est suite � cet appel que la
	 * liste d'adresses (et donc de tron�ons) va �tre rempli.
	 * 
	 * @throws Exception
	 *             En cas d'erreur dans ou lors de la lecture du fichier XML.
	 */
	public void chargerPlan(File xml) throws ParserConfigurationException,
			SAXException, IOException, ExceptionXML {
		clear();
		XMLParser.chargerPlan(this, xml);
		verifierPlan();
		initXMaxYMax();
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Appel��e par le controleur.Le nom du fichier � charger est d�j� d�termin�
	 * par l'OuvreurDeFichier dans le controleur. C'est suite � cet appel que
	 * les livraisons vont �tre renseign�es.
	 * 
	 * @throws Exception
	 *             En cas d'erreur dans ou lors de la lecture du fichier XML
	 */
	public void chargerLivraison(File xml) throws ParserConfigurationException,
			SAXException, IOException, ExceptionXML {
		viderTournee();
		tournee = XMLParser.chargerLivraison(this, xml);
		this.setChanged();
		this.notifyObservers(tournee);
	}

	/**
	 * Appel�e sourtout par la classe Tournee. Cette m�thode calcule le plus
	 * court chemin entre deux adresses
	 * 
	 * @param a1
	 *            l'adresse de d�part
	 * @param a2
	 *            l'adresse d'arriv��e
	 * @return chemin le plus court chemin entre les deux adresses
	 */
	public Chemin calculerChemin(Adresse a1, Adresse a2) {
		Chemin res = new Chemin(a1, a2);

		int nbAdresses = adresses.size();
		HashMap<Adresse, Double> distMap = new HashMap<Adresse, Double>();

		TreeSet<Adresse> blanc = new TreeSet<Adresse>(new AdresseComparator(
				distMap));
		ArrayList<Adresse> noir = new ArrayList<Adresse>();

		double min = Double.MAX_VALUE;
		Adresse[] precedence = new Adresse[nbAdresses];
		precedence[a1.getId()] = a1;

		// pour ne pas avoir la meme distance a l'entrepot
		int k = 0;
		for (Adresse ad : adresses) {
			distMap.put(ad, Double.MAX_VALUE - (k++));
		}
		distMap.put(a1, 0.0);
		blanc.add(a1);

		while (!blanc.isEmpty()) {
			Adresse current = blanc.pollFirst();

			for (Troncon t : current.getTroncons()) {
				if (!noir.contains(t.getArrivee())) {
					double dureePrecedente = distMap.get(t.getArrivee());
					double dureeActuelle = distMap.get(current) + t.getDuree();

					// Mise a jour de la distance
					if (dureeActuelle < dureePrecedente && dureeActuelle < min) {
						distMap.put(t.getArrivee(), dureeActuelle);

						// Mise a jour du tas binaire
						if (t.getArrivee() != a2)
							blanc.add(t.getArrivee());
						else
							min = dureeActuelle;

						// Mise a jour du precedent
						precedence[t.getArrivee().getId()] = current;
						// System.out.println("Precedence["+t.getArrivee().getId()+"] = "+current.getId());
					}
				}
			}

			// Sommet visit�
			noir.add(current);

		}

		Adresse arrivee = a2;
		Adresse depart = precedence[a2.getId()];

		// On remonte le tableau de pr�c�dence pour construire le chemin
		while (arrivee != depart) {
			// Ajouter le troncon au chemin
			for (Troncon t : depart.getTroncons()) {
				if (t.getArrivee().equals(arrivee)) {
					res.ajouterTroncon(t);
					break;
				}
			}

			arrivee = depart;
			depart = precedence[arrivee.getId()];
		}

		return res;
	}

	/**
	 * Accesseur de la liste d'adresses.
	 * 
	 * @return adresses la liste d'adresses.
	 */
	public Collection<Adresse> getAdresses() {
		return adresses;
	}

	/**
	 * Permet de r�cup�rer une adresse dans la liste d'adresses.
	 * 
	 * @param id
	 *            l'id de l'adresse recherch�e.
	 * @return l'adresse recherch�e.
	 */
	public Adresse getAdresse(int id) {
		for (Adresse a : adresses) {
			if (a.getId() == id)
				return a;
		}
		return null;
	}

	/**
	 * Vide le plan de toutes ses adresses.
	 */
	public void clear() {
		adresses.clear();
		viderTournee();
		setChanged();
		notifyObservers(tournee);
	}

	/**
	 * Initialise les coordon�es extr�mes � partir des adresses cahrg�es.
	 */
	private void initXMaxYMax() {
		XMax = -1;
		YMax = -1;
		for (Adresse a : adresses) {
			if (a.getX() > XMax)
				XMax = a.getX();
			if (a.getY() > YMax)
				YMax = a.getY();
		}
	}

	/**
	 * Accesseur de l'attribut XMax.
	 * 
	 * @return XMax la coordon�e x extr�me.
	 */
	public int getXMax() {
		return XMax;
	}

	/**
	 * Accesseur de l'attribut YMax.
	 * 
	 * @return YMax la coordon�e y extr�me.
	 */
	public int getYMax() {
		return YMax;
	}

	/**
	 * Cette m�thode � pour but de retir� du plan toutes les adresses et les
	 * tron�ons inutiles (adresses isol�es, tron�ons qui n'aboutissent nulle
	 * part,...).
	 * 
	 * @throws ExceptionXML
	 *             lorsqu'une erreur est rencontr�e.
	 */
	private void verifierPlan() throws ExceptionXML {
		verifierAdresses();
		verifierTroncon();
	}

	/**
	 * V�rifie la validit� de toutes les adresses charg�es dans le plan.
	 * 
	 * @throws ExceptionXML
	 *             lorsqu'une adresse invalide est d�tect�e.
	 */
	private void verifierAdresses() throws ExceptionXML {
		boolean adresseIsolee = false;
		for (Adresse a : adresses) {
			if (!aTronconEntrant(a)) { // l'adresse a est isolée
				adresseIsolee = true;
			}
		}
		if (adresseIsolee) {
			throw new ExceptionXML(ExceptionXML.ADRESSE_INACCESSIBLE);
		}
	}

	/**
	 * V�rifie que l'adresse pass�e en param�tre permet de rejoindre d'autres
	 * adresses
	 * 
	 * @param a
	 *            l'adresse � v�rifier
	 * @return true si l'adresse comporte au moins un tron�on sortant.
	 */
	private boolean aTronconEntrant(Adresse a) {
		ArrayList<Troncon> listeTroncons = getTroncons();
		for (Troncon t : listeTroncons) {
			if (a.equals(t.getArrivee())) { // l'adresse a a au moins un troncon
											// entrant
				return true;
			}
		}
		return false;
	}

	/**
	 * V�rifie la validit� de tous les tron�ons charg�s dans le plan.
	 * 
	 * @throws ExceptionXML
	 *             lorsuq'un tron�on invalide est rencontr�.
	 */
	private void verifierTroncon() throws ExceptionXML {

		boolean tronconVersNull = false;
		for (Adresse a : adresses) {
			for (Troncon t : a.getTroncons()) {
				if (t.getArrivee() == null) {
					tronconVersNull = true;
				}
			}
		}
		if (tronconVersNull) {
			throw new ExceptionXML(ExceptionXML.ARRIVEE_TRONCON_INEXISTANTE);
		}
	}

	/**
	 * Permet de r�cup�rer la liste de tous les tron�ons charg�s dans le plan.
	 * 
	 * @return la liste de tron�ons.
	 */
	private ArrayList<Troncon> getTroncons() {
		ArrayList<Troncon> listeTroncons = new ArrayList<>();
		for (Adresse a : adresses) {
			for (Troncon t : a.getTroncons()) {
				listeTroncons.add(t);
			}
		}
		return listeTroncons;
	}

	/**
	 * L'accesseur de l'attribut tournee.
	 * 
	 * @return la tournee en cours de modification.
	 */
	public Tournee getTournee() {
		return tournee;
	}

	/**
	 * Permet de r�cup�rer une adresse � partir de ses coordon�es.
	 * 
	 * @param p
	 *            Un point contenant les coordon�es de l'adresse recherch�e.
	 * @return adresse l'adressse recherch�e si elle existe, null sinon.
	 */
	public Adresse getAdresseByCoord(Point p) {
		for (Adresse adresse : adresses) {
			if (p.distance(adresse.getX(), adresse.getY()) <= 2
					* Constants.RAYON_NOEUD + Constants.AREA_CLICK_NOEUD) {
				return adresse;
			}
		}
		return null;
	}

	/**
	 * R�initialise la tourn�e.
	 */
	private void viderTournee() {
		tournee = null;
		for (Adresse adresse : adresses) {
			if (adresse.estAssocierAvecLivraison())
				adresse.setLivraison(null);
		}
		this.setChanged();
		this.notifyObservers(tournee);
	}

}
