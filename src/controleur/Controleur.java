package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import tsp.Graphe;
import util.Constants;
import vue.Fenetre;

import javax.swing.*;

public class Controleur {
	
	private ListeDeCmd historique;
	private static Etat etatCourant;
	private Plan plan;
	private Tournee tournee;
	private Fenetre fenetre;
	
	protected static final EtatIni etatIni = new EtatIni();
	protected static final EtatAjouter1 etatAjouter1 = new EtatAjouter1();
	protected static final EtatAjouter2 etatAjouter2 = new EtatAjouter2();
	protected static final EtatTournee etatTournee = new EtatTournee();
	protected static final EtatPlan etatPlan = new EtatPlan();
	protected static final EtatLivraison etatLivraison = new EtatLivraison();
	protected static final EtatSupprimer etatSupprimer = new EtatSupprimer();
	protected static final EtatEchanger1 etatEchanger1 = new EtatEchanger1();
	protected static final EtatEchanger2 etatEchanger2 = new EtatEchanger2();
	
	protected static void setEtatCourant(Etat etat) { etatCourant = etat; }
	

	public Controleur() {
		this.historique = new ListeDeCmd();
		Controleur.etatCourant = etatIni;
		this.plan = new Plan();
		this.fenetre = new Fenetre(this, plan);
		this.tournee = null;
	}
	
	/**
	 * Annule la derni�re modification effectu�e sur la tourn�e (ajout, suppression ou �change de livraisons)
	 */
	public void undo() {
			etatCourant.undo(fenetre, historique);
	}
	/**
	 * R�effectue la derni�re action annul�e
	 */
	public void redo() {
			etatCourant.redo(fenetre, historique);
	}
	
	
	public Graphe chargerPlan()  {
		plan.clear();
		tournee = null;

		etatCourant.chargerPlan(fenetre, plan);

		this.calculEchelle();

		return null;
		//fixme pourquoi ça doit renvoyer un graphe??
	}
	
	private void calculEchelle() {
		double echelle1 = ( (double) fenetre.getSizeView().getWidth() - 2 * Constants.MARGIN_VUE_GRAPHE ) / (plan.getXMax() + Constants.RAYON_NOEUD);
		double echelle2 = ( (double) fenetre.getSizeView().getHeight() - 2 * Constants.MARGIN_VUE_GRAPHE ) / (plan.getYMax() + Constants.RAYON_NOEUD);
		if ( echelle1 < echelle2)
		{
			fenetre.setEchelle(echelle1);	
		} else {
			fenetre.setEchelle(echelle2);
		}
				
	}


	public Graphe chargerLivraisons() {
	    tournee = null;

		tournee = etatCourant.chargerLivraisons(fenetre, plan);

	    return null;
	    //TODO why?
	}
	
	public Graphe calculerTournee() {
		etatCourant.calculerTournee(fenetre, tournee);
		return null;
	}
	
	public void clicNoeud(int idAdresse) {
			etatCourant.clicNoeud(fenetre, plan.getAdresse(idAdresse),plan,tournee, historique);
	}
	
	public void clicDroit() {
		etatCourant.clicDroit(fenetre);
	}
	
	public void ajouter() {
		etatCourant.ajouter(fenetre);
	}
	
	public void supprimer() {
		etatCourant.supprimer(fenetre);
	}
	
	public void echanger() {
		etatCourant.echanger(fenetre);
	}

	private String obtenirFichier(){
		JFileChooser fc = new JFileChooser();
		int result = fc.showSaveDialog(null);
		String fichier = "";
		if(result == JFileChooser.APPROVE_OPTION){
			fichier = fc.getSelectedFile().getAbsolutePath();
		}
		return fichier;
		//todo v�rifier si on apelle le filechooser dans le controlleur ou la vue
	}
	public void genererFeuilleDeRoute(){
		String fichier;
		if(!(fichier = obtenirFichier()).equals("")) // si un fichir a �t� selectionn�
                etatCourant.genererFeuilleDeRoute(fenetre, fichier, tournee);
        }

	public Tournee getTournee() {
		return tournee;
	}


	public Plan getPlan() {
		// TODO Auto-generated method stub
		return this.plan;
		
	}


	public void afficheInfos(int idAdresse) {
		if (idAdresse < 0) {
			fenetre.ecrireInfos("");
		} else {
			Adresse a = plan.getAdresse(idAdresse);
			fenetre.ecrireInfos(a.toString());
		}
	}

}
