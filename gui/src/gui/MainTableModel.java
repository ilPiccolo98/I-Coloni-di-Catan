package gui;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import utility.Controller;
import utility.InfoGenerali;
import utility.InterazioneDatabase;

public class MainTableModel extends AbstractTableModel
{

	public MainTableModel(Controller controller, int idPartita) throws SQLException
	{
		this.controller = controller;
		this.idPartita = idPartita;
		righe = new ArrayList<InfoGenerali>();
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
		if(righe != null)
		{
			InfoGenerali info = righe.get(rowIndex);
			switch(columnIndex)
			{
				case 0:
					return info.getIdTurno();
				case 1:
					return info.getGiro();
				case 2:
					return info.getIdGiocatore();
				case 3:
					return info.getPosizione();
				case 4:
					return info.getDadoUno();
				case 5:
					return info.getDadoDue();
				case 6:
					return info.getColonieCitta();
				case 7:
					return info.getStrade();
				case 8:
					return info.getCarte();
				case 9:
					return info.getScambi();
				case 10:
					return info.getPunti();
			}
		}
		return null;
	}
	
	public String getColumnName(int colonna)
	{
		return colonne[colonna];
	}
	
	public void refresh() throws SQLException
	{
		righe = InterazioneDatabase.getInfoGenerali(controller, idPartita);
		this.fireTableDataChanged();
	}
	
	public void setIdPartita(int idPartita)
	{
		this.idPartita = idPartita;
	}
	
	public int getIdPartita()
	{
		return idPartita;
	}
	
	public void clear()
	{
		righe = new ArrayList<InfoGenerali>();
		this.fireTableDataChanged();
	}
	
	private String[] colonne = {"idTurno", "Giro", "idGiocatore", "Posizione Brigante", "Dado Uno", "Dado Due", "ColonieCitta'", "Strade", "Carte", "Scambi", "Punti"};
	private ArrayList<InfoGenerali> righe;
	private Controller controller;
	private int idPartita;
}
