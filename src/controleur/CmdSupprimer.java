package controleur;

import modele.Livraison;
import modele.Tournee;

public class CmdSupprimer implements Commande {
	
	private Tournee tournee;
	private Livraison livraison;
	private Livraison livraisonFollow;

	public CmdSupprimer(Livraison livraison,  Tournee tournee) {
		this.tournee=tournee;
		this.livraison=livraison;
		this.livraisonFollow=tournee.getFollowingLivraison(livraison);
	}

	@Override
	public void doCmd() {
		tournee.supprimerLivraison(livraison);
	}

	@Override
	public void undoCmd() {
		tournee.ajouterLivraison(livraison, livraisonFollow);
	}

}
