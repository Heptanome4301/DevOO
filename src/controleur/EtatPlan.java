package controleur;

import modele.Plan;
import tsp.Graphe;

public class EtatPlan extends EtatIni {

	public EtatPlan() {
		// TODO Auto-generated constructor stub
	}

	public Graphe chargerLivraisons(Plan plan) throws Exception {
		// TODO Auto-generated method stub
		plan.chargerLivraison();
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return null;
	}
	
}
