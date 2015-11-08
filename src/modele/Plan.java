package modele;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ExceptionXML;
import xml.XMLParser;

public class Plan extends Observable {
	/**
	 * La liste des adresses qui composent le plan
	 */
	private Collection<Adresse> adresses;

	private Tournee tournee;

	private int XMax;
	private int YMax;

	/**
	 * Le constructeur de la classe Plan ne prend pas de paramï¿½tres. Appelï¿½ par
	 * le controleur
	 */
	public Plan() {
		adresses = new ArrayList<Adresse>();
		this.tournee = null;
	}

	/**
	 * Ajoute une adresse ï¿½ la liste d'adresse
	 * 
	 * @param a
	 *            l'adresse ï¿½ ajouter
	 */
	public void ajouterAdresse(Adresse a) {
		adresses.add(a);
		this.setChanged();
		this.notifyObservers(a); // Pour l'instant on n'utilise pas "a"
	}

	/**
	 * Appelï¿½e par le controleur le nom du fichier ï¿½ charger est dï¿½ja connu par
	 * la classe PlanXMLParser (OuvreurDeFichier qui le fait)
	 * 
	 * @throws Exception
	 *             En cas d'erreur dans le fichier XML
	 */
	public void chargerPlan(File xml) throws ParserConfigurationException,
			SAXException, IOException, ExceptionXML {
		XMLParser.chargerPlan(this, xml);
		verifierPlan();
		initXMaxYMax();
	}


	private void afficherPlan() {
		// for (Adresse a : adresses) {
		// System.out.print(a.getId()+" :");
		// Collection<Troncon> tronconsSortantDeA = a.getTroncons();
		// for (Troncon t : tronconsSortantDeA) {
		// System.out.print(" "+t.getArrivee().getId());
		//
		// }
		// System.out.println();
		// }

	}

	private void completerTronconsManquants() {
		for (Adresse a : adresses) {
			Collection<Troncon> tronconsSortantDeA = a.getTroncons();
			for (Adresse b : adresses) {
				if (a == b)
					continue;
				boolean trouve = false;
				for (Troncon t : tronconsSortantDeA) {
					if (t.getArrivee() == b) {
						trouve = true;
						break;
					}
				}
				if (trouve == false) {
					a.ajouterTroncon(new Troncon(a.getId() + "->" + b.getId(),
							a.getId() * b.getId(), 1, a, b));
				}
			}
		}
	}

	/**
	 * Appelï¿½e par le controleur le nom du fichier ï¿½ charger est dï¿½ja connu par
	 * la classe PlanXMLParser (OuvreurDeFichier qui le fait)
	 * 
	 * @throws Exception
	 *             En cas d'erreur dans le fichier XML
	 * @return la tournee correspendante, et initialise l'attribut tourne
	 */
	public Tournee chargerLivraison(File xml)
			throws ParserConfigurationException, SAXException, IOException,
			ExceptionXML {
		tournee = XMLParser.chargerLivraison(this, xml);
		return tournee;
	}

	/**
	 * Appelï¿½e sourtout par la classe Tournee Calculer le plus court chemin
	 * entre deux adresses
	 * 
	 * @param a1
	 *            l'adresse de dï¿½part
	 * @param a2
	 *            l'adresse d'arrivï¿½e
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

			// Sommet visité
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
	 * Accesseurs de la liste d'adresse
	 * 
	 * @return adresses la liste d'adresses
	 */
	public Collection<Adresse> getAdresses() {
		return adresses;
	}

	/**
	 * retourn la adresse du plan dont le id est donne
	 */
	public Adresse getAdresse(int id) {
		for (Adresse a : adresses) {
			if (a.getId() == id)
				return a;
		}
		return null;
	}
	
	public void clear() {
		adresses.clear();
	}
	
	private void initXMaxYMax() {
		XMax = -1 ;
		YMax = -1 ;
		for(Adresse a : adresses) {
			if(a.getX()> XMax) XMax = a.getX();
			if(a.getY()> YMax) YMax = a.getY();
		}
	}
	
	
	public int getXMax() {
		return XMax;
	}

	public int getYMax() {
		return YMax;
	}

	private void verifierPlan() throws ExceptionXML{
		verifierAdresses();
		verifierTroncon();
	}

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

	private boolean aTronconEntrant(Adresse a){
		ArrayList<Troncon> listeTroncons = getTroncons();
		for(Troncon t : listeTroncons){
			if(a.equals(t.getArrivee())){ //l'adresse a a au moins un troncon entrant
				return true;
			}
		}
		return false;
	}

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
	private ArrayList<Troncon> getTroncons(){
		ArrayList<Troncon> listeTroncons = new ArrayList<>();
		for(Adresse a : adresses){
			for(Troncon t : a.getTroncons()){
				listeTroncons.add(t);
			}
		}
		return listeTroncons;
	}



}
