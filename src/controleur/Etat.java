package controleur;

import java.io.File;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;

/**
 * Interface Etat n�cessaire � la mise en place du design pattern Etat. Elle d�crit les fonctionnalit�s accessibles �
 * l'utilisateur depuis la vue.
 */
public interface Etat {
        /**
         * Annuler la derni�re modification effectu�e.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         * @param listeCmd la liste contenant les commandes effectu�es pr�c�demment.
         */
	void undo(Fenetre fenetre, ListeDeCmd listeCmd);
        /**
         * Refaire la derni�re commande annul�e.
         * @param fenetre la fen�tre d'afficahge des r�sultats.
         * @param listeCmd la liste contenant les commandes effectu�es pr�c�demment.
         */
	void redo(Fenetre fenetre, ListeDeCmd listeCmd);
        /**
         * M�thode invoqu�e lorsque l'utilisateur clique sur un noeud du graphe ou s�lectionne un noeud dans la liste.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         * @param adresse l'Adresse s�lectinn�e par l'utilisateur.
         * @param plan le plan auquel appartient l'Adresse s�lectionn�e.
         * @param listeCmd la liste contenant les commandes effectu�es pr�c�demment.
         */
	void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan, ListeDeCmd listeCmd);
        /**
         * Remplit un plan � partir des adresses d�crites dans un fichier xml. Cette m�thode demande �
         * l'utilisateur de naviguer dans son arborescence de fichiers (� l'aide d'un JFileChooser) pour trouver 
         * le fichier xml � lire.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         * @param plan le plan � remplir.
         * @param xml 
         */
	void chargerPlan(Fenetre fenetre, Plan plan, File xml);
        /**
         * Remplit une tournee � partir des livraisons d�crites dans un fichier xml. Cette m�thode demande �
         * l'utilisateur de naviguer dans sn arborescence de fichiers (� l'aide d'un JFileChooser) pour trouver 
         * le fichier xml � lire.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         * @param plan le plan contenant la tourn�e � remplir.
         * @param controleur 
         */
	void chargerLivraisons(Fenetre fenetre, Plan plan, Controleur controleur);
        /**
         * Cette m�thode calcule l'ordre de passage pour une tourn�e pass�e en param�tre.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         * @param tournee la tourn�e pour laquelle on doit calculer l'ordre de passage.
         */
	void calculerTournee(Fenetre fenetre, Tournee tournee);
        /**
         * G�n�re la feuille de route d�crviant l'ordre de passage calcul� pour la tourn�e pass�e en param�tre.
         * Cette m�thode demande � l'utilisateur de choisir le fichier dans lequel �crire la feuille de route � 
         * l'aide d'un JFileChooser.
         * @param fenetre la fen�tre d'afficahge des r�sultats.
         * @param tournee la tourn�e pour laquelle il faut g�n�rer les instructions.
         */
	void genererFeuilleDeRoute(Fenetre fenetre, Tournee tournee);
        /**
         * M�thode pour annuler l'action en cours d'ex�cution (ajout ou �change de livraisons).
         * @param fenetre la fen�tre d'affichage des r�sultats.
         */
	void clicDroit(Fenetre fenetre);
        /**
         * Permet de d�clencher le processus d'ajout d'une livraison � la tourn�e une fois celle-ci calcul�e.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         */
	void ajouter(Fenetre fenetre);
        /**
         * Permet de d�clencher le processus de suppression d'une livraison de la tourn�e une fois celle-ci calcul�e
         * @param fenetre la fen�tre d'affichage des r�sultats.
         */
	void supprimer(Fenetre fenetre);
        /**
         * Permet de d�clencher le processus d'�change de l'ordre de deux livraisons de la tourn�e une fois celle-ci
         * calcul�e.
         * @param fenetre la fen�tre d'affichage des r�sultats.
         */
	void echanger(Fenetre fenetre);
        /**
         * Cette m�thode est d�clench�e lors de la s�lection d'une livraison dans la liste.
         * @param fenetre la fen�tre d'afficahge des r�sultats
         * @param livraison la livraison s�lectionn�e
         */
	void clicListLivraisons(Fenetre fenetre, Livraison livraison);
}
