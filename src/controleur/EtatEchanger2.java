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
			/*CmdEchanger cmd= new CmdEchanger(adresse1, adresse2, tournee);
			listeCmd.ajoute(cmd);
			cmd.doCmd();*/
			//TODO verifier que les 2 adresses soient des livraisons
			Controleur.setEtatCourant(Controleur.etatTournee);
			fenetre.ecrireLog(Constants.LOGS_DEFAULT);
		}
		
		// annuler l'ajout
		public void clicDroit(Fenetre fenetre) {
			adresse1=null;
			Controleur.setEtatCourant(Controleur.etatTournee);
			fenetre.ecrireLog(Constants.LOGS_DEFAULT);
		}

}
