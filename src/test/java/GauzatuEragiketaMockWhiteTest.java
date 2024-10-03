import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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

import dataAccess.DataAccess;
import domain.Driver;
import testOperations.TestDataAccess;

public class GauzatuEragiketaMockWhiteTest {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();
	
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
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
	    sut=new DataAccess(db);
    }
	
	@After
    public  void tearDown() {
		persistenceMock.close();
    }

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
			
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
			Mockito.when(typedQuery.getSingleResult()).thenReturn(amount);

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
			
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
			Mockito.when(typedQuery.getSingleResult()).thenReturn(amount);

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

			Driver d = new Driver(username, password);
			d.setMoney(initialAmount);
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQueryDriver);		
			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);

			// Invocar al sut
			sut.open();
			result = sut.gauzatuEragiketa(username, depositAmount, deposit);

			assertTrue(result); // Debe devolver true
			assertTrue(d.getMoney() == finalAmount); // Debe actualizarse la BD

		} catch (Exception e) {
			fail(); // Si lanza una excepcion, el test falla
		} finally {
			sut.close();
		}
	}
}