package modele;

public class Troncon {
	
	private String nomRue;
	private double longueur;
	private double vitesse;
	private double duree;
	private Adresse arrivee;
	
	public Troncon (String nomRue, double longueur, double vitesse, Adresse arrivee){
		this.nomRue = nomRue;
		this.longueur = longueur;
		this.vitesse = vitesse;
		if (vitesse > 0)  this.duree = longueur/vitesse;  else this.duree=0 ; 
		this.arrivee = arrivee;
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
	
	public double getDuree(){
		return duree;
	}
	
	public Adresse getArrivee(){
		return arrivee;
	}

}
