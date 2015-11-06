package controleur;

import modele.Livraison;
import modele.Tournee;

public class CmdEchanger implements Commande {
	
	private Livraison livraison1, livraison2;
	private Tournee tournee;
	
	public CmdEchanger(Livraison livraison1, Livraison livraison2, Tournee tournee) {
		this.livraison1=livraison1;
		this.livraison2=livraison2;
		this.tournee=tournee;
	}

	@Override
	public void doCmd() {
		tournee.echangerLivraison(livraison1, livraison2);
	}

	@Override
	public void undoCmd() {
		tournee.echangerLivraison(livraison1, livraison2);
	}

}
