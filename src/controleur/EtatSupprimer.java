package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatSupprimer extends EtatTournee {

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
		if(adresse.estAssocierAvecLivraison()) {
			if(fenetre.confirmerSuppression()) {
				CmdSupprimer cmd = new CmdSupprimer(adresse.getLivraison(), tournee);
				listeCmd.ajoute(cmd);
				cmd.doCmd();
				//TODO message de confirmation?
			}
			Controleur.setEtatCourant(Controleur.etatTournee);
		}
		else {
			//TODO msg erreur ou pas
		}
		

	}
	
	public void clicDroit(Fenetre fenetre) {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}
	
	
}
