package controleur;

import modele.Livraison;
import modele.Tournee;

/**
 * Cette commande permet d'echanger la place de deux liraisons dans une tournee.
 */
public class CmdEchanger implements Commande {
	
        /**
         * La premiere livraison.
         */
	private Livraison livraison1;
        /**
         * La deuxieme livraison.
         */
        private Livraison livraison2;
        /**
         * La tournee a modifier.
         */
	private Tournee tournee;
	
        /**
         * Le constructeur de la commande
         * @param livraison1 la premiere livraison.
         * @param livraison2 la deuxieme livraison.
         * @param tournee la tournee a modifier.
         */
	public CmdEchanger(Livraison livraison1, Livraison livraison2, Tournee tournee) {
		this.livraison1=livraison1;
		this.livraison2=livraison2;
		this.tournee=tournee;
	}
        
        /**
         * Effectue la commande, c'est-a-dire echange la place des deux livraisons dans la tournee.
         */
	@Override
	public void doCmd() {
		tournee.echangerLivraison(livraison1, livraison2);
	}
        
        /**
         * Effectue la commande inverse (dans ce cas la, elle-meme) : echange la place des deux livraisons dans la
         * tournee. Dans notre cas doCmd() et undoCmd() sont equivalent.
         */
	@Override
	public void undoCmd() {
		tournee.echangerLivraison(livraison1, livraison2);
	}

}
