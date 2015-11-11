package controleur;

import javax.swing.JFileChooser;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatTournee extends EtatLivraison {

	public EtatTournee() {
		// TODO Auto-generated constructor stub
	}
		

	@Override
	public void genererFeuilleDeRoute(Fenetre fenetre, Tournee tournee) {
		try {
			String fichier = obtenirFichier();
			tournee.feuilleDeRoute(fichier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undo(Fenetre fenetre, ListeDeCmd listeCmd) {
		try {
			listeCmd.undo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void redo(Fenetre fenetre, ListeDeCmd listeCmd) {
		try {
			listeCmd.redo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ajouter(Fenetre fenetre) {
		Controleur.setEtatCourant(Controleur.etatAjouter1);
		fenetre.ecrireLog(Constants.LOGS_AJOUTER1);
	}

	@Override
	public void supprimer(Fenetre fenetre) {
		Controleur.setEtatCourant(Controleur.etatSupprimer);
		fenetre.ecrireLog(Constants.LOGS_SUPPRIMER);
	}

	@Override
	public void echanger(Fenetre fenetre) {
		Controleur.setEtatCourant(Controleur.etatEchanger1);
		fenetre.ecrireLog(Constants.LOGS_ECHANGER1);
	}

	private String obtenirFichier() {
		JFileChooser fc = new JFileChooser();
		int result = fc.showSaveDialog(null);
		String fichier = "";
		if (result == JFileChooser.APPROVE_OPTION) {
			fichier = fc.getSelectedFile().getAbsolutePath();
		}
		return fichier;
	}

	@Override
	public void clicListLivraisons(Fenetre fenetre, Livraison livraison) {
	
		//fenetre.updateSelection(livraison.getAdresse(),false);
	
	}
	
        @Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan,ListeDeCmd historique)
	{
		//fenetre.updateSelection(adresse,true);
	}
        
        @Override
        public void deplacer(Fenetre fenetre){
            Controleur.setEtatCourant(Controleur.etatDeplacer1);
            fenetre.ecrireLog(Constants.LOGS_DEPLACER1);
        }

}
