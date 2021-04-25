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

import java.awt.Dialog.ModalityType;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoCarteDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JScrollPane scrollPane;
	private JTable table;
	private CarteTableModel tableModel;
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
	private JMenuBar menuBar;
	private JMenu opzioniMenu;
	private JMenuItem mntmAggiungiCartaCavaliere;
	private JMenuItem mntmAggiungiCartaPunto;
	private JMenuItem mntmAggiungiCartaProgresso;
	private JMenuItem mntmEliminaCartaProgresso;

	private void apriInfoCartaRisorsa(int riga)
	{
		boolean b = Boolean.parseBoolean(tableModel.getValueAt(riga, 1).toString());
		if(b)
		{
			int id = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
			InfoCartaRisorsa info = new InfoCartaRisorsa(controller, id);
			info.setVisible(true);
		}
	}
	
	private boolean checkQuantitaCarte() throws SQLException
	{
		int carteLana = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Lana");
		int carteMinerale = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Minerale");
		int carteGrano = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		if(carteLana == 0 || carteMinerale == 0 || carteGrano == 0)
			return false;
		return true;
	}
	
	private void rimuoviRisorse() throws SQLException
	{
		ArrayList<Integer> carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Lana");
		int idLana = carte.get(0);
		carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Minerale");
		int idMinerale = carte.get(0);
		carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Grano");
		int idGrano = carte.get(0);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idLana, idTurno);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idMinerale, idTurno);
		InterazioneDatabase.rimuoviCartaRisorsa(controller, idGrano, idTurno);
	}
	
	private void aggiungiCartaCavaliere() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			if(!checkQuantitaCarte())
				JOptionPane.showMessageDialog(this, "Errore carte risorse insufficienti");
			else
			{
				AggiungiCartaCavaliereDialog dialog = new AggiungiCartaCavaliereDialog(controller, idPartita, idGiocatore, idTurno);
				dialog.setVisible(true);
				rimuoviRisorse();
				InterazioneDatabase.aggiungiCartaCavaliere(controller, idGiocatore, idTurno);
				JOptionPane.showMessageDialog(this, "Carta cavaliere aggiunta");
			}
		}
	}

	private void aggiungiCartaPuntoVittoria() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			if(!checkQuantitaCarte())
				JOptionPane.showMessageDialog(this, "Errore carte risorse insufficienti");
			else
			{
				rimuoviRisorse();
				InterazioneDatabase.aggiungiCartaPuntoVittoria(controller, idGiocatore, idTurno);
				JOptionPane.showMessageDialog(this, "Carta Punto Vittoria aggiunta");
			}
		}
	}

	private void aggiungiCartaProgresso() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			if(!checkQuantitaCarte())
				JOptionPane.showMessageDialog(this, "Errore carte risorse insufficienti");
			else
			{
				rimuoviRisorse();
				InterazioneDatabase.aggiungiCartaProgresso(controller, idGiocatore, idTurno);
				JOptionPane.showMessageDialog(this, "Carta progresso aggiunta");
			}
		}
	}
	
	private void rimuoviCartaProgresso() throws SQLException
	{
		int riga = table.getSelectedRow();
		int colonna = 2;
		if(riga >= 0)
		{
			boolean value = Boolean.parseBoolean(tableModel.getValueAt(riga, colonna).toString());
			if(value)
			{
				int idCarta = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
				InterazioneDatabase.rimuoviCarta(controller, idCarta, idTurno);
				JOptionPane.showMessageDialog(this, "Carta progresso rimossa con successo");
				tableModel.refresh();
			}
			else
				JOptionPane.showMessageDialog(this, "Errore selezionare una carta progresso");
		}
		else
			JOptionPane.showMessageDialog(this, "Errore selezionare una carta progresso");
	}

	/**
	 * Create the dialog.
	 */
	public InfoCarteDialog(Controller controller, int idTurno, int idGiocatore) 
	{
		setResizable(false);
		setTitle("Info Carte");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 670, 320);
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		tableModel = new CarteTableModel(controller, idTurno, idGiocatore);
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == e.BUTTON1 && e.getClickCount() == 2)
				{
					int colonna = table.getSelectedColumn();
					int riga = table.getSelectedRow();
					if(colonna == 1)
						apriInfoCartaRisorsa(riga);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		opzioniMenu = new JMenu("Opzioni");
		menuBar.add(opzioniMenu);
		
		mntmAggiungiCartaCavaliere = new JMenuItem("Aggiungi Carta Cavaliere");
		mntmAggiungiCartaCavaliere.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					aggiungiCartaCavaliere();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nell'aggiunta della carta");				
					dispose();
				}
				try 
				{
					tableModel.refresh();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nel caricamento dei dati");				
					dispose();
				}
			}
		});
		opzioniMenu.add(mntmAggiungiCartaCavaliere);
		
		mntmAggiungiCartaPunto = new JMenuItem("Aggiungi Carta Punto Vittoria");
		mntmAggiungiCartaPunto.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					aggiungiCartaPuntoVittoria();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nell'aggiunta della carta");				
					dispose();
				}
				try 
				{
					tableModel.refresh();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nel caricamento dei dati");				
					dispose();
				}
			}
		});
		opzioniMenu.add(mntmAggiungiCartaPunto);
		
		mntmAggiungiCartaProgresso = new JMenuItem("Aggiungi Carta Progresso");
		mntmAggiungiCartaProgresso.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					aggiungiCartaProgresso();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nell'aggiunta della carta");				
					dispose();
				}
				try 
				{
					tableModel.refresh();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nel caricamento dei dati");				
					dispose();
				}
			}
		});
		opzioniMenu.add(mntmAggiungiCartaProgresso);
		
		mntmEliminaCartaProgresso = new JMenuItem("Elimina Carta Progresso");
		mntmEliminaCartaProgresso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					rimuoviCartaProgresso();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoCarteDialog.this, "Errore nella cancellazione della carta");
				}
			}
		});
		opzioniMenu.add(mntmEliminaCartaProgresso);
		try 
		{
			tableModel.refresh();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento delle carte");
			dispose();
		}
	}

}
