package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.Dialog.ModalityType;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoStradaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JScrollPane scrollPane;
	private JTable table;
	private JMenuBar menuBar;
	private JMenu mnOpzioni;
	private JMenuItem mntmAggiungiStrada;
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
	private StradaTableModel tableModel;

	private void apriAggiungiStrada() throws SQLException
	{
		int idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
		int idTurnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		if(idTurnoMax != idTurno)
			JOptionPane.showMessageDialog(this, "Errore è possibile aggiungere un elemento solo nell'ultimo turno");
		else
		{
			AggiungiStradaDialog dialog = new AggiungiStradaDialog(controller, idTurno, idGiocatore);
			dialog.setVisible(true);
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public InfoStradaDialog(Controller controller, int idTurno, int idGiocatore) {
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		tableModel = new StradaTableModel(controller, idTurno);
		setTitle("Info Strade");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 446, 290);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnOpzioni = new JMenu("Opzioni");
		menuBar.add(mnOpzioni);
		
		mntmAggiungiStrada = new JMenuItem("Aggiungi Strada");
		mntmAggiungiStrada.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					apriAggiungiStrada();
					tableModel.refresh();
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(InfoStradaDialog.this, "Errore nel caricamento dei dati");
					dispose();
				}
			}
		});
		mnOpzioni.add(mntmAggiungiStrada);
		
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
