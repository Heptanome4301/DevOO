package modele;

import java.util.ArrayList;

public class Plan {
	
	private ArrayList<Adresse> adresses;
	
	public Plan(){
		adresses = new ArrayList<Adresse>();
	}
	
	public void ajouterAdresse(Adresse a){
		adresses.add(a);
	}
	
	public ArrayList<Adresse> getAdresses(){
		return adresses;
	}
}
