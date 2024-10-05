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

public class GauzatuEragiketaBDBlackTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	@Test
	/*
	 * sut.gauzatuEragiketa: Solo el nombre de usuario es nulo. Para superar el
	 * test, debe saltar una excepcion y la BD no debe cambiar.
	 */
	public void test1() {
		try {
			// Definir parametros
			String username = null;
			double amount = 100;
			boolean deposit = true;

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, amount, deposit);

			// Si continua, el test habra fallado
			fail();
		} catch (RuntimeException e) {
			// Si lanza exc., superado
			assertTrue(true);
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Solo el valor amount es nulo. Para superar el test,
	 * debe saltar una excepcion y la BD no debe cambiar.
	 */
	public void test2() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = (Double) null;
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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			// Si continua, el test habra fallado
			fail();
		} catch (RuntimeException e) {
			// Si lanza exc., superado
			assertTrue(true);
		} finally {
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated)
				testDA.removeDriver(username);
			testDA.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Solo el valor deposit es nulo. Para superar el test,
	 * debe saltar una excepcion y la BD no debe cambiar.
	 */
	public void test3() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 100;
			boolean deposit = (Boolean) null;

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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			// Si continua, el test habra fallado
			fail();
		} catch (RuntimeException e) {
			// Si lanza exc., superado
			assertTrue(true);
		} finally {
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated)
				testDA.removeDriver(username);
			testDA.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta, habiendo mas dinero en
	 * la cuenta del que se quiere retirar. Para superar el test, la operacion debe
	 * completarse con exito y debe actualizarse el dinero de la cuenta.
	 */
	public void test4() {
		boolean result;
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 100;
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
			assertTrue(result); // El resultado tiene que ser true

			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated)
				testDA.removeDriver(username);
			testDA.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta, habiendo mas dinero en
	 * la cuenta del que se quiere retirar. Para superar el test, la operacion debe
	 * completarse con exito y debe actualizarse el dinero de la cuenta. VALOR
	 * LIMITE: 200
	 */
	public void test4VL1() {
		boolean result;
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 200;
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
			assertTrue(result); // El resultado tiene que ser true

			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated)
				testDA.removeDriver(username);
			testDA.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta, habiendo mas dinero en
	 * la cuenta del que se quiere retirar. Para superar el test, la operacion debe
	 * completarse con exito y debe actualizarse el dinero de la cuenta. VALOR
	 * LIMITE: 199
	 */
	public void test4VL2() {
		boolean result;
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 199;
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
			assertTrue(result); // El resultado tiene que ser true

			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			// Borrar el usuario creado
			testDA.open();
			if (driverCreated)
				testDA.removeDriver(username);
			testDA.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta. En este caso, hay menos
	 * dinero en la cuenta del que se quiere retirar. Para superar el test, debe
	 * saltar una excepcion. VALOR LIMITE: 201
	 */
	public void test4VL3() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 201;
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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

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

	@Test
	/*
	 * sut.gauzatuEragiketa: El usuario no esta en la base de datos. Para superar el
	 * test, debe lanzarse una excepcion.
	 */
	public void test5() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros
			String testUsername = "Name2";
			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 100;
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
			sut.gauzatuEragiketa(testUsername, depositAmount, deposit);

			fail(); // Si continua, falla.

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

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta. En este caso, hay menos
	 * dinero en la cuenta del que se quiere retirar. Para superar el test, debe
	 * saltar una excepcion.
	 */
	public void test6() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 300;
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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

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

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. Para superar el test, deberia saltar una excepcion.
	 */
	public void test7() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = -100;
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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

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

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. Para superar el test, deberia saltar una excepcion. 
	 * VALOR LIMITE: 0
	 */
	public void test7VL1() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 0;
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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

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

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. En este caso el valor limite es 1, por lo que la operacion debe
	 * completarse con exito. 
	 * VALOR LIMITE: 1
	 */
	public void test7VL2() {
		boolean driverCreated = false;
		String username = "Name1";
		boolean result;
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 1;
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
			assertTrue(result); // El resultado tiene que ser true

			testDA.open();
			Driver d = testDA.getDriver(username);
			testDA.close();
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

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
	
	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. Para superar el test, deberia saltar una excepcion. 
	 * VALOR LIMITE: -1
	 */
	public void test7VL3() {
		boolean driverCreated = false;
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = -1;
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
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

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
