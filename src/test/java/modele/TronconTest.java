package modele;

import static org.junit.Assert.*;

import org.junit.Test;

public class TronconTest {


	@Test
	public void testTroncon() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("test constructeur",t!=null);
	}

	@Test
	public void testEqualsObject() {
		Troncon a = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		Troncon b = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		Troncon c = new Troncon("nomru2e", 7.5, 3, new Adresse(3, 2, 2), new Adresse(4, 3, 3));
		assertTrue("test d'egalite",a.equals(b));
		assertFalse("test d'inegalite",a.equals(c));
	}

	@Test
	public void testGetNomRue() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("test GetNomRue",t.getNomRue().equals("nomrue"));
	}

	@Test
	public void testGetLongueur() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("testGetLongueur",t.getLongueur()==5.5);
	}

	@Test
	public void testGetVitesse() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("testGetVitesse",t.getVitesse()==2);
	}

	@Test
	public void testGetDuree() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		int duree = new Double(5.5/2).intValue();
		assertTrue("testGetDuree",t.getDuree() == duree);
	}

	@Test
	public void testGetArrivee() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("testGetArrivee",t.getArrivee().equals(new Adresse(2, 1, 1)));
	}

	@Test
	public void testGetDepart() {
		Troncon t = new Troncon("nomrue", 5.5, 2, new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("testGetDepart",t.getDepart().equals(new Adresse(1, 0, 0)));
	}

}
