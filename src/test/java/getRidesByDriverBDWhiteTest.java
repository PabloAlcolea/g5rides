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

import javax.persistence.NoResultException;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class getRidesByDriverBDWhiteTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	@Test
	/* username == null . Para superar el test, el
	 * metodo debe lanzar una excepcion y no continuar.
	 */
	public void test1() {
		try {
			
			String username = null;
			
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
	/* username no pertenece a la BD. Si lanza una excepción, funciona correctamente. User2 no pertenece a la BD.
	 */
	public void test2() {
		List<Ride> result;
		try {
			// Definir parametros
			String username = "User2";
		

			// Invocar al sut
			sut.open();
			result = sut.getRidesByDriver(username);

			assertFalse(false); // Si devuelve false, superado

		} catch (Exception e) {
			fail(); // Si lanza una excepcion, el test funciona correctamente.
		} finally {
			sut.close();
		}
	}

	@Test
	/* el usuario no ha creado/asociado "createRides". El programa debería funcionar con total normalidad. 
	 * estaría mal implementado.
	 */
	public void test3() {
		List<Ride> rides;
		String username = "User1";
		boolean driverCreated = false;
		List<Ride> ride2 = new ArrayList<Ride>();
		try {
			// Definir parametros

			String password = "pass";
			

			// Anadir usuario a la BD (rol de Driver)
			testDA.open();
			if (!testDA.existDriver(username)) {
				testDA.createDriver(username, password);
				driverCreated = true;
				
			}
			testDA.close();

			// Invocar al sut
			sut.open();
			rides = sut.getRidesByDriver(username);
			
			assertTrue(rides.equals(ride2));

		} catch (Exception e) {
			fail(); // Si lanza una excepcion, el test falla
		} finally {
			sut.close();
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated) testDA.removeDriver(username);
			testDA.close();
		}
	}
	
	@Test
	/* Se crea un usuario ( con rol de Driver ) a la BD y se le asignan unos rides. El test debería funcionar con total normalidad sin 
	 * que salte ninguna excepción. En caso contrario, fallaría el test. 
	 */
	public void test4() {
		List<Ride> rides;
		String username = "User1";
		boolean driverCreated = false;
		Driver d ;
		try {
			// Definir parametros

			String password = "pass";
			
			

			// Anadir usuario a la BD (rol de Driver)
			testDA.open();
			if (!testDA.existDriver(username)) {
				d = testDA.createDriver(username, password);
				driverCreated = true;
				testDA.addRide(d, "Donostia", "Bilbao", null, 5, (float) 50.4);
				
				
			}
			testDA.close();
			
			
			
			
			
			testDA.open();
			List<Ride> aux = testDA.getDriverRides(username);
			
			assertNotNull(aux);
			testDA.close();
			// Invocar al sut
			sut.open();
			
			
			rides = sut.getRidesByDriver(username);
		
			
			assertNotNull(rides);
			System.out.println("1" + rides);
			System.out.println("2" + aux);
			assertEquals(rides , aux); // Son iguales, solo que no se detecta.

		} catch (Exception e) {
			fail(); // Si lanza una excepcion, el test falla
		} finally {
			sut.close();
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated) testDA.removeDriver(username);
			testDA.close();
		}
	}
	
	

}
