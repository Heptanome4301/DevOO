package controleur;

import modele.Livraison;
import modele.Plan;

public class CmdDeplacer implements Commande {
    private Plan plan;
    private Livraison livraisonDeplacee;
    private Livraison livraisonSuivantOrigine;
    private Livraison livraisonCible;
                                                   
    public CmdDeplacer(Plan p, Livraison l1, Livraison l2) {
        this.plan = p;
        this.livraisonDeplacee = l1;
        this.livraisonCible = l2;
        this.livraisonSuivantOrigine = plan.getTournee().getFollowingLivraison(livraisonDeplacee);
    }

    @Override
    public void doCmd() {
        plan.getTournee().supprimerLivraison(livraisonDeplacee);
        plan.getTournee().ajouterLivraison(livraisonDeplacee, livraisonCible);

    }

    @Override
    public void undoCmd() {
        plan.getTournee().supprimerLivraison(livraisonDeplacee);
        plan.getTournee().ajouterLivraison(livraisonDeplacee, livraisonSuivantOrigine);
    }
}
