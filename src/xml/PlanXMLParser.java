package xml;

import java.lang.String;
import java.text.NumberFormat;
import java.text.ParseException;
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
	
	public static Tournee chargerLivraison(Plan p) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		//TO DO
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
