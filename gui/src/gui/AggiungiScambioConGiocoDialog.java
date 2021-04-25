package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class AggiungiScambioConGiocoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int idTurno;
	private int idGiocatore;
	private Controller controller;
	private boolean prosegui;
	private JComboBox tipoDareComboBox;
	private JComboBox tipoRicevereComboBox;

	private ArrayList<String> getTipi()
	{
		ArrayList<String> tipi = new ArrayList<String>();
		tipi.add("Grano");
		tipi.add("Argilla");
		tipi.add("Minerale");
		tipi.add("Legno");
		tipi.add("Lana");
		return tipi;
	}
	
	private void scambioCarteDaDare(int idScambio, String tipo, int quantita) throws SQLException
	{
		ArrayList<Integer> carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, tipo);
		for(int i = 0; i != quantita; ++i)
		{
			int id = carte.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, id);
			InterazioneDatabase.rimuoviCartaRisorsa(controller, id, idTurno);
		}
	}
	
	private void scambioCartaDaRicevere(int idScambio, String tipo) throws SQLException
	{
		int idCartaRisorsa = InterazioneDatabase.aggiungiCartaRisorsa(controller, idGiocatore, idTurno, tipo);
		InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
	}
	
	private void eseguiScambio() throws SQLException
	{
		final int quantitaVoluta = 4;
		String tipoDaDare = tipoDareComboBox.getSelectedItem().toString();
		int quantitaPosseduta = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, tipoDaDare);
		if(quantitaVoluta > quantitaPosseduta)
			JOptionPane.showMessageDialog(this, "Errore la quantità delle carte è errata");
		else
		{
			int idScambio = InterazioneDatabase.aggiungiScambioGioco(controller, idTurno);
			scambioCarteDaDare(idScambio, tipoDaDare, quantitaVoluta);
			String tipoDaRicevere = tipoRicevereComboBox.getSelectedItem().toString();
			scambioCartaDaRicevere(idScambio, tipoDaRicevere);
			JOptionPane.showMessageDialog(this, "Scambio aggiunto con successo");
			dispose();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AggiungiScambioConGiocoDialog(Controller controller, int idTurno, int idGiocatore) {
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		ArrayList<String> tipi = getTipi();
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Aggiungi Scambio Con Gioco");
		setBounds(100, 100, 247, 335);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Carte da dare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			{
				JLabel tipoDareLabel = new JLabel("Tipo:");
				panel.add(tipoDareLabel);
			}
			{
				tipoDareComboBox = new JComboBox(tipi.toArray());
				panel.add(tipoDareComboBox);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Carta da ricevere", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			{
				JLabel tipoRicevereLabel = new JLabel("Tipo:");
				panel.add(tipoRicevereLabel);
			}
			{
				tipoRicevereComboBox = new JComboBox(tipi.toArray());
				panel.add(tipoRicevereComboBox);
			}
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
							eseguiScambio();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiScambioConGiocoDialog.this, "Errore nell'esecuzione dello scambio");
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
				annullaButton.addActionListener(new ActionListener() 
				{
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
