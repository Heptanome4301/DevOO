package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatAjouter2 extends EtatTournee {

	// l'adresse selectionnée lors de l'état 1 
	private Adresse adresse;
	
	public EtatAjouter2() {
		adresse=null;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse=adresse;
	}
	
	public void clicNoeud(Fenetre fenetre, Adresse followAdresse, Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
		//TODO la, on a pas trop compris ce qui a été tenté. Comment on transforme ces adresses en livraisons?

		/*CmdAjouter cmd = new CmdAjouter(tournee, adresse, followAdresse);
		listeCmd.ajoute(cmd);
		cmd.doCmd();*/
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}
	
	// annuler l'ajout
	public void clicDroit(Fenetre fenetre) {
		adresse=null;
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}

}
