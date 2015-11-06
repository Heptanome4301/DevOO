package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;

public class EtatSupprimer extends EtatIni {

	
	/*public void clicNoeud(Adresse adresse,Plan plan,Tournee tournee, ListeDeCmd listeCmd) throws Exception {
		//TODO
		CmdSupprimer cmd = null;
		listeCmd.ajoute(cmd);
		cmd.doCmd();
		Controleur.setEtatCourant(Controleur.etatTournee);
	}*/
	
	public void clicDroit() {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
	}
	
	
}
