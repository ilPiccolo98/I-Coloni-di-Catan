package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import utility.Controller;
import utility.InfoGeneraliStrada;
import utility.InterazioneDatabase;

public class StradaTableModel extends AbstractTableModel
{
	public StradaTableModel(Controller controller, int idTurno)
	{
		this.controller = controller;
		this.idTurno = idTurno;
		righe = new ArrayList<InfoGeneraliStrada>();
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
		InfoGeneraliStrada info = righe.get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return info.getId();
			case 1:
				return info.getPosizione();
			case 2:
				return info.getTurnoDiCostruzione();
		}
		return null;
	}
	
	public String getColumnName(int colonna)
	{
		return colonne[colonna];
	}
	
	public void refresh() throws SQLException
	{
		righe = InterazioneDatabase.getInfoGeneraliStrade(controller, idTurno);
		this.fireTableDataChanged();
	}
	
	public void clear()
	{
		righe = new ArrayList<InfoGeneraliStrada>();
		this.fireTableDataChanged();
	}
	
	private String[] colonne = { "Id", "Posizione", "Turno di costruzione" };
	private ArrayList<InfoGeneraliStrada> righe;
	private Controller controller;
	private int idTurno;
}
