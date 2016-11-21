package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;

import util.Constants;

import vue.Fenetre;

public class EtatDeplacer2 extends EtatTournee {
    
    /**
     * La livraison récupérée par l'EtatDeplacer1, c'est à dire la livraison que l'on souhaite déplacer.
     */
    private Livraison livraison1;

    public EtatDeplacer2() {
    }


    @Override
    public void clicDroit(Fenetre fenetre) {
        Controleur.setEtatCourant(Controleur.etatTournee);
        fenetre.ecrireLog(Constants.LOGS_DEFAULT);
    }

    @Override
    public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, ListeDeCmd historique) {
        if(adresse.estAssocierAvecLivraison()) {
            Livraison livraison2 = adresse.getLivraison();
            
            CmdDeplacer cmd = new CmdDeplacer(plan.getTournee(), livraison1, livraison2);
            cmd.doCmd();
            historique.ajoute(cmd);
        }
        else{            
            fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
        }

        Controleur.setEtatCourant(Controleur.etatTournee);
        fenetre.ecrireLog(Constants.LOGS_DEFAULT);
    }
    
    /**
     * Mutateur de l'attribut livraison1
     * @param livraison1
     */
    public void setLivraison(Livraison livraison1) {
        this.livraison1 = livraison1;
    }
}
