package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Tournee;

public class EtatAjouter2 extends EtatIni {

	// l'adresse selectionnée lors de l'état 1 
	private Adresse adresse;
	
	public EtatAjouter2() {
		adresse=null;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse=adresse;
	}
	
	public void clicNoeud(Adresse followAdresse,Tournee tournee, ListeDeCmd listeCmd) {
		//TODO
		/*CmdAjouter cmd = new CmdAjouter(tournee, adresse, followAdresse);
		listeCmd.ajoute(cmd);
		cmd.doCmd();*/
		Controleur.setEtatCourant(Controleur.etatTournee);
	}
	
	// annuler l'ajout
	public void clicDroit() {
		adresse=null;
		Controleur.setEtatCourant(Controleur.etatTournee);
	}

}
