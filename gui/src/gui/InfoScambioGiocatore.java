package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dialog.ModalityType;

public class InfoScambioGiocatore extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField idGiocatoreCoinvoltoTextField;

	/**
	 * Create the dialog.
	 */
	public InfoScambioGiocatore(int idGiocatoreCoinvolto) {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Info Scambio Giocatore");
		setBounds(100, 100, 384, 115);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JLabel idGiocatoreCoinvoltoLabel = new JLabel("ID Giocatore Coinvolto:");
			contentPanel.add(idGiocatoreCoinvoltoLabel);
		}
		{
			idGiocatoreCoinvoltoTextField = new JTextField(Integer.toString(idGiocatoreCoinvolto));
			idGiocatoreCoinvoltoTextField.setEditable(false);
			contentPanel.add(idGiocatoreCoinvoltoTextField);
			idGiocatoreCoinvoltoTextField.setColumns(10);
		}
	}

}
