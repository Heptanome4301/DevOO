package modele;

public class Troncon {
	
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

}
