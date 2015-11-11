package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;

import util.Constants;

import vue.Fenetre;

public class EtatDeplacer1 extends EtatTournee {
    public EtatDeplacer1() {
    }


    @Override
    public void clicDroit(Fenetre fenetre) {
        Controleur.setEtatCourant(Controleur.etatTournee);
        fenetre.ecrireLog(Constants.LOGS_DEFAULT);
    }

    @Override
    public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, ListeDeCmd historique) {
        if(adresse.estAssocierAvecLivraison()) {
            Controleur.etatDeplacer2.setLivraison(adresse.getLivraison());
            Controleur.setEtatCourant(Controleur.etatDeplacer2);
            fenetre.ecrireLog(Constants.LOGS_DEPLACER2);
        }
        else{
            Controleur.setEtatCourant(Controleur.etatTournee);
            fenetre.ecrireLog(Constants.LOGS_DEFAULT);            
            fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
        }
    }
}
