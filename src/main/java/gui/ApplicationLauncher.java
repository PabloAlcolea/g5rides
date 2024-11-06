package gui;

import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import domain.User;
import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import businessLogic.BLFacadeImplementation;
import businessLogic.ExtendedIterator;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

//		System.out.println(c.getLocale());
//		Locale.setDefault(new Locale(c.getLocale()));
//		System.out.println("Locale: " + Locale.getDefault());
//
//		BLFacade appFacadeInterface = new BLFacadeFactory().createBLFacade(c);
//
//		if (appFacadeInterface != null) {
//			MainGUI.setBussinessLogic(appFacadeInterface);
//			MainGUI a = new MainGUI();
//			a.setVisible(true);
//		}
//
//		ExtendedIterator<String> i = appFacadeInterface.getDepartCitiesIterator();
//		String c1;
//
//		System.out.println("_____________________");
//		System.out.println("FROM\tLAST\tTO\tFIRST");
//		i.goLast(); // Go to last element
//
//		while (i.hasPrevious()) {
//			c1 = i.previous();
//			System.out.println(c1); //
//		}
//
//		System.out.println();
//		System.out.println("_____________________");
//		System.out.println("FROM\tFIRST\tTO\tLAST");
//
//		i.goFirst(); // Go to first element
//
//		while (i.hasNext()) {
//			c1 = i.next();
//			System.out.println(c1); // También aquí imprimimos la ciudad (c1), no el objeto c
//
//		}

			boolean isLocal = true;
			//BLFacadeFactory blFacade = new BLFacadeFactory();
			//Aquí habría que conseguir que el método createBLFacade de la Factory entienda que es local.
			
			c.setBusinessLogicLocal(isLocal); //He tenido que hacer que ConfigXML sea public y generar el setter.
			BLFacade blf2 = new BLFacadeFactory().createBLFacade(c);
			Driver d = blf2.getDriver("Urtzi");
			//DriverModelAdapter da = new DriverModelAdapter(blf2,d);
			 //PARA HACER ALGO, HAY QUE CONSEGUIR QUE BLFacade Y BLFacadeFactory ESTÉN RELACIONADOS.
			DriverTable dt = new DriverTable(blf2,d);
			dt.setVisible(true);
		
	

//		try {
//
//			BLFacade appFacadeInterface;
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//
//			if (c.isBusinessLogicLocal()) {
//
//				DataAccess da = new DataAccess();				
//				
//				appFacadeInterface = new BLFacadeImplementation(da);
//
//			}
//
//			else { // If remote
//
//				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
//						+ c.getBusinessLogicName() + "?wsdl";
//
//				URL url = new URL(serviceName);
//
//				// 1st argument refers to wsdl document above
//				// 2nd argument is service name, refer to wsdl document above
//				QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
//
//				Service service = Service.create(url, qname);
//
//				appFacadeInterface = service.getPort(BLFacade.class);
//			}
//
//			MainGUI.setBussinessLogic(appFacadeInterface);
//			MainGUI a = new MainGUI();
//			a.setVisible(true);
//
//		} catch (Exception e) {
//			// a.jLabelSelectOption.setText("Error: "+e.toString());
//			// a.jLabelSelectOption.setForeground(Color.RED);
//
//			System.out.println("Error in ApplicationLauncher: " + e.toString());
//		}
	// a.pack();

}
}