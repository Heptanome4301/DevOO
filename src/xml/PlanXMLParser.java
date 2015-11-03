package xml;

import java.lang.String;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import modele.*;

public class PlanXMLParser {
	/**
	 * Ouvre un fichier xml et cree plan a partir du contenu du fichier
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ExceptionXML
	 */
	public static void chargerPlan(Plan p) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		File xml = OuvreurDeFichiersXML.getInstance().ouvre();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
           construirePlan(racine, p);
        }
        else
        	throw new ExceptionXML("Document non conforme");
	}
	
	private static void construirePlan(Element root, Plan p) throws ExceptionXML {
		NodeList nodes = root.getElementsByTagName("Noeud");
		for (int i = 0; i < nodes.getLength(); i++) {
			parseAdresse((Element)nodes.item(i),p);
		}
		NodeList sections = root.getElementsByTagName("LeTronconSortant");
		for (int i = 0; i < sections.getLength(); i++) {
			parseTroncon((Element)sections.item(i), p);
		}
	}
	
	private static void parseAdresse(Element elt, Plan p) throws ExceptionXML {
		int id, x, y;
		id = Integer.parseInt(elt.getAttribute("id"));
		x = Integer.parseInt(elt.getAttribute("x"));
		y = Integer.parseInt(elt.getAttribute("y"));
		
		if(x<0 || y<0 || id<0)
			throw new ExceptionXML("Un des attributs est négatif!");
		
		System.out.println("Le noeud " + id +" a pour coordonnées ("+ x + "," + y + ")");
		p.ajouterAdresse(new Adresse(id,x,y));
		
	}
	
	private static void parseTroncon(Element elt, Plan p) throws ExceptionXML {
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
	    Number speed, length;
		
		double vitesse, longueur;
		int destination;
		Node parent = elt.getParentNode();
		int parentId;
		String nom;
		
		try {
			speed = format.parse(elt.getAttribute("vitesse"));
			length = format.parse(elt.getAttribute("longueur"));
		} catch (ParseException e) {
			e.printStackTrace();
			speed = -1;
			length = -1;
		}
		
		vitesse = speed.doubleValue();
		longueur = length.doubleValue();
		destination = Integer.parseInt(elt.getAttribute("idNoeudDestination"));
		parentId = Integer.parseInt(((Element)parent).getAttribute("id"));
		nom = elt.getAttribute("nomRue");
		
		if(nom == null || vitesse<0 || longueur<0 || destination<0)
			throw new ExceptionXML("Un des attributs est négatif!");
		
		Adresse depart = p.getAdresse(parentId);
		depart.ajouterTroncon(new Troncon(nom, longueur, vitesse, p.getAdresse(destination)));
		
	}
	
	
	
	public static Tournee chargerLivraisonX(Plan p) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		//TO DO
		Collection<Livraison> livraisons = new ArrayList<Livraison>();
		Collection<FenetreLivraison> fenetresLivraison = new ArrayList<FenetreLivraison>();
		FenetreLivraison f1 = new FenetreLivraison(0,null, null);
		FenetreLivraison f2 = new FenetreLivraison(1,null, null);
		fenetresLivraison.add(f1);
		fenetresLivraison.add(f2);
		livraisons.add(new Livraison(p.getAdresse(2),f1));
		livraisons.add(new Livraison(p.getAdresse(1),f1));
		return new Tournee(p,livraisons,fenetresLivraison,p.getAdresse(0));
	}
	
	public static Tournee chargerLivraison(Plan p) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		File xml = OuvreurDeFichiersXML.getInstance().ouvre();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        
        Tournee tournee = null  ;
        if (racine.getNodeName().equals("Tournee")) {
            tournee = construireTournee(racine, p);
         }
         else
         	throw new ExceptionXML("Document non conforme");
        
		return tournee;
	}
	
	private static Tournee construireTournee(Element root, Plan p) throws ExceptionXML {
		int idEntropot = Integer.parseInt(root.getAttribute("Entrepot"));
		Adresse entropot = p.getAdresse(idEntropot);
		if(entropot == null) 
			throw new ExceptionXML("l'id du l'enropt est invalide");
		else
			System.out.println("Entrepot := " + entropot.getId() );
		
		NodeList nodes = root.getElementsByTagName("Fenetre_livraison");
		ArrayList<FenetreLivraison> fenetresLivraison = new ArrayList<FenetreLivraison>(); 
		for (int i = 0; i < nodes.getLength(); i++) {
			fenetresLivraison.add(parseFenetre_livraison((Element)nodes.item(i)));
		}
		
		NodeList sections = root.getElementsByTagName("Livraison");
		ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
		for (int i = 0; i < sections.getLength(); i++) {
			livraisons.add(parseLivraison((Element)sections.item(i), p,fenetresLivraison ));
		}
		
		return new Tournee(p,livraisons,fenetresLivraison,entropot);
	}
	
	private static FenetreLivraison parseFenetre_livraison(Element elt) throws ExceptionXML {
		int id, Hdebut, Hfin;
		id = Integer.parseInt(elt.getAttribute("id"));
		Hdebut = Integer.parseInt(elt.getAttribute("Hdebut"));
		Hfin = Integer.parseInt(elt.getAttribute("Hfin"));
		
		if(Hdebut<0 || Hfin<0 || id<0 || Hfin<Hdebut)
			throw new ExceptionXML("Un des attributs est négatif!");
		
		Date debut = new Date(Hdebut);
		Date fin = new Date(Hfin);
		
		System.out.println("La fenetre de livraison " + id +" := ["+ Hdebut + "," + Hfin + "]");
		
		
		return new FenetreLivraison(id,debut, fin);
	}
	
	private static Livraison parseLivraison(Element elt, Plan p, ArrayList<FenetreLivraison> fenetresLivraison) throws ExceptionXML {
		int idAdresse ;
		idAdresse = Integer.parseInt(elt.getAttribute("idAdresse"));
		
		if(idAdresse<0)
			throw new ExceptionXML("Un des attributs est négatif!");
		
		Adresse adresse = p.getAdresse(idAdresse);
		if(adresse == null )
			throw new ExceptionXML("Adresse de livraison invalide");
		
		Element elmtFenetreLiv = (Element) elt.getParentNode();
		int idFenetreLivraision = Integer.parseInt(elmtFenetreLiv.getAttribute("id"));
		
		FenetreLivraison fenetreLivraison = getFenetre(fenetresLivraison,idFenetreLivraision);
		if(fenetreLivraison == null)
			throw new ExceptionXML("Livraison dont idAdresse = "+idAdresse+" a une Fenetre livraison invalide");	
		
		System.out.println("Livraison à l'adresse id=" + idAdresse +" fenetreLivraison id= "+idFenetreLivraision );
		
		return new Livraison(adresse,fenetreLivraison);
		
	}
	
	private static FenetreLivraison getFenetre(Collection<FenetreLivraison> fenetresLivraison,int id){
		for(FenetreLivraison fl :  fenetresLivraison)
			if(fl.getId() == id) return fl;
		return null;
	}

	
	
	//A LAISSER EN COMMENTAIRE, C'EST BON POUR LE COPIE-COL... EUUUUUUH, L'INSPIRATION.
	
	
    /*private static void construireAPartirDeDOMXML(Element noeudDOMRacine) throws ExceptionXML, NumberFormatException{
    	int hauteur = Integer.parseInt(noeudDOMRacine.getAttribute("hauteur"));
        if (hauteur <= 0)
        	throw new ExceptionXML("Erreur lors de la lecture du fichier : La hauteur du plan doit etre positive");
        int largeur = Integer.parseInt(noeudDOMRacine.getAttribute("largeur"));
        if (largeur <= 0)
        	throw new ExceptionXML("Erreur lors de la lecture du fichier : La largeur du plan doit etre positive");
       	plan.reset(largeur,hauteur);
       	NodeList listeCercles = noeudDOMRacine.getElementsByTagName("cercle");
       	for (int i = 0; i < listeCercles.getLength(); i++) {
        	plan.ajoute(creeCercle((Element) listeCercles.item(i)));
       	}
       	NodeList listeRectangles = noeudDOMRacine.getElementsByTagName("rectangle");
       	for (int i = 0; i < listeRectangles.getLength(); i++) {
          	plan.ajoute(creeRectangle((Element) listeRectangles.item(i)));
       	}
    }
    
    private static Cercle creeCercle(Element elt) throws ExceptionXML{
   		int x = Integer.parseInt(elt.getAttribute("x"));
   		int y = Integer.parseInt(elt.getAttribute("y"));
   		Point p = PointFactory.creePoint(x, y);
   		if (p == null)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Coordonnees d'un point en dehors du plan");
   		int rayon = Integer.parseInt(elt.getAttribute("rayon"));
   		if (rayon <= 0)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Cercle de rayon negatif ou nul");
   		return new Cercle(p, rayon);
    }
    
    private static Rectangle creeRectangle(Element elt) throws ExceptionXML{
   		int x = Integer.parseInt(elt.getAttribute("x"));
   		int y = Integer.parseInt(elt.getAttribute("y"));
   		Point p = PointFactory.creePoint(x, y);
   		if (p == null)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Coordonnees d'un point en dehors du plan");
      	int largeurRectangle = Integer.parseInt(elt.getAttribute("largeur"));
   		if (largeurRectangle <= 0)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Rectangle de largeur negative ou nulle");
      	int hauteurRectangle = Integer.parseInt(elt.getAttribute("hauteur"));
   		if (hauteurRectangle <= 0)
   			throw new ExceptionXML("Erreur lors de la lecture du fichier : Rectangle de hauteur negative ou nulle");
   		return new Rectangle(p, largeurRectangle, hauteurRectangle);
    }*/
    
 
}
