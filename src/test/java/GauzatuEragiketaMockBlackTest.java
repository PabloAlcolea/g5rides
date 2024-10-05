import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class GauzatuEragiketaMockBlackTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	@Mock
	TypedQuery<Double> typedQuery;
	@Mock
	TypedQuery<Driver> typedQueryDriver;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
				.thenReturn(entityManagerFactory);

		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut = new DataAccess(db);
	}

	@After
	public void tearDown() {
		persistenceMock.close();
	}

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
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = (Double) null;
			boolean deposit = true;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			// Si continua, el test habra fallado
			fail();
		} catch (RuntimeException e) {
			// Si lanza exc., superado
			assertTrue(true);
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Solo el valor deposit es nulo. Para superar el test,
	 * debe saltar una excepcion y la BD no debe cambiar.
	 */
	public void test3() {
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 100;
			boolean deposit = (Boolean) null;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			// Si continua, el test habra fallado
			fail();
		} catch (RuntimeException e) {
			// Si lanza exc., superado
			assertTrue(true);
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
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 100;
			double finalAmount = initialAmount - depositAmount;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);
			assertTrue(result); // El resultado tiene que ser true
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
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
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 200;
			double finalAmount = initialAmount - depositAmount;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);
			assertTrue(result); // El resultado tiene que ser true
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
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
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 199;
			double finalAmount = initialAmount - depositAmount;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);
			assertTrue(result); // El resultado tiene que ser true
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta. En este caso, hay menos
	 * dinero en la cuenta del que se quiere retirar. Para superar el test, debe
	 * saltar una excepcion. VALOR LIMITE: 201
	 */
	public void test4VL3() {
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 201;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: El usuario no esta en la base de datos. Para superar el
	 * test, debe lanzarse una excepcion.
	 */
	public void test5() {
		String username = "Name1";
		try {
			// Definir parametros
			String testUsername = "Name2";
			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 100;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(testUsername, depositAmount, deposit);

			fail(); // Si continua, falla.

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se retira dinero de la cuenta. En este caso, hay menos
	 * dinero en la cuenta del que se quiere retirar. Para superar el test, debe
	 * saltar una excepcion.
	 */
	public void test6() {
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 300;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. Para superar el test, deberia saltar una excepcion.
	 */
	public void test7() {
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = -100;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. Para superar el test, deberia saltar una excepcion. VALOR LIMITE:
	 * 0
	 */
	public void test7VL1() {
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 0;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. En este caso el valor limite es 1, por lo que la operacion debe
	 * completarse con exito. VALOR LIMITE: 1
	 */
	public void test7VL2() {
		String username = "Name1";
		boolean result;
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = 1;
			double finalAmount = initialAmount - depositAmount;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);
			assertTrue(result); // El resultado tiene que ser true
			assertTrue(d.getMoney() == finalAmount); // La BD se tiene que actualizar

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

	@Test
	/*
	 * sut.gauzatuEragiketa: Se introduce un valor negativo como valor a retirar /
	 * depositar. Para superar el test, deberia saltar una excepcion. VALOR LIMITE:
	 * -1
	 */
	public void test7VL3() {
		String username = "Name1";
		try {
			// Definir parametros

			String password = "pass";
			double initialAmount = 200;
			double depositAmount = -1;
			boolean deposit = false;

			// Configurar el mock para que devuelva el Driver
			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			sut.gauzatuEragiketa(username, depositAmount, deposit);

			fail(); // Si continua, falla

		} catch (Exception e) {
			assertTrue(true); // Si lanza exc., superado
		} finally {
			sut.close();
		}
	}

}
