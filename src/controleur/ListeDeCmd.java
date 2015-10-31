package controleur;

import java.util.ArrayList;

public class ListeDeCmd {
	private ArrayList<Commande> listeCmd;
	private int position;
	
	public ListeDeCmd() {
		listeCmd = new ArrayList<Commande>();
		position = listeCmd.size();
	}
	
	public void	ajoute(Commande cmd) {
		listeCmd.add(cmd);
		position = listeCmd.size();
	}
	
	public void undo() {
		if(position>0)	{
			listeCmd.get(position).undoCmd();
			position--;
		}
	}
	
	public void redo() {
		if(position<listeCmd.size()) {
			listeCmd.get(position).doCmd();
			position++;
		}
	}
}