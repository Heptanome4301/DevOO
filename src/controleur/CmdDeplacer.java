package controleur;

import modele.Livraison;
import modele.Plan;
import modele.Tournee;

/**
 * Cette commande permet le deplacement d'une livraison dans l'ordre de la tournee.
 */
public class CmdDeplacer implements Commande {
    /**
     * La tournee a modifier.
     */
    private Tournee tournee;
    /**
     * La livraison a deplacer.
     */
    private Livraison livraisonDeplacee;
    /**
     * La livraison qui suit celle a deplacer (utile pour la commande inverse).
     */
    private Livraison livraisonSuivantOrigine;
    /**
     * La livraison avant laquelle placer livraisonDeplacee.
     */
    private Livraison livraisonCible;
    
    /**
     * Constructeur de la commande deplacer. 
     * @param tournee la tournee a modifier.
     * @param livraisonDeplacee la livraison a deplacer.
     * @param livraisonCible la livraison qui suivra l'autre suivant celle deplacee.
     */
    public CmdDeplacer(Tournee tournee, Livraison l1, Livraison l2) {
        this.tournee = tournee;
        this.livraisonDeplacee = l1;
        this.livraisonCible = l2;
        this.livraisonSuivantOrigine = tournee.getFollowingLivraison(livraisonDeplacee);
    }
    
    /**
     * Effectue la commande de deplacement.
     */
    @Override
    public void doCmd() {
        tournee.supprimerLivraison(livraisonDeplacee);
        tournee.ajouterLivraison(livraisonDeplacee, livraisonCible);

    }
    
    /**
     * Effectue la commande inverse, c'est-a-dire delacer la livraison vers sa position initiale.
     */
    @Override
    public void undoCmd() {
        tournee.supprimerLivraison(livraisonDeplacee);
        tournee.ajouterLivraison(livraisonDeplacee, livraisonSuivantOrigine);
    }
}
