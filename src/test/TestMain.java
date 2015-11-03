package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import xml.ExceptionXML;
import modele.Plan;

public class TestMain {

	public static void main(String[] args) {
		Plan plan = new Plan();
		try {
			plan.chargerPlan();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExceptionXML e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello world");
	}

}
