package test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controleur.Controleur;
import vue.Fenetre;
import xml.ExceptionXML;
import xml.OuvreurDeFichiersXML;
import modele.Plan;

public class TestMain {
	
	public static void main(String[] args) {
		
		Controleur c = new Controleur(new Plan());
		
	}


}
