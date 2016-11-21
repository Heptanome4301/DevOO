package modele;

import modele.Adresse;
import modele.Chemin;
import modele.Troncon;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Emilien Bai (emilien.bai@insa-lyon.fr) on 11/2015.
 * Project : DevOO
 */
public class CheminTest {

    @Test
    public void testAjouterTroncon() throws Exception {
        Collection<Troncon> troncons = new ArrayList<>();
        Adresse adresse1 = new Adresse(1, 10, 50);
        Adresse adresse2 = new Adresse(2, 10, 100);
        Adresse adresse3 = new Adresse(3, 10, 150);
        Adresse adresse4 = new Adresse(4, 10, 200);

        Troncon troncon12 = new Troncon("12", 50, 30, adresse1, adresse2);
        Troncon troncon23 = new Troncon("23", 50, 30, adresse2, adresse3);
        Troncon troncon34 = new Troncon("34", 50, 30, adresse3, adresse4);

        troncons.add(troncon12);
        troncons.add(troncon23);
        troncons.add(troncon34);

        Chemin chemin = new Chemin(adresse1, adresse4);
        chemin.ajouterTroncon(troncon12);
        chemin.ajouterTroncon(troncon23);
        chemin.ajouterTroncon(troncon34);

        assertEquals(troncons, chemin.getTroncons());
    }

    @Test
    public void testContient() throws Exception {
        Adresse adresse1 = new Adresse(1, 10, 50);
        Adresse adresse2 = new Adresse(2, 10, 100);
        Adresse adresse3 = new Adresse(3, 10, 150);
        Adresse adresse4 = new Adresse(4, 10, 200);

        Troncon troncon12 = new Troncon("12", 50, 30, adresse1, adresse2);
        Troncon troncon23 = new Troncon("23", 50, 30, adresse2, adresse3);
        Troncon troncon24 = new Troncon("24", 100, 30, adresse2, adresse4);
        Troncon troncon34 = new Troncon("34", 50, 30, adresse3, adresse4);

        Chemin chemin = new Chemin(adresse1, adresse4);
        chemin.ajouterTroncon(troncon12);
        chemin.ajouterTroncon(troncon23);
        chemin.ajouterTroncon(troncon34);

        assertFalse(chemin.contient(troncon24));
        assertTrue(chemin.contient(troncon12));
    }

	@Test
	public void testChemin() {
		Chemin c = new Chemin(new Adresse(1, 0, 0), new Adresse(2, 1, 1));
		assertTrue("test constructeur",c!=null);
	}

	@Test
	public void testAjouterTroncon2() {
		Adresse a1 = new Adresse(1, 0, 0);
		Adresse a2 = new Adresse(2, 1, 1);
		Adresse a3 = new Adresse(3, 2, 2);
		Chemin c = new Chemin(a1, a3);
		Troncon t1 = new Troncon("a1->a2", 5, 2, a1, a2);
		Troncon t2 = new Troncon("a2->a3", 5, 2, a2, a3);
		Troncon t3 = new Troncon("a2->a1", 5, 2, a2, a1);
		c.ajouterTroncon(t1);
		c.ajouterTroncon(t2);

		assertTrue("testAjouterTroncon arrivee",c.getArrivee().equals(a3));
		assertFalse("testAjouterTroncon arrivee",c.getArrivee().equals(a1));
		
		assertTrue("testAjouterTroncon depart",c.getDepart().equals(a1));
		assertFalse("testAjouterTroncon depart",c.getDepart().equals(a2));
		
		assertTrue("testAjouterTroncon contient",c.contient(t1));
		assertFalse("testAjouterTroncon contient",c.contient(t3));
		
		assertTrue("testAjouterTroncon duree",c.getDuree() == t1.getDuree()+t2.getDuree());
	}


	@Test
	public void testGetDuree() {
		Adresse a1 = new Adresse(1, 0, 0);
		Adresse a2 = new Adresse(2, 1, 1);
		Adresse a3 = new Adresse(3, 2, 2);
		Chemin c = new Chemin(a1, a2);
		Troncon t1 = new Troncon("a1->a2", 5, 2, a1, a2);
		Troncon t2 = new Troncon("a2->a3", 5, 2, a2, a3);
		c.ajouterTroncon(t1);
		c.ajouterTroncon(t2);
		assertTrue("testGetDuree",c.getDuree() == t1.getDuree()+t2.getDuree());
	}

	@Test
	public void testGetTroncons() {
		Adresse a1 = new Adresse(1, 0, 0);
		Adresse a2 = new Adresse(2, 1, 1);
		Adresse a3 = new Adresse(3, 2, 2);
		Chemin c = new Chemin(a1, a2);
		Troncon t1 = new Troncon("a1->a2", 5, 2, a1, a2);
		Troncon t2 = new Troncon("a2->a3", 5, 2, a2, a3);
		c.ajouterTroncon(t1);
		c.ajouterTroncon(t2);
		
		ArrayList<Troncon> l1 = (ArrayList<Troncon>) c.getTroncons();
		ArrayList<Troncon> l2 = new ArrayList<>();
		l2.add(t1);
		l2.add(t2);
		
		assertTrue("testGetTroncons",l1.equals(l2));
		
	}

	@Test
	public void testGetDepart() {
		Adresse a1 = new Adresse(1, 0, 0);
		Adresse a2 = new Adresse(2, 1, 1);
		Adresse a3 = new Adresse(3, 2, 2);
		Chemin c = new Chemin(a1, a2);
		Troncon t1 = new Troncon("a1->a2", 5, 2, a1, a2);
		Troncon t2 = new Troncon("a2->a3", 5, 2, a2, a3);
		
		c.ajouterTroncon(t1);
		c.ajouterTroncon(t2);

		assertTrue("testGetDepart",c.getDepart().equals(a1));
		assertFalse("testGetDepart",c.getDepart().equals(a2));
		
	}

	@Test
	public void testGetArrivee() {
		Adresse a1 = new Adresse(1, 0, 0);
		Adresse a2 = new Adresse(2, 1, 1);
		Adresse a3 = new Adresse(3, 2, 2);
		Chemin c = new Chemin(a1, a3);
		Troncon t1 = new Troncon("a1->a2", 5, 2, a1, a2);
		Troncon t2 = new Troncon("a2->a3", 5, 2, a2, a3);
		
		c.ajouterTroncon(t1);
		c.ajouterTroncon(t2);

		assertTrue("testGetArrivee",c.getArrivee().equals(a3));
		assertFalse("testGetArrivee",c.getArrivee().equals(a1));

	}

	@Test
	public void testContient2() {
		Adresse a1 = new Adresse(1, 0, 0);
		Adresse a2 = new Adresse(2, 1, 1);
		Adresse a3 = new Adresse(3, 2, 2);
		Chemin c = new Chemin(a1, a2);
		Troncon t1 = new Troncon("a1->a2", 5, 2, a1, a2);
		Troncon t2 = new Troncon("a2->a3", 5, 2, a2, a3);
		Troncon t3 = new Troncon("a2->a1", 5, 2, a2, a1);
		c.ajouterTroncon(t1);
		c.ajouterTroncon(t2);

		
		assertTrue("testContient",c.contient(t1));
		assertFalse("testContient",c.contient(t3));
		
	}



}