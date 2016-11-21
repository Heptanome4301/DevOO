package modele;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Cette classe sert a comparer des adresses en fonction d'une valeur double qui leur est associee dans une map.
 */
public class AdresseComparator implements Comparator<Adresse> {
	
        /**
         * La map qui associe une valeur decimale a une adresse.
         */
	private HashMap<Adresse,Double> map;
	
        /**
         * Le constructeur du comparateur.
         * @param map la map qui associe une valeur a une adresse.
         */
	public AdresseComparator(HashMap<Adresse,Double> map){
		this.map = map;
	}
        
        /**
         * Compare deux adresses sur la base de la valeur qui leur est associée dans la map ou sur les id 
         * en cas d'egalite
         * @param a1 La premiere adresse a comparer
         * @param a2 La seconde adresse a comparer
         * @return 1 a1>a2, -1 si a2<a1, -1 si a1=a2 && a1.id<a2.id
         */
	@Override
	public int compare(Adresse a1, Adresse a2) {
		double val1, val2;
		val1 = map.get(a1);
		val2 = map.get(a2);
		
		if(val1-val2 < 0.0)
			return -1;
		else if (val1-val2 > 0.0)
			return 1;
                else{
                    if(a1.getId()<a2.getId())
                        return -1;
                    else
                        return 1;
                }
	}
}
