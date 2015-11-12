package controleur;

import java.util.ArrayList;

/**
 * Cette classe représente la pile d'actions de modification effectuées pour pouvoir implémenter un système
 * de undo/redo.
 */
public class ListeDeCmd {
        /**
         * La liste des commandes effectuées.
         */
	private ArrayList<Commande> listeCmd;
        /**
         * La position actuelle du curseur
         */
	private int position;
	
	public ListeDeCmd() {
		listeCmd = new ArrayList<>();
		position = listeCmd.size();
	}
	
	public void ajoute(Commande cmd) {
                for(int i = position; i<listeCmd.size()-1 ;i++)
                    listeCmd.remove(i);
		listeCmd.add(cmd);
		position = listeCmd.size();
	}
	
	public void undo() throws Exception {
		if(position>0)	{
			listeCmd.get(position-1).undoCmd();
			position--;
		}
	}
	
	public void redo() throws Exception {
		if(position<listeCmd.size()) {
			listeCmd.get(position).doCmd();
			position++;
		}
	}
}
