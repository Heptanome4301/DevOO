package modele;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class AdresseTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals() {
		//fail("Not yet implemented");
		Adresse a = new Adresse(1, 2, 3);
		Adresse b = new Adresse(1, 54, 3);
		Adresse c = new Adresse(2,5,3);
		assertTrue("test d'egalite",a.equals(b));
		assertFalse("test d'inegalite",a.equals(c));
	}
	
	@Test
	public void testAjouterTroncon() {
		Adresse b = new Adresse(1, 54, 3);
		Adresse c = new Adresse(2, 5 , 3);
		int idC = c.getId(); 
		Troncon t = new Troncon("nom de rue", 10, 10, b, c);
		b.ajouterTroncon(t);
	/*	for(Troncon t : b.getTroncons()) {
			
		}*/
		
	}
	
	


}
