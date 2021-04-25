package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;

public class InfoCittaColonieDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private CittaColonieTableModel tableModel;
	private int idTurno;
	private Controller controller;
	private JMenuBar menuBar;
	private JMenu opzioniMenu;
	private JMenuItem aggiungiColoniaMenuItem;
	private int idGiocatore;
	private JMenuItem mntmAggiornaACitt;
	
	private void apriAggiungiColonia() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			AggiungiColoniaDialog a = new AggiungiColoniaDialog(controller, idTurno, idGiocatore);
			a.setVisible(true);
		}
	}
	
	private boolean checkRisorse() throws SQLException
	{
		final int granoRichesto = 2;
		final int mineraleRichesto = 3;
		int grano = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		int minerale = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Minerale");
		if(grano < granoRichesto || minerale < mineraleRichesto)
			return false;
		return true;
	}
	
	private void rimuoviRisorse() throws SQLException
	{
		final int granoRichesto = 2;
		final int mineraleRichesto = 3;
		ArrayList<Integer> risorse = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Grano");
		for(int i = 0; i != granoRichesto; ++i)
			InterazioneDatabase.rimuoviCartaRisorsa(controller, risorse.get(i), idTurno);
		risorse = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Minerale");
		for(int i = 0; i != mineraleRichesto; ++i)
			InterazioneDatabase.rimuoviCartaRisorsa(controller, risorse.get(i), idTurno);
	}

	private void aggiornaACitta() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			int riga = table.getSelectedRow();
			if(riga == -1)
				JOptionPane.showMessageDialog(this, "Errore selezionare una colonia");
			else if(Boolean.parseBoolean(tableModel.getValueAt(riga, 1).toString()))
				JOptionPane.showMessageDialog(this, "Errore selezionare una colonia");
			else if(!checkRisorse())
				JOptionPane.showMessageDialog(this, "Errore quantita di risorse errata");	
			else
			{
				rimuoviRisorse();
				int id = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
				InterazioneDatabase.aggiornaACitta(controller, id, idTurno);
				JOptionPane.showMessageDialog(this, "Colonia aggiornata con successo");
				tableModel.refresh();
			}
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public InfoCittaColonieDialog(Controller controller, int idTurno, int idGiocatore) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Info Citta Colonie");
		setBounds(100, 100, 609, 352);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tableModel = new CittaColonieTableModel(controller, idTurno);
				table = new JTable(tableModel);
				table.getTableHeader().setReorderingAllowed(false);
				scrollPane.setViewportView(table);
			}
		}
		{
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				opzioniMenu = new JMenu("Opzioni");
				menuBar.add(opzioniMenu);
				{
					aggiungiColoniaMenuItem = new JMenuItem("Aggiungi Colonia");
					aggiungiColoniaMenuItem.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							try 
							{
								apriAggiungiColonia();
								tableModel.refresh();
							} 
							catch (SQLException e1) 
							{
								JOptionPane.showMessageDialog(InfoCittaColonieDialog.this, "Errore nel caricamento dei dati");
								dispose();
							}
						}
					});
					opzioniMenu.add(aggiungiColoniaMenuItem);
				}
				{
					mntmAggiornaACitt = new JMenuItem("Aggiorna a Citt\u00E0");
					mntmAggiornaACitt.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							try 
							{
								aggiornaACitta();
							} 
							catch (SQLException e1) 
							{
								JOptionPane.showMessageDialog(InfoCittaColonieDialog.this, "Errore nell'aggiornamento a città");
							}
						}
					});
					opzioniMenu.add(mntmAggiornaACitt);
				}
			}
		}
		try 
		{
			tableModel.refresh();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei dati");
			dispose();
		}
	}

}
