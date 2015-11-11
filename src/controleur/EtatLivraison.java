package controleur;

import java.io.File;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;
import xml.ExceptionXML;
import xml.OuvreurDeFichiersXML;

public class EtatLivraison extends EtatPlan {

	public EtatLivraison() {
		// TODO Auto-generated constructor stub
	}

	public void calculerTournee(Fenetre fenetre, Tournee tournee) {
		tournee.calculerTournee();
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_TOURNEE);
		fenetre.activerBuotonsModification();
	}

	@Override
	public void clicListLivraisons(Fenetre fenetre, Livraison livraison) {

	}
	

}
