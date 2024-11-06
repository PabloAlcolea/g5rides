package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import businessLogic.BLFacade;
import businessLogic.BLFacadeFactory;
import businessLogic.BLFacadeImplementation;
import domain.Driver;
import domain.Ride;

public class DriverAdapter extends AbstractTableModel{
	
	private BLFacade blf;
	private Driver d;

	private List<Ride> datalist;

	public DriverAdapter(BLFacade blf,Driver d) {
		this.blf = blf;
		this.d = d;
		datalist = blf.getRidesByDriver(d.getUsername());
	}

	@Override
	public int getRowCount() {
		return datalist.size();
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ride r = datalist.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return r.getFrom();
		case 1:
			return r.getTo();
		case 2:
			return r.getDate();
		case 3:
			return r.getnPlaces();
		case 4:
			return r.getPrice();
		default:
			return null;
		}
	}

}
