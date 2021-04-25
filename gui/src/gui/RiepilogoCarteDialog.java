package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;

import utility.Controller;
import utility.InterazioneDatabase;

public class RiepilogoCarteDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField granoGiocatoreUnoTextField;
	private JTextField legnoGiocatoreUnoTextField;
	private JTextField argillaGiocatoreUnoTextField;
	private JTextField lanaGiocatoreUnoTextField;
	private JTextField mineraleGiocatoreUnoTextField;
	private JTextField granoGiocatoreDueTextField;
	private JTextField legnoGiocatoreDueTextField;
	private JTextField argillaGiocatoreDueTextField;
	private JTextField mineraleGiocatoreDueTextField;
	private JTextField lanaGiocatoreDueTextField;
	private JTextField granoGiocatoreTreTextField;
	private JTextField legnoGiocatoreTreTextField;
	private JTextField argillaGiocatoreTreTextField;
	private JTextField mineraleGiocatoreTreTextField;
	private JTextField lanaGiocatoreTreTextField;
	private JTextField granoGiocatoreQuattroTextField;
	private JTextField legnoGiocatoreQuattroTextField;
	private JTextField argillaGiocatoreQuattroTextField;
	private JTextField mineraleGiocatoreQuattroTextField;
	private JTextField lanaGiocatoreQuattroTextField;
	private JTextField idGiocatoreUnoTextField;
	private JTextField idGiocatoreDueTextField;
	private JTextField idGiocatoreTreTextField;
	private JTextField idGiocatoreQuattroTextField;
	private Controller controller;
	private int idPartita;
	private int idTurno;
	
	private ArrayList<Integer> getIdsGiocatori() throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdGiocatori(controller, idPartita);
		return ids;
	}
	
	private void setGiocatoreUno(int idGiocatore) throws SQLException
	{
		idGiocatoreUnoTextField.setText(Integer.toString(idGiocatore));
		int carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		granoGiocatoreUnoTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Legno");
		legnoGiocatoreUnoTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Argilla");
		argillaGiocatoreUnoTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Minerale");
		mineraleGiocatoreUnoTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Lana");
		lanaGiocatoreUnoTextField.setText(Integer.toString(carte));
	}
	
	private void setGiocatoreDue(int idGiocatore) throws SQLException
	{
		idGiocatoreDueTextField.setText(Integer.toString(idGiocatore));
		int carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		granoGiocatoreDueTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Legno");
		legnoGiocatoreDueTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Argilla");
		argillaGiocatoreDueTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Minerale");
		mineraleGiocatoreDueTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Lana");
		lanaGiocatoreDueTextField.setText(Integer.toString(carte));
	}
	
	private void setGiocatoreTre(int idGiocatore) throws SQLException
	{
		idGiocatoreTreTextField.setText(Integer.toString(idGiocatore));
		int carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		granoGiocatoreTreTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Legno");
		legnoGiocatoreTreTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Argilla");
		argillaGiocatoreTreTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Minerale");
		mineraleGiocatoreTreTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Lana");
		lanaGiocatoreTreTextField.setText(Integer.toString(carte));
	}
	
	private void setGiocatoreQuattro(int idGiocatore) throws SQLException
	{
		idGiocatoreQuattroTextField.setText(Integer.toString(idGiocatore));
		int carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Grano");
		granoGiocatoreQuattroTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Legno");
		legnoGiocatoreQuattroTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Argilla");
		argillaGiocatoreQuattroTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Minerale");
		mineraleGiocatoreQuattroTextField.setText(Integer.toString(carte));
		carte = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Lana");
		lanaGiocatoreQuattroTextField.setText(Integer.toString(carte));
	}
	
	public void setTexFields() throws SQLException
	{
		ArrayList<Integer> giocatori = InterazioneDatabase.getIdGiocatori(controller, idPartita);
		setGiocatoreUno(giocatori.get(0));
		setGiocatoreDue(giocatori.get(1));
		setGiocatoreTre(giocatori.get(2));
		setGiocatoreQuattro(giocatori.get(3));
	}
	

	/**
	 * Create the dialog.
	 */
	public RiepilogoCarteDialog(Controller controller, int idPartita, int idTurno) {
		this.controller = controller;
		this.idPartita = idPartita;
		this.idTurno = idTurno;
		setTitle("Riepilogo Carte");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 739, 574);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblTurnoCorrente = new JLabel("Turno Corrente:");
				GridBagConstraints gbc_lblTurnoCorrente = new GridBagConstraints();
				gbc_lblTurnoCorrente.insets = new Insets(0, 0, 0, 5);
				gbc_lblTurnoCorrente.anchor = GridBagConstraints.EAST;
				gbc_lblTurnoCorrente.gridx = 0;
				gbc_lblTurnoCorrente.gridy = 0;
				panel.add(lblTurnoCorrente, gbc_lblTurnoCorrente);
			}
			{
				textField = new JTextField(Integer.toString(idTurno));
				textField.setEditable(false);
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.anchor = GridBagConstraints.WEST;
				gbc_textField.gridx = 1;
				gbc_textField.gridy = 0;
				panel.add(textField, gbc_textField);
				textField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel idGiocatoreUnoLabel = new JLabel("ID:");
				GridBagConstraints gbc_idGiocatoreUnoLabel = new GridBagConstraints();
				gbc_idGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_idGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_idGiocatoreUnoLabel.gridx = 0;
				gbc_idGiocatoreUnoLabel.gridy = 0;
				panel.add(idGiocatoreUnoLabel, gbc_idGiocatoreUnoLabel);
			}
			{
				idGiocatoreUnoTextField = new JTextField();
				idGiocatoreUnoTextField.setEditable(false);
				GridBagConstraints gbc_idGiocatoreUnoTextField = new GridBagConstraints();
				gbc_idGiocatoreUnoTextField.anchor = GridBagConstraints.WEST;
				gbc_idGiocatoreUnoTextField.insets = new Insets(0, 0, 5, 0);
				gbc_idGiocatoreUnoTextField.gridx = 1;
				gbc_idGiocatoreUnoTextField.gridy = 0;
				panel.add(idGiocatoreUnoTextField, gbc_idGiocatoreUnoTextField);
				idGiocatoreUnoTextField.setColumns(10);
			}
			{
				JLabel granoGiocatoreUnoLabel = new JLabel("Grano:");
				GridBagConstraints gbc_granoGiocatoreUnoLabel = new GridBagConstraints();
				gbc_granoGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_granoGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_granoGiocatoreUnoLabel.gridx = 0;
				gbc_granoGiocatoreUnoLabel.gridy = 1;
				panel.add(granoGiocatoreUnoLabel, gbc_granoGiocatoreUnoLabel);
			}
			{
				granoGiocatoreUnoTextField = new JTextField();
				granoGiocatoreUnoTextField.setEditable(false);
				GridBagConstraints gbc_granoGiocatoreUnoTextField = new GridBagConstraints();
				gbc_granoGiocatoreUnoTextField.anchor = GridBagConstraints.WEST;
				gbc_granoGiocatoreUnoTextField.insets = new Insets(0, 0, 5, 0);
				gbc_granoGiocatoreUnoTextField.gridx = 1;
				gbc_granoGiocatoreUnoTextField.gridy = 1;
				panel.add(granoGiocatoreUnoTextField, gbc_granoGiocatoreUnoTextField);
				granoGiocatoreUnoTextField.setColumns(10);
			}
			{
				JLabel legnoGiocatoreUnoLabel = new JLabel("Legno:");
				GridBagConstraints gbc_legnoGiocatoreUnoLabel = new GridBagConstraints();
				gbc_legnoGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_legnoGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_legnoGiocatoreUnoLabel.gridx = 0;
				gbc_legnoGiocatoreUnoLabel.gridy = 2;
				panel.add(legnoGiocatoreUnoLabel, gbc_legnoGiocatoreUnoLabel);
			}
			{
				legnoGiocatoreUnoTextField = new JTextField();
				legnoGiocatoreUnoTextField.setEditable(false);
				GridBagConstraints gbc_legnoGiocatoreUnoTextField = new GridBagConstraints();
				gbc_legnoGiocatoreUnoTextField.anchor = GridBagConstraints.WEST;
				gbc_legnoGiocatoreUnoTextField.insets = new Insets(0, 0, 5, 0);
				gbc_legnoGiocatoreUnoTextField.gridx = 1;
				gbc_legnoGiocatoreUnoTextField.gridy = 2;
				panel.add(legnoGiocatoreUnoTextField, gbc_legnoGiocatoreUnoTextField);
				legnoGiocatoreUnoTextField.setColumns(10);
			}
			{
				JLabel argillaGiocatoreUnoLabel = new JLabel("Argilla:");
				GridBagConstraints gbc_argillaGiocatoreUnoLabel = new GridBagConstraints();
				gbc_argillaGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_argillaGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_argillaGiocatoreUnoLabel.gridx = 0;
				gbc_argillaGiocatoreUnoLabel.gridy = 3;
				panel.add(argillaGiocatoreUnoLabel, gbc_argillaGiocatoreUnoLabel);
			}
			{
				argillaGiocatoreUnoTextField = new JTextField();
				argillaGiocatoreUnoTextField.setEditable(false);
				GridBagConstraints gbc_argillaGiocatoreUnoTextField = new GridBagConstraints();
				gbc_argillaGiocatoreUnoTextField.anchor = GridBagConstraints.WEST;
				gbc_argillaGiocatoreUnoTextField.insets = new Insets(0, 0, 5, 0);
				gbc_argillaGiocatoreUnoTextField.gridx = 1;
				gbc_argillaGiocatoreUnoTextField.gridy = 3;
				panel.add(argillaGiocatoreUnoTextField, gbc_argillaGiocatoreUnoTextField);
				argillaGiocatoreUnoTextField.setColumns(10);
			}
			{
				JLabel mineraleGiocatoreUnoLabel = new JLabel("Minerale:");
				GridBagConstraints gbc_mineraleGiocatoreUnoLabel = new GridBagConstraints();
				gbc_mineraleGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_mineraleGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_mineraleGiocatoreUnoLabel.gridx = 0;
				gbc_mineraleGiocatoreUnoLabel.gridy = 4;
				panel.add(mineraleGiocatoreUnoLabel, gbc_mineraleGiocatoreUnoLabel);
			}
			{
				mineraleGiocatoreUnoTextField = new JTextField();
				mineraleGiocatoreUnoTextField.setEditable(false);
				GridBagConstraints gbc_mineraleGiocatoreUnoTextField = new GridBagConstraints();
				gbc_mineraleGiocatoreUnoTextField.anchor = GridBagConstraints.WEST;
				gbc_mineraleGiocatoreUnoTextField.insets = new Insets(0, 0, 5, 0);
				gbc_mineraleGiocatoreUnoTextField.gridx = 1;
				gbc_mineraleGiocatoreUnoTextField.gridy = 4;
				panel.add(mineraleGiocatoreUnoTextField, gbc_mineraleGiocatoreUnoTextField);
				mineraleGiocatoreUnoTextField.setColumns(10);
			}
			{
				JLabel lanaGiocatoreUnoLabel = new JLabel("Lana:");
				GridBagConstraints gbc_lanaGiocatoreUnoLabel = new GridBagConstraints();
				gbc_lanaGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_lanaGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lanaGiocatoreUnoLabel.gridx = 0;
				gbc_lanaGiocatoreUnoLabel.gridy = 5;
				panel.add(lanaGiocatoreUnoLabel, gbc_lanaGiocatoreUnoLabel);
			}
			{
				lanaGiocatoreUnoTextField = new JTextField();
				lanaGiocatoreUnoTextField.setEditable(false);
				GridBagConstraints gbc_lanaGiocatoreUnoTextField = new GridBagConstraints();
				gbc_lanaGiocatoreUnoTextField.anchor = GridBagConstraints.WEST;
				gbc_lanaGiocatoreUnoTextField.insets = new Insets(0, 0, 5, 0);
				gbc_lanaGiocatoreUnoTextField.gridx = 1;
				gbc_lanaGiocatoreUnoTextField.gridy = 5;
				panel.add(lanaGiocatoreUnoTextField, gbc_lanaGiocatoreUnoTextField);
				lanaGiocatoreUnoTextField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel idGiocatoreDueLabel = new JLabel("ID:");
				GridBagConstraints gbc_idGiocatoreDueLabel = new GridBagConstraints();
				gbc_idGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_idGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_idGiocatoreDueLabel.gridx = 0;
				gbc_idGiocatoreDueLabel.gridy = 0;
				panel.add(idGiocatoreDueLabel, gbc_idGiocatoreDueLabel);
			}
			{
				idGiocatoreDueTextField = new JTextField();
				idGiocatoreDueTextField.setEditable(false);
				GridBagConstraints gbc_idGiocatoreDueTextField = new GridBagConstraints();
				gbc_idGiocatoreDueTextField.anchor = GridBagConstraints.WEST;
				gbc_idGiocatoreDueTextField.insets = new Insets(0, 0, 5, 0);
				gbc_idGiocatoreDueTextField.gridx = 1;
				gbc_idGiocatoreDueTextField.gridy = 0;
				panel.add(idGiocatoreDueTextField, gbc_idGiocatoreDueTextField);
				idGiocatoreDueTextField.setColumns(10);
			}
			{
				JLabel granoGiocatoreDueLabel = new JLabel("Grano:");
				GridBagConstraints gbc_granoGiocatoreDueLabel = new GridBagConstraints();
				gbc_granoGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_granoGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_granoGiocatoreDueLabel.gridx = 0;
				gbc_granoGiocatoreDueLabel.gridy = 1;
				panel.add(granoGiocatoreDueLabel, gbc_granoGiocatoreDueLabel);
			}
			{
				granoGiocatoreDueTextField = new JTextField();
				granoGiocatoreDueTextField.setEditable(false);
				GridBagConstraints gbc_granoGiocatoreDueTextField = new GridBagConstraints();
				gbc_granoGiocatoreDueTextField.anchor = GridBagConstraints.WEST;
				gbc_granoGiocatoreDueTextField.insets = new Insets(0, 0, 5, 0);
				gbc_granoGiocatoreDueTextField.gridx = 1;
				gbc_granoGiocatoreDueTextField.gridy = 1;
				panel.add(granoGiocatoreDueTextField, gbc_granoGiocatoreDueTextField);
				granoGiocatoreDueTextField.setColumns(10);
			}
			{
				JLabel legnoGiocatoreDueLabel = new JLabel("Legno:");
				GridBagConstraints gbc_legnoGiocatoreDueLabel = new GridBagConstraints();
				gbc_legnoGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_legnoGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_legnoGiocatoreDueLabel.gridx = 0;
				gbc_legnoGiocatoreDueLabel.gridy = 2;
				panel.add(legnoGiocatoreDueLabel, gbc_legnoGiocatoreDueLabel);
			}
			{
				legnoGiocatoreDueTextField = new JTextField();
				legnoGiocatoreDueTextField.setEditable(false);
				GridBagConstraints gbc_legnoGiocatoreDueTextField = new GridBagConstraints();
				gbc_legnoGiocatoreDueTextField.anchor = GridBagConstraints.WEST;
				gbc_legnoGiocatoreDueTextField.insets = new Insets(0, 0, 5, 0);
				gbc_legnoGiocatoreDueTextField.gridx = 1;
				gbc_legnoGiocatoreDueTextField.gridy = 2;
				panel.add(legnoGiocatoreDueTextField, gbc_legnoGiocatoreDueTextField);
				legnoGiocatoreDueTextField.setColumns(10);
			}
			{
				JLabel argillaGiocatoreDueLabel = new JLabel("Argilla:");
				GridBagConstraints gbc_argillaGiocatoreDueLabel = new GridBagConstraints();
				gbc_argillaGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_argillaGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_argillaGiocatoreDueLabel.gridx = 0;
				gbc_argillaGiocatoreDueLabel.gridy = 3;
				panel.add(argillaGiocatoreDueLabel, gbc_argillaGiocatoreDueLabel);
			}
			{
				argillaGiocatoreDueTextField = new JTextField();
				argillaGiocatoreDueTextField.setEditable(false);
				GridBagConstraints gbc_argillaGiocatoreDueTextField = new GridBagConstraints();
				gbc_argillaGiocatoreDueTextField.anchor = GridBagConstraints.WEST;
				gbc_argillaGiocatoreDueTextField.insets = new Insets(0, 0, 5, 0);
				gbc_argillaGiocatoreDueTextField.gridx = 1;
				gbc_argillaGiocatoreDueTextField.gridy = 3;
				panel.add(argillaGiocatoreDueTextField, gbc_argillaGiocatoreDueTextField);
				argillaGiocatoreDueTextField.setColumns(10);
			}
			{
				JLabel mineraleGiocatoreDueLabel = new JLabel("Minerale:");
				GridBagConstraints gbc_mineraleGiocatoreDueLabel = new GridBagConstraints();
				gbc_mineraleGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_mineraleGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_mineraleGiocatoreDueLabel.gridx = 0;
				gbc_mineraleGiocatoreDueLabel.gridy = 4;
				panel.add(mineraleGiocatoreDueLabel, gbc_mineraleGiocatoreDueLabel);
			}
			{
				mineraleGiocatoreDueTextField = new JTextField();
				mineraleGiocatoreDueTextField.setEditable(false);
				GridBagConstraints gbc_mineraleGiocatoreDueTextField = new GridBagConstraints();
				gbc_mineraleGiocatoreDueTextField.anchor = GridBagConstraints.WEST;
				gbc_mineraleGiocatoreDueTextField.insets = new Insets(0, 0, 5, 0);
				gbc_mineraleGiocatoreDueTextField.gridx = 1;
				gbc_mineraleGiocatoreDueTextField.gridy = 4;
				panel.add(mineraleGiocatoreDueTextField, gbc_mineraleGiocatoreDueTextField);
				mineraleGiocatoreDueTextField.setColumns(10);
			}
			{
				JLabel lanaGiocatoreDueLabel = new JLabel("Lana:");
				GridBagConstraints gbc_lanaGiocatoreDueLabel = new GridBagConstraints();
				gbc_lanaGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_lanaGiocatoreDueLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lanaGiocatoreDueLabel.gridx = 0;
				gbc_lanaGiocatoreDueLabel.gridy = 5;
				panel.add(lanaGiocatoreDueLabel, gbc_lanaGiocatoreDueLabel);
			}
			{
				lanaGiocatoreDueTextField = new JTextField();
				lanaGiocatoreDueTextField.setEditable(false);
				GridBagConstraints gbc_lanaGiocatoreDueTextField = new GridBagConstraints();
				gbc_lanaGiocatoreDueTextField.anchor = GridBagConstraints.WEST;
				gbc_lanaGiocatoreDueTextField.gridx = 1;
				gbc_lanaGiocatoreDueTextField.gridy = 5;
				panel.add(lanaGiocatoreDueTextField, gbc_lanaGiocatoreDueTextField);
				lanaGiocatoreDueTextField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 2;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel idGiocatoreTreLabel = new JLabel("ID:");
				GridBagConstraints gbc_idGiocatoreTreLabel = new GridBagConstraints();
				gbc_idGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_idGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_idGiocatoreTreLabel.gridx = 0;
				gbc_idGiocatoreTreLabel.gridy = 0;
				panel.add(idGiocatoreTreLabel, gbc_idGiocatoreTreLabel);
			}
			{
				idGiocatoreTreTextField = new JTextField();
				idGiocatoreTreTextField.setEditable(false);
				GridBagConstraints gbc_idGiocatoreTreTextField = new GridBagConstraints();
				gbc_idGiocatoreTreTextField.anchor = GridBagConstraints.WEST;
				gbc_idGiocatoreTreTextField.insets = new Insets(0, 0, 5, 0);
				gbc_idGiocatoreTreTextField.gridx = 1;
				gbc_idGiocatoreTreTextField.gridy = 0;
				panel.add(idGiocatoreTreTextField, gbc_idGiocatoreTreTextField);
				idGiocatoreTreTextField.setColumns(10);
			}
			{
				JLabel granoGiocatoreTreLabel = new JLabel("Grano:");
				GridBagConstraints gbc_granoGiocatoreTreLabel = new GridBagConstraints();
				gbc_granoGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_granoGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_granoGiocatoreTreLabel.gridx = 0;
				gbc_granoGiocatoreTreLabel.gridy = 1;
				panel.add(granoGiocatoreTreLabel, gbc_granoGiocatoreTreLabel);
			}
			{
				granoGiocatoreTreTextField = new JTextField();
				granoGiocatoreTreTextField.setEditable(false);
				GridBagConstraints gbc_granoGiocatoreTreTextField = new GridBagConstraints();
				gbc_granoGiocatoreTreTextField.anchor = GridBagConstraints.WEST;
				gbc_granoGiocatoreTreTextField.insets = new Insets(0, 0, 5, 0);
				gbc_granoGiocatoreTreTextField.gridx = 1;
				gbc_granoGiocatoreTreTextField.gridy = 1;
				panel.add(granoGiocatoreTreTextField, gbc_granoGiocatoreTreTextField);
				granoGiocatoreTreTextField.setColumns(10);
			}
			{
				JLabel legnoGiocatoreTreLabel = new JLabel("Legno:");
				GridBagConstraints gbc_legnoGiocatoreTreLabel = new GridBagConstraints();
				gbc_legnoGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_legnoGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_legnoGiocatoreTreLabel.gridx = 0;
				gbc_legnoGiocatoreTreLabel.gridy = 2;
				panel.add(legnoGiocatoreTreLabel, gbc_legnoGiocatoreTreLabel);
			}
			{
				legnoGiocatoreTreTextField = new JTextField();
				legnoGiocatoreTreTextField.setEditable(false);
				GridBagConstraints gbc_legnoGiocatoreTreTextField = new GridBagConstraints();
				gbc_legnoGiocatoreTreTextField.anchor = GridBagConstraints.WEST;
				gbc_legnoGiocatoreTreTextField.insets = new Insets(0, 0, 5, 0);
				gbc_legnoGiocatoreTreTextField.gridx = 1;
				gbc_legnoGiocatoreTreTextField.gridy = 2;
				panel.add(legnoGiocatoreTreTextField, gbc_legnoGiocatoreTreTextField);
				legnoGiocatoreTreTextField.setColumns(10);
			}
			{
				JLabel argillaGiocatoreTreLabel = new JLabel("Argilla:");
				GridBagConstraints gbc_argillaGiocatoreTreLabel = new GridBagConstraints();
				gbc_argillaGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_argillaGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_argillaGiocatoreTreLabel.gridx = 0;
				gbc_argillaGiocatoreTreLabel.gridy = 3;
				panel.add(argillaGiocatoreTreLabel, gbc_argillaGiocatoreTreLabel);
			}
			{
				argillaGiocatoreTreTextField = new JTextField();
				argillaGiocatoreTreTextField.setEditable(false);
				GridBagConstraints gbc_argillaGiocatoreTreTextField = new GridBagConstraints();
				gbc_argillaGiocatoreTreTextField.anchor = GridBagConstraints.WEST;
				gbc_argillaGiocatoreTreTextField.insets = new Insets(0, 0, 5, 0);
				gbc_argillaGiocatoreTreTextField.gridx = 1;
				gbc_argillaGiocatoreTreTextField.gridy = 3;
				panel.add(argillaGiocatoreTreTextField, gbc_argillaGiocatoreTreTextField);
				argillaGiocatoreTreTextField.setColumns(10);
			}
			{
				JLabel mineraleGiocatoreTreLabel = new JLabel("Minerale:");
				GridBagConstraints gbc_mineraleGiocatoreTreLabel = new GridBagConstraints();
				gbc_mineraleGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_mineraleGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_mineraleGiocatoreTreLabel.gridx = 0;
				gbc_mineraleGiocatoreTreLabel.gridy = 4;
				panel.add(mineraleGiocatoreTreLabel, gbc_mineraleGiocatoreTreLabel);
			}
			{
				mineraleGiocatoreTreTextField = new JTextField();
				mineraleGiocatoreTreTextField.setEditable(false);
				GridBagConstraints gbc_mineraleGiocatoreTreTextField = new GridBagConstraints();
				gbc_mineraleGiocatoreTreTextField.anchor = GridBagConstraints.WEST;
				gbc_mineraleGiocatoreTreTextField.insets = new Insets(0, 0, 5, 0);
				gbc_mineraleGiocatoreTreTextField.gridx = 1;
				gbc_mineraleGiocatoreTreTextField.gridy = 4;
				panel.add(mineraleGiocatoreTreTextField, gbc_mineraleGiocatoreTreTextField);
				mineraleGiocatoreTreTextField.setColumns(10);
			}
			{
				JLabel lanaGiocatoreTreLabel = new JLabel("Lana:");
				GridBagConstraints gbc_lanaGiocatoreTreLabel = new GridBagConstraints();
				gbc_lanaGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_lanaGiocatoreTreLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lanaGiocatoreTreLabel.gridx = 0;
				gbc_lanaGiocatoreTreLabel.gridy = 5;
				panel.add(lanaGiocatoreTreLabel, gbc_lanaGiocatoreTreLabel);
			}
			{
				lanaGiocatoreTreTextField = new JTextField();
				lanaGiocatoreTreTextField.setEditable(false);
				GridBagConstraints gbc_lanaGiocatoreTreTextField = new GridBagConstraints();
				gbc_lanaGiocatoreTreTextField.anchor = GridBagConstraints.WEST;
				gbc_lanaGiocatoreTreTextField.gridx = 1;
				gbc_lanaGiocatoreTreTextField.gridy = 5;
				panel.add(lanaGiocatoreTreTextField, gbc_lanaGiocatoreTreTextField);
				lanaGiocatoreTreTextField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 2;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel idGiocatoreQuattroLabel = new JLabel("ID:");
				GridBagConstraints gbc_idGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_idGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_idGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_idGiocatoreQuattroLabel.gridx = 0;
				gbc_idGiocatoreQuattroLabel.gridy = 0;
				panel.add(idGiocatoreQuattroLabel, gbc_idGiocatoreQuattroLabel);
			}
			{
				idGiocatoreQuattroTextField = new JTextField();
				idGiocatoreQuattroTextField.setEditable(false);
				GridBagConstraints gbc_idGiocatoreQuattroTextField = new GridBagConstraints();
				gbc_idGiocatoreQuattroTextField.anchor = GridBagConstraints.WEST;
				gbc_idGiocatoreQuattroTextField.insets = new Insets(0, 0, 5, 0);
				gbc_idGiocatoreQuattroTextField.gridx = 1;
				gbc_idGiocatoreQuattroTextField.gridy = 0;
				panel.add(idGiocatoreQuattroTextField, gbc_idGiocatoreQuattroTextField);
				idGiocatoreQuattroTextField.setColumns(10);
			}
			{
				JLabel granoGiocatoreQuattroLabel = new JLabel("Grano:");
				GridBagConstraints gbc_granoGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_granoGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_granoGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_granoGiocatoreQuattroLabel.gridx = 0;
				gbc_granoGiocatoreQuattroLabel.gridy = 1;
				panel.add(granoGiocatoreQuattroLabel, gbc_granoGiocatoreQuattroLabel);
			}
			{
				granoGiocatoreQuattroTextField = new JTextField();
				granoGiocatoreQuattroTextField.setEditable(false);
				GridBagConstraints gbc_granoGiocatoreQuattroTextField = new GridBagConstraints();
				gbc_granoGiocatoreQuattroTextField.anchor = GridBagConstraints.WEST;
				gbc_granoGiocatoreQuattroTextField.insets = new Insets(0, 0, 5, 0);
				gbc_granoGiocatoreQuattroTextField.gridx = 1;
				gbc_granoGiocatoreQuattroTextField.gridy = 1;
				panel.add(granoGiocatoreQuattroTextField, gbc_granoGiocatoreQuattroTextField);
				granoGiocatoreQuattroTextField.setColumns(10);
			}
			{
				JLabel legnoGiocatoreQuattroLabel = new JLabel("Legno:");
				GridBagConstraints gbc_legnoGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_legnoGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_legnoGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_legnoGiocatoreQuattroLabel.gridx = 0;
				gbc_legnoGiocatoreQuattroLabel.gridy = 2;
				panel.add(legnoGiocatoreQuattroLabel, gbc_legnoGiocatoreQuattroLabel);
			}
			{
				legnoGiocatoreQuattroTextField = new JTextField();
				legnoGiocatoreQuattroTextField.setEditable(false);
				GridBagConstraints gbc_legnoGiocatoreQuattroTextField = new GridBagConstraints();
				gbc_legnoGiocatoreQuattroTextField.anchor = GridBagConstraints.WEST;
				gbc_legnoGiocatoreQuattroTextField.insets = new Insets(0, 0, 5, 0);
				gbc_legnoGiocatoreQuattroTextField.gridx = 1;
				gbc_legnoGiocatoreQuattroTextField.gridy = 2;
				panel.add(legnoGiocatoreQuattroTextField, gbc_legnoGiocatoreQuattroTextField);
				legnoGiocatoreQuattroTextField.setColumns(10);
			}
			{
				JLabel argillaGiocatoreQuattroLabel = new JLabel("Argilla:");
				GridBagConstraints gbc_argillaGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_argillaGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_argillaGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_argillaGiocatoreQuattroLabel.gridx = 0;
				gbc_argillaGiocatoreQuattroLabel.gridy = 3;
				panel.add(argillaGiocatoreQuattroLabel, gbc_argillaGiocatoreQuattroLabel);
			}
			{
				argillaGiocatoreQuattroTextField = new JTextField();
				argillaGiocatoreQuattroTextField.setEditable(false);
				GridBagConstraints gbc_argillaGiocatoreQuattroTextField = new GridBagConstraints();
				gbc_argillaGiocatoreQuattroTextField.anchor = GridBagConstraints.WEST;
				gbc_argillaGiocatoreQuattroTextField.insets = new Insets(0, 0, 5, 0);
				gbc_argillaGiocatoreQuattroTextField.gridx = 1;
				gbc_argillaGiocatoreQuattroTextField.gridy = 3;
				panel.add(argillaGiocatoreQuattroTextField, gbc_argillaGiocatoreQuattroTextField);
				argillaGiocatoreQuattroTextField.setColumns(10);
			}
			{
				JLabel mineraleGiocatoreQuattroLabel = new JLabel("Minerale:");
				GridBagConstraints gbc_mineraleGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_mineraleGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_mineraleGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_mineraleGiocatoreQuattroLabel.gridx = 0;
				gbc_mineraleGiocatoreQuattroLabel.gridy = 4;
				panel.add(mineraleGiocatoreQuattroLabel, gbc_mineraleGiocatoreQuattroLabel);
			}
			{
				mineraleGiocatoreQuattroTextField = new JTextField();
				mineraleGiocatoreQuattroTextField.setEditable(false);
				GridBagConstraints gbc_mineraleGiocatoreQuattroTextField = new GridBagConstraints();
				gbc_mineraleGiocatoreQuattroTextField.anchor = GridBagConstraints.WEST;
				gbc_mineraleGiocatoreQuattroTextField.insets = new Insets(0, 0, 5, 0);
				gbc_mineraleGiocatoreQuattroTextField.gridx = 1;
				gbc_mineraleGiocatoreQuattroTextField.gridy = 4;
				panel.add(mineraleGiocatoreQuattroTextField, gbc_mineraleGiocatoreQuattroTextField);
				mineraleGiocatoreQuattroTextField.setColumns(10);
			}
			{
				JLabel lanaGiocatoreQuattroLabel = new JLabel("Lana:");
				GridBagConstraints gbc_lanaGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_lanaGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_lanaGiocatoreQuattroLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lanaGiocatoreQuattroLabel.gridx = 0;
				gbc_lanaGiocatoreQuattroLabel.gridy = 5;
				panel.add(lanaGiocatoreQuattroLabel, gbc_lanaGiocatoreQuattroLabel);
			}
			{
				lanaGiocatoreQuattroTextField = new JTextField();
				lanaGiocatoreQuattroTextField.setEditable(false);
				GridBagConstraints gbc_lanaGiocatoreQuattroTextField = new GridBagConstraints();
				gbc_lanaGiocatoreQuattroTextField.anchor = GridBagConstraints.WEST;
				gbc_lanaGiocatoreQuattroTextField.gridx = 1;
				gbc_lanaGiocatoreQuattroTextField.gridy = 5;
				panel.add(lanaGiocatoreQuattroTextField, gbc_lanaGiocatoreQuattroTextField);
				lanaGiocatoreQuattroTextField.setColumns(10);
			}
		}
		try 
		{
			setTexFields();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei dati");
			dispose();
		}
	}

}
