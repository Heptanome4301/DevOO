package xml;

import java.lang.String;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class PlanXMLParser {
	/**
	 * Ouvre un fichier xml et cree plan a partir du contenu du fichier
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ExceptionXML
	 */
	public static void charger() throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		File xml = OuvreurDeFichiersXML.getInstance().ouvre(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
           buildCityMap(racine);
        }
        else
        	throw new ExceptionXML("Document non conforme");
	}
	
	private static void buildCityMap(Element root) throws ExceptionXML {
		NodeList nodes = root.getElementsByTagName("Noeud");
		for (int i = 0; i < nodes.getLength(); i++) {
			parseNode((Element)nodes.item(i));
		}
		NodeList sections = root.getElementsByTagName("LeTronconSortant");
		for (int i = 0; i < sections.getLength(); i++) {
			parseSection((Element)sections.item(i));
		}
	}
	
	private static void parseNode(Element elt) throws ExceptionXML {
		int id, x, y;
		id = Integer.parseInt(elt.getAttribute("id"));
		x = Integer.parseInt(elt.getAttribute("x"));
		y = Integer.parseInt(elt.getAttribute("y"));
		
		if(x<0 || y<0 || id<0)
			throw new ExceptionXML("Un des attributs est négatif!");
		
		System.out.println("Le noeud" + id +" a pour coordonnées ("+ x + "," + y + ")");
	}
	
	private static void parseSection(Element elt) throws ExceptionXML {
		int speed, length, destination;
		String name;
		speed = Integer.parseInt(elt.getAttribute("vitesse"));
		length = Integer.parseInt(elt.getAttribute("longueur"));
		destination = Integer.parseInt(elt.getAttribute("idNoeudDestination"));
		name = elt.getAttribute("nomRue");
		
		if(name == null || speed<0 || length<0 || destination<0)
			throw new ExceptionXML("Un des attributs est négatif!");
		
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
