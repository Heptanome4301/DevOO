package controleur;

import modele.Livraison;
import modele.Tournee;
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

	@Override
	public void clicListLivraisons(Fenetre fenetre, Livraison livraison) {
		fenetre.getVue().selection(livraison.getAdresse().getId());
		fenetre.ecrireInfos(livraison.toString());
	}
	

}
