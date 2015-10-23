package modele;

import java.util.Comparator;
import java.util.HashMap;

public class AdresseComparator implements Comparator<Adresse> {
	
	private HashMap<Adresse,Double> map;
	
	public AdresseComparator(HashMap<Adresse,Double> map){
		this.map = map;
	}

	@Override
	public int compare(Adresse a1, Adresse a2) {
		double val1, val2;
		val1 = map.get(a1);
		val2 = map.get(a2);
		
		if(val1-val2 < 0.0)
			return -1;
		else if (val1-val2 > 0.0)
			return 1;
		else
			return 0;
	}
}
