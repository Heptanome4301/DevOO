package xml;

public class ExceptionXML extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*Liste des types d'Exception*/
	public static final String DOCUMENT_NON_CONFORME = "Document xml non conforme à la dtd";
	public static final String ATTRIBUT_NEGATIF = "Un des attributs est négatif";
	public static final String ID_IDENTIQUES = "Deux livraisons on le même identifiant";
	public static final String ENTREPOT_MANQUANT = "Le fichier ne contient pas les coordonnées de l'entrepot";
	public static final String PLUSIEUR_ENTREPOTS = "Le fichier contient plusieurs coordonnées pour l'entrepot";
	public static final String PLAGE_HORAIRE_INVALIDE = "Une des plages horaires spécifiées est mal formée";
	public static final String ADRESSE_INVALIDE = "Une des adresse de livraison spécifiée est invalide";

	public ExceptionXML(String message) {
		super(message);
	}

}
