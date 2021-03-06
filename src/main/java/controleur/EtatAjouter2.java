package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

import util.Constants;

import vue.Fenetre;

public class EtatAjouter2 extends EtatTournee {
        
        /**
         * l'adresse récupérée par l'EtatAjouter1, c'est à dire l'adresse qui recevra la nouvelle livraison
         */
	private Adresse adresse;
        /**
         * Attribut pour une génération automatique des id de livraison créées
         */
        private static int generatedId = 100;
	
	public EtatAjouter2() {
		adresse=null;
	}
	
        /**
         * Mutateur de l'attribut adresse
         * @param adresse
         */
	public void setAdresse(Adresse adresse) {
		this.adresse=adresse;
	}
	

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse followingAdresse, Plan plan, ListeDeCmd listeCmd) {
                if( !followingAdresse.estAssocierAvecLivraison()){
                    fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);                   
                    Controleur.setEtatCourant(Controleur.etatTournee);
                }
                else{
                    Livraison followingLivraison = followingAdresse.getLivraison();
                    Livraison newLivraison  = new Livraison(generatedId++, adresse, followingLivraison.getFenetreLivraison());
                    CmdAjouter cmd = new CmdAjouter(plan.getTournee(), newLivraison, followingLivraison);
                    listeCmd.ajoute(cmd);
                    cmd.doCmd();                   
                    Controleur.setEtatCourant(Controleur.etatTournee);
                    fenetre.ecrireLog(Constants.LOGS_DEFAULT);
                }		
	}
	
        @Override
	public void clicDroit(Fenetre fenetre) {
		adresse=null;
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}
}
