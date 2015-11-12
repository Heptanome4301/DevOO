package modele;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
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
		Tournee tournee;
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			Adresse adresse = p.getAdresse(1);
			Adresse adresseF = p.getAdresse(73);
			//Livraison livraisonF = tournee.getLivraison(adresseF);
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
	public void testDeplacerLivraison() {
		File pXml = new File("xmlTest/plan10x10.xml");
		File lXml = new File("xmlTest/livraison10x10-1.xml");
		Plan p = new Plan();
		Tournee tournee;
		try {
			p.chargerPlan(pXml);
			p.chargerLivraison(lXml);
			p.getTournee().calculerTournee();
			Adresse adresse = p.getAdresse(73);
			//Livraison livraison = tournee.getLivraison(adresse);
			Livraison livraison = adresse.getLivraison();
			Adresse adresse2 = p.getAdresse(13);
			//Livraison livraison2 = tournee.getLivraison(adresse2);
			Livraison livraison2 = adresse2.getLivraison();
			p.getTournee().echangerLivraison(livraison, livraison2);
			assertTrue(livraison.getHoraire().compareTo(livraison2.getHoraire())<0);
			assertTrue(true);
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			fail("test failed - catched exception");
			e.printStackTrace();
		}
		
		
	}
}
