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
	public void test() {
		//fail("Not yet implemented");
		Adresse a = new Adresse(1, 2, 3);
		Adresse b = new Adresse(1, 2, 3);
		assert(a.equals(b));
		
	}
	
	@Test
	public void test1() {
		//fail("Not yet implemented");
		Adresse a = new Adresse(1, 2, 3);
		Adresse b = new Adresse(1, 4, 3);
		assert(a.equals(b));
		
	}

}
