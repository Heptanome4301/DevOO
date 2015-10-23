package modele;

import java.util.Comparator;

public class AdresseComparator implements Comparator<Adresse> {

	@Override
	public int compare(Adresse a0, Adresse a1) {
		// TODO Auto-generated method stub
		if(a0.getDistanceEntrepot() - a1.getDistanceEntrepot() < 0.0){
			return -1;
		}
		else if(a0.getDistanceEntrepot() - a1.getDistanceEntrepot() == 0.0){
			return 0;
		}
		else{
			return 1;
		}	
	}
}
