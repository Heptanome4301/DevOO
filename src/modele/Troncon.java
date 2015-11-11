package modele;

/**
 * Cette classe représente à la fois une rue entre deux Adresse et une arrête du Plan de la ville (qui peut être
 * considéré comme un graphe). La classe tronçon comporte UN SENS DE CIRCULATION, une rue à double sens sera alors 
 * représentée par deux tronçons.
 */
public class Troncon {
        
        /**
         * Le nom du tronçon.
         */
	private String nomRue;
        /**
         * La longueur du tronçon.
         */
	private double longueur;
        /**
         * La vitesse moyenne des véhicules sur le tronçon.
         */
	private double vitesse;
        /**
         * La durée moyenne nécessaire à emprunter le tronçon.
         */
	private int duree;
        /**
         * L'Adresse d'arrivée du tronçon.
         */
	private Adresse arrivee;
        /**
         * L'Adresse de départ du tronçon.
         */
	private Adresse depart;
	
        /**
         * Le constructeur de la classe, la duree est calculée à partir de la longueur et de la vitesse.
         * @param nomRue Le nom de la tronçon.
         * @param longueur La longueur du tronçon.
         * @param vitesse la vitesse moyenne sur le tronçon.
         * @param depart L'adresse de départ.
         * @param arrivee L'adresse d'arrivée du tronçon.
         */
	public Troncon (String nomRue, double longueur, double vitesse, Adresse depart, Adresse arrivee){
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
	
    /**
     * Surcharge du calcul de l'index de hachage afin de pouvoir utiliser un HashSet.
     * @return l'index de hachage.
     */
    @Override
    public int hashCode() {
           final int prime = 31;
           int result = 1;
           result = prime * result + ((arrivee == null) ? 0 : arrivee.hashCode());
           result = prime * result + ((depart == null) ? 0 : depart.hashCode());
           result = prime * result + ((nomRue == null) ? 0 : nomRue.hashCode());
           return result;
    }
    
    /**
    * Surcharge de la méthode d'égalité.
    * @param obj l'objet à comparer.
    * @return true si les id des deux adresses de chaque tronçon concordent et que les noms de rues concordent.
    */
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
        
        /**
         * Accesseur de l'attribut nomRue.
         * @return le nom du troçon.
         */
	public String getNomRue(){
		return nomRue;
	}
	
        /**
         * Acesseur de l'attribut longueur.
         * @return la longueur du tronçon.
         */
	public double getLongueur(){
		return longueur;
	}
	
        /**
         * Accesseur de l'attribut vitesse.
         * @return la vittese moyenne des céhicules sur le tronçon.
         */
	public double getVitesse(){
		return vitesse;
	}
	
        /**
         * Accesseur de l'attribut duree.
         * @return la duree nécessaire à franchir le tronçon.
         */
	public int getDuree(){
		return duree;
	}
	
        /**
         * Accesseur de l'attribut arrivee.
         * @return l'adresse d'arrivée du tronçon.
         */
	public Adresse getArrivee(){
		return arrivee;
	}
	
        /**
         * Accesseur de l'attribut de départ.
         * @return l'adresse de départ du tronçon.
         */
	public Adresse getDepart(){
		return depart;
	}
        
        /**
         * Méthode nécessaire à la mise en place du pattern Visiteur.
         * @param visiteur le visiteur.
         */
	public void accept(Visiteur visiteur) {
		visiteur.visite(this, false);
		
	}

}
