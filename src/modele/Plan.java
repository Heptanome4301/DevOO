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
 * Cette classe représente le plan d'une ville sous forme d'adresses et de tronçons entre ces adresses. Elle peut
 * être apparentée à un graphe.
 */
public class Plan extends Observable {
	/**
	 * La liste des adresses qui composent le plan.
	 */
	private Collection<Adresse> adresses;
        /**
         * La tournée qui passent par toutes les livraisons commandées.
         */
	private Tournee tournee;
        /**
         * Cordonnée extrême des adresses sur l'axe Est/Ouest.
         */
	private int XMax;
        /**
         * Coordonnée extrême des adresses sur l'axe Nord/Sud
         */
	private int YMax;

	/**
	 * Le constructeur de la classe Plan. Il ne prend pas de paramètres. Il est appelé par
	 * le controleur
	 */
	public Plan() {
		adresses = new ArrayList<Adresse>();
		this.tournee = null;
	}

	/**
	 * Ajoute une adresse à la liste d'adresse
	 * 
	 * @param a l'adresse à ajouter
	 */
	public void ajouterAdresse(Adresse a) {
		adresses.add(a);
                //TODO Appliquer le pattern obervable avec la vue
		//this.setChanged();
		//this.notifyObservers(a); // Pour l'instant on n'utilise pas "a"
	}

	/**
	 * Appelé par le controleur. Le nom du fichier à charger est déjà déterminé par l'OuvreurDeFichier dans le
         * controleur. C'est suite à cet appel que la liste d'adresses (et donc de tronçons) va être rempli.
	 * 
	 * @throws Exception En cas d'erreur dans ou lors de la lecture du fichier XML.
	 */
	public void chargerPlan(File xml) throws ParserConfigurationException,
			SAXException, IOException, ExceptionXML {
                viderTournee();
		XMLParser.chargerPlan(this, xml);
		verifierPlan();
		initXMaxYMax();
		this.setChanged();
		this.notifyObservers();
	}


	/**
	 * Appelïée par le controleur.Le nom du fichier à charger est déjà déterminé par l'OuvreurDeFichier dans le
         * controleur. C'est suite à cet appel que les livraisons vont être renseignées.
	 * 
	 * @throws Exception En cas d'erreur dans ou lors de la lecture du fichier XML
	 */
	public void chargerLivraison(File xml)
			throws ParserConfigurationException, SAXException, IOException,
			ExceptionXML {
                viderTournee();
		tournee = XMLParser.chargerLivraison(this, xml);
		this.setChanged();
		this.notifyObservers(tournee);
	}

