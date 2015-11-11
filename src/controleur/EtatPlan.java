package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;
import xml.ExceptionXML;
import xml.OuvreurDeFichiersXML;

import java.io.File;

public class EtatPlan extends EtatIni {

	public EtatPlan() {
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public void chargerLivraisons(Fenetre fenetre, Plan plan,Controleur c) {
		File xml = c.getFile();
		if(xml == null) return;
		try {
			fenetre.desactiverBuotonsModification();
			plan.chargerLivraison(xml);
			Controleur.setEtatCourant(Controleur.etatLivraison);
			fenetre.ecrireLog(Constants.LOGS_LIVRAISON);
		} catch (Exception e) {
			fenetre.signalerErreur(e.getMessage());
			Controleur.setEtatCourant(Controleur.etatPlan);
		}
	}
}
