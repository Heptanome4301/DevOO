package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;
import xml.OuvreurDeFichiersXML;

import java.io.File;

public class EtatPlan extends EtatIni {

	public EtatPlan() {
		// TODO Auto-generated constructor stub
	}

	public Tournee chargerLivraisons(Fenetre fenetre, Plan plan){
		File xml;
		Tournee resultat = null;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			resultat = plan.chargerLivraison(xml);
			Controleur.setEtatCourant(Controleur.etatLivraison);
			fenetre.ecrireLog(Constants.LOGS_LIVRAISON);
		} catch (Exception e){
			fenetre.signalerErreur(e.getMessage());
			Controleur.setEtatCourant(Controleur.etatPlan);
		}
		return resultat; //TODO pourquoi avoir une tournée dans le controlleur vu qu'on en a déjà une dans le plan?
	}
	//TODO implémenter clicNoeuds : la mise à jour de l'affichage doit passer par le controlleur
	
	public void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan, Tournee tournee, ListeDeCmd listeCmd){
		
	}
}
