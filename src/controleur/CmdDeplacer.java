package controleur;

import modele.Livraison;
import modele.Plan;

public class CmdDeplacer implements Commande {
    private Plan plan;
    private Livraison livraison1;
    private Livraison livraison2;
                                                   
    public CmdDeplacer(Plan p, Livraison l1, Livraison l2) {
        this.plan = p;
        this.livraison1 = l1;
        this.livraison2 = l2;
    }

    @Override
    public void doCmd() {
        plan.getTournee().supprimerLivraison(livraison1);
        plan.getTournee().ajouterLivraison(livraison1, livraison2);
    }

    @Override
    public void undoCmd() {
        // TODO Implement this method
    }
}
