package controleur;

import java.awt.Point;
import java.io.File;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;
import xml.ExceptionXML;
import xml.OuvreurDeFichiersXML;

public class Controleur {

	private ListeDeCmd historique;
	private static Etat etatCourant;
	private Plan plan;
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
        protected static final EtatDeplacer1 etatDeplacer1 = new EtatDeplacer1();
        protected static final EtatDeplacer2 etatDeplacer2 = new EtatDeplacer2();

	protected static void setEtatCourant(Etat etat) {
		etatCourant = etat;
	}

	public Controleur() {
		this.historique = new ListeDeCmd();
		Controleur.etatCourant = etatIni;
		this.plan = new Plan();
		this.fenetre = new Fenetre(this, plan);
	}

	/**
	 * Annule la derniï¿½re modification effectuï¿½e sur la tournï¿½e (ajout,
	 * suppression ou ï¿½change de livraisons)
	 */
	public void undo() {
		etatCourant.undo(fenetre, historique);
	}

	/**
	 * Rï¿½effectue la derniï¿½re action annulï¿½e
	 */
	public void redo() {
		etatCourant.redo(fenetre, historique);
	}

	private void calculEchelle() {
		double echelle1 = ((double) fenetre.getVue().getWidth() - 2 * Constants.MARGIN_VUE_GRAPHE)
				/ (plan.getXMax() + Constants.RAYON_NOEUD);
		double echelle2 = ((double) fenetre.getVue().getHeight() - 2 * Constants.MARGIN_VUE_GRAPHE)
				/ (plan.getYMax() + Constants.RAYON_NOEUD);
		if (echelle1 < echelle2) {
			fenetre.setEchelle(echelle1);
		} else {
			fenetre.setEchelle(echelle2);
		}

	}
	
	
	protected File getFile(){
		File xml = null;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
		} catch (ExceptionXML e) {
			fenetre.signalerErreur(e.getMessage());
		}
		return xml;
	}

	public void chargerPlan() {
		File xml = getFile();
		etatCourant.chargerPlan(fenetre, plan,xml);
		this.calculEchelle();
	}
	
	
	public void chargerLivraisons() {
		etatCourant.chargerLivraisons(fenetre, plan,this);
	}

	
	public void calculerTournee() {
		etatCourant.calculerTournee(fenetre, plan.getTournee());
	}

	public void clicNoeud(Point p) {
		Adresse adresse = plan.getAdresseByCoord(p);
		fenetre.updateSelection(adresse,true);
		etatCourant.clicNoeud(fenetre, adresse, plan,historique);

	}

	public void clicDroit() {
		etatCourant.clicDroit(fenetre);
	}

	public void clicListLivraisons(Livraison livraison) {

		fenetre.updateSelection(livraison.getAdresse(),false);
		etatCourant.clicNoeud(fenetre, livraison.getAdresse(), plan, historique);

		//etatCourant.clicListLivraisons(fenetre, livraison);
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

	public void genererFeuilleDeRoute() {
		etatCourant.genererFeuilleDeRoute(fenetre, plan.getTournee());
	}

	public Tournee getTournee() {
		return plan.getTournee();
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
