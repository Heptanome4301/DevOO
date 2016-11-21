package controleur;

import modele.Livraison;
import modele.Tournee;

/**
 * Cette commande permet la suppression d'une livraison d'une tournee.
 */
public class CmdSupprimer implements Commande {
	
        /**
         * La tournee a modifier.
         */
	private Tournee tournee;
        /**
         * La livraison a supprimer.
         */
	private Livraison livraison;
        /**
         * La livraison suivant celle qui a ete supprime. (utile pour le undoCmd())
         */
	private Livraison livraisonFollow;
        
        /**
         * Le constructeur de la commande.
         * @param livraison la livraison a supprimer.
         * @param tournee la tournee dans laquelle supprimer la tournee.
         */
	public CmdSupprimer(Livraison livraison,  Tournee tournee) {
		this.tournee=tournee;
		this.livraison=livraison;
		this.livraisonFollow=tournee.getFollowingLivraison(livraison);
	}
        
        /**
         * Effectue la commande : supprime la livraison de la tournee.
         */
	@Override
	public void doCmd() {
		tournee.supprimerLivraison(livraison);
	}
        
        /**
         * Effectue la commande inverse : ajoute la livraison supprimee avant la livraison qui la suivait.
         */
	@Override
	public void undoCmd() {
		tournee.ajouterLivraison(livraison, livraisonFollow);
	}

}