	/**
	 * Appelée sourtout par la classe Tournee. Cette méthode calcule le plus court chemin
	 * entre deux adresses
	 * 
	 * @param a1
	 *            l'adresse de départ
	 * @param a2
	 *            l'adresse d'arrivïée
	 * @return chemin le plus court chemin entre les deux adresses
	 */
	public Chemin calculerChemin(Adresse a1, Adresse a2) {
		Chemin res = new Chemin(a1, a2);

		int nbAdresses = adresses.size();
		HashMap<Adresse, Double> distMap = new HashMap<Adresse, Double>();

		TreeSet<Adresse> blanc = new TreeSet<Adresse>(new AdresseComparator(distMap));
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
                                                //System.out.println("Precedence["+t.getArrivee().getId()+"] = "+current.getId());
					}
				}
			}

			// Sommet visitï¿½
			noir.add(current);
                        
		}

		Adresse arrivee = a2;
		Adresse depart = precedence[a2.getId()];

		// On remonte le tableau de prï¿½cï¿½dence pour construire le chemin
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
         * Permet de récupérer une adresse dans la liste d'adresses.
         * @param id l'id de l'adresse recherchée.
         * @return l'adresse recherchée.
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
	}
	
        /**
         * Initialise les coordonées extrêmes à partir des adresses cahrgées.
         */
	private void initXMaxYMax() {
		XMax = -1 ;
		YMax = -1 ;
		for(Adresse a : adresses) {
			if(a.getX()> XMax) XMax = a.getX();
			if(a.getY()> YMax) YMax = a.getY();
		}
	}
	
	/**
         * Accesseur de l'attribut XMax.
         * @return XMax la coordonée x extrême.
         */
	public int getXMax() {
		return XMax;
	}
        
        /**
         *Accesseur de l'attribut YMax.
         * @return YMax la coordonée y extrême.
         */
	public int getYMax() {
		return YMax;
	}

        /**
         * Cette méthode à pour but de retiré du plan toutes les adresses et les tronçons inutiles (adresses isolées,
         * tronçons qui n'aboutissent nulle part,...).
         * @throws ExceptionXML lorsqu'une erreur est rencontrée.
         */
	private void verifierPlan() throws ExceptionXML{
		verifierAdresses();
		verifierTroncon();
	}
        
        /**
         * Vérifie la validité de toutes les adresses chargées dans le plan.
         * @throws ExceptionXML lorsqu'une adresse invalide est détectée.
         */
	private void verifierAdresses() throws ExceptionXML{
		boolean adresseIsolee = false;
		for(Adresse a : adresses){
			if(!aTronconEntrant(a)){ //l'adresse a est isolÃ©e
				adresseIsolee = true;
			}
		}
		if(adresseIsolee){
			throw new ExceptionXML(ExceptionXML.ADRESSE_INACCESSIBLE);
		}
	}
        
        /**
         * Vérifie que l'adresse passée en paramètre permet de rejoindre d'autres adresses
         * @param a l'adresse à vérifier
         * @return true si l'adresse comporte au moins un tronçon sortant.
         */
	private boolean aTronconEntrant(Adresse a){
		ArrayList<Troncon> listeTroncons = getTroncons();
		for(Troncon t : listeTroncons){
			if(a.equals(t.getArrivee())){ //l'adresse a a au moins un troncon entrant
				return true;
			}
		}
		return false;
	}

        /**
         * Vérifie la validité de tous les tronçons chargés dans le plan.
         * @throws ExceptionXML lorsuq'un tronçon invalide est rencontré.
         */
	private void verifierTroncon() throws ExceptionXML{

		boolean tronconVersNull = false;
		for(Adresse a : adresses){
			for (Troncon t : a.getTroncons()){
				if(t.getArrivee() == null){
					tronconVersNull = true;
				}
			}
		}
		if(tronconVersNull){
			throw new ExceptionXML(ExceptionXML.ARRIVEE_TRONCON_INEXISTANTE);
		}
	}
        
        /**
         * Permet de récupérer la liste de tous les tronçons chargés dans le plan.
         * @return la liste de tronçons.
         */
	private ArrayList<Troncon> getTroncons(){
		ArrayList<Troncon> listeTroncons = new ArrayList<>();
		for(Adresse a : adresses){
			for(Troncon t : a.getTroncons()){
				listeTroncons.add(t);
			}
		}
		return listeTroncons;
	}

        /**
         * L'accesseur de l'attribut tournee.
         * @return la tournee en cours de modification.
         */
	public Tournee getTournee() {
		return tournee;
	}

        /**
         * Permet de récupérer une adresse à partir de ses coordonées. 
         * @param p Un point contenant les coordonées de l'adresse recherchée.
         * @return adresse l'adressse recherchée si elle existe, null sinon.
         */
	public Adresse getAdresseByCoord(Point p) {
		for (Adresse adresse : adresses) {
			if (p.distance(adresse.getX(), adresse.getY()) <= 2 * Constants.RAYON_NOEUD + Constants.AREA_CLICK_NOEUD)
			{
				return adresse;
			}
		}
		return null;
	}
        
        /**
         * Réinitialise la tournée.
         */
        private void viderTournee(){
            tournee = null;
        }


}
