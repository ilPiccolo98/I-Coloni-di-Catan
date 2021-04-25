package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import utility.Controller;
import utility.InfoCarte;
import utility.InterazioneDatabase;

public class CarteTableModel extends AbstractTableModel
{

	public CarteTableModel(Controller controller, int idTurno, int idGiocatore)
	{
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		righe = new ArrayList<InfoCarte>();
	}
	
	public int getRowCount() 
	{
		return righe.size();
	}

	public int getColumnCount() 
	{
		return colonne.length;
	}
	
	public String getColumnName(int colonna)
	{
		return colonne[colonna];
	}

	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		InfoCarte obj = righe.get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return obj.getIdCarta();
			case 1:
				return obj.isCartaRisorsa();
			case 2:
				return obj.isCartaProgresso();
			case 3:
				return obj.isCartaCavaliere();
			case 4:
				return obj.isCartaPuntoVittoria();
			case 5:
				return obj.getTurnoDiAcquisizione();
		}
		return null;
	}
	
	public void refresh() throws SQLException
	{
		righe = InterazioneDatabase.getInfoCarte(controller, idGiocatore, idTurno);
		this.fireTableDataChanged();
	}
	
	private String[] colonne = { "idCarta", "Carta Risorsa", "Carta Progresso", "Carta Cavaliere", "Carta Punto Vittoria", "Turno di acquisizione" };
	private ArrayList<InfoCarte> righe;
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
}
