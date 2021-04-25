package gui;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import utility.Controller;
import utility.InfoCarteRicevute;
import utility.InterazioneDatabase;

public class CarteRicevuteTableModel extends AbstractTableModel
{
	public CarteRicevuteTableModel(Controller controller, int idScambio)
	{
		this.controller = controller;
		this.idScambio = idScambio;
		righe = new ArrayList<InfoCarteRicevute>();
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
		InfoCarteRicevute info = righe.get(rowIndex);
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
		righe = InterazioneDatabase.getInfoCarteRicevute(controller, idScambio);
		this.fireTableDataChanged();
	}
	
	public void clear()
	{
		righe = new ArrayList<InfoCarteRicevute>();
		this.fireTableDataChanged();
	}
	
	private String[] colonne = { "Id ", "Id Carta Risorsa", "Tipo" };
	Controller controller;
	private int idScambio;
	private ArrayList<InfoCarteRicevute> righe;
}
