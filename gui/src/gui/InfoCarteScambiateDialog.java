package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.border.TitledBorder;
import java.awt.Dialog.ModalityType;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoCarteScambiateDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Controller controller;
	private String tipo;
	private int idScambio;
	private CarteDateTableModel tableModelCarteDate;
	private CarteRicevuteTableModel tableModelCarteRicevute;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTable carteDateTable;
	private JTable carteRicevuteTable;
	private JMenuBar menuBar;
	private JMenuItem infoMenuItem;

	private void apriInfoScambioGioco()
	{
		InfoScambioGioco i = new InfoScambioGioco();
		i.setVisible(true);
	}
	
	private void apriInfoScambioGiocatore() throws SQLException
	{
		int idGiocatoreCoinvolto = InterazioneDatabase.getGiocatoreCoinvolto(controller, idScambio);
		InfoScambioGiocatore i = new InfoScambioGiocatore(idGiocatoreCoinvolto);
		i.setVisible(true);
	}
	
	private void apriInfoScambioPorto() throws SQLException
	{
		int idEsagono = InterazioneDatabase.getIdEsagono(controller, idScambio);
		InfoScambioPorto i = new InfoScambioPorto(idEsagono);
		i.setVisible(true);
	}
	
	/**
	 * Create the dialog.
	 */
	public InfoCarteScambiateDialog(Controller controller, int idScambio, String tipo) {
		setResizable(false);
		setTitle("Info Carte Scambiate");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.controller = controller;
		this.idScambio = idScambio;
		this.tipo = tipo;
		tableModelCarteDate = new CarteDateTableModel(controller, idScambio);
		tableModelCarteRicevute = new CarteRicevuteTableModel(controller, idScambio);
		setBounds(100, 100, 350, 475);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Carte Date", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPanel.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		carteDateTable = new JTable(tableModelCarteDate);
		carteDateTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(carteDateTable);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Carte Ricevute", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPanel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		carteRicevuteTable = new JTable(tableModelCarteRicevute);
		carteRicevuteTable.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(carteRicevuteTable);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		infoMenuItem = new JMenuItem("Info");
		infoMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				switch(tipo)
				{
					case "ScambioGioco":
						apriInfoScambioGioco();
						break;
					case "ScambioGiocatore":
						try 
						{
							apriInfoScambioGiocatore();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(InfoCarteScambiateDialog.this, "Errore nel caricamento delle info");
						}
						break;
					case "ScambioPorto":
						try 
						{
							apriInfoScambioPorto();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(InfoCarteScambiateDialog.this, "Errore nel caricamento delle info");
						}
						break;
				}
			}
		});
		menuBar.add(infoMenuItem);
		try 
		{
			tableModelCarteRicevute.refresh();
			tableModelCarteDate.refresh();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei dati");
			dispose();
		}
	}

}
