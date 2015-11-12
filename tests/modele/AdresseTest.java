package tests.modele;

import modele.Adresse;
import modele.FenetreLivraison;
import modele.Livraison;
import modele.Troncon;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Emilien Bai (emilien.bai@insa-lyon.fr) on 11/2015.
 * Project : DevOO
 */
public class AdresseTest {

    @Test
    public void testEquals() throws Exception {
        Adresse adresse1 = new Adresse(1, 10, 50);
        Adresse adresse2 = new Adresse(2, 10, 50);
        Adresse adresse3 = new Adresse(1, 90, 150);

        assertFalse(adresse1.equals(adresse2));
        assertTrue(adresse1.equals(adresse3));
    }

    @Test
    public void testAjouterTroncon() throws Exception {
        Collection<Troncon> tronconsSortants = new ArrayList<>();
        Adresse depart1 = new Adresse(1, 10, 50);
        Adresse arrivee1 = new Adresse(2, 90, 60);
        Adresse arrivee2 = new Adresse(3, 50, 90);
        Troncon troncon1 = new Troncon("Rue 1", 150, 30, depart1, arrivee1);
        depart1.ajouterTroncon(troncon1);
        tronconsSortants.add(troncon1);
        assertEquals(tronconsSortants, depart1.getTroncons());

        Troncon troncon2 = new Troncon("Rue 2", 200, 50, depart1, arrivee2);
        depart1.ajouterTroncon(troncon2);
        tronconsSortants.add(troncon2);
        assertEquals(tronconsSortants, depart1.getTroncons());
    }

    @Test
    public void testEstAssocierAvecLivraison() throws Exception {
        Adresse adresse = new Adresse(1, 10, 50);
        Date debut = new Date(2015, 1, 1, 16, 0, 0);
        Date fin = new Date(2015, 1, 1, 18, 0, 0);
        FenetreLivraison fl = new FenetreLivraison(debut, fin);
        Livraison livraison = new Livraison(10, adresse, fl);

        assertTrue(adresse.estAssocierAvecLivraison());
        /*L'adresse est associée à une livraison car la livraison
         a ete creee avec l'adresse en parametre
          */
    }
}