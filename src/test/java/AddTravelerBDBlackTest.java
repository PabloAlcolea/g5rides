import static org.junit.Assert.*;

import org.junit.Test;

import dataAccess.DataAccess;
import testOperations.TestDataAccess;

public class AddTravelerBDBlackTest {
	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	@Test
	public void test1() {
		/**
		 * El usuario no está en la BD y lo añade correctamente. Para pasar el test,
		 * debe terminar el método.
		 */
		String username = "JuanBlack";
		String password = "juanpass";
		Boolean result = null;

		try {
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
	public void test2() {
		/**
		 * El usuario ya está en la BD, por lo que no lo añade. Para superar el test,
		 * debe terminar el método.
		 */
		String username = "ManuelBlack";
		String password = "manuelpass";
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

//	@Test
	public void test3() {
		/**
		 * El usuario vale null. Para que pase el test, debe saltar una excepción.
		 */
		String username = null;
		String password = null;
		Boolean result = null;

		try {
			sut.open();
			result = sut.addTraveler(username, password);
			fail();

		} catch (Exception e) {
			assertFalse(result);

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
	public void test4() {
		/**
		 * El usuario no pertenece al alfabeto regular latino, por lo que no puede ser
		 * añadido a la DB. Para pasar el test, debe saltar una excepción.
		 */
		String username = "123";
		String password = "pass";

		try {
			sut.open();
			sut.addTraveler(username, password);
			fail();

		} catch (Exception e) {
			assertFalse(false);

		} finally {
			sut.close();
			testDA.open();
			testDA.removeTraveler(username);
			testDA.close();
		}
	}
}
