import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ExceptionXML;
import xml.PlanXMLParser;
import modele.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Plan p = new Plan();
		try {
			PlanXMLParser.charger(p);
		} catch (ParserConfigurationException | SAXException | IOException
				| ExceptionXML e) {
			e.printStackTrace();
		}
	}

}
