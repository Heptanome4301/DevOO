package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatEchanger2 extends EtatTournee {

	// l'adresse selectionnée lors de l'état 1 
		private Adresse adresse1;
		
		public EtatEchanger2() {
			adresse1=null;
		}
		
		public void setAdresse(Adresse adresse) {
			this.adresse1=adresse;
		}
		
		public void clicNoeud(Fenetre fenetre, Adresse adresse2, Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
			if(adresse2.estAssocierAvecLivraison()) {
				CmdEchanger cmd= new CmdEchanger(adresse1.getLivraison(), adresse2.getLivraison(), tournee);
				listeCmd.ajoute(cmd);
				cmd.doCmd();
				tournee.echangerLivraison(adresse1.getLivraison(), adresse2.getLivraison());
				Controleur.setEtatCourant(Controleur.etatTournee);
				fenetre.ecrireLog(Constants.LOGS_DEFAULT);
			}
			else {
				//TODO message d'erreur -> on reste dans l'état dans etatTournee2
				fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
				fenetre.ecrireLog(Constants.LOGS_ECHANGER2);
			}
		}
		
		// annuler l'ajout
		public void clicDroit(Fenetre fenetre) {
			adresse1=null;
			Controleur.setEtatCourant(Controleur.etatTournee);
			fenetre.ecrireLog(Constants.LOGS_DEFAULT);
		}

}
