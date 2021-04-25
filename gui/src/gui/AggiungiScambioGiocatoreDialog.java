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
import javax.swing.border.TitledBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AggiungiScambioGiocatoreDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField quantitaLanaDareTextField;
	private JTextField quantitaLegnoDareTextField;
	private JTextField quantitaMineraleDareTextField;
	private JTextField quantitaArgillaDareTextField;
	private JTextField quantitaGranoDareTextField;
	private JTextField quantitaLanaRicevereTextField;
	private JTextField quantitaLegnoRicevereTextField;
	private JTextField quantitaMineraleRicevereTextField;
	private JTextField quantitaArgillaRicevereTextField;
	private JTextField quantitaGranoRicevereTextField;
	private int idTurno;
	private Controller controller;
	private int idPartita;
	private int idGiocatore;
	private JComboBox idComboBox;

	private ArrayList<Integer> getIdGiocatori() throws SQLException
	{
		ArrayList<Integer> idGiocatori = InterazioneDatabase.getIdGiocatori(controller, idPartita);
		idGiocatori.remove((Object)idGiocatore);
		return idGiocatori;
	}
	
	private void checkQuantita(String tipo, int quantita, int idGiocatore) throws Exception
	{
		if(InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, tipo) < quantita)
			throw new Exception();
	}
	
	private void checkCarteDaDare() throws Exception
	{
		checkQuantita("Grano", Integer.parseInt(quantitaGranoDareTextField.getText()), idGiocatore);
		checkQuantita("Minerale", Integer.parseInt(quantitaMineraleDareTextField.getText()), idGiocatore);
		checkQuantita("Argilla", Integer.parseInt(quantitaArgillaDareTextField.getText()), idGiocatore);
		checkQuantita("Lana", Integer.parseInt(quantitaLanaDareTextField.getText()), idGiocatore);
		checkQuantita("Legno", Integer.parseInt(quantitaLegnoDareTextField.getText()), idGiocatore);
	}
	
	private void checkCarteDaRicevere() throws Exception
	{
		int idGiocatore = Integer.parseInt(idComboBox.getSelectedItem().toString());
		checkQuantita("Grano", Integer.parseInt(quantitaGranoRicevereTextField.getText()), idGiocatore);
		checkQuantita("Minerale", Integer.parseInt(quantitaMineraleRicevereTextField.getText()), idGiocatore);
		checkQuantita("Argilla", Integer.parseInt(quantitaArgillaRicevereTextField.getText()), idGiocatore);
		checkQuantita("Lana", Integer.parseInt(quantitaLanaRicevereTextField.getText()), idGiocatore);
		checkQuantita("Legno", Integer.parseInt(quantitaLegnoRicevereTextField.getText()), idGiocatore);
	}
	
	private void swapCarteDareGrano(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Grano");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, secondoGiocatore, idTurno);
		}
	}
	
	private void swapCarteDareLegno(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Legno");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, secondoGiocatore, idTurno);
		}
	}
	
	private void swapCarteDareMinerale(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Minerale");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, secondoGiocatore, idTurno);
		}
	}
	
	private void swapCarteDareArgilla(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Argilla");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, secondoGiocatore, idTurno);
		}
	}
	
	private void swapCarteDareLana(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Lana");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaDate(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, secondoGiocatore, idTurno);
		}
	}
	
	private void swapCarteRicevereGrano(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, secondoGiocatore, idTurno, "Grano");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, idGiocatore, idTurno);
		}
	}
	
	private void swapCarteRicevereLegno(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, secondoGiocatore, idTurno, "Legno");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, idGiocatore, idTurno);
		}
	}
	
	private void swapCarteRicevereMinerale(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, secondoGiocatore, idTurno, "Minerale");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, idGiocatore, idTurno);
		}
	}
	
	private void swapCarteRicevereArgilla(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, secondoGiocatore, idTurno, "Argilla");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, idGiocatore, idTurno);
		}
	}
	
	private void swapCarteRicevereLana(int idScambio, int secondoGiocatore, int quantita) throws SQLException
	{
		ArrayList<Integer> ids = InterazioneDatabase.getIdsCarteRisorsa(controller, secondoGiocatore, idTurno, "Lana");
		for(int i = 0; i != quantita; ++i)
		{
			int idCartaRisorsa = ids.get(i);
			InterazioneDatabase.aggiungiScambiCarteRisorsaRic(controller, idScambio, idCartaRisorsa);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, idCartaRisorsa, idGiocatore, idTurno);
		}
	}
	
	private void swapCarteDare(int idScambio, int secondoGiocatore) throws SQLException
	{
		int quantita = Integer.parseInt(quantitaGranoDareTextField.getText().toString());
		swapCarteDareGrano(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaLegnoDareTextField.getText().toString());
		swapCarteDareLegno(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaMineraleDareTextField.getText().toString());
		swapCarteDareMinerale(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaArgillaDareTextField.getText().toString());
		swapCarteDareArgilla(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaLanaDareTextField.getText().toString());
		swapCarteDareLana(idScambio, secondoGiocatore, quantita);
	}
	
	private void swapCarteRicevere(int idScambio, int secondoGiocatore) throws SQLException
	{
		int quantita = Integer.parseInt(quantitaGranoRicevereTextField.getText().toString());
		swapCarteRicevereGrano(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaLegnoRicevereTextField.getText().toString());
		swapCarteRicevereLegno(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaMineraleRicevereTextField.getText().toString());
		swapCarteRicevereMinerale(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaArgillaRicevereTextField.getText().toString());
		swapCarteRicevereArgilla(idScambio, secondoGiocatore, quantita);
		quantita = Integer.parseInt(quantitaLanaRicevereTextField.getText().toString());
		swapCarteRicevereLana(idScambio, secondoGiocatore, quantita);
	}
	
	
	private void eseguiScambio()
	{
		try 
		{
			if(checkTextFields())
			{
				checkCarteDaDare();
				checkCarteDaRicevere();
				int secondoGiocatore = Integer.parseInt(idComboBox.getSelectedItem().toString());
				int idScambio = InterazioneDatabase.aggiungiScambioGiocatore(controller, idTurno, secondoGiocatore);
				swapCarteDare(idScambio, secondoGiocatore);
				swapCarteRicevere(idScambio, secondoGiocatore);
				JOptionPane.showMessageDialog(this, "Scambio aggiunto con successo");
				dispose();
			}
			else
				JOptionPane.showMessageDialog(this, "Errore riempire tutte le caselle");
		} 
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "Errore nell'aggiunta dello scambio");
			dispose();
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, "Errore la quantità delle carte è errata");
		}
	}
	
	private boolean checkTextFields()
	{
		boolean lanaDare = quantitaLanaDareTextField.equals("");
		boolean granoDare = quantitaGranoDareTextField.equals("");
		boolean legnoDare = quantitaLegnoDareTextField.equals("");
		boolean argillaDare = quantitaArgillaDareTextField.equals("");
		boolean mineraleDare = quantitaMineraleDareTextField.equals("");
		boolean lanaRicevere = quantitaLanaRicevereTextField.equals("");
		boolean granoRicevere = quantitaGranoRicevereTextField.equals("");
		boolean legnoRicevere = quantitaLegnoRicevereTextField.equals("");
		boolean argillaRicevere = quantitaArgillaRicevereTextField.equals("");
		boolean mineraleRicevere = quantitaMineraleRicevereTextField.equals("");
		if(lanaDare || granoDare || legnoDare || argillaDare || mineraleDare ||
		   lanaRicevere || granoRicevere || legnoRicevere || argillaRicevere || mineraleRicevere)
			return false;
		return true;
	}

	/**
	 * Create the dialog.
	 */
	public AggiungiScambioGiocatoreDialog(Controller controller, int idTurno, int idGiocatore) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		ArrayList<Integer> idGiocatori = null;
		try 
		{
			idPartita = InterazioneDatabase.getIdPartita(controller, idTurno);
			idGiocatori = getIdGiocatori();
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento della finestra");
			dispose();
		}
		setTitle("Aggiungi Scambio Con Giocatore");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setBounds(100, 100, 288, 529);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Giocatore Con Cui Commerciare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel idLabel = new JLabel("ID:");
				GridBagConstraints gbc_idLabel = new GridBagConstraints();
				gbc_idLabel.insets = new Insets(0, 0, 0, 5);
				gbc_idLabel.anchor = GridBagConstraints.EAST;
				gbc_idLabel.gridx = 0;
				gbc_idLabel.gridy = 1;
				panel.add(idLabel, gbc_idLabel);
			}
			{
				idComboBox = new JComboBox(idGiocatori.toArray());
				GridBagConstraints gbc_idComboBox = new GridBagConstraints();
				gbc_idComboBox.anchor = GridBagConstraints.WEST;
				gbc_idComboBox.gridx = 1;
				gbc_idComboBox.gridy = 1;
				panel.add(idComboBox, gbc_idComboBox);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Carte Da Dare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel quantitaLanaDareLabel = new JLabel("Quantit\u00E0 Lana:");
				GridBagConstraints gbc_quantitaLanaDareLabel = new GridBagConstraints();
				gbc_quantitaLanaDareLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaLanaDareLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaLanaDareLabel.gridx = 0;
				gbc_quantitaLanaDareLabel.gridy = 1;
				panel.add(quantitaLanaDareLabel, gbc_quantitaLanaDareLabel);
			}
			{
				quantitaLanaDareTextField = new JTextField();
				quantitaLanaDareTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaLanaDareTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaLanaDareTextField = new GridBagConstraints();
				gbc_quantitaLanaDareTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaLanaDareTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaLanaDareTextField.gridx = 1;
				gbc_quantitaLanaDareTextField.gridy = 1;
				panel.add(quantitaLanaDareTextField, gbc_quantitaLanaDareTextField);
				quantitaLanaDareTextField.setColumns(10);
			}
			{
				JLabel quantitaLegnoDareLabel = new JLabel("Quantit\u00E0 Legno:");
				GridBagConstraints gbc_quantitaLegnoDareLabel = new GridBagConstraints();
				gbc_quantitaLegnoDareLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaLegnoDareLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaLegnoDareLabel.gridx = 0;
				gbc_quantitaLegnoDareLabel.gridy = 2;
				panel.add(quantitaLegnoDareLabel, gbc_quantitaLegnoDareLabel);
			}
			{
				quantitaLegnoDareTextField = new JTextField();
				quantitaLegnoDareTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaLegnoDareTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaLegnoDareTextField = new GridBagConstraints();
				gbc_quantitaLegnoDareTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaLegnoDareTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaLegnoDareTextField.gridx = 1;
				gbc_quantitaLegnoDareTextField.gridy = 2;
				panel.add(quantitaLegnoDareTextField, gbc_quantitaLegnoDareTextField);
				quantitaLegnoDareTextField.setColumns(10);
			}
			{
				JLabel quantitaMineraleDareLabel = new JLabel("Quantit\u00E0 Minerale:");
				GridBagConstraints gbc_quantitaMineraleDareLabel = new GridBagConstraints();
				gbc_quantitaMineraleDareLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaMineraleDareLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaMineraleDareLabel.gridx = 0;
				gbc_quantitaMineraleDareLabel.gridy = 3;
				panel.add(quantitaMineraleDareLabel, gbc_quantitaMineraleDareLabel);
			}
			{
				quantitaMineraleDareTextField = new JTextField();
				quantitaMineraleDareTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaMineraleDareTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaMineraleDareTextField = new GridBagConstraints();
				gbc_quantitaMineraleDareTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaMineraleDareTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaMineraleDareTextField.gridx = 1;
				gbc_quantitaMineraleDareTextField.gridy = 3;
				panel.add(quantitaMineraleDareTextField, gbc_quantitaMineraleDareTextField);
				quantitaMineraleDareTextField.setColumns(10);
			}
			{
				JLabel quantitaArgillaDareLabel = new JLabel("Quantit\u00E0 Argilla:");
				GridBagConstraints gbc_quantitaArgillaDareLabel = new GridBagConstraints();
				gbc_quantitaArgillaDareLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaArgillaDareLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaArgillaDareLabel.gridx = 0;
				gbc_quantitaArgillaDareLabel.gridy = 4;
				panel.add(quantitaArgillaDareLabel, gbc_quantitaArgillaDareLabel);
			}
			{
				quantitaArgillaDareTextField = new JTextField();
				quantitaArgillaDareTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaArgillaDareTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaArgillaDareTextField = new GridBagConstraints();
				gbc_quantitaArgillaDareTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaArgillaDareTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaArgillaDareTextField.gridx = 1;
				gbc_quantitaArgillaDareTextField.gridy = 4;
				panel.add(quantitaArgillaDareTextField, gbc_quantitaArgillaDareTextField);
				quantitaArgillaDareTextField.setColumns(10);
			}
			{
				JLabel quantitaGranoDareLabel = new JLabel("Quantit\u00E0 Grano:");
				GridBagConstraints gbc_quantitaGranoDareLabel = new GridBagConstraints();
				gbc_quantitaGranoDareLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaGranoDareLabel.insets = new Insets(0, 0, 0, 5);
				gbc_quantitaGranoDareLabel.gridx = 0;
				gbc_quantitaGranoDareLabel.gridy = 5;
				panel.add(quantitaGranoDareLabel, gbc_quantitaGranoDareLabel);
			}
			{
				quantitaGranoDareTextField = new JTextField();
				quantitaGranoDareTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaGranoDareTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaGranoDareTextField = new GridBagConstraints();
				gbc_quantitaGranoDareTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaGranoDareTextField.gridx = 1;
				gbc_quantitaGranoDareTextField.gridy = 5;
				panel.add(quantitaGranoDareTextField, gbc_quantitaGranoDareTextField);
				quantitaGranoDareTextField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Carte Da Ricevere", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
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
				JLabel quantitaLanaRicevereLabel = new JLabel("Quantit\u00E0 Lana:");
				GridBagConstraints gbc_quantitaLanaRicevereLabel = new GridBagConstraints();
				gbc_quantitaLanaRicevereLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaLanaRicevereLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaLanaRicevereLabel.gridx = 0;
				gbc_quantitaLanaRicevereLabel.gridy = 1;
				panel.add(quantitaLanaRicevereLabel, gbc_quantitaLanaRicevereLabel);
			}
			{
				quantitaLanaRicevereTextField = new JTextField();
				quantitaLanaRicevereTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaLanaRicevereTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaLanaRicevereTextField = new GridBagConstraints();
				gbc_quantitaLanaRicevereTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaLanaRicevereTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaLanaRicevereTextField.gridx = 1;
				gbc_quantitaLanaRicevereTextField.gridy = 1;
				panel.add(quantitaLanaRicevereTextField, gbc_quantitaLanaRicevereTextField);
				quantitaLanaRicevereTextField.setColumns(10);
			}
			{
				JLabel quantitaLegnoRicevereLabel = new JLabel("Quantit\u00E0 Legno:");
				GridBagConstraints gbc_quantitaLegnoRicevereLabel = new GridBagConstraints();
				gbc_quantitaLegnoRicevereLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaLegnoRicevereLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaLegnoRicevereLabel.gridx = 0;
				gbc_quantitaLegnoRicevereLabel.gridy = 2;
				panel.add(quantitaLegnoRicevereLabel, gbc_quantitaLegnoRicevereLabel);
			}
			{
				quantitaLegnoRicevereTextField = new JTextField();
				quantitaLegnoRicevereTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaLegnoRicevereTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaLegnoRicevereTextField = new GridBagConstraints();
				gbc_quantitaLegnoRicevereTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaLegnoRicevereTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaLegnoRicevereTextField.gridx = 1;
				gbc_quantitaLegnoRicevereTextField.gridy = 2;
				panel.add(quantitaLegnoRicevereTextField, gbc_quantitaLegnoRicevereTextField);
				quantitaLegnoRicevereTextField.setColumns(10);
			}
			{
				JLabel quantitaMineraleRicevereLabel = new JLabel("Quantit\u00E0 Minerale:");
				GridBagConstraints gbc_quantitaMineraleRicevereLabel = new GridBagConstraints();
				gbc_quantitaMineraleRicevereLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaMineraleRicevereLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaMineraleRicevereLabel.gridx = 0;
				gbc_quantitaMineraleRicevereLabel.gridy = 3;
				panel.add(quantitaMineraleRicevereLabel, gbc_quantitaMineraleRicevereLabel);
			}
			{
				quantitaMineraleRicevereTextField = new JTextField();
				quantitaMineraleRicevereTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaMineraleRicevereTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaMineraleRicevereTextField = new GridBagConstraints();
				gbc_quantitaMineraleRicevereTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaMineraleRicevereTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaMineraleRicevereTextField.gridx = 1;
				gbc_quantitaMineraleRicevereTextField.gridy = 3;
				panel.add(quantitaMineraleRicevereTextField, gbc_quantitaMineraleRicevereTextField);
				quantitaMineraleRicevereTextField.setColumns(10);
			}
			{
				JLabel quantitaArgillaRicevereLabel = new JLabel("Quantit\u00E0 Argilla:");
				GridBagConstraints gbc_quantitaArgillaRicevereLabel = new GridBagConstraints();
				gbc_quantitaArgillaRicevereLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaArgillaRicevereLabel.insets = new Insets(0, 0, 5, 5);
				gbc_quantitaArgillaRicevereLabel.gridx = 0;
				gbc_quantitaArgillaRicevereLabel.gridy = 4;
				panel.add(quantitaArgillaRicevereLabel, gbc_quantitaArgillaRicevereLabel);
			}
			{
				quantitaArgillaRicevereTextField = new JTextField();
				quantitaArgillaRicevereTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaArgillaRicevereTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaArgillaRicevereTextField = new GridBagConstraints();
				gbc_quantitaArgillaRicevereTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaArgillaRicevereTextField.insets = new Insets(0, 0, 5, 0);
				gbc_quantitaArgillaRicevereTextField.gridx = 1;
				gbc_quantitaArgillaRicevereTextField.gridy = 4;
				panel.add(quantitaArgillaRicevereTextField, gbc_quantitaArgillaRicevereTextField);
				quantitaArgillaRicevereTextField.setColumns(10);
			}
			{
				JLabel quantitaGranoRicevereLabel = new JLabel("Quantit\u00E0 Grano:");
				GridBagConstraints gbc_quantitaGranoRicevereLabel = new GridBagConstraints();
				gbc_quantitaGranoRicevereLabel.anchor = GridBagConstraints.EAST;
				gbc_quantitaGranoRicevereLabel.insets = new Insets(0, 0, 0, 5);
				gbc_quantitaGranoRicevereLabel.gridx = 0;
				gbc_quantitaGranoRicevereLabel.gridy = 5;
				panel.add(quantitaGranoRicevereLabel, gbc_quantitaGranoRicevereLabel);
			}
			{
				quantitaGranoRicevereTextField = new JTextField();
				quantitaGranoRicevereTextField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) 
					{
						if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
						{
							JOptionPane.showMessageDialog(AggiungiScambioGiocatoreDialog.this, "Errore! Non e' possibile inserire dei caratteri");
							quantitaGranoRicevereTextField.setText("");
						}
					}
				});
				GridBagConstraints gbc_quantitaGranoRicevereTextField = new GridBagConstraints();
				gbc_quantitaGranoRicevereTextField.anchor = GridBagConstraints.WEST;
				gbc_quantitaGranoRicevereTextField.gridx = 1;
				gbc_quantitaGranoRicevereTextField.gridy = 5;
				panel.add(quantitaGranoRicevereTextField, gbc_quantitaGranoRicevereTextField);
				quantitaGranoRicevereTextField.setColumns(10);
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
						eseguiScambio();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton annullaButton = new JButton("Annulla");
				annullaButton.addActionListener(new ActionListener() {
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
