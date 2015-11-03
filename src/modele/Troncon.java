package modele;

public class Troncon {
	
	private String nomRue;
	private double longueur;
	private double vitesse;
	private double duree;
	private Adresse arrivee;
	private Adresse depart;
	
	public Troncon (String nomRue, double longueur, double vitesse,Adresse depart, Adresse arrivee){
		this.longueur = longueur;
		this.vitesse = vitesse;
		if (vitesse > 0)  this.duree = longueur/vitesse;  else this.duree=0 ; 
		this.arrivee = arrivee;
		this.depart = depart;
		this.nomRue = depart.getId()+"->"+arrivee.getId();//nomRue;
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
	
	public Adresse getDepart(){
		return depart;
	}

}
