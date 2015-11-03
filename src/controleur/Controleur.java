package controleur;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tsp.Graphe;
import xml.ExceptionXML;
import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

public class Controleur {
	
	private ListeDeCmd historique;
	private static Etat etatCourant;
	private Plan plan;
	private Tournee tournee;
	
	protected static final EtatIni etatIni = new EtatIni();
	protected static final EtatAjouter1 etatAjouter1 = new EtatAjouter1();
	protected static final EtatAjouter2 etatAjouter2 = new EtatAjouter2();
	protected static final EtatTournee etatTournee = new EtatTournee();
	protected static final EtatPlan etatPLan = new EtatPlan();
	protected static final EtatLivraison etatLivraison = new EtatLivraison();
	protected static final EtatSupprimer etatSupprimer = new EtatSupprimer();
	
	protected static void setEtatCourant(Etat etat) { etatCourant = etat; }
	
	public Tournee getTournee() {
		return tournee;
	}

	public Controleur() {
		historique = new ListeDeCmd();
		etatCourant = etatIni;
		plan = new Plan();
		tournee = null;
	}
	
	public void undo() {
		try {
			etatCourant.undo(historique);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void redo() {
		try {
			etatCourant.redo(historique);
		} catch (Exception e) {

		} 
	}
	
	
	public Graphe chargerPlan() {
		try {
			etatCourant.chargerPlan();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Graphe chargerLivraisons() {
		//tournee=etatCourant.chargerLivraisons(plan);
		return null;
	}
	
	public Graphe calculerTournee() {
		etatCourant.calculerTournee(tournee);
		return null;
	}
	
	public void clicNoeud(Adresse adresse,Livraison livraison, Tournee tournee, ListeDeCmd listeCmd) {
		etatCourant.clicNoeud(adresse,livraison,tournee, listeCmd);
	}
	
	public void clicDroit() {
		etatCourant.clicDroit();
	}
	
	public void ajouter() {
		etatCourant = etatAjouter1;
	}
	
	public void supprimer() {
		etatCourant = etatSupprimer;
	}
	
	public void genererFeuilleDeRoute(String fichier) throws Exception {
		etatCourant.genererFeuilleDeRoute(fichier,tournee);
	}


}
