package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InfoPosizioneColonia;
import utility.InfoStrada;
import utility.InterazioneDatabase;

import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiColoniaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
	private ArrayList<InfoPosizioneColonia> idVertici;
	private JComboBox comboBox;
	private int idCartaLegno;
	private int idCartaLana;
	private int idCartaGrano;
	private int idCartaArgilla;
	private int idTurnoLegno;
	private int idTurnoLana;
	private int idTurnoGrano;
	private int idTurnoArgilla;
	private ArrayList<Integer> posizioni;
	
	private boolean checkVertice(int idVertice) throws SQLException
	{
		ArrayList<Integer> lati = InterazioneDatabase.getLatiAdiacenti(controller, idVertice);
		int count = 0;
		for(int i = 0; i != lati.size(); ++i)
		{
			boolean check = InterazioneDatabase.checkEsisteStrada(controller, idTurno, lati.get(i));
			if(check)
				++count;
		}
		int cittaColonia = InterazioneDatabase.getIdCittaColonia(controller, idVertice);
		if(count <= 1 && cittaColonia == 0)
			return true;
		return false;
	}
	
	private InfoPosizioneColonia getVertice(int idStrada) throws SQLException
	{
		int idLato = InterazioneDatabase.getIdLato(controller, idStrada);
		ArrayList<Integer> vertici = InterazioneDatabase.getVerticiAdiacenti(controller, idLato);
		for(int i = 0; i != vertici.size(); ++i)
		{
			if(checkVertice(vertici.get(i)))
				return new InfoPosizioneColonia(idStrada, vertici.get(i));
		}
		return null;
	}
	
	private ArrayList<InfoPosizioneColonia> getIdsVertici() throws SQLException
	{
		ArrayList<Integer> strada = InterazioneDatabase.getIdStrade(controller, idTurno);
		ArrayList<InfoPosizioneColonia> vertici = new ArrayList<InfoPosizioneColonia>();
		for(int i = 0; i != strada.size(); ++i)
		{
			boolean checkCittaColoniaSucc = InterazioneDatabase.haColoniaSuccessiva(controller, strada.get(i));
			boolean checkStradaSucc = InterazioneDatabase.haStradaSuccessiva(controller, strada.get(i));
			if(!checkCittaColoniaSucc && !checkStradaSucc)
			{
				InfoPosizioneColonia vertice = getVertice(strada.get(i));
				vertici.add(vertice);
			}
		}
		return vertici;
	}
	
	private ArrayList<Integer> getPosizioni() throws SQLException
	{
		ArrayList<Integer> posizioni = new ArrayList<Integer>();
		for(int i = 0; i != idVertici.size(); ++i)
		{
			int posizione = InterazioneDatabase.getPosizioneVertice(controller, idVertici.get(i).getIdVertice());
			posizioni.add(posizione);
		}
		return posizioni;
	}
	
	private boolean checkQuantitaCarte() throws SQLException
	{
		int legno = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Legno");
		int argilla = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Argilla");
		int lana = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Lana");
		int grano = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		if(legno == 0 || argilla == 0 || lana == 0 || grano == 0)
			return false;
		return true;
	}
	
	private void rimuoviCarte() throws SQLException
	{
		ArrayList<Integer> carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Grano");
		idCartaGrano = carte.get(0);
		idTurnoGrano = getTurnoMinCartaRisorsa(idCartaGrano);
		carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Argilla");
		idCartaArgilla = carte.get(0);
		idTurnoArgilla = getTurnoMinCartaRisorsa(idCartaArgilla);
		carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Lana");
		idCartaLana = carte.get(0);
		idTurnoLana = getTurnoMinCartaRisorsa(idCartaLana);
		carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Legno");
		idCartaLegno = carte.get(0);
		idTurnoLegno = getTurnoMinCartaRisorsa(idCartaLegno);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idCartaGrano, idTurno);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idCartaArgilla, idTurno);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idCartaLana, idTurno);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idCartaLegno, idTurno);
	}
	
	private void aggiungiColonia() throws SQLException
	{
		if(!checkQuantitaCarte())
			JOptionPane.showMessageDialog(this, "Errore la quantità delle carte è errata");
		else
		{
			rimuoviCarte();
			int index = comboBox.getSelectedIndex();
			InterazioneDatabase.aggiungiColonia(controller, idTurno, idVertici.get(index).getIdVertice(), idVertici.get(index).getIdStrada());
			JOptionPane.showMessageDialog(this, "Colonia aggiunta con successo");
			dispose();
		}
	}
	
	private int getTurnoMinCartaRisorsa(int idCartaRisorsa) throws SQLException
	{
		int idCarta = InterazioneDatabase.getIdCarta(controller, idCartaRisorsa);
		int idTurno = InterazioneDatabase.getTurnoMinCarta(controller, idCarta);
		return idTurno;
	}
	
	private void rollback()
	{
		try
		{
			int id = InterazioneDatabase.nuovoIdCartaTurno(controller);
			InterazioneDatabase.aggiungiCartaTurno(controller, id, idCartaGrano, idTurnoGrano);
			id = InterazioneDatabase.nuovoIdCartaTurno(controller);
			InterazioneDatabase.aggiungiCartaTurno(controller, id, idCartaLegno, idTurnoLegno);
			id = InterazioneDatabase.nuovoIdCartaTurno(controller);
			InterazioneDatabase.aggiungiCartaTurno(controller, id, idCartaArgilla, idTurnoArgilla);
			id = InterazioneDatabase.nuovoIdCartaTurno(controller);
			InterazioneDatabase.aggiungiCartaTurno(controller, id, idCartaLana, idTurnoLana);
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "Fatal error");
			dispose();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public AggiungiColoniaDialog(Controller controller, int idTurno, int idGiocatore) {
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		try 
		{
			this.idVertici = getIdsVertici();
			this.posizioni = getPosizioni();
		} 
		catch (SQLException e2) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei vertici");
			dispose();
		}
		setTitle("Aggiungi Colonia");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 226, 147);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		{
			JLabel verticeColoniaLabel = new JLabel("Vertice Colonia:");
			contentPanel.add(verticeColoniaLabel);
		}
		{
			comboBox = new JComboBox(posizioni.toArray());
			contentPanel.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						try 
						{
							aggiungiColonia();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiColoniaDialog.this, "Errore nell'aggiunta della colonia");
							rollback();
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton annullaButton = new JButton("Annulla");
				annullaButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						dispose();
					}
				});
				annullaButton.setActionCommand("Cancel");
				buttonPane.add(annullaButton);
			}
		}
	}

}
