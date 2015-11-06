package controleur;

import modele.Adresse;
import modele.Tournee;

public class CmdEchanger implements Commande {
	
	private Adresse adresse1, adresse2;
	private Tournee tournee;
	
	public CmdEchanger(Adresse adresse1, Adresse adresse2, Tournee tournee) {
		this.adresse1=adresse1;
		this.adresse2=adresse2;
		this.tournee=tournee;
	}

	@Override
	public void doCmd() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void undoCmd() throws Exception {
		// TODO Auto-generated method stub

	}

}
