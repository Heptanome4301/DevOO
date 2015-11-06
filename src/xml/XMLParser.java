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

public class XMLParser {
	/**
	 * Ouvre un fichier xml et cree plan a partir du contenu du fichier
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ExceptionXML
	 */
	public static void chargerPlan(Plan p,File xml) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{ 
		//xml = OuvreurDeFichiersXML.getInstance().ouvre();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        if (racine.getNodeName().equals("Reseau")) {
           construirePlan(racine, p);
        }
        else
        	throw new ExceptionXML(ExceptionXML.DOCUMENT_NON_CONFORME);
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
			throw new ExceptionXML(ExceptionXML.ATTRIBUT_NEGATIF);
		
		System.out.println("Le noeud " + id +" a pour coordonn�es ("+ x + "," + y + ")");
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
			throw new ExceptionXML(ExceptionXML.ATTRIBUT_NEGATIF);
		
		Adresse depart = p.getAdresse(parentId);
		depart.ajouterTroncon(new Troncon(nom, longueur, vitesse, depart, p.getAdresse(destination)));

		
	}
	
	

	
	public static Tournee chargerLivraison(Plan p,File xml ) throws ParserConfigurationException, SAXException, IOException, ExceptionXML{
		//xml = OuvreurDeFichiersXML.getInstance().ouvre();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
        Document document = docBuilder.parse(xml);
        Element racine = document.getDocumentElement();
        
        Tournee tournee = null  ;
        if (racine.getNodeName().equals("JourneeType")) {
            tournee = construireTournee(racine, p);
         }
         else
         	throw new ExceptionXML(ExceptionXML.DOCUMENT_NON_CONFORME);
        
		return tournee;
	}
	
	private static Tournee construireTournee(Element root, Plan p) throws ExceptionXML {
		int idEntropot = parseEntrepot(root); 
		Adresse entropot = p.getAdresse(idEntropot);
		if(entropot == null) 
			throw new ExceptionXML(ExceptionXML.ID_ENTREPOT_INVALIDE);
		else
			System.out.println("Entrepot := " + entropot.getId() );
		
		NodeList nodes = root.getElementsByTagName("Plage");
		ArrayList<FenetreLivraison> fenetresLivraison = new ArrayList<FenetreLivraison>(); 
		for (int i = 0; i < nodes.getLength(); i++) {
			fenetresLivraison.add(parseFenetre_livraison((Element)nodes.item(i)));
		}
		
		NodeList sections = root.getElementsByTagName("Livraison");
		ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
		for (int i = 0; i < sections.getLength(); i++) {
			Livraison l = parseLivraison((Element)sections.item(i), p,fenetresLivraison );
			if(livraisons.contains(l)) throw new ExceptionXML(ExceptionXML.ID_IDENTIQUES);
			livraisons.add(l);
		}
		
		return new Tournee(p,livraisons,fenetresLivraison,entropot);
	}
	
	private static int parseEntrepot(Element root) throws ExceptionXML{
		NodeList nodes = root.getElementsByTagName("Entrepot");
		if(nodes.getLength() < 1 ) throw new ExceptionXML(ExceptionXML.ENTREPOT_MANQUANT);
		if(nodes.getLength() > 1) throw new ExceptionXML(ExceptionXML.PLUSIEUR_ENTREPOTS);
		Element elt = (Element)nodes.item(0);
		return Integer.parseInt(elt.getAttribute("adresse"));
	
	}

	private static FenetreLivraison parseFenetre_livraison(Element elt) throws ExceptionXML {
		
		String Hdebut, Hfin;
		//id = Integer.parseInt(elt.getAttribute("id"));
		Hdebut = elt.getAttribute("heureDebut");
		Hfin = elt.getAttribute("heureFin");
		
	
		Date debut = paseDate(Hdebut);
		Date fin = paseDate(Hdebut);
		
		if( debut.after(fin) )
			throw new ExceptionXML(ExceptionXML.PLAGE_HORAIRE_INVALIDE);
		
		//System.out.println("La fenetre de livraison := ["+ Hdebut + "," + Hfin + "]");
		
		
		return new FenetreLivraison(debut, fin);
	}
	
	private static Date paseDate(String hdebut) throws ExceptionXML {
		// TODO Auto-generated method stub
		String[] hDebutSplitted = hdebut.split(":");
		try{
			int heure = Integer.parseInt(hDebutSplitted[0]);
			int minute = Integer.parseInt(hDebutSplitted[1]);
			int seconde = Integer.parseInt(hDebutSplitted[2]);
			int annee = 2015,mois = 1, jour = 1;
			Date dateDebut = new Date(annee, mois, jour, heure, minute, seconde);
			return dateDebut;
		} catch(Exception e){
			throw new ExceptionXML(ExceptionXML.PLAGE_HORAIRE_INVALIDE);
		}
		
	}

	private static Livraison parseLivraison(Element elt, Plan p, ArrayList<FenetreLivraison> fenetresLivraison) throws ExceptionXML {
		int idAdresse ,id;
		idAdresse = Integer.parseInt(elt.getAttribute("adresse"));
		id = Integer.parseInt(elt.getAttribute("id"));
		
		if(idAdresse<0 || id < 0 )
			throw new ExceptionXML(ExceptionXML.ATTRIBUT_NEGATIF);
		
		Adresse adresse = p.getAdresse(idAdresse);
		if(adresse == null )
			throw new ExceptionXML(ExceptionXML.ADRESSE_INVALIDE);
		
		Element elmtFenetreLiv = (Element) elt.getParentNode().getParentNode();
		FenetreLivraison fenetrelivraison = parseFenetre_livraison(elmtFenetreLiv);
		
		
		//System.out.println("Livraison � l'adresse id=" + idAdresse +" fenetreLivraison id= "+fenetrelivraison.getHeureDebut());
		
		return new Livraison(id,adresse,fenetrelivraison);
		
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
