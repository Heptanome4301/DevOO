package controleur;

public interface Commande {
	void doCmd() throws Exception;
	void undoCmd() throws Exception;
}
