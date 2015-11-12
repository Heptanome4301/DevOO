package modele;

/**
 * Cette classe repr�sente � la fois une rue entre deux Adresse et une arr�te du Plan de la ville (qui peut �tre
 * consid�r� comme un graphe). La classe tron�on comporte UN SENS DE CIRCULATION, une rue � double sens sera alors 
 * repr�sent�e par deux tron�ons.
 */
public class Troncon {
        
        /**
         * Le nom du tron�on.
         */
	private String nomRue;
        /**
         * La longueur du tron�on.
         */
	private double longueur;
        /**
         * La vitesse moyenne des v�hicules sur le tron�on.
         */
	private double vitesse;
        /**
         * La dur�e moyenne n�cessaire � emprunter le tron�on.
         */
	private int duree;
        /**
         * L'Adresse d'arriv�e du tron�on.
         */
	private Adresse arrivee;
        /**
         * L'Adresse de d�part du tron�on.
         */
	private Adresse depart;
	
        /**
         * Le constructeur de la classe, la duree est calcul�e � partir de la longueur et de la vitesse.
         * @param nomRue Le nom de la tron�on.
         * @param longueur La longueur du tron�on.
         * @param vitesse la vitesse moyenne sur le tron�on.
         * @param depart L'adresse de d�part.
         * @param arrivee L'adresse d'arriv�e du tron�on.
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
    * Surcharge de la m�thode d'�galit�.
    * @param obj l'objet � comparer.
    * @return true si les id des deux adresses de chaque tron�on concordent et que les noms de rues concordent.
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
         * @return le nom du tro�on.
         */
	public String getNomRue(){
		return nomRue;
	}
	
        /**
         * Acesseur de l'attribut longueur.
         * @return la longueur du tron�on.
         */
	public double getLongueur(){
		return longueur;
	}
	
        /**
         * Accesseur de l'attribut vitesse.
         * @return la vittese moyenne des c�hicules sur le tron�on.
         */
	public double getVitesse(){
		return vitesse;
	}
	
        /**
         * Accesseur de l'attribut duree.
         * @return la duree n�cessaire � franchir le tron�on.
         */
	public int getDuree(){
		return duree;
	}
	
        /**
         * Accesseur de l'attribut arrivee.
         * @return l'adresse d'arriv�e du tron�on.
         */
	public Adresse getArrivee(){
		return arrivee;
	}
	
        /**
         * Accesseur de l'attribut de d�part.
         * @return l'adresse de d�part du tron�on.
         */
	public Adresse getDepart(){
		return depart;
	}
        
        /**
         * M�thode n�cessaire � la mise en place du pattern Visiteur.
         * @param visiteur le visiteur.
         */
	/*public void accept(VisiteurPlan visiteur) {
		visiteur.visite(this, false);
		
	}*/
}
