package controleur;

import modele.Adresse;
import modele.Tournee;

public class CmdAjouter implements Commande {
	
	Tournee tournee;
	Adresse addAdresse;
	Adresse followAdresse;

	public CmdAjouter(Tournee tournee, Adresse addAdresse, Adresse followAdresse) {
		this.tournee=tournee;
		this.followAdresse=followAdresse;
		this.addAdresse=addAdresse;
	}

	@Override
	public void doCmd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undoCmd() {
		// TODO Auto-generated method stub

	}

}
