package util;

public class Constants {
	public static final int RAYON_NOEUD = 5;
	public static final int MARGIN_VUE_GRAPHE = 10;
	public static final int AREA_CLICK_NOEUD = 3;
	public static final int MARGIN_TEXTE_PANEL = 10;

	/*Messages de Log*/
	public static final String LOGS_DEFAULT = "";
	public static final String LOGS_AJOUTER2 = "Cliquez sur la livraison qui précédera celle que vous souhaitez ajouter." +
			" Clic droit pour annuler.";
	public static final String LOGS_AJOUTER1 = "Cliquez sur l'adresse de la nouvelle livraison. Clic droit pour annuler.";
	public static final String LOGS_SUPPRIMER = "Cliquez sur la livraison que vous souhaitez supprimer. Clic droit pour annuler.";
	public static final String LOGS_ECHANGER1 = "Cliquez sur la première livraison à échanger. Clic droit pour annuler";
	public static final String LOGS_ECHANGER2 = "Cliquez sur la livraison avec laquelle il faut échanger. Clic droit pour annuler";
	public static final String LOGS_TOURNEE = "Tournée calculée avec succès";
	public static final String LOGS_LIVRAISON = "Livraisons chargées avec succès.";
	public static final String LOGS_PLAN = "Plan chargé avec succès.";
        public static final String LOGS_DEPLACER1 = "Choississez la livraison que vous souhaitez déplacer. Clic droit pour annuler";
        public static final String LOGS_DEPLACER2 = "Choississez la livraison qui suivra celle déplacée. Clic droit pour annuler";
        
        /*Messages d'erreur � l'attention de l'utilisateur*/
        public static final String ERR_GENERE_FEUILLE = "Veuillez calculer la tourn�e afin de pouvoir g�n�rer la feuille de route.";
        public static final String ERR_CHARGEMENT_LIVRAISON = "Il faut charger un plan avant de pouvoir charger des livraisons.";
        public static final String ERR_CALCUL_TOURNEE = "Il faut d'abord charger un plan et des livraisons avant de pouvoir calculer la tournée";
        public static final String ERR_PAS_ADRESSE_LIVRAISON = "L'adresse selectionnée n'est associée à aucune livraison";
        public static final String ERR_ADRESSE_LIVRAISON = "L'adresse selectionnée est d�j� associée àune livraison";

        
        /*Constantes pour les textes des boutons*/
        public static final String CHARGER_PLAN = "Charger un plan";
        public static final String CHARGER_LIVRAISONS = "Charger des livraisons";
        public static final String CALCULER_TOURNEE = "Calculer une tourn�e";
        public static final String AJOUTER_LIVRAISONS = "Ajouter une livraison";
        public static final String SUPPRIMER_LIVRAISON = "Supprimer une livraison";
        public static final String INVERSER_LIVRAISONS = "Inverser deux livraisons";
        public static final String SAUVEGARDER_FEUILLE_DE_ROUTE = "Sauvegarder le feuille de route";
        public static final String DEPLACER_LIVRAISON = "Déplacer une livraison";
}
