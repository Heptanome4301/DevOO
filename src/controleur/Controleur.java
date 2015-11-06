package controleur;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tsp.Graphe;
import vue.Fenetre;
import xml.OuvreurDeFichiersXML;
import modele.Adresse;
import modele.Plan;
import modele.Tournee;

public class Controleur {
	
	private ListeDeCmd historique;
	private static Etat etatCourant;
	private Plan plan;
	private Tournee tournee;
	private Fenetre fenetre;
	
	protected static final EtatIni etatIni = new EtatIni();
	protected static final EtatAjouter1 etatAjouter1 = new EtatAjouter1();
	protected static final EtatAjouter2 etatAjouter2 = new EtatAjouter2();
	protected static final EtatTournee etatTournee = new EtatTournee();
	protected static final EtatPlan etatPlan = new EtatPlan();
	protected static final EtatLivraison etatLivraison = new EtatLivraison();
	protected static final EtatSupprimer etatSupprimer = new EtatSupprimer();
	protected static final EtatEchanger1 etatEchanger1 = new EtatEchanger1();
	protected static final EtatEchanger2 etatEchanger2 = new EtatEchanger2();
	
	protected static void setEtatCourant(Etat etat) { etatCourant = etat; }
	

	public Controleur() {
		this.historique = new ListeDeCmd();
		this.etatCourant = etatIni;
		this.plan = new Plan();
		this.fenetre = new Fenetre(this, plan);
		this.tournee = null;
	}
	
	/**
	 * Annule la dernière modification effectuée sur la tournée (ajout, suppression ou échange de livraisons)
	 */
	public void undo() {
		try {
			etatCourant.undo(historique);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Réeffectue la dernière action annulée
	 */
	public void redo() {
		try {
			etatCourant.redo(historique);
		} catch (Exception e) {

		} 
	}
	
	
	public Graphe chargerPlan()  {
		plan.clear();
		File xml ;
		tournee = null;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			etatCourant.chargerPlan(plan,xml);
		} catch (Exception e) {
			//TODO signaler erreur a la vue
			e.printStackTrace();
		}
		return null;
		//TODO
	}
	
	public Graphe chargerLivraisons() {
	    File xml ;
	    tournee = null;
	    try {
	            xml = OuvreurDeFichiersXML.getInstance().ouvre();
	            etatCourant.chargerLivraisons(plan,xml);
	    } catch (Exception e) {
	            //TODO signaler erreur a la vue
	            e.printStackTrace();
	    }
	    return null;
	    //TODO
	}
	
	public Graphe calculerTournee() {
		etatCourant.calculerTournee(tournee);
		return null;
	}
	
	public void clicNoeud(Adresse adresse, Plan plan,Tournee tournee, ListeDeCmd listeCmd) {
		try {
			etatCourant.clicNoeud(adresse,plan,tournee, listeCmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clicDroit() {
		etatCourant.clicDroit();
	}
	
	public void ajouter() {
		if(etatCourant == etatTournee) {
			etatCourant = etatAjouter1;
		}
		
	}
	
	public void supprimer() {
		if(etatCourant == etatTournee) {
			etatCourant = etatSupprimer;	
		}
	}
	
	public void echanger() {
		if(etatCourant == etatTournee) {
			//etatCourant = etatEchanger;
		}
	}
	
	public void genererFeuilleDeRoute(String fichier) throws Exception {
		etatCourant.genererFeuilleDeRoute(fichier,tournee);
	}

	public Tournee getTournee() {
		return tournee;
	}


	public Plan getPlan() {
		// TODO Auto-generated method stub
		return this.plan;
		
	}

}
