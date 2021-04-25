package gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.Dialog.ModalityType;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.JTextField;

public class InfoPartitaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Controller controller;
	private int idPartita;
	private JTextField idPartitaTextField;
	private JTextField durataTextField;
	private JTextField dataTextField;
	
	private String getData() throws SQLException
	{
		return InterazioneDatabase.getDataPartita(controller, idPartita);
	}
	
	private int getDurata() throws SQLException
	{
		return InterazioneDatabase.getDurataPartita(controller, idPartita);
	}

	/**
	 * Create the dialog.
	 */
	public InfoPartitaDialog(Controller controller, int idPartita) {
		setResizable(false);
		setTitle("Info Partita");
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.controller = controller;
		this.idPartita = idPartita;
		String data = null;
		int durata = 0;
		try 
		{
			data = getData();
			durata = getDurata();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento delle info");
			dispose();
		}
		setBounds(100, 100, 439, 242);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{51, 96, 0};
		gbl_contentPanel.rowHeights = new int[]{40, 20, 20, 20, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel idPartitaLabel = new JLabel("ID Partita:");
			GridBagConstraints gbc_idPartitaLabel = new GridBagConstraints();
			gbc_idPartitaLabel.insets = new Insets(0, 0, 5, 5);
			gbc_idPartitaLabel.gridx = 0;
			gbc_idPartitaLabel.gridy = 1;
			contentPanel.add(idPartitaLabel, gbc_idPartitaLabel);
		}
		{
			idPartitaTextField = new JTextField(Integer.toString(idPartita));
			idPartitaTextField.setEditable(false);
			GridBagConstraints gbc_idPartitaTextField = new GridBagConstraints();
			gbc_idPartitaTextField.insets = new Insets(0, 0, 5, 0);
			gbc_idPartitaTextField.gridx = 1;
			gbc_idPartitaTextField.gridy = 1;
			contentPanel.add(idPartitaTextField, gbc_idPartitaTextField);
			idPartitaTextField.setColumns(30);
		}
		{
			JLabel durataLabel = new JLabel("Durata:");
			GridBagConstraints gbc_durataLabel = new GridBagConstraints();
			gbc_durataLabel.insets = new Insets(0, 0, 5, 5);
			gbc_durataLabel.gridx = 0;
			gbc_durataLabel.gridy = 2;
			contentPanel.add(durataLabel, gbc_durataLabel);
		}
		{
			durataTextField = new JTextField(Integer.toString(durata));
			durataTextField.setEditable(false);
			GridBagConstraints gbc_durataTextField = new GridBagConstraints();
			gbc_durataTextField.insets = new Insets(0, 0, 5, 0);
			gbc_durataTextField.gridx = 1;
			gbc_durataTextField.gridy = 2;
			contentPanel.add(durataTextField, gbc_durataTextField);
			durataTextField.setColumns(30);
		}
		{
			JLabel dataLabel = new JLabel("Data:");
			GridBagConstraints gbc_dataLabel = new GridBagConstraints();
			gbc_dataLabel.insets = new Insets(0, 0, 0, 5);
			gbc_dataLabel.gridx = 0;
			gbc_dataLabel.gridy = 3;
			contentPanel.add(dataLabel, gbc_dataLabel);
		}
		{
			dataTextField = new JTextField(data);
			dataTextField.setEditable(false);
			GridBagConstraints gbc_dataTextField = new GridBagConstraints();
			gbc_dataTextField.gridx = 1;
			gbc_dataTextField.gridy = 3;
			contentPanel.add(dataTextField, gbc_dataTextField);
			dataTextField.setColumns(30);
		}
	}

}
