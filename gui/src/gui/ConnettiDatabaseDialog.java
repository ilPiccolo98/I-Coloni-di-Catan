package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class ConnettiDatabaseDialog extends JDialog {
	private JTextField nomeHostTextField;
	private JTextField nomeDatabaseTextField;
	private JTextField nomeUtenteTextField;
	private boolean confermato = false;
	private JPasswordField passwordField;

	public boolean getConfermato()
	{
		return confermato;
	}
	
	public String getNomeHost()
	{
		return nomeHostTextField.getText();
	}
	
	public String getNomeDatabase()
	{
		return nomeDatabaseTextField.getText();
	}
	
	public String getNomeUtente()
	{
		return nomeUtenteTextField.getText();
	}
	
	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
	
	/**
	 * Create the dialog.
	 */
	public ConnettiDatabaseDialog() {
		setTitle("Connetti al Database");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 279);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				panel.add(panel_1, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) 
						{
							confermato = true;
							dispose();
						}
					});
					panel_1.add(okButton);
				}
				{
					JButton annullaButton = new JButton("Annulla");
					annullaButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) 
						{
							confermato = false;
							dispose();
						}
					});
					panel_1.add(annullaButton);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				GridBagLayout gbl_panel_1 = new GridBagLayout();
				gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
				gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
				gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
				gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel_1.setLayout(gbl_panel_1);
				{
					JLabel nomeHostLabel = new JLabel("Nome Host:");
					GridBagConstraints gbc_nomeHostLabel = new GridBagConstraints();
					gbc_nomeHostLabel.anchor = GridBagConstraints.EAST;
					gbc_nomeHostLabel.insets = new Insets(0, 0, 5, 5);
					gbc_nomeHostLabel.gridx = 1;
					gbc_nomeHostLabel.gridy = 1;
					panel_1.add(nomeHostLabel, gbc_nomeHostLabel);
				}
				{
					nomeHostTextField = new JTextField();
					GridBagConstraints gbc_nomeHostTextField = new GridBagConstraints();
					gbc_nomeHostTextField.insets = new Insets(0, 0, 5, 0);
					gbc_nomeHostTextField.fill = GridBagConstraints.HORIZONTAL;
					gbc_nomeHostTextField.gridx = 2;
					gbc_nomeHostTextField.gridy = 1;
					panel_1.add(nomeHostTextField, gbc_nomeHostTextField);
					nomeHostTextField.setColumns(50);
				}
				{
					JLabel nomeDatabaseLabel = new JLabel("Nome Database:");
					GridBagConstraints gbc_nomeDatabaseLabel = new GridBagConstraints();
					gbc_nomeDatabaseLabel.anchor = GridBagConstraints.EAST;
					gbc_nomeDatabaseLabel.insets = new Insets(0, 0, 5, 5);
					gbc_nomeDatabaseLabel.gridx = 1;
					gbc_nomeDatabaseLabel.gridy = 2;
					panel_1.add(nomeDatabaseLabel, gbc_nomeDatabaseLabel);
				}
				{
					nomeDatabaseTextField = new JTextField();
					GridBagConstraints gbc_nomeDatabaseTextField = new GridBagConstraints();
					gbc_nomeDatabaseTextField.insets = new Insets(0, 0, 5, 0);
					gbc_nomeDatabaseTextField.fill = GridBagConstraints.HORIZONTAL;
					gbc_nomeDatabaseTextField.gridx = 2;
					gbc_nomeDatabaseTextField.gridy = 2;
					panel_1.add(nomeDatabaseTextField, gbc_nomeDatabaseTextField);
					nomeDatabaseTextField.setColumns(30);
				}
				{
					JLabel nomeUtenteLabel = new JLabel("Nome Utente:");
					GridBagConstraints gbc_nomeUtenteLabel = new GridBagConstraints();
					gbc_nomeUtenteLabel.anchor = GridBagConstraints.EAST;
					gbc_nomeUtenteLabel.insets = new Insets(0, 0, 5, 5);
					gbc_nomeUtenteLabel.gridx = 1;
					gbc_nomeUtenteLabel.gridy = 3;
					panel_1.add(nomeUtenteLabel, gbc_nomeUtenteLabel);
				}
				{
					nomeUtenteTextField = new JTextField();
					GridBagConstraints gbc_nomeUtenteTextField = new GridBagConstraints();
					gbc_nomeUtenteTextField.insets = new Insets(0, 0, 5, 0);
					gbc_nomeUtenteTextField.fill = GridBagConstraints.HORIZONTAL;
					gbc_nomeUtenteTextField.gridx = 2;
					gbc_nomeUtenteTextField.gridy = 3;
					panel_1.add(nomeUtenteTextField, gbc_nomeUtenteTextField);
					nomeUtenteTextField.setColumns(30);
				}
				{
					JLabel passwordLabel = new JLabel("Password:");
					GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
					gbc_passwordLabel.anchor = GridBagConstraints.EAST;
					gbc_passwordLabel.insets = new Insets(0, 0, 0, 5);
					gbc_passwordLabel.gridx = 1;
					gbc_passwordLabel.gridy = 4;
					panel_1.add(passwordLabel, gbc_passwordLabel);
				}
				{
					passwordField = new JPasswordField();
					GridBagConstraints gbc_passwordField = new GridBagConstraints();
					gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
					gbc_passwordField.gridx = 2;
					gbc_passwordField.gridy = 4;
					panel_1.add(passwordField, gbc_passwordField);
				}
			}
		}
	}

}
