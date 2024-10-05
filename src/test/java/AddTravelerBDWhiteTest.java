import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Traveler;
import testOperations.TestDataAccess;

public class AddTravelerBDWhiteTest {
	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	@SuppressWarnings("unused")
	private Traveler traveler;

	@Test
	/**
	 * El usuario no existe en la BD y se añade correctamente. Para superar el test,
	 * el método debe continuar y no lanzar una excepción.
	 */
	public void test1() {
		String username = "Juan";
		String password = "juanpass";

		try {

			Boolean result = null;

			sut.open();
			result = sut.addTraveler(username, password);
			assertTrue(result);

		} catch (Exception e) {
			fail();

		} finally {
			sut.close();
			testDA.open();
			testDA.removeTraveler(username);
			testDA.close();
		}
	}

	@Test
	/**
	 * El usuario (Traveler) ya existe en la BD, por lo que no se añade. Para
	 * superar el test, el método debe continuar y no lanzar una excepción.
	 */
	public void test2BD() {
		String username = "user";
		String password = "user";
		Boolean result = null;

		try {
			testDA.open();
			testDA.createTraveler(username, password);
			testDA.close();

			sut.open();
			result = sut.addTraveler(username, password);
			assertFalse(result);

		} catch (Exception e) {
			fail();

		} finally {
			sut.close();
			testDA.open();
			testDA.removeTraveler(username);
			testDA.close();
		}
	}

	@Test
	/**
	 * El usuario tiene valor null. Para superar el test, el método debe lanzar una
	 * excepción.
	 */
	public void test3() {
		String username = null;
		String password = null;
		try {
			sut.open();
			sut.addTraveler(username, password);
			fail();

		} catch (Exception e) {
			assertFalse(false);

		} finally {
			sut.close();
			testDA.open();
			try {
				testDA.removeTraveler(username);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				testDA.close();
			}
		}
	}

	@Test
	/**
	 * El usuario (Rider) ya existe en la BD, por lo que no se añade. Para superar
	 * el test, el método debe continuar.
	 */
	public void test4() {
		String username = "driver";
		String password = "driver";
		Boolean result = null;
		try {
			testDA.open();
			testDA.createDriver(username, password);
			testDA.close();

			sut.open();
			result = sut.addTraveler(username, password);
			assertFalse(result);

		} catch (Exception e) {
			fail();

		} finally {
			sut.close();
			testDA.open();
			testDA.removeDriver(username);
			testDA.close();
		}
	}
}
