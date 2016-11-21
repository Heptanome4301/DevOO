package modele;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class FenetreLivraisonTest {



	@Test
	public void testFenetreLivraison() {
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		assertTrue("test constructeur",f!=null);
	}

	@Test
	public void testEqualsObject() {
		FenetreLivraison a = new FenetreLivraison(new Date(250), new Date(255));
		FenetreLivraison b = new FenetreLivraison(new Date(250), new Date(255));
		FenetreLivraison c = new FenetreLivraison(new Date(255), new Date(260));
		assertTrue("test d'egalite",a.equals(b));
		assertFalse("test d'inegalite",a.equals(c));
	}

	@Test
	public void testGetHeureDebut() {
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		assertTrue("testGetHeureDebut",f.getHeureDebut().equals(new Date(250)));
	}

	@Test
	public void testGetHeureFin() {
		FenetreLivraison f = new FenetreLivraison(new Date(250), new Date(255));
		assertTrue("testGetHeureFin",f.getHeureFin().equals(new Date(255)));
	}


}
