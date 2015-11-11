package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;

/**
 * Interface Etat nécessaire à la mise en place du design pattern Etat. Elle décrit les fonctionnalités accessibles à
 * l'utilisateur depuis la vue.
 */
public interface Etat {
        /**
         * Annuler la dernière modification effectuée.
         * @param fenetre la fenêtre d'affichage des résultats.
         * @param listeCmd la liste contenant les commandes effectuées précédemment.
         */
	void undo(Fenetre fenetre, ListeDeCmd listeCmd);
        /**
         * Refaire la dernière commande annulée.
         * @param fenetre la fenêtre d'afficahge des résultats.
         * @param listeCmd la liste contenant les commandes effectuées précédemment.
         */
	void redo(Fenetre fenetre, ListeDeCmd listeCmd);
        /**
         * Méthode invoquée lorsque l'utilisateur clique sur un noeud du graphe ou sélectionne un noeud dans la liste.
         * @param fenetre la fenêtre d'affichage des résultats.
         * @param adresse l'Adresse sélectinnée par l'utilisateur.
         * @param plan le plan auquel appartient l'Adresse sélectionnée.
         * @param listeCmd la liste contenant les commandes effectuées précédemment.
         */
	void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan, ListeDeCmd listeCmd);
        /**
         * Remplit un plan à partir des adresses décrites dans un fichier xml. Cette méthode demande à
         * l'utilisateur de naviguer dans son arborescence de fichiers (à l'aide d'un JFileChooser) pour trouver 
         * le fichier xml à lire.
         * @param fenetre la fenêtre d'affichage des résultats.
         * @param plan le plan à remplir.
         */
	void chargerPlan(Fenetre fenetre, Plan plan);
        /**
         * Remplit une tournee à partir des livraisons décrites dans un fichier xml. Cette méthode demande à
         * l'utilisateur de naviguer dans sn arborescence de fichiers (à l'aide d'un JFileChooser) pour trouver 
         * le fichier xml à lire.
         * @param fenetre la fenêtre d'affichage des résultats.
         * @param plan le plan contenant la tournée à remplir.
         */
	void chargerLivraisons(Fenetre fenetre, Plan plan);
        /**
         * Cette méthode calcule l'ordre de passage pour une tournée passée en paramètre.
         * @param fenetre la fenêtre d'affichage des résultats.
         * @param tournee la tournée pour laquelle on doit calculer l'ordre de passage.
         */
	void calculerTournee(Fenetre fenetre, Tournee tournee);
        /**
         * Génère la feuille de route décrviant l'ordre de passage calculé pour la tournée passée en paramètre.
         * Cette méthode demande à l'utilisateur de choisir le fichier dans lequel écrire la feuille de route à 
         * l'aide d'un JFileChooser.
         * @param fenetre la fenêtre d'afficahge des résultats.
         * @param tournee la tournée pour laquelle il faut générer les instructions.
         */
	void genererFeuilleDeRoute(Fenetre fenetre, Tournee tournee);
        /**
         * Méthode pour annuler l'action en cours d'exécution (ajout ou échange de livraisons).
         * @param fenetre la fenêtre d'affichage des résultats.
         */
	void clicDroit(Fenetre fenetre);
        /**
         * Permet de déclencher le processus d'ajout d'une livraison à la tournée une fois celle-ci calculée.
         * @param fenetre la fenêtre d'affichage des résultats.
         */
	void ajouter(Fenetre fenetre);
        /**
         * Permet de déclencher le processus de suppression d'une livraison de la tournée une fois celle-ci calculée
         * @param fenetre la fenêtre d'affichage des résultats.
         */
	void supprimer(Fenetre fenetre);
        /**
         * Permet de déclencher le processus d'échange de l'ordre de deux livraisons de la tournée une fois celle-ci
         * calculée.
         * @param fenetre la fenêtre d'affichage des résultats.
         */
	void echanger(Fenetre fenetre);
        
        /**
         * Permet de déclencher le processus de déplacement d'une livraison dans la tournée une fois celle-ci calculée
         * @param fenetre la fenêtre d'affichage des résultats.
         */
        void deplacer (Fenetre fenetre);
        
        /**
         * Cette méthode est déclenchée lors de la sélection d'une livraison dans la liste.
         * @param fenetre la fenêtre d'afficahge des résultats
         * @param livraison la livraison sélectionnée
         */
	void clicListLivraisons(Fenetre fenetre, Livraison livraison);
}
