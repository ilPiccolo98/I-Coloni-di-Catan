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

public class InfoGiocatoreDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField idGiocatoreTextField;
	private JTextField etaTextField;
	private JTextField nicknameTextField;
	private JTextField coloreTextField;
	private int idPartita;
	private int idGiocatore;
	private Controller controller;

	private void setIdGiocatoreTextField()
	{
		idGiocatoreTextField.setText(Integer.toString(idGiocatore));
	}
	
	private void setEtaTextField()
	{
		try 
		{
			String eta = Integer.toString(InterazioneDatabase.getEtaGiocatore(controller, idGiocatore));
			etaTextField.setText(eta);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'eta'");
			dispose();
		}
	}
	
	private void setNicknameTextField()
	{
		try 
		{
			String nickname = InterazioneDatabase.getNicknameGiocatore(controller, idGiocatore, idPartita);
			nicknameTextField.setText(nickname);
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento del nickname");
			dispose();
		}
	}
	
	private void setColoreTextField()
	{
		try 
		{
			String colore = InterazioneDatabase.getColoreGiocatore(controller, idGiocatore, idPartita);
			coloreTextField.setText(colore);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento del colore");
			dispose();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InfoGiocatoreDialog(Controller controller, int idPartita, int idGiocatore) {
		setResizable(false);
		setTitle("Info Giocatore");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 297, 218);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel idGiocatoreLabel = new JLabel("Id Giocatore:");
			GridBagConstraints gbc_idGiocatoreLabel = new GridBagConstraints();
			gbc_idGiocatoreLabel.anchor = GridBagConstraints.EAST;
			gbc_idGiocatoreLabel.insets = new Insets(0, 0, 5, 5);
			gbc_idGiocatoreLabel.gridx = 1;
			gbc_idGiocatoreLabel.gridy = 1;
			contentPanel.add(idGiocatoreLabel, gbc_idGiocatoreLabel);
		}
		{
			idGiocatoreTextField = new JTextField();
			idGiocatoreTextField.setEditable(false);
			GridBagConstraints gbc_idGiocatoreTextField = new GridBagConstraints();
			gbc_idGiocatoreTextField.anchor = GridBagConstraints.WEST;
			gbc_idGiocatoreTextField.insets = new Insets(0, 0, 5, 0);
			gbc_idGiocatoreTextField.gridx = 2;
			gbc_idGiocatoreTextField.gridy = 1;
			contentPanel.add(idGiocatoreTextField, gbc_idGiocatoreTextField);
			idGiocatoreTextField.setColumns(10);
		}
		{
			JLabel etaLabel = new JLabel("Et\u00E0:");
			GridBagConstraints gbc_etaLabel = new GridBagConstraints();
			gbc_etaLabel.anchor = GridBagConstraints.EAST;
			gbc_etaLabel.insets = new Insets(0, 0, 5, 5);
			gbc_etaLabel.gridx = 1;
			gbc_etaLabel.gridy = 2;
			contentPanel.add(etaLabel, gbc_etaLabel);
		}
		{
			etaTextField = new JTextField();
			etaTextField.setEditable(false);
			GridBagConstraints gbc_etaTextField = new GridBagConstraints();
			gbc_etaTextField.anchor = GridBagConstraints.WEST;
			gbc_etaTextField.insets = new Insets(0, 0, 5, 0);
			gbc_etaTextField.gridx = 2;
			gbc_etaTextField.gridy = 2;
			contentPanel.add(etaTextField, gbc_etaTextField);
			etaTextField.setColumns(10);
		}
		{
			JLabel nicknameLabel = new JLabel("Nickname:");
			GridBagConstraints gbc_nicknameLabel = new GridBagConstraints();
			gbc_nicknameLabel.anchor = GridBagConstraints.EAST;
			gbc_nicknameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_nicknameLabel.gridx = 1;
			gbc_nicknameLabel.gridy = 3;
			contentPanel.add(nicknameLabel, gbc_nicknameLabel);
		}
		{
			nicknameTextField = new JTextField();
			nicknameTextField.setEditable(false);
			GridBagConstraints gbc_nicknameTextField = new GridBagConstraints();
			gbc_nicknameTextField.anchor = GridBagConstraints.WEST;
			gbc_nicknameTextField.insets = new Insets(0, 0, 5, 0);
			gbc_nicknameTextField.gridx = 2;
			gbc_nicknameTextField.gridy = 3;
			contentPanel.add(nicknameTextField, gbc_nicknameTextField);
			nicknameTextField.setColumns(10);
		}
		{
			JLabel coloreLabel = new JLabel("Colore:");
			GridBagConstraints gbc_coloreLabel = new GridBagConstraints();
			gbc_coloreLabel.anchor = GridBagConstraints.EAST;
			gbc_coloreLabel.insets = new Insets(0, 0, 0, 5);
			gbc_coloreLabel.gridx = 1;
			gbc_coloreLabel.gridy = 4;
			contentPanel.add(coloreLabel, gbc_coloreLabel);
		}
		{
			coloreTextField = new JTextField();
			coloreTextField.setEditable(false);
			GridBagConstraints gbc_coloreTextField = new GridBagConstraints();
			gbc_coloreTextField.anchor = GridBagConstraints.WEST;
			gbc_coloreTextField.gridx = 2;
			gbc_coloreTextField.gridy = 4;
			contentPanel.add(coloreTextField, gbc_coloreTextField);
			coloreTextField.setColumns(10);
		}
		this.controller = controller;
		this.idPartita = idPartita;
		this.idGiocatore = idGiocatore;
		setIdGiocatoreTextField();
		setEtaTextField();
		setNicknameTextField();
		setColoreTextField();
	}

}
