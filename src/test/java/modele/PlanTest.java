package modele;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import xml.ExceptionXML;

public class PlanTest {

	@Test
	public void testPlan() {
		Plan p = new Plan();
		assertTrue("test constructeur",p!=null);
	}

	@Test
	public void testAjouterAdresse() {
		Adresse a = new Adresse(1, 2, 3);
		Plan p = new Plan();
		p.ajouterAdresse(a);
		assertTrue("testAjouterAdresse",p.getAdresse(1).equals(new Adresse(1, 2, 3)));
		assertFalse("testAjouterAdresse",p.getAdresse(1).equals(new Adresse(2, 4, 5)));
		
	}

	@Test
	public void testChargerPlan() {
		File testSuccess1 = new File("./xmlTest/plan10x10.xml");
		File testSuccess2 = new File("./xmlTest/plan20x20.xml");
		File testFail1 = new File("./xmlTest/Fichiers_Avec_Erreurs/plan10x10_Adresses_Inaccessibles.xml");
		File testFail2 = new File("./xmlTest/Fichiers_Avec_Erreurs/plan10x10_Troncon_Vers_Inexistant.xml");
		
		// fichiers sans erreurs
		try {
			Plan p = new Plan();
			p.chargerPlan(testSuccess1);
			assertTrue("testChargerPlan10*10",true);
			p.clear();
			p.chargerPlan(testSuccess2);
			assertTrue("testChargerPlan20*20",true);
		} catch(Exception e) {
			assertTrue("testChargerPlan : " + e.toString(),false);
		}

		// fichiers avec erreurs
		try {
			Plan p = new Plan();
			p.chargerPlan(testFail1);
			assertTrue("testChargerPlanFail1",false);
		} catch(xml.ExceptionXML e) {
			assertTrue("testChargerPlanFail1 : " + e.toString(),e.getMessage().equals(ExceptionXML.ADRESSE_INACCESSIBLE));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue("testChargerPlanFail1 : " + e.toString(),false);
		} 
		
		try {
			Plan p = new Plan();
			p.chargerPlan(testFail2);
			assertTrue("testChargerPlanFail2",false);
		} catch(xml.ExceptionXML e) {
			assertTrue("testChargerPlanFail2 : " + e.toString(),e.getMessage().equals(ExceptionXML.ARRIVEE_TRONCON_INEXISTANTE));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue("testChargerPlanFail2 : " + e.toString(),false);
		} 
		

	}

