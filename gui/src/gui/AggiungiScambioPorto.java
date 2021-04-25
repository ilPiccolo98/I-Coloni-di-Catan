package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InfoEsagonoPorto;
import utility.InterazioneDatabase;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextField;
import java.awt.Dialog.ModalityType;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiScambioPorto extends JDialog {

	private final JPanel tipoLabel = new JPanel();
	private JTextField carteDaDareTextField;
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
	private ArrayList<InfoEsagonoPorto> info;
	private JComboBox esagoniPortoComboBox;
	private JComboBox tipoCarteDareComboBox;
	private JComboBox carteRicevereComboBox;
	
	private ArrayList<Integer> getPosizioni()
	{
		ArrayList<Integer> posizioni = new ArrayList<Integer>();
		for(int i = 0; i != info.size(); ++i)
			posizioni.add(info.get(i).getPosizione());
		return posizioni;
	}
	
	private void setCarteDaDare()
	{
		int index = esagoniPortoComboBox.getSelectedIndex();
		if(index >= 0)
		{
			int rapportoScambio = info.get(index).getRapportoScambio();
			carteDaDareTextField.setText(Integer.toString(rapportoScambio));
		}
	}
	
	private void setTipoCarteDaDare()
	{
		int index = esagoniPortoComboBox.getSelectedIndex();
		if(index >= 0)
		{
			String specializzazione = info.get(index).getSpecializzazione();
			ArrayList<String> elementi = new ArrayList<String>();
			if(!specializzazione.equals("InputUtente"))
				elementi.add(specializzazione);			
			else
			{
				elementi.add("Grano");
				elementi.add("Legno");
				elementi.add("Lana");
				elementi.add("Argilla");
				elementi.add("Minerale");
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(elementi.toArray());
			tipoCarteDareComboBox.setModel(model);
		}
	}
	
	private void setCarteDaRicevere()
	{
		ArrayList<String> elementi = new ArrayList<String>();
		elementi.add("Grano");
		elementi.add("Legno");
		elementi.add("Lana");
		elementi.add("Argilla");
		elementi.add("Minerale");
		DefaultComboBoxModel model = new DefaultComboBoxModel(elementi.toArray());
		carteRicevereComboBox.setModel(model);
	}
	
	private void setComponents()
	{
		setCarteDaDare();
		setTipoCarteDaDare();
		setCarteDaRicevere();
	}
	
	private void aggiungiCarteDaDare(int idScambio, String tipo, int quantita) throws SQLException
	{
		ArrayList<Integer> carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, tipo);
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = carte.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.rimuoviCartaRisorsa(controller, idCartaRisorsa, idTurno);
		}
	}
	
	private void aggiungiCartaDaRicevere(int idScambio, String tipo) throws SQLException
	{
		int idCartaRisorsa = InterazioneDatabase.aggiungiCartaRisorsa(controller, idGiocatore, idTurno, tipo);
		InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
	}
	
	private void aggiungiScambioPorto() throws SQLException
	{
		if(esagoniPortoComboBox.getComponentCount() == 0)
			JOptionPane.showMessageDialog(this, "Il giocatore non possiede esagoni porto");
		else
		{
			int quantitaVoluta = Integer.parseInt(carteDaDareTextField.getText());
			int quantitaPosseduta = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, tipoCarteDareComboBox.getSelectedItem().toString());
			if(quantitaVoluta > quantitaPosseduta)
				JOptionPane.showMessageDialog(this, "Errore le carte selezionate non sono possedute");
			else
			{
				int index = esagoniPortoComboBox.getSelectedIndex();
				String tipoDaDare = tipoCarteDareComboBox.getSelectedItem().toString();
				String tipoDaRicevere = carteRicevereComboBox.getSelectedItem().toString();
				int idEsagonoPorto = info.get(index).getId();
				int idScambio = InterazioneDatabase.aggiungiScambioPorto(controller, idTurno, idEsagonoPorto);
				aggiungiCarteDaDare(idScambio, tipoDaDare, quantitaVoluta);
				aggiungiCartaDaRicevere(idScambio, tipoDaRicevere);
				JOptionPane.showMessageDialog(this, "Scambio aggiunto con successo");
				dispose();
			}
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public AggiungiScambioPorto(Controller controller, int idTurno, int idGiocatore) {
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		try 
		{
			info = InterazioneDatabase.getInfoEsagoniPorto(controller, idTurno);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei dati");
			dispose();
		}
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Aggiungi Scambio Porto");
		setBounds(100, 100, 280, 273);
		getContentPane().setLayout(new BorderLayout());
		tipoLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(tipoLabel, BorderLayout.CENTER);
		GridBagLayout gbl_tipoLabel = new GridBagLayout();
		gbl_tipoLabel.columnWidths = new int[]{0, 0, 0};
		gbl_tipoLabel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_tipoLabel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_tipoLabel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		tipoLabel.setLayout(gbl_tipoLabel);
		{
			JLabel esagoniPortoLabel = new JLabel("Esagono Porto:");
			GridBagConstraints gbc_esagoniPortoLabel = new GridBagConstraints();
			gbc_esagoniPortoLabel.insets = new Insets(0, 0, 5, 5);
			gbc_esagoniPortoLabel.anchor = GridBagConstraints.EAST;
			gbc_esagoniPortoLabel.gridx = 0;
			gbc_esagoniPortoLabel.gridy = 1;
			tipoLabel.add(esagoniPortoLabel, gbc_esagoniPortoLabel);
		}
		{
			esagoniPortoComboBox = new JComboBox(getPosizioni().toArray());
			esagoniPortoComboBox.addItemListener(new ItemListener() 
			{
				public void itemStateChanged(ItemEvent e) 
				{
					setComponents();
				}
			});
			GridBagConstraints gbc_esagoniPortoComboBox = new GridBagConstraints();
			gbc_esagoniPortoComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_esagoniPortoComboBox.anchor = GridBagConstraints.WEST;
			gbc_esagoniPortoComboBox.gridx = 1;
			gbc_esagoniPortoComboBox.gridy = 1;
			tipoLabel.add(esagoniPortoComboBox, gbc_esagoniPortoComboBox);
		}
		{
			JLabel carteDaDareLabel = new JLabel("Carte da dare:");
			GridBagConstraints gbc_carteDaDareLabel = new GridBagConstraints();
			gbc_carteDaDareLabel.anchor = GridBagConstraints.NORTHEAST;
			gbc_carteDaDareLabel.insets = new Insets(0, 0, 5, 5);
			gbc_carteDaDareLabel.gridx = 0;
			gbc_carteDaDareLabel.gridy = 2;
			tipoLabel.add(carteDaDareLabel, gbc_carteDaDareLabel);
		}
		{
			carteDaDareTextField = new JTextField();
			carteDaDareTextField.setEditable(false);
			GridBagConstraints gbc_carteDaDareTextField = new GridBagConstraints();
			gbc_carteDaDareTextField.insets = new Insets(0, 0, 5, 0);
			gbc_carteDaDareTextField.anchor = GridBagConstraints.WEST;
			gbc_carteDaDareTextField.gridx = 1;
			gbc_carteDaDareTextField.gridy = 2;
			tipoLabel.add(carteDaDareTextField, gbc_carteDaDareTextField);
			carteDaDareTextField.setColumns(10);
		}
		{
			JLabel tipiCarteDareLabel = new JLabel("Tipo di carte da dare:");
			GridBagConstraints gbc_tipiCarteDareLabel = new GridBagConstraints();
			gbc_tipiCarteDareLabel.anchor = GridBagConstraints.EAST;
			gbc_tipiCarteDareLabel.insets = new Insets(0, 0, 5, 5);
			gbc_tipiCarteDareLabel.gridx = 0;
			gbc_tipiCarteDareLabel.gridy = 3;
			tipoLabel.add(tipiCarteDareLabel, gbc_tipiCarteDareLabel);
		}
		{
			tipoCarteDareComboBox = new JComboBox();
			GridBagConstraints gbc_tipoCarteDareComboBox = new GridBagConstraints();
			gbc_tipoCarteDareComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_tipoCarteDareComboBox.anchor = GridBagConstraints.WEST;
			gbc_tipoCarteDareComboBox.gridx = 1;
			gbc_tipoCarteDareComboBox.gridy = 3;
			tipoLabel.add(tipoCarteDareComboBox, gbc_tipoCarteDareComboBox);
		}
		{
			JLabel carteRicevereLabel = new JLabel("Carta da ricevere:");
			GridBagConstraints gbc_carteRicevereLabel = new GridBagConstraints();
			gbc_carteRicevereLabel.anchor = GridBagConstraints.EAST;
			gbc_carteRicevereLabel.insets = new Insets(0, 0, 0, 5);
			gbc_carteRicevereLabel.gridx = 0;
			gbc_carteRicevereLabel.gridy = 4;
			tipoLabel.add(carteRicevereLabel, gbc_carteRicevereLabel);
		}
		{
			carteRicevereComboBox = new JComboBox();
			GridBagConstraints gbc_carteRicevereComboBox = new GridBagConstraints();
			gbc_carteRicevereComboBox.anchor = GridBagConstraints.WEST;
			gbc_carteRicevereComboBox.gridx = 1;
			gbc_carteRicevereComboBox.gridy = 4;
			tipoLabel.add(carteRicevereComboBox, gbc_carteRicevereComboBox);
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
							aggiungiScambioPorto();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiScambioPorto.this, "Errore nell'esecuzione dello scambio");
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
		setComponents();
	}

}
