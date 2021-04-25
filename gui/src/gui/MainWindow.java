package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InfoGenerali;
import utility.InterazioneDatabase;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem mntmNewMenuItem;
	private Controller controller;
	private JMenu infoMenu;
	private JMenuItem infoPartitaMenuItem;
	private JMenu opzioniMenu;
	private JMenuItem selezionaPartitaMenuItem;
	private int idPartita = -1;
	private JMenuItem creaPartitaMenuItem;
	private JScrollPane scrollPane;
	private MainTableModel tableModel;
	private JTable table;
	private JMenuItem mntmAggiungiTurno;
	private JMenuItem riepilogoCarteMenuItem;
	private JMenuItem mntmEliminaUltimoTurno;
	private JMenuItem mntmEliminaPartita;
	private JMenuItem mntmCostiDiSviluppo;
	
	private void apriConnessione(String nomeHost, String nomeDatabase, String nomeUtente, String password)
	{
		try 
		{
			controller.chiudiConnessione();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(MainWindow.this, "Impossibile chiudere la connessione");
		}
		try 
		{
			controller.connettiAlDatabase(nomeHost, nomeDatabase, nomeUtente, password);
			JOptionPane.showMessageDialog(MainWindow.this, "Connessione riuscita");
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(MainWindow.this, "Connessione non riuscita");
		}
	}
	
	private void apriSelezionaPartita()
	{
		if(controller.esisteConnessione())
		{
			SelezionaPartitaDialog s = new SelezionaPartitaDialog(controller);
			s.setVisible(true);
			if(s.getConfermato())
			{
				idPartita = s.getPartitaSelezionata();
				try 
				{
					tableModel.setIdPartita(idPartita);
					tableModel.refresh();
				} 
				catch (SQLException e) 
				{
					JOptionPane.showMessageDialog(this, "Errore nel caricamento dei dati");
					dispose();
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Connessione con il database inesistente");
		}
	}
	
	private void apriInfoPartita()
	{
		if(!controller.esisteConnessione())
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Connettersi prima al database");
		else if(idPartita == -1)
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Selezionare prima una partita");
		else
		{
			InfoPartitaDialog info = new InfoPartitaDialog(controller, idPartita);
			info.setVisible(true);
		}
	}
	
	private void apriAggiungiTurno()
	{
		if(!controller.esisteConnessione())
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Connettersi prima al database");
		else if(idPartita == -1)
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Selezionare prima una partita");
		else
		{
			AggiungiTurnoDialog dialog = new AggiungiTurnoDialog(controller, idPartita);
			dialog.setVisible(true);
			if(dialog.getProsegui())
			{
				try 
				{
					tableModel.refresh();
				} 
				catch (SQLException e) 
				{
					JOptionPane.showMessageDialog(this, "Errore nel caricamento dei dati");
				}
			}
		}
	}
	
	private void rollbackNuovaPartita(CreaPartitaDialog dialog)
	{
		try 
		{
			InterazioneDatabase.rimuoviPartita(controller, dialog.getIdPartita());
			if(dialog.giocatoreUnoNuovo())
				InterazioneDatabase.rimuoviGiocatore(controller, dialog.getIdGiocatoreUno());
			if(dialog.giocatoreDueNuovo())
				InterazioneDatabase.rimuoviGiocatore(controller, dialog.getIdGiocatoreDue());
			if(dialog.giocatoreTreNuovo())
				InterazioneDatabase.rimuoviGiocatore(controller, dialog.getIdGiocatoreTre());
			if(dialog.giocatoreQuattroNuovo())
				InterazioneDatabase.rimuoviGiocatore(controller, dialog.getIdGiocatoreQuattro());	
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Fatal Error!");
			dispose();
		}
	}
	
	private void creaPartita()
	{
		if(!controller.esisteConnessione())
			JOptionPane.showMessageDialog(this, "Errore! Connettersi prima al database");
		else
		{
			CreaPartitaDialog creaPartita = new CreaPartitaDialog(controller);
			creaPartita.setVisible(true);
			if(creaPartita.getProsegui())
			{
				AggiungiColonieStradeDialog aggiungiColonieStrade = new AggiungiColonieStradeDialog(creaPartita.getIdPartita(), controller, creaPartita.getIdGiocatoreUno(), creaPartita.getIdGiocatoreDue(), creaPartita.getIdGiocatoreTre(), creaPartita.getIdGiocatoreQuattro());
				aggiungiColonieStrade.setVisible(true);
				if(aggiungiColonieStrade.getProsegui())
				{
					idPartita = creaPartita.getIdPartita();
					tableModel.setIdPartita(idPartita);
					JOptionPane.showMessageDialog(this, "Partita aggiunta con successo");
				}
				else
					rollbackNuovaPartita(creaPartita);
			}
			else
				rollbackNuovaPartita(creaPartita);
		}
	}
	
	private void apriInfoCarteDialog(int riga)
	{
		int idTurno = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
		int idGiocatore = Integer.parseInt(tableModel.getValueAt(riga, 2).toString());
		InfoCarteDialog info = new InfoCarteDialog(controller, idTurno, idGiocatore);
		info.setVisible(true);
		try 
		{
			tableModel.refresh();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento delle carte");
		}
	}
	
	private void apriInfoScambiDialog(int riga)
	{
		int idTurno = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
		int idGiocatore = Integer.parseInt(tableModel.getValueAt(riga, 2).toString());
		InfoScambiDialog info = new InfoScambiDialog(controller, idTurno, idGiocatore);
		info.setVisible(true);
		try 
		{
			tableModel.refresh();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento degli scambi");
		}
	}
	
	private void apriRiepilogoCarte() throws SQLException
	{
		if(!controller.esisteConnessione())
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Connettersi prima al database");
		else if(idPartita == -1)
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Selezionare prima una partita");
		else
		{
			int idTurno = InterazioneDatabase.getMaxTurno(controller, idPartita);
			RiepilogoCarteDialog dialog = new RiepilogoCarteDialog(controller, idPartita, idTurno);
			dialog.setVisible(true);
		}
	}
	
	private void apriInfoCittaColonieDialog(int riga)
	{
		int idTurno = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
		int idGiocatore = Integer.parseInt(tableModel.getValueAt(riga, 2).toString());
		InfoCittaColonieDialog info = new InfoCittaColonieDialog(controller, idTurno, idGiocatore);
		info.setVisible(true);
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
	
	private void apriInfoStrade(int riga)
	{
		int idGiocatore = Integer.parseInt(tableModel.getValueAt(riga, 2).toString());
		int idTurno = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
		InfoStradaDialog info = new InfoStradaDialog(controller, idTurno, idGiocatore);
		info.setVisible(true);
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
	
	private void eliminaUltimoTurno() throws SQLException
	{
		if(!controller.esisteConnessione())
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Connettersi prima al database");
		else if(idPartita == -1)
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Selezionare prima una partita");
		else
		{
			int scelta = JOptionPane.showConfirmDialog(this, "Sicuro di voler cancellare il turno?", "Cancellazione turno", JOptionPane.YES_NO_OPTION);
			if(scelta == JOptionPane.YES_OPTION)
			{
				int row = tableModel.getRowCount() - 1;
				int column = 0;
				int idTurno = (int)tableModel.getValueAt(row, column);
				InterazioneDatabase.cancellaTurno(controller, idTurno);
				JOptionPane.showMessageDialog(this, "Turno eliminato con successo");
				tableModel.refresh();
			}
		}
	}
	
	private void eliminaUltimaPartita() throws SQLException
	{
		if(!controller.esisteConnessione())
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Connettersi prima al database");
		else if(idPartita == -1)
			JOptionPane.showMessageDialog(MainWindow.this, "Errore! Selezionare prima una partita");
		else
		{
			int scelta = JOptionPane.showConfirmDialog(this, "Sicuro di voler cancellare la partita?", "Cancellazione Partita", JOptionPane.YES_NO_OPTION);
			if(scelta == JOptionPane.YES_OPTION)
			{
				InterazioneDatabase.rimuoviPartita(controller, idPartita);
				idPartita = -1;
				JOptionPane.showMessageDialog(this, "Partita cancellata correttamente");
				tableModel.clear();
			}
		}
	}
	
	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 */
	public MainWindow() throws ClassNotFoundException {
		setTitle("Coloni di Catan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 965, 612);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		mntmNewMenuItem = new JMenuItem("Connetti Database");
		mntmNewMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ConnettiDatabaseDialog c = new ConnettiDatabaseDialog();
				c.setVisible(true);
				if(c.getConfermato())
					apriConnessione(c.getNomeHost(), c.getNomeDatabase(), c.getNomeUtente(), c.getPassword());
				tableModel.clear();
			}
		});
		
		fileMenu.add(mntmNewMenuItem);
		
		infoMenu = new JMenu("Info");
		menuBar.add(infoMenu);
		
		infoPartitaMenuItem = new JMenuItem("Info Partita");
		infoPartitaMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				apriInfoPartita();
			}
		});
		infoMenu.add(infoPartitaMenuItem);
		
		riepilogoCarteMenuItem = new JMenuItem("Riepilogo Carte");
		infoMenu.add(riepilogoCarteMenuItem);
		
		mntmCostiDiSviluppo = new JMenuItem("Costi di costruzione");
		mntmCostiDiSviluppo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				CostiDiCostruzioneDialog dialog = new CostiDiCostruzioneDialog();
				dialog.setVisible(true);
			}
		});
		infoMenu.add(mntmCostiDiSviluppo);
		riepilogoCarteMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					apriRiepilogoCarte();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(MainWindow.this, "Errore nel caricamento dei dati");
				}
			}
		});
		
		opzioniMenu = new JMenu("Opzioni");
		menuBar.add(opzioniMenu);
		
		selezionaPartitaMenuItem = new JMenuItem("Seleziona Partita");
		selezionaPartitaMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				apriSelezionaPartita();
			}
		});
		
		creaPartitaMenuItem = new JMenuItem("Crea Partita");
		creaPartitaMenuItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				creaPartita();
				tableModel.clear();
			}
		});
		opzioniMenu.add(creaPartitaMenuItem);
		
		opzioniMenu.add(selezionaPartitaMenuItem);
		
		mntmAggiungiTurno = new JMenuItem("Aggiungi Turno");
		mntmAggiungiTurno.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				apriAggiungiTurno();
			}
		});
		opzioniMenu.add(mntmAggiungiTurno);
		
		mntmEliminaUltimoTurno = new JMenuItem("Elimina Ultimo Turno");
		mntmEliminaUltimoTurno.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					eliminaUltimoTurno();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(MainWindow.this, "Errore nella cancellazione");
				}
			}
		});
		opzioniMenu.add(mntmEliminaUltimoTurno);
		
		mntmEliminaPartita = new JMenuItem("Elimina Partita");
		mntmEliminaPartita.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					eliminaUltimaPartita();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(MainWindow.this, "Errore nella cancellazione della partita");
				}
			}
		});
		opzioniMenu.add(mntmEliminaPartita);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		controller = new Controller();
		
		try 
		{
			tableModel = new MainTableModel(controller, idPartita);
		} 
		catch (SQLException e1) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento della tabella");
			dispose();
		}
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == e.BUTTON1 && e.getClickCount() == 2)
				{
					InfoGiocatoreDialog infoGiocatore = null;
					int colonna = table.getSelectedColumn();
					int riga = table.getSelectedRow();
					int obj = Integer.parseInt(table.getValueAt(riga, colonna).toString());
					switch(colonna)
					{
						case 0:
							break;
						case 1:
							break;
						case 2:
							infoGiocatore = new InfoGiocatoreDialog(controller, idPartita, obj);
							infoGiocatore.setVisible(true);
							break;
						case 6:
							apriInfoCittaColonieDialog(riga);
							break;
						case 7:
							apriInfoStrade(riga);
							break;
						case 8:
							apriInfoCarteDialog(riga);
							break;
						case 9:
							apriInfoScambiDialog(riga);
							break;
					}
				}
			}
		});

		scrollPane.setViewportView(table);
	}

}
