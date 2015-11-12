package controleur;

import modele.Livraison;
import modele.Tournee;

/**
 * Cette classe permet l'ajout d'une livraison (ou la supression lorsqu'on veut undo).
 */
public class CmdAjouter implements Commande {
	/**
         * La tournee concernee par la modification.
         */
	Tournee tournee;
        /**
         * La livraison a ajouter a la tournee.
         */
	Livraison livraisonAdd;
        /**
         * La livraison qui suivra celle ajouter.
         */
	Livraison livraisonFollow;
        
        /**
         * Constructeur de la commande.
         * @param tournee la Tournee a modifier.
         * @param lAdd la Livraison a ajouter.
         * @param lFollow la Livraison qui suivra celle ajouter.
         */
	public CmdAjouter(Tournee tournee, Livraison lAdd, Livraison lFollow) {
		this.tournee=tournee;
		this.livraisonFollow=lFollow;
		this.livraisonAdd=lAdd;
	}

        /**
         * Ajoute la livraison a la Tournee.
         */
	@Override
	public void doCmd() {
		tournee.ajouterLivraison(livraisonAdd, livraisonFollow);
	}
        
        /**
         * Effectue l'inverse, i.e. supprime la livraison qui a ete ajoutee.
         */
	@Override
	public void undoCmd() {
		tournee.supprimerLivraison(livraisonAdd);
	}

}
