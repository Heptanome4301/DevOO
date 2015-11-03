package controleur;

import modele.Livraison;
import modele.Tournee
;
public class CmdSupprimer implements Commande {
	
	private Tournee tournee;
	private Livraison livraison;

	public CmdSupprimer(Livraison livraison,Tournee tournee) {
		this.tournee=tournee;
		this.livraison=livraison;
	}

	@Override
	public void doCmd() throws Exception {
		tournee.supprimerLivraison(livraison);
		//TODO
	}

	@Override
	public void undoCmd() throws Exception {
		//TODO
	}

}
