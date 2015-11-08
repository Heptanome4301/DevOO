package controleur;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tsp.Graphe;
import util.Constants;
import vue.Fenetre;
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
	 * Annule la dernière modification effectuée sur la tournée (ajout, suppression ou échange de livraisons)
	 */
	public void undo() {
		try {
			etatCourant.undo(historique);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Réeffectue la dernière action annulée
	 */
	public void redo() {
		try {
			etatCourant.redo(historique);
		} catch (Exception e) {

		} 
	}
	
	
	public Graphe chargerPlan()  {
		plan.clear();
		File xml ;
		tournee = null;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			etatCourant.chargerPlan(plan,xml);
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
	            tournee = etatCourant.chargerLivraisons(plan,xml);
	            fenetre.setTournee(tournee);
	    } catch (Exception e) {
	            //TODO signaler erreur a la vue
	            e.printStackTrace();
	    }
	    return null;
	    //TODO
	}
	
	public Graphe calculerTournee() {
		etatCourant.calculerTournee(tournee);
		return null;
	}
	
	public void clicNoeud(Adresse adresse, Plan plan,Tournee tournee, ListeDeCmd listeCmd) {
		try {
			etatCourant.clicNoeud(adresse,plan,tournee, listeCmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	public String getFileSave() throws Exception{
		
 		int returnVal;
 		JFileChooser jFileChooserXML = new JFileChooser();
        jFileChooserXML.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Fichier TXT";
			}
			
			@Override
			public boolean accept(File f) {
				if (f == null) return false;
		    	if (f.isDirectory()) return true;
		    	String extension = getExtension(f);
		    	if (extension == null) return false;
		    	return extension.contentEquals("txt");
			}
			
			private String getExtension(File f) {
			    String filename = f.getName();
			    int i = filename.lastIndexOf('.');
			    if (i>0 && i<filename.length()-1) 
			    	return filename.substring(i+1).toLowerCase();
			    return null;
		   }
		});
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        returnVal = jFileChooserXML.showOpenDialog(null);
       
        if (returnVal != JFileChooser.APPROVE_OPTION) 
        	throw new Exception("Probleme a l'ouverture du fichier");
        return jFileChooserXML.getSelectedFile().getAbsolutePath();
		
	}
	
	
	public void genererFeuilleDeRoute(){
            try {
            	String fichier = getFileSave();
                etatCourant.genererFeuilleDeRoute(fichier, tournee);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
