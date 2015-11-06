package controleur;

import modele.Adresse;
import modele.Tournee;

public class EtatEchanger2 extends EtatIni {

	// l'adresse selectionnée lors de l'état 1 
		private Adresse adresse1;
		
		public EtatEchanger2() {
			adresse1=null;
		}
		
		public void setAdresse(Adresse adresse) {
			this.adresse1=adresse;
		}
		
		public void clicNoeud(Adresse adresse2,Tournee tournee, ListeDeCmd listeCmd) {
			/*CmdEchanger cmd= new CmdEchanger(adresse1, adresse2, tournee);
			listeCmd.ajoute(cmd);
			cmd.doCmd();*/
			//TODO
			Controleur.setEtatCourant(Controleur.etatTournee);
		}
		
		// annuler l'ajout
		public void clicDroit() {
			adresse1=null;
			Controleur.setEtatCourant(Controleur.etatTournee);
		}

}
