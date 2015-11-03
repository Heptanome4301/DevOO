package modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ExceptionXML;
import xml.PlanXMLParser;

public class Plan extends Observable {
	/**
	 * La liste des adresses qui composent le plan
	 */
	private Collection<Adresse> adresses;

	private Tournee tournee;

	/**
	 * Le constructeur de la classe Plan ne prend pas de paramètres. Appelé par
	 * le controleur
	 */
	public Plan() {
		adresses = new ArrayList<Adresse>();
		this.tournee = null;
	}

	/**
	 * Ajoute une adresse à la liste d'adresse
	 * 
	 * @param a
	 *            l'adresse à ajouter
	 */
	public void ajouterAdresse(Adresse a) {
		adresses.add(a);
		this.notifyObservers(this);
	}

	/**
	 * Appelée par le controleur le nom du fichier à charger est déja connu par
	 * la classe PlanXMLParser (OuvreurDeFichier qui le fait)
	 * 
	 * @throws Exception
	 *             En cas d'erreur dans le fichier XML
	 */
	public void chargerPlan() throws ParserConfigurationException,
			SAXException, IOException, ExceptionXML {
		PlanXMLParser.chargerPlan(this);
		//completerTraconsManquants();
		afficherPlan();
	}

	private void afficherPlan() {
		for (Adresse a : adresses) {
			System.out.print(a.getId()+" :");
			Collection<Troncon> tronconsSortantDeA = a.getTroncons();
			for (Troncon t : tronconsSortantDeA) {
				System.out.print(" "+t.getArrivee().getId());
		
			}
			System.out.println();
		}

	}

	private void completerTraconsManquants() {
		for (Adresse a : adresses) {
			Collection<Troncon> tronconsSortantDeA = a.getTroncons();
			for (Adresse b : adresses) {
				if(a==b) continue;
				boolean trouve = false;
				for (Troncon t : tronconsSortantDeA) {
					if (t.getArrivee() == b){
						trouve = true;
						break;
					}
				}
				if (trouve == false) {
					a.ajouterTroncon(new Troncon(a.getId() + "->" + b.getId(),
							a.getId() * b.getId(), 1, b));
				}
			}
		}
	}

	/**
	 * Appelée par le controleur le nom du fichier à charger est déja connu par
	 * la classe PlanXMLParser (OuvreurDeFichier qui le fait)
	 * 
	 * @throws Exception
	 *             En cas d'erreur dans le fichier XML
	 * @return la tournee correspendante, et initialise l'attribut tourne
	 */
	public Tournee chargerLivraison() throws ParserConfigurationException,
			SAXException, IOException, ExceptionXML {
		tournee = PlanXMLParser.chargerLivraison(this);
		return tournee;
	}

	/**
	 * Appelée sourtout par la classe Tournee Calculer le plus court chemin
	 * entre deux adresses
	 * 
	 * @param a1
	 *            l'adresse de départ
	 * @param a2
	 *            l'adresse d'arrivée
	 * @return chemin le plus court chemin entre les deux adresses
	 */
	public Chemin calculerChemin(Adresse a1, Adresse a2) {
		Chemin res = new Chemin(a1,a2);

		int nbAdresses = adresses.size();
		HashMap<Adresse, Double> distMap = new HashMap<Adresse, Double>();

		TreeSet<Adresse> blanc = new TreeSet<Adresse>(new AdresseComparator(
				distMap));
		ArrayList<Adresse> noir = new ArrayList<Adresse>();

		double min = 1000000000000.0; // Integer.MAX_VALUE; //??
		Adresse[] precedence = new Adresse[nbAdresses];
		precedence[a1.getId()] = a1;

		// pour ne pas avoir la meme distance a l'entrepot
		int k = 0;
		for (Adresse ad : adresses) {
			distMap.put(ad, 1000000000000.0 + (k++));
			blanc.add(ad);
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
						blanc.remove(t.getArrivee());
						blanc.add(t.getArrivee());

						// Mise a jour du precedent
						precedence[t.getArrivee().getId()] = current;

						if (t.getArrivee() == a2) {
							min = dureeActuelle;
						}
					}
				}
			}

			// Sommet visté
			noir.add(current);
		}

		Adresse arrivee = a2;
		Adresse depart = precedence[a2.getId()];

		// On remonte le tableau de précédence pour construire le chemin
		while (depart!=null && arrivee != depart) {
			// Ajouter le troncon au chemin
			for (Troncon t : depart.getTroncons()) {
				if (t.getArrivee() == arrivee) {
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

}
