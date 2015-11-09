package controleur;

import modele.Tournee;
import tsp.Graphe;
import util.Constants;
import vue.Fenetre;

public class EtatLivraison extends EtatPlan {

	public EtatLivraison() {
		// TODO Auto-generated constructor stub
	}
	
	public void calculerTournee(Fenetre fenetre, Tournee tournee) {
		tournee.calculerTournee();
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_TOURNEE);
	}

}
