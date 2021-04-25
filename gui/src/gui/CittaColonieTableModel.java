package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import utility.Controller;
import utility.InfoCittaColonie;
import utility.InterazioneDatabase;

public class CittaColonieTableModel extends AbstractTableModel
{
	public CittaColonieTableModel(Controller controller, int idTurno)
	{
		this.controller = controller;
		this.idTurno = idTurno;
		info = new ArrayList<InfoCittaColonie>();
	}
	
	@Override
	public int getRowCount() 
	{
		return info.size();
	}

	@Override
	public int getColumnCount() 
	{
		return colonne.length;
	}
	
	public String getColumnName(int colonna)
	{
		return colonne[colonna];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		InfoCittaColonie obj = info.get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return obj.getId();
			case 1:
				return obj.isCitta();
			case 2:
				return obj.isColonia();
			case 3:
				return obj.getPosizione();
			case 4:
				return obj.getTurnoDiCostruzione();
		}
		return null;
	}
	
	public void refresh() throws SQLException
	{
		info = InterazioneDatabase.getInfoCittaColonie(controller, idTurno);
		this.fireTableDataChanged();
	}
	
	public void clear()
	{
		info = new ArrayList<InfoCittaColonie>();
		this.fireTableDataChanged();
	}
	
	private Controller controller;
	private int idTurno;
	private String[] colonne = { "Id", "Citta", "Colonie", "Posizione", "Turno di costruzione" };
	private ArrayList<InfoCittaColonie> info;
}
