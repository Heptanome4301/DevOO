package controleur;

import java.io.File;

import modele.Plan;
import modele.Tournee;
import tsp.Graphe;

public class EtatLivraison extends EtatIni {

	public EtatLivraison() {
		// TODO Auto-generated constructor stub
	}
	
	public Graphe calculerTournee(Tournee tournee) {
		// TODO Auto-generated method stub
		tournee.calculerTournee();
		Controleur.setEtatCourant(Controleur.etatTournee);
		return null;
	}
	
	public Tournee chargerLivraisons(Plan plan,File file) throws Exception {
		// TODO Auto-generated method stub
		//plan.chargerLivraison();
		// plan.chargerLivraison(file);
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return null;
	}
	
}
