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

public class AddTravelerMockBlackTest {

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

//	@Test
	/**
	 * El usuario no está en la BD y lo añade correctamente. Para pasar el test,
	 * debe terminar el método.
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
	 * El usuario ya está en la BD, por lo que no lo añade. Para superar el test,
	 * debe terminar el método.
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
	 * El usuario vale null. Para que pase el test, debe saltar una excepción.
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
	 * El usuario no pertenece al alfabeto regular latino, por lo que no puede ser
	 * añadido a la DB. Para pasar el test, debe saltar una excepción.
	 */
	public void test4() {
		String username = "123";
		String password = "pass";

		try {
			
			Traveler t = new Traveler(username, password);
			Driver d = new Driver("d", "d");
			
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);
		    Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d); // Simula que el driver ya existe
			
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryTraveler);
		    Mockito.when(typedQueryTraveler.getSingleResult()).thenReturn(t); // Simula que el traveler ya existe
			
			sut.open();
			sut.addTraveler(username, password);
			fail();

		} catch (Exception e) {
			assertFalse(false);

		} finally {
			sut.close();
		}
	}
}