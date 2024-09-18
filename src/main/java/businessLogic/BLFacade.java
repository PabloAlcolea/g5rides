package business_Logic;

import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE; // Importación estática

import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Alert;
import domain.Booking;
import domain.Car;
import domain.Discount;
import domain.Driver;
import domain.Complaint;
import domain.Movement;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

    // Métodos de la lógica de negocio...

    public static void main(String[] args) {
        // Ejemplo de cómo podrías usar JFrame y DISPOSE_ON_CLOSE
        JFrame frame = new JFrame("JFrame");
        frame.setSize(400, 300);
        
        // Configura la operación de cierre para que al cerrar la ventana, se liberen los recursos
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        frame.setVisible(true);
    }

    // Los demás métodos de la lógica de negocio...
    
    @WebMethod
    public List<String> getDepartCities();

    @WebMethod
    public List<String> getDestinationCities(String from);

    @WebMethod
    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
            throws RideMustBeLaterThanTodayException, RideAlreadyExistException;

    @WebMethod
    public List<Ride> getRides(String from, String to, Date date);

    @WebMethod
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);

    @WebMethod
    public void initializeBD();

    public User getUser(String erab);

    public double getActualMoney(String erab);

    public boolean isRegistered(String erab, String passwd);

    public Driver getDriver(String erab);

    public Traveler getTraveler(String erab);

    public String getMotaByUsername(String erab);

    public boolean addDriver(String username, String password);

    public boolean addTraveler(String username, String password);

    public boolean gauzatuEragiketa(String username, double amount, boolean b);

    public boolean bookRide(String username, Ride ride, int seats, double desk);

    public List<Movement> getAllMovements(User user);

    public void addMovement(User user, String eragiketa, double amount);

    public List<Booking> getBookedRides(String username);

    public void updateTraveler(Traveler traveler);

    public void updateDriver(Driver driver);

    public void updateUser(User user);

    public List<Booking> getPastBookedRides(String username);

    public void updateBooking(Booking booking);

    public List<Booking> getBookingFromDriver(String username);

    public List<Ride> getRidesByDriver(String username);

    public void cancelRide(Ride ride);

    public boolean addCar(String username, Car kotxe);

    public boolean isAdded(String username, String matr);

    public Car getKotxeByMatrikula(String matr);

    public void deleteCar(Car car);

    public boolean erreklamazioaBidali(String username1, String username2, Date gaur, Booking book, String textua,
            boolean aurk);

    public void updateComplaint(Complaint erreklamazioa);

    public void createDiscount(Discount di);

    public List<Discount> getAllDiscounts();

    public void deleteDiscount(Discount dis);

    public void updateDiscount(Discount dis);

    public Discount getDiscount(String desk);

    public List<User> getUserList();

    public void deleteUser(User us);

    public List<Alert> getAlertsByUsername(String username);

    public Alert getAlert(int alertNumber);

    public void updateAlert(Alert alert);

    public boolean updateAlertaAurkituak(String username);

    public boolean createAlert(Alert newAlert);

    public boolean deleteAlert(int alertNumber);

    public Complaint getComplaintsByBook(Booking bo);
}
