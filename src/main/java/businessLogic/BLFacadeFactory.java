package businessLogic;

import java.net.URL;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BLFacadeFactory {

//	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

	public BLFacadeImplementation createBLFacade(ConfigXML c) {
		BLFacadeImplementation appFacadeInterface = null;
		try {

			
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			if (c.isBusinessLogicLocal()) {

				DataAccess da = new DataAccess();				
				
				appFacadeInterface = new BLFacadeImplementation(da);
				
				

			}

			else { // If remote

				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
						+ c.getBusinessLogicName() + "?wsdl";

				URL url = new URL(serviceName);

				// 1st argument refers to wsdl document above
				// 2nd argument is service name, refer to wsdl document above
				QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

				Service service = Service.create(url, qname);

				appFacadeInterface = (BLFacadeImplementation) service.getPort(BLFacade.class);
				
			}

		} catch (Exception e) {
			// a.jLabelSelectOption.setText("Error: "+e.toString());
			// a.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		return appFacadeInterface;
	}

	// *******************************************************************************************//
	// **************ESTO ES LO QUE HICISTEIS VOSOTROS EN CLASE, LO DEJO POR SI
	// ACASO*************//
	// *******************************************************************************************//

//	public BLFacadeImplementation createBLFacade(ConfigXML c) {
//		if (c.isBusinessLogicLocal()) {
//
//			DataAccess da = new DataAccess();
//
//			return new BLFacadeImplementation(da);
//
//		}
//
//		else { // If remote
//
//			String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
//					+ c.getBusinessLogicName() + "?wsdl";
//
//			URL url = new URL(serviceName);
//
//			// 1st argument refers to wsdl document above
//			// 2nd argument is service name, refer to wsdl document above
//			QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
//
//			Service service = Service.create(url, qname);
//
//			appFacadeInterface = service.getPort(BLFacade.class);
//		}
//	}
}
