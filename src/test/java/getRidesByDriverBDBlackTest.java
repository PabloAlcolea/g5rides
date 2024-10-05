import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class getRidesByDriverBDBlackTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	@Test
	/*
	 *  username ∉  ^[A-Z][a-zñáéíóúü]*(\s[A-Z][azñáéíóúü]*)*$ 
	 */
	public void test1() {
		try {
			// Definir parametros
			String username = "Gorka12";
			

			// Invocar al sut
			sut.open();
			sut.getRidesByDriver(username);

			// Si continua, el test habra fallado
			fail();
		} catch (Exception e) {
			// Si lanza exc., superado
			assertTrue(true);
		} finally {
			sut.close();
		}
	}
	@Test
	/*
	 * username = null.
	 */
	public void test2() {
		try {
			List<Ride> r = new ArrayList<Ride>();
			// Definir parametros
			String username = null;
			
			// Invocar al sut
			sut.open();
			r = sut.getRidesByDriver(username);

			// Si continua, el test habra fallado
			assertNull(r);
		} catch (Exception e) {
			// Si lanza exc., superado
			fail();
		} finally {
			sut.close();
		}
	}


	@Test
	/*
	 * sut.gauzatuEragiketa: El usuario no esta en la base de datos. Para superar el
	 * test, debe lanzarse una excepcion.
	 */
	public void test3() {
		boolean driverCreated = false;
		String username = "Gorka";
		List<Ride> r = new ArrayList<Ride>();
		try {
			// Definir parametros
			String testUsername = "Gorka12";
			String password = "pass";
		

			// Anadir usuario a la BD
			testDA.open();
			if (!testDA.existDriver(username)) {
				testDA.createDriver(username, password);
				driverCreated = true;
			}
			testDA.close();

			// Invocar al sut
			sut.open();
			 r = sut.getRidesByDriver(testUsername);

			assertNull(r); // Si continua, falla.

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated)
				testDA.removeDriver(username);
			testDA.close();
		}
	}
	
}