	@Test
	public void testChargerLivraison() {
		File Plan10x10 = new File("./xmlTest/plan10x10.xml");
		File Plan20x20 = new File("./xmlTest/plan20x20.xml");
		File testSuccess10x10_1 = new File("./xmlTest/livraison10x10-1.xml");
		File testSuccess10x10_2 = new File("./xmlTest/livraison10x10-2.xml");
		File testSuccess10x10_3 = new File("./xmlTest/livraison10x10-3.xml");
		File testSuccess20x20_1 = new File("./xmlTest/livraison20x20-1.xml");
		File testSuccess20x20_2 = new File("./xmlTest/livraison20x20-2.xml");
		
		File testFail10x10_1 = new File("./xmlTest/Fichiers_Avec_Erreurs/livraison10x10-1_Adresse_Inexistante.xml");
		File testFail10x10_2 = new File("./xmlTest/Fichiers_Avec_Erreurs/livraison10x10-1_Attribut_Negatif.xml");
		File testFail10x10_3 = new File("./xmlTest/Fichiers_Avec_Erreurs/livraison10x10-1_DTD_incorrecte.xml");
		
		// fichiers 10x10 sans erreurs
		
		try {
			Plan p = new Plan();
			p.chargerPlan(Plan10x10);
			p.chargerLivraison(testSuccess10x10_1);
			assertTrue("testChargerLivraison10*10",true);
			p.viderTournee();
			p.chargerLivraison(testSuccess10x10_2);
			assertTrue("testChargerLivraison10*10",true);
			p.viderTournee();
			p.chargerLivraison(testSuccess10x10_3);
			assertTrue("testChargerLivraison10*10",true);
		} catch(Exception e) {
			assertTrue("testChargerLivraison10*10",false);
		}
		
		// fichiers 20*20 sans erreurs

		try {
			Plan p = new Plan();
			p.chargerPlan(Plan20x20);
			p.chargerLivraison(testSuccess20x20_1);
			assertTrue("testChargerLivraison20*20",true);
			p.viderTournee();
			p.chargerLivraison(testSuccess20x20_2);
			assertTrue("testChargerLivraison20*20",true);
		} catch(Exception e) {
			assertTrue("testChargerLivraison20*20",false);
		}
		
		// fichiers 10x10 avec erreurs
		
		
		try {
			Plan p = new Plan();
			p.chargerPlan(Plan10x10);
			p.chargerLivraison(testFail10x10_1);
			assertTrue("testChargerLivraison10*10",false);
		} catch(xml.ExceptionXML e) {
			assertTrue("testChargerLivraison10*10 : " + e.toString(),e.getMessage().equals(ExceptionXML.ADRESSE_INVALIDE));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue("testChargerLivraison10*10 : " + e.toString(),false);
		} 
		
		try {
			Plan p = new Plan();
			p.chargerPlan(Plan10x10);
			p.chargerLivraison(testFail10x10_2);
			assertTrue("testChargerLivraison10*10",false);
		} catch(xml.ExceptionXML e) {
			assertTrue("testChargerLivraison10*10 : " + e.toString(),e.getMessage().equals(ExceptionXML.ATTRIBUT_NEGATIF));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue("testChargerLivraison10*10 : " + e.toString(),false);
		} 
		
		try {
			Plan p = new Plan();
			p.chargerPlan(Plan10x10);
			p.chargerLivraison(testFail10x10_3);
			assertTrue("testChargerLivraison10*10",false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue("testChargerLivraison10*10 : " + e.toString(),true);
		} 
	}

	@Test
	public void testCalculerChemin() {
		File Plan10x10 = new File("./xmlTest/plan10x10.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(Plan10x10);
		} catch (ParserConfigurationException | SAXException | IOException
				| ExceptionXML e) {
			fail("testCalculerChemin : echec ouverture fichier[./xmlTest/plan10x10.xml]");
		}
		
		Adresse depart = p.getAdresse(0);
		Adresse arrivee = p.getAdresse(5);
		
		Chemin chemin = p.calculerChemin(depart, arrivee);
		
		assertTrue("test CalculerChemin",depart.equals(chemin.getDepart()));
		assertTrue("test CalculerChemin",arrivee.equals(chemin.getArrivee()));
		

		ArrayList<Troncon> troncons = (ArrayList<Troncon>) chemin.getTroncons();
		int i,size = chemin.getTroncons().size();
		
		
		Adresse a = chemin.getArrivee();
		for( i = 0 ; i < size ; i++ ){
			assertTrue("test CalculerChemin",troncons.get(i).getArrivee().equals(a));
			for(Adresse tmp : p.getAdresses()){
				if( tmp.getTroncons().contains(troncons.get(i))){
					a = tmp;
					break;
				}
			}
		}
		assertTrue("test CalculerChemin",depart.equals(a));
		
	}

	@Test
	public void testGetAdresses() {
		Adresse a1 = new Adresse(1, 2, 3);
		Adresse a2 = new Adresse(2, 3, 3);
		Adresse a3 = new Adresse(3, 5, 3);
		Adresse a4 = new Adresse(4, 2, 8);
		Plan p = new Plan();
		p.ajouterAdresse(a1);
		p.ajouterAdresse(a2);
		p.ajouterAdresse(a3);
		
		ArrayList<Adresse> l2 = new ArrayList<>();
		ArrayList<Adresse> l3 = new ArrayList<>();
		l2.add(a1);
		l2.add(a2);
		l2.add(a3);
		l3.addAll(l2);
		l3.add(a4);
				
		assertTrue("testGetAdresses",p.getAdresses().equals(l2));
		assertFalse("testGetAdresses",p.getAdresses().equals(l3));
		
	}

	@Test
	public void testGetAdresse() {
		Adresse a = new Adresse(1, 2, 3);
		Plan p = new Plan();
		p.ajouterAdresse(a);
		assertTrue("testGetAdresse",p.getAdresse(1).equals(new Adresse(1, 2, 3)));
		assertFalse("testGetAdresse",p.getAdresse(1).equals(new Adresse(2, 4, 5)));
	}

	@Test
	public void testClear() {
		Adresse a = new Adresse(1, 2, 3);
		Plan p = new Plan();
		p.ajouterAdresse(a);
		assertFalse("testClear",p.getAdresses().isEmpty());
		p.clear();
		assertTrue("testClear",p.getAdresses().isEmpty());
	}

	@Test
	public void testGetXMax() {
		File Plan10x10 = new File("./xmlTest/plan10x10.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(Plan10x10);
			assertTrue("testGetXMax",p.getXMax() == 729);
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			// TODO Auto-generated catch block
			assertTrue("testGetXMax",false);
		}
		
	}

	@Test
	public void testGetYMax() {
		File Plan10x10 = new File("./xmlTest/plan10x10.xml");
		Plan p = new Plan();
		try {
			p.chargerPlan(Plan10x10);
			assertTrue("testGetYMax",p.getYMax() == 746);
		} catch (ParserConfigurationException | SAXException | IOException | ExceptionXML e) {
			// TODO Auto-generated catch block
			assertTrue("testGetYMax",false);
		}
	}


	@Test
	public void testGetAdresseByCoord() {
		Adresse a = new Adresse(1, 2, 3);
		Plan p = new Plan();
		p.ajouterAdresse(a);
		assertTrue("testGetAdresse",p.getAdresseByCoord(new Point(2, 3)).equals(new Adresse(1, 2, 3)));
		assertFalse("testGetAdresse",p.getAdresseByCoord(new Point(2, 3)).equals(new Adresse(2, 4, 5)));
	}

	@Test
	public void testViderTournee() {
		File Plan10x10 = new File("./xmlTest/plan10x10.xml");
		File liv10x10_1 = new File("./xmlTest/livraison10x10-1.xml");
		
		try {
			Plan p = new Plan();
			p.chargerPlan(Plan10x10);
			p.chargerLivraison(liv10x10_1);
			p.getTournee().calculerTournee();
			assertFalse("testViderTournee",p.getTournee().getSortedLivraisons().isEmpty());
			p.viderTournee();
			assertTrue("testViderTournee",p.getTournee()==null);
		} catch (Exception e) {
			assertTrue("testViderTournee",false);
		}
	}


}
