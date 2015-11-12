package tests.modele;

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
}