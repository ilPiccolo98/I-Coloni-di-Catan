package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import utility.Controller;
import utility.InfoScambi;
import utility.InterazioneDatabase;

public class ScambiTableModel extends AbstractTableModel
{
	public ScambiTableModel(Controller controller, int idTurno)
	{
		this.controller = controller;
		this.idTurno = idTurno;
		righe = new ArrayList<InfoScambi>();
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
		InfoScambi info = righe.get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return info.getIdScambio();
			case 1:
				return info.isScambioConGiocatore();
			case 2:
				return info.isScambioConPorto();
			case 3:
				return info.isScambioConGioco();
		}
		return null;
	}
	
	public String getColumnName(int colonna)
	{
		return colonne[colonna];
	}
	
	public void refresh() throws SQLException
	{
		righe = InterazioneDatabase.getInfoScambi(controller, idTurno);
		this.fireTableDataChanged();
	}
	
	public void clear()
	{
		righe = new ArrayList<InfoScambi>();
		this.fireTableDataChanged();
	}
	
	private String[] colonne = { "Id Scambio", "Scambio con giocatore", "Scambio con porto", "Scambio con gioco" };
	private ArrayList<InfoScambi> righe;
	private Controller controller;
	private int idTurno;
}
