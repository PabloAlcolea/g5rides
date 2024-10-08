import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

import businessLogic.BLFacade;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Traveler;
import testOperations.TestDataAccess;

public class AddTravelerMockWhiteTest {
	static DataAccess sut = new DataAccess();

	static TestDataAccess testDA = new TestDataAccess();

	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	@Mock
	static BLFacade appFacadeInterface = Mockito.mock(BLFacade.class);
	@Mock
	TypedQuery<Boolean> typedQuery;
	@Mock
	TypedQuery<Driver> typedQueryDriver;
	@Mock
	TypedQuery<Traveler> typedQueryTraveler;

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
	/**
	 * El usuario no existe en la BD y se añade correctamente. Para superar el test,
	 * el método debe continuar y no lanzar una excepción.
	 */
	public void test1() {
		String username = "Juan";
		String password = "juanpass";
		Boolean res = null;

		try {
			Traveler t = new Traveler(username, password);

//			Mockito.when(db.find(Traveler.class, username)).thenReturn(t);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryTraveler);
			Mockito.when(typedQuery.getSingleResult()).thenReturn(true);

			sut.open();
			res = sut.addTraveler(username, password);
			assertTrue(res);

		} catch (Exception e) {
			fail();

		} finally {
			sut.close();
		}
	}

	@Test
	/**
	 * El usuario (Traveler) ya existe en la BD, por lo que no se añade. Para
	 * superar el test, el método debe continuar y no lanzar una excepción.
	 */
	public void test2() {
		String username = "user";
		String password = "user";
		Boolean result = null;

		try {
			Traveler t = new Traveler(username, password);
			List<Traveler> tList = new ArrayList<Traveler>();
			tList.add(t);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(Traveler.class)))
					.thenReturn(typedQueryTraveler);
			Mockito.when(typedQueryTraveler.getResultList()).thenReturn(tList);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(Driver.class))).thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(null);

			sut.open();
			result = sut.addTraveler(username, password);
			System.out.println(result);
			assertFalse(result);

		} catch (Exception e) {
			fail();

		} finally {
			sut.close();
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
			Traveler t = new Traveler(username, password);

//			Mockito.when(db.find(Traveler.class, username)).thenReturn(t);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryTraveler);
			Mockito.when(typedQuery.getSingleResult()).thenReturn(true);

			sut.open();
			sut.addTraveler(username, password);
			fail();

		} catch (Exception e) {
			assertFalse(false);

		} finally {
			sut.close();
		}
	}

	@Test
	/**
	 * El usuario (Rider) ya existe en la BD, por lo que no se añade. Para superar
	 * el test, el método debe continuar.
	 */
	public void test4() {
		String username = "user";
		String password = "user";
		Boolean result = null;

		try {
			Driver d = new Driver(username, password);
			List<Driver> dList = new ArrayList<Driver>();
			dList.add(d);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(Driver.class)))
					.thenReturn(typedQueryDriver);
			Mockito.when(typedQueryDriver.getResultList()).thenReturn(dList);

			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(Traveler.class))).thenReturn(typedQueryTraveler);
			Mockito.when(typedQueryTraveler.getSingleResult()).thenReturn(null);

			sut.open();
			result = sut.addTraveler(username, password);
			System.out.println(result);
			assertFalse(result);

		} catch (Exception e) {
			fail();

		} finally {
			sut.close();
		}
	}
}
