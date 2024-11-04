package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businessLogic.BLFacadeFactory;
import configuration.ConfigXML;
import domain.Driver;

public class DriverTable extends JFrame {
	private Driver driver;
	private JTable tabla;
	
	public DriverTable(Driver driver) {
		super(driver.getUsername() + "'s rides ");
		this.setBounds(100, 100, 700, 200);
		this.driver = driver;
		DriverAdapter adapt = new DriverAdapter(driver);
		tabla = new JTable(adapt);
		tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPane = new JScrollPane(tabla);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		boolean isLocal = true;
		BLFacadeFactory blFacade = new BLFacadeFactory();
		//Aquí habría que conseguir que el método createBLFacade de la Factory entienda que es local.
		ConfigXML c = new ConfigXML();
		c.setBusinessLogicLocal(isLocal); //He tenido que hacer que ConfigXML sea public y generar el setter.
		Driver d = blFacade.getDriver("Urtzi"); //PARA HACER ALGO, HAY QUE CONSEGUIR QUE BLFacade Y BLFacadeFactory ESTÉN RELACIONADOS.
		DriverTable dt = new DriverTable(d);
		dt.setVisible(true);
	}
}
