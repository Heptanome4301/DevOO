package controleur;

import modele.Livraison;
import modele.Tournee;

public class CmdAjouter implements Commande {
	
	Tournee tournee;
	Livraison livraisonAdd;
	Livraison livraisonFollow;

	public CmdAjouter(Tournee tournee, Livraison lAdd, Livraison lFollow) {
		this.tournee=tournee;
		this.livraisonFollow=lFollow;
		this.livraisonAdd=lAdd;
	}

	@Override
	public void doCmd() {
		tournee.ajouterLivraison(livraisonAdd, livraisonFollow);
	}

	@Override
	public void undoCmd() {
		tournee.supprimerLivraison(livraisonAdd);
	}

}
