package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;

public class EtatSupprimer extends EtatIni {

	
	public void clicNoeud(Adresse adresse,Plan plan,Tournee tournee, ListeDeCmd listeCmd) {
		/*CmdSupprimer cmd = CmdSupprimer(adresse,tournee);
		listeCmd.ajoute(cmd);
		cmd.doCmd();*/
		//TODO get livraison a partir d'une adresse ?
	}
	
	public void clicDroit() {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
	}
	
	
}
