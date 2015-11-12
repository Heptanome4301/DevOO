package modele;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import xml.ExceptionXML;

public class TourneeTest {
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAjoutLivraison() {
		
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			Adresse adresse = p.getAdresse(1);
			Adresse adresseF = p.getAdresse(73);
			Livraison livraisonF = adresseF.getLivraison();
			int annee = 2015,mois = 1, jour = 1;
			Date dateDebut = new Date(annee, mois, jour, 8, 0, 0);
			Date dateFin = new Date(annee, mois, jour, 12, 0, 0);
			Livraison livraison = new Livraison(1565, adresse, new FenetreLivraison(dateDebut,dateFin));
			p.getTournee().ajouterLivraison(livraison, livraisonF);
			assertTrue(adresse.getLivraison()!=null && livraison.getHoraire().compareTo(livraisonF.getHoraire())<0);
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testSupprimerLivraison() {
		
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		Tournee tournee;
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			Adresse adresse = p.getAdresse(43);
			//Livraison livraison = tournee.getLivraison(adresse);
			Livraison livraison = adresse.getLivraison();
			p.getTournee().supprimerLivraison(livraison);
			
			//assertTrue(tournee.getLivraison(adresse)==null);
			assertTrue(adresse.getLivraison()==null);
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testEchangerLivraison() {

		
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			Adresse adresse = p.getAdresse(73);
			Livraison livraison = adresse.getLivraison();
			Adresse adresse2 = p.getAdresse(13);
			Livraison livraison2 = adresse2.getLivraison();
			int min = livraison.getHoraire().compareTo(livraison2.getHoraire());
			p.getTournee().echangerLivraison(livraison, livraison2);
			if(min<0) {
				assertTrue(livraison.getHoraire().compareTo(livraison2.getHoraire())>0);
			}
			else if(min>0) {
				assertTrue(livraison.getHoraire().compareTo(livraison2.getHoraire())<0);
			}
			else {
				assertTrue(livraison.getHoraire().compareTo(livraison2.getHoraire())==0);
			}
						
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testCalculerTournee() {
		final double DUREE_TOURNEE = 6592; // durée précalculée qu'on doit obtenir
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			
			assertTrue(p.getTournee().getDuree()==DUREE_TOURNEE);
			
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSortedLivraisons() {
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			Livraison temp=null;
			for(Livraison livraison : p.getTournee().getSortedLivraisons()) {
				if(temp!=null) {
					assertTrue(livraison.getHoraire().compareTo(temp.getHoraire())<0);
				}
				temp=livraison;
			}
			
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCalculerTourneeFenetre() {
		final int NB_CHEMINS =8;
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);		
			int annee = 2015,mois = 1, jour = 1;
			Date dateDebut = new Date(annee, mois, jour, 8, 0, 0);
			Date dateFin = new Date(annee, mois, jour, 12, 0, 0);
			FenetreLivraison fenetreLivraison = new FenetreLivraison(dateDebut, dateFin);
			Adresse depart = p.getAdresse(40);
			ArrayList<Chemin> chemins = p.getTournee().calculerTourneeFenetre(depart, fenetreLivraison);
			assertTrue(chemins.size()==NB_CHEMINS);
			System.out.println(chemins);
			
			
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
	}
}
