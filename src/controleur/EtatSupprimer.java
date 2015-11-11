package controleur;

import modele.Adresse;
import modele.Plan;
import util.Constants;
import vue.Fenetre;

public class EtatSupprimer extends EtatTournee {

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, ListeDeCmd listeCmd) {
		if(adresse.estAssocierAvecLivraison()) {
			if(fenetre.confirmerSuppression()) {
				CmdSupprimer cmd = new CmdSupprimer(adresse.getLivraison(), plan.getTournee());
				listeCmd.ajoute(cmd);
				cmd.doCmd();
				//TODO message de confirmation?
			}
			Controleur.setEtatCourant(Controleur.etatTournee);
			fenetre.ecrireLog(Constants.LOGS_DEFAULT);
		}
		else {
			fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
		}
		

	}
	
        @Override
	public void clicDroit(Fenetre fenetre) {
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}
	
	
}
