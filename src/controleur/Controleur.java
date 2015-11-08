package controleur;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tsp.Graphe;
import util.Constants;
import vue.Fenetre;
import xml.ExceptionXML;
import xml.OuvreurDeFichiersXML;
import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

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
	

	public Controleur(Plan plan) {
		this.historique = new ListeDeCmd();
		this.etatCourant = etatIni;
		this.plan = plan;
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
		File xml ;
		tournee = null;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			etatCourant.chargerPlan(fenetre, plan,xml);
		} catch (Exception e) {
			//TODO signaler erreur a la vue
			e.printStackTrace();
		} finally {
			this.calculEchelle();
		}
		return null;
		//TODO
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
	    File xml ;
	    tournee = null;

	    try {
	            xml = OuvreurDeFichiersXML.getInstance().ouvre();
	            tournee = etatCourant.chargerLivraisons(fenetre,plan,xml);
	            fenetre.setTournee(tournee);
	    } catch (ExceptionXML exceptionXML) {
	            //TODO signaler erreur a la vue
	    		exceptionXML.printStackTrace();
	    }

	    return null;
	    //TODO
	}
	
	public Graphe calculerTournee() {
		etatCourant.calculerTournee(fenetre, tournee);
		return null;
	}
	
	public void clicNoeud(Adresse adresse, Plan plan,Tournee tournee, ListeDeCmd listeCmd) {
			etatCourant.clicNoeud(fenetre, adresse,plan,tournee, listeCmd);
	}
	
	public void clicDroit() {
		etatCourant.clicDroit();
	}
	
	public void ajouter() {
		if(etatCourant == etatTournee) {
			etatCourant = etatAjouter1;
		}
		
	}
	
	public void supprimer() {
		if(etatCourant == etatTournee) {
			etatCourant = etatSupprimer;	
		}
	}
	
	public void echanger() {
		if(etatCourant == etatTournee) {
			//etatCourant = etatEchanger;
		}
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
			// ERROR
		} else {
			Adresse a = plan.getAdresse(idAdresse);
			fenetre.ecrireInfos(a.toString());
		}
	}

}
