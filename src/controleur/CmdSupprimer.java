package controleur;

import modele.Livraison;
import modele.Tournee
;
public class CmdSupprimer implements Commande {
	
	private Tournee tournee;
	private Livraison livraisonAdd;
	private Livraison livraisonFollow;

	public CmdSupprimer(Livraison livraisonAdd, Livraison livraisonFollow, Tournee tournee) {
		this.tournee=tournee;
		this.livraisonAdd=livraisonAdd;
		this.livraisonFollow=livraisonFollow;
	}

	@Override
	public void doCmd() {
		tournee.supprimerLivraison(livraisonAdd);
	}

	@Override
	public void undoCmd() {
		tournee.ajouterLivraison(livraisonAdd, livraisonFollow);
	}

}
