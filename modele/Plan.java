package modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ExceptionXML;
import xml.PlanXMLParser;

public class Plan {
	/**
	 * La liste des adresses qui composent le plan
	 */
	private ArrayList<Adresse> adresses;
	
	/**
	 * Le constructeur de la classe Plan ne prend pas de paramètres.
	 */
	public Plan(){
		adresses = new ArrayList<Adresse>();
	}
	
	/**
	 * Ajoute une adresse à la liste d'adresse
	 * @param a l'adresse à ajouter
	 */
	public void ajouterAdresse(Adresse a){
		adresses.add(a);
	}
	
	/**
	 * Construire un plan à partir d'un fichier XML de description
	 */
	public void chargerPlan(){
		try {
			PlanXMLParser.charger(this);
		} catch (ParserConfigurationException | SAXException | IOException
				| ExceptionXML e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calculer le plus court chemin entre deux adresses
	 * @param a1 l'adresse de départ
	 * @param a2 l'adresse d'arrivée
	 * @return chemin le plus court chemin entre les deux adresses
	 */
	public Chemin calculerChemin(Adresse a1, Adresse a2){
		Chemin res = new Chemin();
		
		int nbAdresses = adresses.size();
		HashMap<Adresse,Double> distMap = new HashMap<Adresse,Double>();
		
		TreeSet<Adresse> blanc = new TreeSet<Adresse>(new AdresseComparator(distMap));
		ArrayList<Adresse> noir = new ArrayList<Adresse>();
		
		double min = 1000000000000.0;
		Adresse[] precedence = new Adresse[nbAdresses];
		precedence[a1.getId()] = a1;
		
		//pour ne pas avoir la meme distance a l'entrepot
		int k = 0;
		for(Adresse ad : adresses){
			distMap.put(ad,1000000000000.0+(k++));
			blanc.add(ad);
		}
		distMap.put(a1, 0.0);
		blanc.add(a1);
		
		while(!blanc.isEmpty()){
			Adresse current = blanc.pollFirst();
			
			for(Troncon t : current.getTroncons()){
				if(!noir.contains(t.getArrivee())){
					double dureePrecedente = distMap.get(t.getArrivee());
					double dureeActuelle =  distMap.get(current) + t.getDuree();
					
					//Mise a jour de la distance
					if(dureeActuelle < dureePrecedente && dureeActuelle < min){
						distMap.put(t.getArrivee(), dureeActuelle);
						
						//Mise a jour du tas binaire
						blanc.remove(t.getArrivee());
						blanc.add(t.getArrivee());
						
						//Mise a jour du precedent
						precedence[t.getArrivee().getId()] = current;
						
						if(t.getArrivee() == a2){
							min = dureeActuelle;
						}
					}
				}
			}
			
			//Sommet visté
			noir.add(current);
		}
		
		Adresse arrivee = a2;
		Adresse depart = precedence[a2.getId()];
		
		//On remonte le tableau de précédence pour construire le chemin
		while(arrivee != depart){
			//Ajouter le troncon au chemin
			for(Troncon t : depart.getTroncons()){
				if(t.getArrivee() == arrivee){
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
	 * @return adresses la liste d'adresses
	 */
	public ArrayList<Adresse> getAdresses(){
		return adresses;
	}
}
