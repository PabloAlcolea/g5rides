import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import domain.Ride;
import testOperations.TestDataAccess;

public class getRidesByDriverMockWhiteTest {

//	// sut:system under test
//	static DataAccess sut = new DataAccess();
//
//	// additional operations needed to execute the test
//	static TestDataAccess testDA = new TestDataAccess();
//	
//	protected MockedStatic<Persistence> persistenceMock;
//	@Mock
//	protected  EntityManagerFactory entityManagerFactory;
//	@Mock
//	protected  EntityManager db;
//	@Mock
//    protected  EntityTransaction  et;
//	@Mock
//	TypedQuery<Double> typedQuery;
//	@Mock
//	TypedQuery<Driver> typedQueryDriver;
//	
//	@Before
//    public void init() {
//        MockitoAnnotations.openMocks(this);
//        persistenceMock = Mockito.mockStatic(Persistence.class);
//		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
//        .thenReturn(entityManagerFactory);
//        
//        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
//		Mockito.doReturn(et).when(db).getTransaction();
//	    sut=new DataAccess(db);
//    }
//	
//	@After
//    public  void tearDown() {
//		persistenceMock.close();
//    }
//
//	@Test
//	/* sut.gauzatuEragiketa: El nombre de usuario es nulo. Para superar el test, el
//	 * metodo debe lanzar una excepcion y no continuar.
//	 */
//	public void test1() {
//		try {
//			// Definir parametros
//			String username = null;
//			
//			
//			
//			Mockito.when(db.createQuery(Mockito.anyString())).thenReturn(typedQueryDriver);		
//			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(null);
//			
//			// Invocar al sut
//			sut.open();
//			List<Ride> ridesByDriver = sut.getRidesByDriver(username);
//
//			// Si continua, el test habra fallado
//			assertNull(ridesByDriver);
//		} catch (RuntimeException e) {
//			// Si lanza exc., superado
//			fail();
//		} finally {
//			sut.close();
//		}
//	}
//	
//	@Test
//	/* El usuario no existe en la BD. Para superar el test, el
//	 * la salida debe ser false. El test supone que el usuario con nombre "User2" no
//	 * existe en la BD.
//	 */
//	public void test2() {
//		List<Ride> result;
//		try {
//			// Definir parametros
//			String username = "User2";
//			
//			Mockito.when(db.createQuery(Mockito.anyString())).thenReturn(typedQueryDriver);		
//			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(null);
//			
//			
//			// Invocar al sut
//			sut.open();
//			result = sut.getRidesByDriver(username);
//
//			assertNull(result); // Si devuelve false, superado
//
//		} catch (Exception e) {
//			fail(); // Si lanza una excepcion, el test funciona correctamente.
//		} finally {
//			sut.close();
//		}
//	}
//	
//	@Test
//	/* el usuario no ha creado/asociado "createRides".
//	 * 
//	 */
//	
//	public void test3() {
//		List<Ride> rides;
//		String username = "User1";
//		
//		try {
//			
//
//			
//			
//
//			// Anadir usuario a la BD (rol de Driver)
//			
//			Driver d = new Driver(username,"pass");
//			Mockito.when(db.createQuery(Mockito.anyString())).thenReturn(typedQueryDriver);		
//			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);
//			
//			// Invocar al sut
//			sut.open();
//			rides = sut.getRidesByDriver(d.getUsername());
//			
//			assertNotNull(rides);
//			d.addRide("Donostia", "bilbao",null, 0, 0);
//			
//
//		} catch (Exception e) {
//			fail(); // Si lanza una excepcion, el test falla
//		}
//		
//	}
//	@Test
//	/* Se crea un usuario ( con rol de Driver ) a la BD y se le asignan unos rides. El test debería funcionar con total normalidad sin 
//	 * que salte ninguna excepción. En caso contrario, fallaría el test. 
//	 */
//	public void test4() {
//		List<Ride> rides;
//		String username = "User1";
//		boolean driverCreated = false;
//		
//		try {
//			// Definir parametros
//
//			
//			
//			
//
//			// Anadir usuario a la BD (rol de Driver)
//			
//			
//			Driver d = new Driver(username,"pass");
//			Mockito.when(db.createQuery(Mockito.anyString())).thenReturn(typedQueryDriver);		
//			Mockito.when(typedQueryDriver.getSingleResult()).thenReturn(d);
//			// Invocar al sut
//			sut.open();
//			
//			d.addRide("Donostia","Bilbao",null,0, 0);
//			rides = sut.getRidesByDriver(d.getUsername());
//
//			assertNotNull(rides);
//
//		} catch (Exception e) {
//			fail(); // Si lanza una excepcion, el test falla
//		}
//	}
//	
	
}