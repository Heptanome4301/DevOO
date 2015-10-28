package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ExceptionXML;
import modele.Plan;
import modele.Tournee;

public class Controleur {
	
	private ListeDeCmd historique;
	private Etat etatCourant;
	private Plan plan;
	private Tournee tournee;

	public Tournee getTournee() {
		return tournee;
	}

	public Controleur() {
		historique = new ListeDeCmd();
		etatCourant = new EtatDefaut();
		plan = new Plan();
		tournee = null;
	}
	
	public void undo() {
		etatCourant.undo(historique);
	}
	
	public void redo() {
		etatCourant.redo(historique);
	}
	
	public void chargerPlan() {
		//TODO
		try {
			plan.chargerPlan();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionXML e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void chargerLivraisons() {
		try {
			tournee = plan.chargerLivraison();
		} catch (ParserConfigurationException | SAXException | IOException
				| ExceptionXML e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clicNoeud() {
		//TODO
	}
	
	public void clicDroit() {
		//TODO
	}
	
	public void ajouter() {
		etatCourant = new EtatAjouter1();
	}
	
	public void supprimer() {
		etatCourant = new EtatSupprimer();
	}
	
	public void genererFeuilleDeRoute() {
		etatCourant.genererFeuilleDeRoute();
		//prkoi pas tournee.feuilleDeRoute("out.txt");
	}

}
