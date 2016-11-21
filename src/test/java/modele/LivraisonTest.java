package modele;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class LivraisonTest {


	@Test
	public void testLivraison() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		assertTrue("test constructeur",l!=null);
	}

	@Test
	public void testEqualsObject() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		
		Adresse a2 = new Adresse(1, 2, 3);
		FenetreLivraison f2 = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l2 = new Livraison(1, a2, f2);
		
		Adresse a3 = new Adresse(2, 3, 3);
		FenetreLivraison f3 = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l3 = new Livraison(1, a3, f3);
		
		
		assertTrue("test d'egalite",l.equals(l2));
		assertFalse("test d'inegalite",l.equals(l3));
	}

	@Test
	public void testGetHoraire() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		l.setHoraire(new Date(253));
		assertTrue("testGetHoraire",l.getHoraire().equals(new Date(253)));
	}

	@Test
	public void testGetAdresse() {
		Adresse a = new Adresse(1, 2, 3);
		Adresse a2 = new Adresse(2,3, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		l.setAdresse(a2);
		assertTrue("testGetAdresse",l.getAdresse().equals(a2));
		assertFalse("testGetAdresse",l.getAdresse().equals(a));
	}

	@Test
	public void testGetFenetreLivraison() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		FenetreLivraison f2 = new FenetreLivraison(new Date(250), new Date(255));
		FenetreLivraison f3 = new FenetreLivraison(new Date(255), new Date(260));
		Livraison l = new Livraison(1, a, f);
		assertTrue("testGetFenetreLivraison",l.getFenetreLivraison().equals(f2));
		assertFalse("testGetFenetreLivraison",l.getFenetreLivraison().equals(f3));
	}

	@Test
	public void testSetHoraire() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		l.setHoraire(new Date(253));
		assertTrue("testSetHoraire",l.getHoraire().equals(new Date(253)));
	}

	@Test
	public void testIsRetard() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		l.setHoraire(new Date(256));
		l.positionnerRetard();
		
		Adresse a2 = new Adresse(1, 2, 3);
		FenetreLivraison f2 = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l2 = new Livraison(1, a, f);
		l2.setHoraire(new Date(253));
		l2.positionnerRetard();
		
		assertTrue("testIsRetard",l.isRetard());
		assertFalse("testIsRetard",l2.isRetard());
	}

	@Test
	public void testGetId() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		
		assertTrue("testGetId",l.getId()==1);
		assertFalse("testGetId",l.getId()==2);
	}

	@Test
	public void testPositionnerRetard() {
		Adresse a = new Adresse(1, 2, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		l.setHoraire(new Date(256));
		l.positionnerRetard();
		
		Adresse a2 = new Adresse(1, 2, 3);
		FenetreLivraison f2 = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l2 = new Livraison(1, a, f);
		l2.setHoraire(new Date(253));
		l2.positionnerRetard();
		
		assertTrue("testPositionnerRetard",l.isRetard());
		assertFalse("testPositionnerRetard",l2.isRetard());
	}



	@Test
	public void testSetAdresse() {
		Adresse a = new Adresse(1, 2, 3);
		Adresse a2 = new Adresse(2,3, 3);
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		Livraison l = new Livraison(1, a, f);
		l.setAdresse(a2);
		assertTrue("testSetAdresse",l.getAdresse().equals(a2));
		assertFalse("testSetAdresse",l.getAdresse().equals(a));
	}

}
