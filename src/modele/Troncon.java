package modele;

public class Troncon {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivee == null) ? 0 : arrivee.hashCode());
		result = prime * result + ((depart == null) ? 0 : depart.hashCode());
		result = prime * result + ((nomRue == null) ? 0 : nomRue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null ) 
			return false; 
		if(obj.getClass() != getClass())
			return false;
		
		Troncon other = (Troncon) obj;
		
		return getArrivee().getId() == other.getArrivee().getId()
			&& getDepart().getId() == other.getDepart().getId()
			&& getNomRue() == other.getNomRue();
		
	}

	private String nomRue;
	private double longueur;
	private double vitesse;
	private int duree;
	private Adresse arrivee;
	private Adresse depart;
	
	public Troncon (String nomRue, double longueur, double vitesse,Adresse depart, Adresse arrivee){
		this.longueur = longueur;
		this.vitesse = vitesse;
		if (vitesse > 0)  
			this.duree = new Double(longueur/vitesse).intValue();  
		else 
			this.duree=0 ; 
		this.arrivee = arrivee;
		this.depart = depart;
		this.nomRue = nomRue;
	}
	
	public String getNomRue(){
		return nomRue;
	}
	
	public double getLongueur(){
		return longueur;
	}
	
	public double getVitesse(){
		return vitesse;
	}
	
	public int getDuree(){
		return duree;
	}
	
	public Adresse getArrivee(){
		return arrivee;
	}
	
	public Adresse getDepart(){
		return depart;
	}

	public void accept(Visiteur visiteur) {
		visiteur.visite(this, false);
		
	}

}
