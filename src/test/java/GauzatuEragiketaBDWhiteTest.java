import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class GauzatuEragiketaBDWhiteTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	@Test
	/* sut.gauzatuEragiketa: El nombre de usuario es nulo. Para superar el test, el
	 * metodo debe lanzar una excepcion y no continuar.
	 */
	public void test1() {
		try {
			// Definir parametros
			String username = null;
			double amount = 100; // irrelevante (*)
			boolean deposit = true; // irrelevante (*)

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);

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
	/* sut.gauzatuEragiketa: El usuario no existe en la BD. Para superar el test, el
	 * la salida debe ser false. El test supone que el usuario con nombre "Name2" no
	 * existe en la BD.
	 */
	public void test2() {
		boolean result;
		try {
			// Definir parametros
			String username = "Name2";
			double amount = 100; // irrelevante (*)
			boolean deposit = true; // irrelevante (*)

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, amount, deposit);

			assertFalse(result); // Si devuelve false, superado

		} catch (Exception e) {
			fail(); // Si lanza una excepcion, el test falla
		} finally {
			sut.close();
		}
	}

	@Test
	/* sut.gauzatuEragiketa: El usuario existe en la base de datos y se hace un
	 * deposito en su cuenta. Para superar el test, la operacion debe completarse
	 * con exito y quedar reflejada en el estado final de la BD. El test supone que
	 * no existe un usuario de nombre "Name1" en la base de datos (lo crea y lo
	 * elimina para testear).
	 */
	public void test3() {
		boolean result;
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 50;
			double depositAmount = 40;
			double finalAmount = initialAmount + depositAmount;
			boolean deposit = true;

			// Anadir usuario a la BD (supondremos que es un Driver, el tipo es irrelevante
			// para esta operacion)
			testDA.open();
			if (!testDA.existDriver(username)) {
				testDA.createDriver(username, password);
				testDA.addMoneyToDriver(username, initialAmount);
				driverCreated = true;
			}
			testDA.close();

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);

			assertTrue(result); // Debe devolver true
			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // Debe actualizarse la BD

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
	/* sut.gauzatuEragiketa: El usuario extrae mas dinero del que tiene en la cuenta. Para superar
	 * el test, el metodo debe devolver True y la cuenta del usuario debe quedar a 0. El test supone que
	 * no existe un usuario de nombre "Name1" en la base de datos (lo crea y lo elimina para testear).
	 */
	public void test4() {
		boolean result;
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 50;
			double depositAmount = 60;
			double finalAmount = 0;
			boolean deposit = false;

			// Anadir usuario a la BD (supondremos que es un Driver, el tipo es irrelevante
			// para esta operacion)
			testDA.open();
			if (!testDA.existDriver(username)) {
				testDA.createDriver(username, password);
				testDA.addMoneyToDriver(username, initialAmount);
				driverCreated = true;
			}
			testDA.close();

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);

			assertTrue(result); // Debe devolver true
			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // Debe actualizarse la BD

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
	/* sut.gauzatuEragiketa: El usuario extrae menos dinero del que tiene en la cuenta. Para superar
	 * el test, la operacion debe realizarse con exito y debe quedar reflejada en el estado de la BD.
	 * El test supone que no existe un usuario de nombre "Name1" en la base de datos (lo crea y lo 
	 * elimina para testear).
	 */
	public void test5() {
		boolean result;
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 50;
			double depositAmount = 40;
			double finalAmount = initialAmount - depositAmount;
			boolean deposit = false;

			// Anadir usuario a la BD (supondremos que es un Driver, el tipo es irrelevante
			// para esta operacion)
			testDA.open();
			if (!testDA.existDriver(username)) {
				testDA.createDriver(username, password);
				testDA.addMoneyToDriver(username, initialAmount);
				driverCreated = true;
			}
			testDA.close();

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);

			assertTrue(result); // Debe devolver true
			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // Debe actualizarse la BD

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
