package controleur;

import java.io.File;
import java.io.IOException;

import modele.Plan;
import modele.Tournee;
import org.xml.sax.SAXException;
import vue.Fenetre;
import xml.ExceptionXML;

import javax.xml.parsers.ParserConfigurationException;

public class EtatPlan extends EtatIni {

	public EtatPlan() {
		// TODO Auto-generated constructor stub
	}

	public Tournee chargerLivraisons(Fenetre fenetre, Plan plan, File file){
		Tournee resultat = null;
		try {
			resultat = plan.chargerLivraison(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExceptionXML exceptionXML) {
			exceptionXML.printStackTrace();
		}
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return resultat;
	}
	
}
