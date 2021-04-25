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

public class InfoCartaRisorsa extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField idCartaRisorsaTextField;
	private JTextField tipoTextField;
	
	/**
	 * Create the dialog.
	 */
	public InfoCartaRisorsa(Controller controller, int idCarta) {
		setTitle("Info Carta Risorsa");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 268, 162);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel idCartaRisorsaLabel = new JLabel("Id Carta Risorsa:");
			GridBagConstraints gbc_idCartaRisorsaLabel = new GridBagConstraints();
			gbc_idCartaRisorsaLabel.anchor = GridBagConstraints.EAST;
			gbc_idCartaRisorsaLabel.insets = new Insets(0, 0, 5, 5);
			gbc_idCartaRisorsaLabel.gridx = 1;
			gbc_idCartaRisorsaLabel.gridy = 1;
			contentPanel.add(idCartaRisorsaLabel, gbc_idCartaRisorsaLabel);
		}
		int idCartaRisorsa = 0;
		try 
		{
			idCartaRisorsa = InterazioneDatabase.getIdCartaRisorsa(controller, idCarta);
		} 			
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'id della carta");
			dispose();
		}
			idCartaRisorsaTextField = new JTextField();
			idCartaRisorsaTextField.setText(Integer.toString(idCartaRisorsa));
			idCartaRisorsaTextField.setEditable(false);
			GridBagConstraints gbc_idCartaRisorsaTextField = new GridBagConstraints();
			gbc_idCartaRisorsaTextField.anchor = GridBagConstraints.WEST;
			gbc_idCartaRisorsaTextField.insets = new Insets(0, 0, 5, 0);
			gbc_idCartaRisorsaTextField.gridx = 2;
			gbc_idCartaRisorsaTextField.gridy = 1;
			contentPanel.add(idCartaRisorsaTextField, gbc_idCartaRisorsaTextField);
			idCartaRisorsaTextField.setColumns(10);
		{
			JLabel tipoLabel = new JLabel("Tipo:");
			GridBagConstraints gbc_tipoLabel = new GridBagConstraints();
			gbc_tipoLabel.anchor = GridBagConstraints.EAST;
			gbc_tipoLabel.insets = new Insets(0, 0, 0, 5);
			gbc_tipoLabel.gridx = 1;
			gbc_tipoLabel.gridy = 2;
			contentPanel.add(tipoLabel, gbc_tipoLabel);
		}
		String tipo = null;
		try 
		{
			tipo = InterazioneDatabase.getTipoCartaRisorsa(controller, idCartaRisorsa);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento del tipo della carta");
			dispose();
		}
		
			tipoTextField = new JTextField(tipo);
			tipoTextField.setEditable(false);
			GridBagConstraints gbc_tipoTextField = new GridBagConstraints();
			gbc_tipoTextField.anchor = GridBagConstraints.WEST;
			gbc_tipoTextField.gridx = 2;
			gbc_tipoTextField.gridy = 2;
			contentPanel.add(tipoTextField, gbc_tipoTextField);
			tipoTextField.setColumns(10);
	}

}
