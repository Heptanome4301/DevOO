package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatEchanger1 extends EtatTournee {
		
	public EtatEchanger1() {
		// TODO Auto-generated constructor stub
	}
	
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
		if(adresse.estAssocierAvecLivraison()) {
			Controleur.etatEchanger2.setAdresse(adresse);
			Controleur.setEtatCourant(Controleur.etatEchanger2);
			fenetre.ecrireLog(Constants.LOGS_ECHANGER2);
		}
		else {
			fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
			fenetre.ecrireLog(Constants.LOGS_ECHANGER1);
		}
		
	}
	
	public void clicDroit(Fenetre fenetre) {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}
}
