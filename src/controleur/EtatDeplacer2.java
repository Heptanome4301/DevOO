package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;

import util.Constants;

import vue.Fenetre;

public class EtatDeplacer2 extends EtatTournee {
    
    
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
            
            CmdDeplacer cmd = new CmdDeplacer(plan, livraison1, livraison2);
            cmd.doCmd();
            historique.ajoute(cmd);
        }
        else{            
            fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
        }

        Controleur.setEtatCourant(Controleur.etatTournee);
        fenetre.ecrireLog(Constants.LOGS_DEFAULT);
    }
    
    public void setLivraison(Livraison livraison1) {
        this.livraison1 = livraison1;
    }
}
