package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class CostiDiCostruzioneDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public CostiDiCostruzioneDialog() {
		setTitle("Costi di costruzione");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 356, 163);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblStrada = new JLabel("Strada:");
			GridBagConstraints gbc_lblStrada = new GridBagConstraints();
			gbc_lblStrada.insets = new Insets(0, 0, 5, 5);
			gbc_lblStrada.gridx = 0;
			gbc_lblStrada.gridy = 1;
			contentPanel.add(lblStrada, gbc_lblStrada);
		}
		{
			JLabel lblNewLabel = new JLabel("1 argilla + 1 legno");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 1;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JLabel lblColonia = new JLabel("Colonia:");
			GridBagConstraints gbc_lblColonia = new GridBagConstraints();
			gbc_lblColonia.insets = new Insets(0, 0, 5, 5);
			gbc_lblColonia.gridx = 0;
			gbc_lblColonia.gridy = 2;
			contentPanel.add(lblColonia, gbc_lblColonia);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("1 argilla + 1 legno + 1 lana + 1 grano");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 2;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			JLabel lblCitt = new JLabel("Citt\u00E0");
			GridBagConstraints gbc_lblCitt = new GridBagConstraints();
			gbc_lblCitt.insets = new Insets(0, 0, 5, 5);
			gbc_lblCitt.gridx = 0;
			gbc_lblCitt.gridy = 3;
			contentPanel.add(lblCitt, gbc_lblCitt);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("2 grani + 3 minerale");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_2.gridx = 1;
			gbc_lblNewLabel_2.gridy = 3;
			contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		{
			JLabel lblCartaSviluppo_1 = new JLabel("Carta Sviluppo:");
			GridBagConstraints gbc_lblCartaSviluppo_1 = new GridBagConstraints();
			gbc_lblCartaSviluppo_1.insets = new Insets(0, 0, 0, 5);
			gbc_lblCartaSviluppo_1.gridx = 0;
			gbc_lblCartaSviluppo_1.gridy = 4;
			contentPanel.add(lblCartaSviluppo_1, gbc_lblCartaSviluppo_1);
		}
		{
			JLabel lblCartaSviluppo = new JLabel("1 grano + 1 lana + 1 minerale");
			GridBagConstraints gbc_lblCartaSviluppo = new GridBagConstraints();
			gbc_lblCartaSviluppo.anchor = GridBagConstraints.WEST;
			gbc_lblCartaSviluppo.gridx = 1;
			gbc_lblCartaSviluppo.gridy = 4;
			contentPanel.add(lblCartaSviluppo, gbc_lblCartaSviluppo);
		}
	}

}
