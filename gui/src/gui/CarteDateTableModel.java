package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import utility.Controller;
import utility.InfoCarteDate;
import utility.InfoGenerali;
import utility.InterazioneDatabase;

public class CarteDateTableModel extends AbstractTableModel
{
	public CarteDateTableModel(Controller controller, int idScambio)
	{
		this.controller = controller;
		this.idScambio = idScambio;
		righe = new ArrayList<InfoCarteDate>();
	}
	
	public int getRowCount() 
	{
		return righe.size();
	}

	public int getColumnCount() 
	{
		return colonne.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		InfoCarteDate info = righe.get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return info.getId();
			case 1:
				return info.getIdCartaRisorsa();
			case 2:
				return info.getTipo();
		}
		return null;
	}
	
	public String getColumnName(int colonna)
	{
		return colonne[colonna];
	}
	
	public void refresh() throws SQLException
	{
		righe = InterazioneDatabase.getInfoCarteDate(controller, idScambio);
		this.fireTableDataChanged();
	}
	
	public void clear()
	{
		righe = new ArrayList<InfoCarteDate>();
		this.fireTableDataChanged();
	}
	
	private String[] colonne = { "Id ", "Id Carta Risorsa", "Tipo" };
	Controller controller;
	private int idScambio;
	private ArrayList<InfoCarteDate> righe;
}
