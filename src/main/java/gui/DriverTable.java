package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import configuration.ConfigXML;
import domain.Driver;

public class DriverTable extends JFrame{
	private Driver driver;
	private JTable tabla;
	private BLFacade blf;
	
	public DriverTable(BLFacade blf, Driver driver) {
	
		super(driver.getUsername() + "'s rides ");
		this.blf = blf;
		this.setBounds(100, 100, 700, 200);
		this.driver = driver;
		DriverAdapter adapt = new DriverAdapter(blf,driver);
		tabla = new JTable(adapt);
		tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
		JScrollPane scrollPane = new JScrollPane(tabla);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	
}
