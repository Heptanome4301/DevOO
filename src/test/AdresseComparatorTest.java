package test;

import modele.Adresse;
import modele.AdresseComparator;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Emilien Bai (emilien.bai@insa-lyon.fr) on 11/2015.
 * Project : DevOO
 */
public class AdresseComparatorTest {

    @Test
    public void testCompare() throws Exception {
        Adresse adresse1 = new Adresse(1, 10, 50);
        Adresse adresse2 = new Adresse(2, 10, 100);
        Adresse adresse3 = new Adresse(3, 10, 150);
        Adresse adresse4 = new Adresse(4, 10, 150);
        double dist11 = 0;
        double dist12 = 50;
        double dist13 = 100;

        HashMap<Adresse, Double> adresseDoubleHashMap = new HashMap<>();
        adresseDoubleHashMap.put(adresse1, dist11);
        adresseDoubleHashMap.put(adresse2, dist12);
        adresseDoubleHashMap.put(adresse3, dist13);
        adresseDoubleHashMap.put(adresse4, dist13);

        AdresseComparator adresseComparator = new AdresseComparator(adresseDoubleHashMap);

        /*L'adresse 2 est plus proche que l'adresse 3*/
        assertEquals(-1, adresseComparator.compare(adresse2, adresse3));

        /*L'adresse 3 est plus eloigne que l'adresse 2*/
        assertEquals(1, adresseComparator.compare(adresse3, adresse2));

        /*Les adresses 3 et 4 sont a la meme distance, l'indice de 3 est plus faible*/
        assertEquals(-1, adresseComparator.compare(adresse3, adresse4));

    }
}