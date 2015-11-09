package controleur;

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

	public void chargerLivraisons(Fenetre fenetre, Plan plan){
		File xml;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			plan.chargerLivraison(xml);
			Controleur.setEtatCourant(Controleur.etatLivraison);
			fenetre.ecrireLog(Constants.LOGS_LIVRAISON);
		} catch (Exception e){
			fenetre.signalerErreur(e.getMessage());
			Controleur.setEtatCourant(Controleur.etatPlan);
		}
	}
	//TODO implémenter clicNoeuds : la mise à jour de l'affichage doit passer par le controlleur
}
