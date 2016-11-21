package controleur;

public interface Commande {
        /**
         * Effectue la commande.
         */
	void doCmd();
        /**
         * Effectue la commande inverse (possibilite de undo).
         */
	void undoCmd();
}
