package controleur;

import java.util.ArrayList;

/**
 * Cette classe represente la pile d'actions de modification effectuees pour pouvoir implementer un systeme
 * de undo/redo.
 */
public class ListeDeCmd {
        /**
         * La liste des commandes effectuees.
         */
	private ArrayList<Commande> listeCmd;
        /**
         * La position actuelle du curseur.
         */
	private int position;
	
        /**
         * Le constructeur de la classe ne prend aucun parametre, il se contente d'initialiser les attributs.
         */
	public ListeDeCmd() {
		listeCmd = new ArrayList<Commande>();
		position = listeCmd.size();
	}
	
        /**
         * Ajoute une comande a la liste de commande.
         * @param cmd la commande a ajouter.
         */
	public void ajoute(Commande cmd) {
                for(int i = position; i<listeCmd.size()-1 ;i++)
                    listeCmd.remove(i);
		listeCmd.add(cmd);
		position = listeCmd.size();
	}
	
        /**
         * Annule la derniere commande effectuee et decremente la position.
         */
	public void undo() {
		if(position>0)	{
			listeCmd.get(position-1).undoCmd();
			position--;
		}
	}
	
        /**
         * Reexecute la derniere action annulee et incrmente la position.
         */
	public void redo() {
		if(position<listeCmd.size()) {
			listeCmd.get(position).doCmd();
			position++;
		}
	}
        
        /**
         * Vide la liste des commandes.
         */
        public void clear() {
            listeCmd.clear();
            position = 0;
        }
}
