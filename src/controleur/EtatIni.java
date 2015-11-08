package controleur;

import org.xml.sax.SAXException;
import tsp.Graphe;

import java.io.File;
import java.io.IOException;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;
import xml.ExceptionXML;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

public class EtatIni implements Etat {

	public EtatIni() {
	}

	@Override
	public void undo(Fenetre fenetre, ListeDeCmd listeCmd){
		// Does nothing (or return exception?)
	}

	@Override
	public void redo(Fenetre fenetre, ListeDeCmd listeCmd){
		// Does nothing (or return exception?)
	}

	@Override
	public void genererFeuilleDeRoute(Fenetre fenetre, String fichier,Tournee tournee){
		throw new UnsupportedOperationException();
	}

	@Override
	public void clicDroit() {
		// Does nothing	
	}

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan, Tournee tournee, ListeDeCmd listeCmd){
		throw new UnsupportedOperationException();
	}

	@Override
	public Graphe chargerPlan(Fenetre fenetre, Plan plan, File file){
		try {
			plan.chargerPlan(file);
			Controleur.setEtatCourant(Controleur.etatPlan);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExceptionXML exceptionXML) {
			exceptionXML.printStackTrace();
			String message = exceptionXML.getMessage();
			fenetre.signalerErreur(message);
		}
		return null;
		//TODO
	}

	@Override
	public Tournee chargerLivraisons(Fenetre fenetre, Plan plan,File file){
		throw new UnsupportedOperationException();
	}

	@Override
	public Graphe calculerTournee(Fenetre fenetre, Tournee tournee) {
		throw new UnsupportedOperationException();
	}

}
