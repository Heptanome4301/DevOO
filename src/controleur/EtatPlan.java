package controleur;

import java.io.File;

import modele.Plan;
import modele.Tournee;

public class EtatPlan extends EtatIni {

	public EtatPlan() {
		// TODO Auto-generated constructor stub
	}

	public Tournee chargerLivraisons(Plan plan, File file) throws Exception {
		// TODO Auto-generated method stub
//		plan.chargerLivraison();
		// plan.chargerLivraison(file);
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return null;
	}
	
}
