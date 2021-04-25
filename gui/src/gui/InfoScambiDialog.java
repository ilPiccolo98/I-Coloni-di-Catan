package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.GridBagLayout;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoScambiDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ScambiTableModel tableModel;
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
	private JTable table;

	private void apriInfoScambioGioco(int idScambio)
	{
		InfoCarteScambiateDialog i = new InfoCarteScambiateDialog(controller, idScambio, "ScambioGioco");
		i.setVisible(true);
	}
	
	private void apriInfoScambioGiocatore(int idScambio)
	{
		InfoCarteScambiateDialog i = new InfoCarteScambiateDialog(controller, idScambio, "ScambioGiocatore");
		i.setVisible(true);
	}
	
	private void apriInfoScambioPorto(int idScambio)
	{
		InfoCarteScambiateDialog i = new InfoCarteScambiateDialog(controller, idScambio, "ScambioPorto");
		i.setVisible(true);	
	}
	
	private void apriScambioGiocatoreDialog() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			AggiungiScambioGiocatoreDialog dialog = new AggiungiScambioGiocatoreDialog(controller, idTurno, idGiocatore);
			dialog.setVisible(true);

		}
	}
	
	private void apriScambioPortoDialog() throws SQLException
	{
		int count = InterazioneDatabase.countCittaColonieAdiacentiPorti(controller, idTurno);
		if(count == 0)
			JOptionPane.showMessageDialog(this, "Errore il giocatore non ha città-colonie adiacenti a porti");
		else
		{
			int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
			int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
			if(idTurnoMax != idTurno)
				JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
			else
			{
				AggiungiScambioPorto dialog = new AggiungiScambioPorto(controller, idTurno, idGiocatore);
				dialog.setVisible(true);
			}
		}
	}
	
	private void apriScambioGiocoDialog() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			AggiungiScambioConGiocoDialog dialog = new AggiungiScambioConGiocoDialog(controller, idTurno, idGiocatore);
			dialog.setVisible(true);
		}
	}
	

	/**
	 * Create the dialog.
	 */
	public InfoScambiDialog(Controller controller, int idTurno, int idGiocatore) {
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		setTitle("Info Scambi");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		tableModel = new ScambiTableModel(controller, idTurno);
		setBounds(100, 100, 581, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tableModel = new ScambiTableModel(controller, idTurno);
				table = new JTable(tableModel);
				table.getTableHeader().setReorderingAllowed(false);
				table.addMouseListener(new MouseAdapter() 
				{
					public void mouseClicked(MouseEvent e) 
					{
						if(e.getClickCount() == 2 && e.getButton() == e.BUTTON1)
						{
							int riga = table.getSelectedRow();
							int colonna = table.getSelectedColumn();
							if(colonna != 0)
							{
								int idScambio = Integer.parseInt(tableModel.getValueAt(riga, 0).toString());
								boolean b = Boolean.parseBoolean(tableModel.getValueAt(riga, colonna).toString());
								if(colonna == 1 && b)
									apriInfoScambioGiocatore(idScambio);
								else if(colonna == 2 && b)
									apriInfoScambioPorto(idScambio);
								else if(colonna == 3 && b)
									apriInfoScambioGioco(idScambio);
							}
						}
					}
				});
				scrollPane.setViewportView(table);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu aggiungiMenu = new JMenu("Aggiungi");
				menuBar.add(aggiungiMenu);
				{
					JMenuItem scambioConGiocatoreMenuItem = new JMenuItem("Scambio Con Giocatore");
					scambioConGiocatoreMenuItem.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							try 
							{
								apriScambioGiocatoreDialog();
								tableModel.refresh();
							} 
							catch (SQLException e1) 
							{
								JOptionPane.showMessageDialog(InfoScambiDialog.this, "Errore nel caricamento dei dati");
								dispose();
							}
						}
					});
					aggiungiMenu.add(scambioConGiocatoreMenuItem);
				}
				{
					JMenuItem scambioConPortoMenuItem = new JMenuItem("Scambio Con Porto");
					scambioConPortoMenuItem.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							try 
							{
								apriScambioPortoDialog();
								tableModel.refresh();
							} 
							catch (SQLException e1) 
							{
								JOptionPane.showMessageDialog(InfoScambiDialog.this, "Errore nel caricamento dei dati");
								dispose();
							}
						}
					});
					aggiungiMenu.add(scambioConPortoMenuItem);
				}
				{
					JMenuItem scambioConGiocoMenuItem = new JMenuItem("Scambio Con Gioco");
					scambioConGiocoMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) 
						{
							try 
							{
								apriScambioGiocoDialog();
								tableModel.refresh();
							} 
							catch (SQLException e1) 
							{
								JOptionPane.showMessageDialog(InfoScambiDialog.this, "Errore nel caricamento dei dati");
								dispose();
							}
						}
					});
					aggiungiMenu.add(scambioConGiocoMenuItem);
				}
			}
		}
		try 
		{
			tableModel.refresh();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore con il caricamento dei dati");
			dispose();
		}
	}

}
