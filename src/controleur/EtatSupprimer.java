package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatSupprimer extends EtatTournee {

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
		if(fenetre.confirmerSuppression()) {
			//TODO
		/*CmdSupprimer cmd = null;
		listeCmd.ajoute(cmd);
		cmd.doCmd();*/
		}
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);

	}
	
	public void clicDroit(Fenetre fenetre) {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}
	
	
}
