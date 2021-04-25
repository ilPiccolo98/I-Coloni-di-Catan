package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreaPartitaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel giocatoreUnoPanel;
	private JPanel giocatoreDuePanel;
	private JPanel giocatoreTrePanel;
	private JPanel giocatoreQuattroPanel;
	private JPanel pulsantiPanel;
	private JButton okButton;
	private JButton annullaButton;
	private JLabel nicknameUnoLabel;
	private JLabel coloreUnoLabel;
	private JLabel etaUnoLabel;
	private JPanel infoPartitaPanel;
	private JTextField nicknameUnoTextField;
	private JComboBox coloreUnoComboBox;
	private JTextField etaUnoTextField;
	private JLabel durataLabel;
	private JLabel giornoLabel;
	private JComboBox giornoComboBox;
	private JLabel meseLabel;
	private JComboBox meseComboBox;
	private JLabel annoLabel;
	private JComboBox annoComboBox;
	private JTextField durataTextField;
	private JLabel nicknameDueLabel;
	private JLabel coloreDueLabel;
	private JLabel etaDueLabel;
	private JLabel nicknameTreLabel;
	private JLabel coloreTreLabel;
	private JLabel etaTreLabel;
	private JLabel nicknameQuattroLabel;
	private JLabel coloreQuattroLabel;
	private JLabel etaQuattroLabel;
	private JTextField nicknameDueTextField;
	private JTextField etaDueTextField;
	private JTextField nicknameTreTextField;
	private JTextField etaTreTextField;
	private JTextField nicknameQuattroTextField;
	private JTextField etaQuattroTextField;
	private JComboBox coloreDueComboBox;
	private JComboBox coloreTreComboBox;
	private JComboBox coloreQuattroComboBox;
	private Controller controller;
	private JLabel idGiocatoreUnoLabel;
	private JLabel idGiocatoreDueLabel;
	private JLabel idGiocatoreTreLabel;
	private JLabel idGiocatoreQuattroLabel;
	private JCheckBox nuovoGiocatoreDueCheckBox;
	private JCheckBox nuovoGiocatoreTreCheckBox;
	private JCheckBox nuovoGiocatoreUnoCheckBox;
	private JCheckBox nuovoGiocatoreQuattroCheckBox;
	private int idPartita;
	private boolean prosegui;
	private int idGiocatoreUno = 0;
	private int idGiocatoreDue = 0;
	private int idGiocatoreTre = 0;
	private int idGiocatoreQuattro = 0;
	private JComboBox idGiocatoreUnoComboBox;
	private JComboBox idGiocatoreDueComboBox;
	private JComboBox idGiocatoreTreComboBox;
	private JComboBox idGiocatoreQuattroComboBox;
	
	private Object[] generaIdGiocatori() throws SQLException
	{
		return InterazioneDatabase.getIdGiocatori(controller).toArray();
	}
	
	private Object[] generaMesi()
	{
		ArrayList<Integer> mesi = new ArrayList<Integer>();
		for(int i = 1; i <= 12; ++i)
			mesi.add(i);
		return mesi.toArray();
	}
	
	private Object[] generaAnni()
	{
		ArrayList<Integer> anni = new ArrayList<Integer>();
		for(int i = 1900; i <= 2100; ++i)
			anni.add(i);
		return anni.toArray();
	}
	
	private boolean bisestile(int anno)
	{
		if((anno % 4 == 0 && anno % 100 != 0) || (anno % 4 == 0 && anno % 100 == 0 && anno % 400 == 0))
			return true;
		return false;
	}
	
	private void setComboBoxGiorni()
	{
		String mese = meseComboBox.getSelectedItem().toString();
		ArrayList<Integer> giorni = new ArrayList<Integer>();
		DefaultComboBoxModel model = null;
		switch(mese)
		{
			case "1": case "3": case "5": case "7": 
			case "8": case "10": case "12":
				for(int i = 1; i <= 31; ++i)
					giorni.add(i);
				model = new DefaultComboBoxModel(giorni.toArray());
				giornoComboBox.setModel(model);
				break;
			case "4": case "6": case "9": case "11":
				for(int i = 1; i <= 30; ++i)
					giorni.add(i);
				model = new DefaultComboBoxModel(giorni.toArray());
				giornoComboBox.setModel(model);
				break;
			case "2":
				if(bisestile((int)annoComboBox.getSelectedItem()))
				{	
					for(int i = 1; i <= 29; ++i)
						giorni.add(i);
					model = new DefaultComboBoxModel(giorni.toArray());
					giornoComboBox.setModel(model);
				}
				else
				{
					for(int i = 1; i <= 28; ++i)
						giorni.add(i);
					model = new DefaultComboBoxModel(giorni.toArray());
					giornoComboBox.setModel(model);	
				}
					
				break;
		}
	}
	
	public boolean getProsegui()
	{
		return prosegui;
	}
	
	public int getIdGiocatoreUno()
	{
		return idGiocatoreUno;
	}
	
	public int getIdGiocatoreDue()
	{
		return idGiocatoreDue;
	}
	
	public int getIdGiocatoreTre()
	{
		return idGiocatoreTre;
	}
	
	public int getIdGiocatoreQuattro()
	{
		return idGiocatoreQuattro;
	}
	
	public boolean giocatoreUnoNuovo()
	{
		return nuovoGiocatoreUnoCheckBox.isSelected();
	}
	
	public boolean giocatoreDueNuovo()
	{
		return nuovoGiocatoreDueCheckBox.isSelected();
	}
	
	public boolean giocatoreTreNuovo()
	{
		return nuovoGiocatoreTreCheckBox.isSelected();
	}
	
	public boolean giocatoreQuattroNuovo()
	{
		return nuovoGiocatoreQuattroCheckBox.isSelected();
	}
	
	private boolean checkNicknameTextFields()
	{
		if(nicknameUnoTextField.getText().equals("") || nicknameDueTextField.getText().equals("")||
		   nicknameTreTextField.getText().equals("") || nicknameQuattroTextField.getText().equals(""))
			return false;
		return true;
	}
	
	private boolean checkEtaTextFields()
	{
		if((etaUnoTextField.isEnabled() && etaUnoTextField.getText().equals("")) ||
		   (etaDueTextField.isEnabled() && etaDueTextField.getText().equals("")) ||
		   (etaTreTextField.isEnabled() && etaTreTextField.getText().equals("")) ||
		   (etaQuattroTextField.isEnabled() && etaQuattroTextField.getText().equals("")))
			return false;
		return true;
	}
	
	private int aggiungiGiocatore(JCheckBox nuovoGiocatore, JComboBox id, JTextField eta) throws SQLException
	{
		if(nuovoGiocatore.isSelected())
		{
			int idGiocatore = InterazioneDatabase.nuovoIdGiocatore(controller);
			InterazioneDatabase.aggiungiGiocatore(controller, idGiocatore, Integer.parseInt(eta.getText()));
			return idGiocatore;
		}
		return Integer.parseInt(id.getSelectedItem().toString());
	}
	
	private void aggiungiPartita()
	{
		if(durataTextField.getText().equals("") || !checkNicknameTextFields() || !checkEtaTextFields())
			JOptionPane.showMessageDialog(this, "Riempire tutte le caselle!");
		else
		{
			String data = InterazioneDatabase.fondiData(giornoComboBox.getSelectedItem().toString(), meseComboBox.getSelectedItem().toString(), annoComboBox.getSelectedItem().toString());
			idPartita = 0;
			try 
			{
				idPartita = InterazioneDatabase.nuovoIdPartita(controller);
				InterazioneDatabase.aggiungiPartita(controller, idPartita, Integer.parseInt(durataTextField.getText()), data);
				idGiocatoreUno = aggiungiGiocatore(nuovoGiocatoreUnoCheckBox, idGiocatoreUnoComboBox, etaUnoTextField);
				idGiocatoreDue = aggiungiGiocatore(nuovoGiocatoreDueCheckBox, idGiocatoreDueComboBox, etaDueTextField);
				idGiocatoreTre = aggiungiGiocatore(nuovoGiocatoreTreCheckBox, idGiocatoreTreComboBox, etaTreTextField);
				idGiocatoreQuattro = aggiungiGiocatore(nuovoGiocatoreQuattroCheckBox, idGiocatoreQuattroComboBox, etaQuattroTextField);
				InterazioneDatabase.aggiungiGiocatoriPartite(controller, idPartita, 
															 idGiocatoreUno, nicknameUnoTextField.getText(), coloreUnoComboBox.getSelectedItem().toString(), 
															 idGiocatoreDue, nicknameDueTextField.getText(), coloreDueComboBox.getSelectedItem().toString(), 
															 idGiocatoreTre, nicknameTreTextField.getText(), coloreTreComboBox.getSelectedItem().toString(), 
															 idGiocatoreQuattro, nicknameQuattroTextField.getText(), coloreQuattroComboBox.getSelectedItem().toString());
				prosegui = true;
				dispose();
			} 
			catch (NumberFormatException e) 
			{
				
			} 
			catch (SQLException e) 
			{
				try 
				{
					InterazioneDatabase.rimuoviPartita(controller, idPartita);
					if(giocatoreUnoNuovo())
						InterazioneDatabase.rimuoviGiocatore(controller, idGiocatoreUno);
					if(giocatoreDueNuovo())
						InterazioneDatabase.rimuoviGiocatore(controller, idGiocatoreDue);
					if(giocatoreTreNuovo())
						InterazioneDatabase.rimuoviGiocatore(controller, idGiocatoreTre);
					if(giocatoreQuattroNuovo())
						InterazioneDatabase.rimuoviGiocatore(controller, idGiocatoreQuattro);
					JOptionPane.showMessageDialog(this, "I dati inseriti non sono corretti!");
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(this, "Fatal error!");
				}
			}
		}
	}
	
	public int getIdPartita()
	{
		return idPartita;
	}
	
	public boolean nuovoGiocatoreUnoSelezionato()
	{
		return nuovoGiocatoreUnoCheckBox.isSelected();
	}
	
	public boolean nuovoGiocatoreDueSelezionato()
	{
		return nuovoGiocatoreDueCheckBox.isSelected();
	}
	
	public boolean nuovoGiocatoreTreSelezionato()
	{
		return nuovoGiocatoreTreCheckBox.isSelected();
	}
	
	public boolean nuovoGiocatoreQuattroSelezionato()
	{
		return nuovoGiocatoreQuattroCheckBox.isSelected();
	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller c = new Controller();
					c.connettiAlDatabase("jdbc:oracle:thin:@localhost:1521", "orcl", "system", "mydatabase");
					CreaPartitaDialog frame = new CreaPartitaDialog(c);
					frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}
	/**
	 * Create the dialog.
	 */
	public CreaPartitaDialog(Controller controller) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.controller = controller;
		Object[] colori = { "Rosso", "Blu", "Bianco", "Arancione" };
		Object[] anni = generaAnni();
		Object[] mesi = generaMesi();
		Object[] giocatori = null;
		try 
		{
			giocatori = generaIdGiocatori();
		} 
		catch (SQLException e1) 
		{
			JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore nel caricamento dei giocatori");
			dispose();
		}
		setTitle("Creazione Partita");
		setBounds(100, 100, 550, 797);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		infoPartitaPanel = new JPanel();
		infoPartitaPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Info Partita", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_infoPartitaPanel = new GridBagConstraints();
		gbc_infoPartitaPanel.insets = new Insets(0, 0, 5, 0);
		gbc_infoPartitaPanel.fill = GridBagConstraints.BOTH;
		gbc_infoPartitaPanel.gridx = 0;
		gbc_infoPartitaPanel.gridy = 0;
		contentPanel.add(infoPartitaPanel, gbc_infoPartitaPanel);
		GridBagLayout gbl_infoPartitaPanel = new GridBagLayout();
		gbl_infoPartitaPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_infoPartitaPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_infoPartitaPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_infoPartitaPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		infoPartitaPanel.setLayout(gbl_infoPartitaPanel);
		
		durataLabel = new JLabel("Durata in minuti");
		GridBagConstraints gbc_durataLabel = new GridBagConstraints();
		gbc_durataLabel.anchor = GridBagConstraints.EAST;
		gbc_durataLabel.insets = new Insets(0, 0, 5, 5);
		gbc_durataLabel.gridx = 0;
		gbc_durataLabel.gridy = 0;
		infoPartitaPanel.add(durataLabel, gbc_durataLabel);
		
		durataTextField = new JTextField();
		durataTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyChar() < '0' || e.getKeyChar() > '9' && e.getKeyCode() != e.VK_BACK_SPACE)
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire dei caratteri");
					durataTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_durataTextField = new GridBagConstraints();
		gbc_durataTextField.anchor = GridBagConstraints.WEST;
		gbc_durataTextField.insets = new Insets(0, 0, 5, 5);
		gbc_durataTextField.gridx = 1;
		gbc_durataTextField.gridy = 0;
		infoPartitaPanel.add(durataTextField, gbc_durataTextField);
		durataTextField.setColumns(10);
		
		annoLabel = new JLabel("Anno:");
		GridBagConstraints gbc_annoLabel = new GridBagConstraints();
		gbc_annoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_annoLabel.anchor = GridBagConstraints.EAST;
		gbc_annoLabel.gridx = 0;
		gbc_annoLabel.gridy = 1;
		infoPartitaPanel.add(annoLabel, gbc_annoLabel);
		
		annoComboBox = new JComboBox(anni);
		annoComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) 
			{
				if(e.getStateChange() == e.SELECTED)
					setComboBoxGiorni();
			}
		});
		GridBagConstraints gbc_annoComboBox = new GridBagConstraints();
		gbc_annoComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_annoComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_annoComboBox.gridx = 1;
		gbc_annoComboBox.gridy = 1;
		infoPartitaPanel.add(annoComboBox, gbc_annoComboBox);
		
		meseLabel = new JLabel("Mese:");
		GridBagConstraints gbc_meseLabel = new GridBagConstraints();
		gbc_meseLabel.insets = new Insets(0, 0, 5, 5);
		gbc_meseLabel.anchor = GridBagConstraints.EAST;
		gbc_meseLabel.gridx = 0;
		gbc_meseLabel.gridy = 2;
		infoPartitaPanel.add(meseLabel, gbc_meseLabel);
		
		meseComboBox = new JComboBox(mesi);
		meseComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) 
			{
				if(e.getStateChange() == ItemEvent.SELECTED)
					setComboBoxGiorni();
			}
		});
		GridBagConstraints gbc_meseComboBox = new GridBagConstraints();
		gbc_meseComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_meseComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_meseComboBox.gridx = 1;
		gbc_meseComboBox.gridy = 2;
		infoPartitaPanel.add(meseComboBox, gbc_meseComboBox);
		
		giornoLabel = new JLabel("Giorno:");
		GridBagConstraints gbc_giornoLabel = new GridBagConstraints();
		gbc_giornoLabel.insets = new Insets(0, 0, 0, 5);
		gbc_giornoLabel.anchor = GridBagConstraints.EAST;
		gbc_giornoLabel.gridx = 0;
		gbc_giornoLabel.gridy = 3;
		infoPartitaPanel.add(giornoLabel, gbc_giornoLabel);
		
		giornoComboBox = new JComboBox();
		GridBagConstraints gbc_giornoComboBox = new GridBagConstraints();
		gbc_giornoComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_giornoComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_giornoComboBox.gridx = 1;
		gbc_giornoComboBox.gridy = 3;
		infoPartitaPanel.add(giornoComboBox, gbc_giornoComboBox);
		
		giocatoreUnoPanel = new JPanel();
		giocatoreUnoPanel.setBorder(new TitledBorder(null, "Giocatore 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_giocatoreUnoPanel = new GridBagConstraints();
		gbc_giocatoreUnoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_giocatoreUnoPanel.fill = GridBagConstraints.BOTH;
		gbc_giocatoreUnoPanel.gridx = 0;
		gbc_giocatoreUnoPanel.gridy = 1;
		contentPanel.add(giocatoreUnoPanel, gbc_giocatoreUnoPanel);
		GridBagLayout gbl_giocatoreUnoPanel = new GridBagLayout();
		gbl_giocatoreUnoPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_giocatoreUnoPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_giocatoreUnoPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_giocatoreUnoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		giocatoreUnoPanel.setLayout(gbl_giocatoreUnoPanel);
		
		nicknameUnoLabel = new JLabel("Nickname:");
		GridBagConstraints gbc_nicknameUnoLabel = new GridBagConstraints();
		gbc_nicknameUnoLabel.anchor = GridBagConstraints.EAST;
		gbc_nicknameUnoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameUnoLabel.gridx = 0;
		gbc_nicknameUnoLabel.gridy = 0;
		giocatoreUnoPanel.add(nicknameUnoLabel, gbc_nicknameUnoLabel);
		
		nicknameUnoTextField = new JTextField();
		nicknameUnoTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if((e.getKeyChar() < 'a' || e.getKeyChar() > 'z') && (e.getKeyChar() < '0' || e.getKeyChar() > '9') && e.getKeyCode() != e.VK_BACK_SPACE)
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire caratteri speciali");
					nicknameUnoTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_nicknameUnoTextField = new GridBagConstraints();
		gbc_nicknameUnoTextField.anchor = GridBagConstraints.WEST;
		gbc_nicknameUnoTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameUnoTextField.gridx = 1;
		gbc_nicknameUnoTextField.gridy = 0;
		giocatoreUnoPanel.add(nicknameUnoTextField, gbc_nicknameUnoTextField);
		nicknameUnoTextField.setColumns(10);
		
		coloreUnoLabel = new JLabel("Colore:");
		GridBagConstraints gbc_coloreUnoLabel = new GridBagConstraints();
		gbc_coloreUnoLabel.anchor = GridBagConstraints.EAST;
		gbc_coloreUnoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_coloreUnoLabel.gridx = 0;
		gbc_coloreUnoLabel.gridy = 1;
		giocatoreUnoPanel.add(coloreUnoLabel, gbc_coloreUnoLabel);
		
		coloreUnoComboBox = new JComboBox(colori);
		GridBagConstraints gbc_coloreUnoComboBox = new GridBagConstraints();
		gbc_coloreUnoComboBox.anchor = GridBagConstraints.WEST;
		gbc_coloreUnoComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_coloreUnoComboBox.gridx = 1;
		gbc_coloreUnoComboBox.gridy = 1;
		giocatoreUnoPanel.add(coloreUnoComboBox, gbc_coloreUnoComboBox);
		
		etaUnoLabel = new JLabel("Eta':");
		GridBagConstraints gbc_etaUnoLabel = new GridBagConstraints();
		gbc_etaUnoLabel.anchor = GridBagConstraints.EAST;
		gbc_etaUnoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_etaUnoLabel.gridx = 0;
		gbc_etaUnoLabel.gridy = 2;
		giocatoreUnoPanel.add(etaUnoLabel, gbc_etaUnoLabel);
		
		etaUnoTextField = new JTextField();
		etaUnoTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire dei caratteri");
					etaUnoTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_etaUnoTextField = new GridBagConstraints();
		gbc_etaUnoTextField.insets = new Insets(0, 0, 5, 5);
		gbc_etaUnoTextField.anchor = GridBagConstraints.WEST;
		gbc_etaUnoTextField.gridx = 1;
		gbc_etaUnoTextField.gridy = 2;
		giocatoreUnoPanel.add(etaUnoTextField, gbc_etaUnoTextField);
		etaUnoTextField.setColumns(10);
		
		idGiocatoreUnoLabel = new JLabel("Id:");
		GridBagConstraints gbc_idGiocatoreUnoLabel = new GridBagConstraints();
		gbc_idGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
		gbc_idGiocatoreUnoLabel.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreUnoLabel.gridx = 0;
		gbc_idGiocatoreUnoLabel.gridy = 3;
		giocatoreUnoPanel.add(idGiocatoreUnoLabel, gbc_idGiocatoreUnoLabel);
		
		nuovoGiocatoreUnoCheckBox = new JCheckBox("Nuovo Giocatore");
		nuovoGiocatoreUnoCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(nuovoGiocatoreUnoCheckBox.isSelected())
				{
					idGiocatoreUnoComboBox.setEnabled(false);
					etaUnoTextField.setEnabled(true);
				}
				else
				{
					if(idGiocatoreUnoComboBox.getItemCount() == 0)
					{
						JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Non esistono giocatori nel database");
						nuovoGiocatoreUnoCheckBox.setSelected(true);
					}
					else
					{
						idGiocatoreUnoComboBox.setEnabled(true);
						etaUnoTextField.setEnabled(false);	
					}
				}
			}
		});
		
		idGiocatoreUnoComboBox = new JComboBox(giocatori);
		idGiocatoreUnoComboBox.setEnabled(false);
		GridBagConstraints gbc_idGiocatoreUnoComboBox = new GridBagConstraints();
		gbc_idGiocatoreUnoComboBox.anchor = GridBagConstraints.WEST;
		gbc_idGiocatoreUnoComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreUnoComboBox.gridx = 1;
		gbc_idGiocatoreUnoComboBox.gridy = 3;
		giocatoreUnoPanel.add(idGiocatoreUnoComboBox, gbc_idGiocatoreUnoComboBox);
		nuovoGiocatoreUnoCheckBox.setSelected(true);
		GridBagConstraints gbc_nuovoGiocatoreUnoCheckBox = new GridBagConstraints();
		gbc_nuovoGiocatoreUnoCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_nuovoGiocatoreUnoCheckBox.gridx = 2;
		gbc_nuovoGiocatoreUnoCheckBox.gridy = 3;
		giocatoreUnoPanel.add(nuovoGiocatoreUnoCheckBox, gbc_nuovoGiocatoreUnoCheckBox);
		
		giocatoreDuePanel = new JPanel();
		giocatoreDuePanel.setBorder(new TitledBorder(null, "Giocatore 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_giocatoreDuePanel = new GridBagConstraints();
		gbc_giocatoreDuePanel.insets = new Insets(0, 0, 5, 0);
		gbc_giocatoreDuePanel.fill = GridBagConstraints.BOTH;
		gbc_giocatoreDuePanel.gridx = 0;
		gbc_giocatoreDuePanel.gridy = 2;
		contentPanel.add(giocatoreDuePanel, gbc_giocatoreDuePanel);
		GridBagLayout gbl_giocatoreDuePanel = new GridBagLayout();
		gbl_giocatoreDuePanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_giocatoreDuePanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_giocatoreDuePanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_giocatoreDuePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		giocatoreDuePanel.setLayout(gbl_giocatoreDuePanel);
		
		nicknameDueLabel = new JLabel("Nickname:");
		GridBagConstraints gbc_nicknameDueLabel = new GridBagConstraints();
		gbc_nicknameDueLabel.anchor = GridBagConstraints.EAST;
		gbc_nicknameDueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameDueLabel.gridx = 0;
		gbc_nicknameDueLabel.gridy = 0;
		giocatoreDuePanel.add(nicknameDueLabel, gbc_nicknameDueLabel);
		
		nicknameDueTextField = new JTextField();
		nicknameDueTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if((e.getKeyChar() < 'a' || e.getKeyChar() > 'z') && (e.getKeyChar() < '0' || e.getKeyChar() > '9') && e.getKeyCode() != e.VK_BACK_SPACE)
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire caratteri speciali");
					nicknameDueTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_nicknameDueTextField = new GridBagConstraints();
		gbc_nicknameDueTextField.anchor = GridBagConstraints.WEST;
		gbc_nicknameDueTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameDueTextField.gridx = 1;
		gbc_nicknameDueTextField.gridy = 0;
		giocatoreDuePanel.add(nicknameDueTextField, gbc_nicknameDueTextField);
		nicknameDueTextField.setColumns(10);
		
		coloreDueLabel = new JLabel("Colore:");
		GridBagConstraints gbc_coloreDueLabel = new GridBagConstraints();
		gbc_coloreDueLabel.anchor = GridBagConstraints.EAST;
		gbc_coloreDueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_coloreDueLabel.gridx = 0;
		gbc_coloreDueLabel.gridy = 1;
		giocatoreDuePanel.add(coloreDueLabel, gbc_coloreDueLabel);
		
		coloreDueComboBox = new JComboBox(colori);
		GridBagConstraints gbc_coloreDueComboBox = new GridBagConstraints();
		gbc_coloreDueComboBox.anchor = GridBagConstraints.WEST;
		gbc_coloreDueComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_coloreDueComboBox.gridx = 1;
		gbc_coloreDueComboBox.gridy = 1;
		giocatoreDuePanel.add(coloreDueComboBox, gbc_coloreDueComboBox);
		
		etaDueLabel = new JLabel("Eta':");
		GridBagConstraints gbc_etaDueLabel = new GridBagConstraints();
		gbc_etaDueLabel.anchor = GridBagConstraints.EAST;
		gbc_etaDueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_etaDueLabel.gridx = 0;
		gbc_etaDueLabel.gridy = 2;
		giocatoreDuePanel.add(etaDueLabel, gbc_etaDueLabel);
		
		etaDueTextField = new JTextField();
		etaDueTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire dei caratteri");
					etaDueTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_etaDueTextField = new GridBagConstraints();
		gbc_etaDueTextField.insets = new Insets(0, 0, 5, 5);
		gbc_etaDueTextField.anchor = GridBagConstraints.WEST;
		gbc_etaDueTextField.gridx = 1;
		gbc_etaDueTextField.gridy = 2;
		giocatoreDuePanel.add(etaDueTextField, gbc_etaDueTextField);
		etaDueTextField.setColumns(10);
		
		idGiocatoreDueLabel = new JLabel("Id:");
		GridBagConstraints gbc_idGiocatoreDueLabel = new GridBagConstraints();
		gbc_idGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
		gbc_idGiocatoreDueLabel.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreDueLabel.gridx = 0;
		gbc_idGiocatoreDueLabel.gridy = 3;
		giocatoreDuePanel.add(idGiocatoreDueLabel, gbc_idGiocatoreDueLabel);
		
		nuovoGiocatoreDueCheckBox = new JCheckBox("Nuovo Giocatore");
		nuovoGiocatoreDueCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(nuovoGiocatoreDueCheckBox.isSelected())
				{
					idGiocatoreDueComboBox.setEnabled(false);
					etaDueTextField.setEnabled(true);
				}
				else
				{
					if(idGiocatoreDueComboBox.getItemCount() == 0)
					{
						JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Non esistono giocatori nel database");
						nuovoGiocatoreDueCheckBox.setSelected(true);
					}
					else
					{
						idGiocatoreDueComboBox.setEnabled(true);
						etaDueTextField.setEnabled(false);
					}
				}
			}
		});
		
		idGiocatoreDueComboBox = new JComboBox(giocatori);
		idGiocatoreDueComboBox.setEnabled(false);
		GridBagConstraints gbc_idGiocatoreDueComboBox = new GridBagConstraints();
		gbc_idGiocatoreDueComboBox.anchor = GridBagConstraints.WEST;
		gbc_idGiocatoreDueComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreDueComboBox.gridx = 1;
		gbc_idGiocatoreDueComboBox.gridy = 3;
		giocatoreDuePanel.add(idGiocatoreDueComboBox, gbc_idGiocatoreDueComboBox);
		nuovoGiocatoreDueCheckBox.setSelected(true);
		GridBagConstraints gbc_nuovoGiocatoreDueCheckBox = new GridBagConstraints();
		gbc_nuovoGiocatoreDueCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_nuovoGiocatoreDueCheckBox.gridx = 2;
		gbc_nuovoGiocatoreDueCheckBox.gridy = 3;
		giocatoreDuePanel.add(nuovoGiocatoreDueCheckBox, gbc_nuovoGiocatoreDueCheckBox);
		
		giocatoreTrePanel = new JPanel();
		giocatoreTrePanel.setBorder(new TitledBorder(null, "Giocatore 3", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_giocatoreTrePanel = new GridBagConstraints();
		gbc_giocatoreTrePanel.insets = new Insets(0, 0, 5, 0);
		gbc_giocatoreTrePanel.fill = GridBagConstraints.BOTH;
		gbc_giocatoreTrePanel.gridx = 0;
		gbc_giocatoreTrePanel.gridy = 3;
		contentPanel.add(giocatoreTrePanel, gbc_giocatoreTrePanel);
		GridBagLayout gbl_giocatoreTrePanel = new GridBagLayout();
		gbl_giocatoreTrePanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_giocatoreTrePanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_giocatoreTrePanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_giocatoreTrePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		giocatoreTrePanel.setLayout(gbl_giocatoreTrePanel);
		
		nicknameTreLabel = new JLabel("Nickname:");
		GridBagConstraints gbc_nicknameTreLabel = new GridBagConstraints();
		gbc_nicknameTreLabel.anchor = GridBagConstraints.EAST;
		gbc_nicknameTreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameTreLabel.gridx = 0;
		gbc_nicknameTreLabel.gridy = 0;
		giocatoreTrePanel.add(nicknameTreLabel, gbc_nicknameTreLabel);
		
		nicknameTreTextField = new JTextField();
		nicknameTreTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if((e.getKeyChar() < 'a' || e.getKeyChar() > 'z') && (e.getKeyChar() < '0' || e.getKeyChar() > '9') && e.getKeyCode() != e.VK_BACK_SPACE)
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire caratteri speciali");
					nicknameTreTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_nicknameTreTextField = new GridBagConstraints();
		gbc_nicknameTreTextField.anchor = GridBagConstraints.WEST;
		gbc_nicknameTreTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameTreTextField.gridx = 1;
		gbc_nicknameTreTextField.gridy = 0;
		giocatoreTrePanel.add(nicknameTreTextField, gbc_nicknameTreTextField);
		nicknameTreTextField.setColumns(10);
		
		coloreTreLabel = new JLabel("Colore:");
		GridBagConstraints gbc_coloreTreLabel = new GridBagConstraints();
		gbc_coloreTreLabel.anchor = GridBagConstraints.EAST;
		gbc_coloreTreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_coloreTreLabel.gridx = 0;
		gbc_coloreTreLabel.gridy = 1;
		giocatoreTrePanel.add(coloreTreLabel, gbc_coloreTreLabel);
		
		coloreTreComboBox = new JComboBox(colori);
		GridBagConstraints gbc_coloreTreComboBox = new GridBagConstraints();
		gbc_coloreTreComboBox.anchor = GridBagConstraints.WEST;
		gbc_coloreTreComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_coloreTreComboBox.gridx = 1;
		gbc_coloreTreComboBox.gridy = 1;
		giocatoreTrePanel.add(coloreTreComboBox, gbc_coloreTreComboBox);
		
		etaTreLabel = new JLabel("Eta':");
		GridBagConstraints gbc_etaTreLabel = new GridBagConstraints();
		gbc_etaTreLabel.anchor = GridBagConstraints.EAST;
		gbc_etaTreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_etaTreLabel.gridx = 0;
		gbc_etaTreLabel.gridy = 2;
		giocatoreTrePanel.add(etaTreLabel, gbc_etaTreLabel);
		
		etaTreTextField = new JTextField();
		etaTreTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire dei caratteri");
					etaTreTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_etaTreTextField = new GridBagConstraints();
		gbc_etaTreTextField.insets = new Insets(0, 0, 5, 5);
		gbc_etaTreTextField.anchor = GridBagConstraints.WEST;
		gbc_etaTreTextField.gridx = 1;
		gbc_etaTreTextField.gridy = 2;
		giocatoreTrePanel.add(etaTreTextField, gbc_etaTreTextField);
		etaTreTextField.setColumns(10);
		
		idGiocatoreTreLabel = new JLabel("Id:");
		GridBagConstraints gbc_idGiocatoreTreLabel = new GridBagConstraints();
		gbc_idGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
		gbc_idGiocatoreTreLabel.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreTreLabel.gridx = 0;
		gbc_idGiocatoreTreLabel.gridy = 3;
		giocatoreTrePanel.add(idGiocatoreTreLabel, gbc_idGiocatoreTreLabel);
		
		nuovoGiocatoreTreCheckBox = new JCheckBox("Nuovo Giocatore");
		nuovoGiocatoreTreCheckBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(nuovoGiocatoreTreCheckBox.isSelected())
				{
					idGiocatoreTreComboBox.setEnabled(false);
					etaTreTextField.setEnabled(true);
				}
				else
				{
					if(idGiocatoreTreComboBox.getItemCount() == 0)
					{
						JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Non esistono giocatori nel database");
						nuovoGiocatoreTreCheckBox.setSelected(true);
					}
					else
					{
						idGiocatoreTreComboBox.setEnabled(true);
						etaTreTextField.setEnabled(false);
					}
				}
			}
		});
		
		idGiocatoreTreComboBox = new JComboBox(giocatori);
		idGiocatoreTreComboBox.setEnabled(false);
		GridBagConstraints gbc_idGiocatoreTreComboBox = new GridBagConstraints();
		gbc_idGiocatoreTreComboBox.anchor = GridBagConstraints.WEST;
		gbc_idGiocatoreTreComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreTreComboBox.gridx = 1;
		gbc_idGiocatoreTreComboBox.gridy = 3;
		giocatoreTrePanel.add(idGiocatoreTreComboBox, gbc_idGiocatoreTreComboBox);
		nuovoGiocatoreTreCheckBox.setSelected(true);
		GridBagConstraints gbc_nuovoGiocatoreTreCheckBox = new GridBagConstraints();
		gbc_nuovoGiocatoreTreCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_nuovoGiocatoreTreCheckBox.gridx = 2;
		gbc_nuovoGiocatoreTreCheckBox.gridy = 3;
		giocatoreTrePanel.add(nuovoGiocatoreTreCheckBox, gbc_nuovoGiocatoreTreCheckBox);
		
		giocatoreQuattroPanel = new JPanel();
		giocatoreQuattroPanel.setBorder(new TitledBorder(null, "Giocatore 4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_giocatoreQuattroPanel = new GridBagConstraints();
		gbc_giocatoreQuattroPanel.insets = new Insets(0, 0, 5, 0);
		gbc_giocatoreQuattroPanel.fill = GridBagConstraints.BOTH;
		gbc_giocatoreQuattroPanel.gridx = 0;
		gbc_giocatoreQuattroPanel.gridy = 4;
		contentPanel.add(giocatoreQuattroPanel, gbc_giocatoreQuattroPanel);
		GridBagLayout gbl_giocatoreQuattroPanel = new GridBagLayout();
		gbl_giocatoreQuattroPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_giocatoreQuattroPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_giocatoreQuattroPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_giocatoreQuattroPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		giocatoreQuattroPanel.setLayout(gbl_giocatoreQuattroPanel);
		
		nicknameQuattroLabel = new JLabel("Nickname:");
		GridBagConstraints gbc_nicknameQuattroLabel = new GridBagConstraints();
		gbc_nicknameQuattroLabel.anchor = GridBagConstraints.EAST;
		gbc_nicknameQuattroLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameQuattroLabel.gridx = 0;
		gbc_nicknameQuattroLabel.gridy = 0;
		giocatoreQuattroPanel.add(nicknameQuattroLabel, gbc_nicknameQuattroLabel);
		
		nicknameQuattroTextField = new JTextField();
		nicknameQuattroTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if((e.getKeyChar() < 'a' || e.getKeyChar() > 'z') && (e.getKeyChar() < '0' || e.getKeyChar() > '9') && e.getKeyCode() != e.VK_BACK_SPACE)
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire caratteri speciali");
					nicknameQuattroTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_nicknameQuattroTextField = new GridBagConstraints();
		gbc_nicknameQuattroTextField.anchor = GridBagConstraints.WEST;
		gbc_nicknameQuattroTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameQuattroTextField.gridx = 1;
		gbc_nicknameQuattroTextField.gridy = 0;
		giocatoreQuattroPanel.add(nicknameQuattroTextField, gbc_nicknameQuattroTextField);
		nicknameQuattroTextField.setColumns(10);
		
		coloreQuattroLabel = new JLabel("Colore:");
		GridBagConstraints gbc_coloreQuattroLabel = new GridBagConstraints();
		gbc_coloreQuattroLabel.anchor = GridBagConstraints.EAST;
		gbc_coloreQuattroLabel.insets = new Insets(0, 0, 5, 5);
		gbc_coloreQuattroLabel.gridx = 0;
		gbc_coloreQuattroLabel.gridy = 1;
		giocatoreQuattroPanel.add(coloreQuattroLabel, gbc_coloreQuattroLabel);
		
		coloreQuattroComboBox = new JComboBox(colori);
		GridBagConstraints gbc_coloreQuattroComboBox = new GridBagConstraints();
		gbc_coloreQuattroComboBox.anchor = GridBagConstraints.WEST;
		gbc_coloreQuattroComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_coloreQuattroComboBox.gridx = 1;
		gbc_coloreQuattroComboBox.gridy = 1;
		giocatoreQuattroPanel.add(coloreQuattroComboBox, gbc_coloreQuattroComboBox);
		
		etaQuattroLabel = new JLabel("Eta':");
		GridBagConstraints gbc_etaQuattroLabel = new GridBagConstraints();
		gbc_etaQuattroLabel.anchor = GridBagConstraints.EAST;
		gbc_etaQuattroLabel.insets = new Insets(0, 0, 5, 5);
		gbc_etaQuattroLabel.gridx = 0;
		gbc_etaQuattroLabel.gridy = 2;
		giocatoreQuattroPanel.add(etaQuattroLabel, gbc_etaQuattroLabel);
		
		etaQuattroTextField = new JTextField();
		etaQuattroTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
				{
					JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Errore! Non e' possibile inserire dei caratteri");
					etaQuattroTextField.setText("");
				}
			}
		});
		GridBagConstraints gbc_etaQuattroTextField = new GridBagConstraints();
		gbc_etaQuattroTextField.insets = new Insets(0, 0, 5, 5);
		gbc_etaQuattroTextField.anchor = GridBagConstraints.WEST;
		gbc_etaQuattroTextField.gridx = 1;
		gbc_etaQuattroTextField.gridy = 2;
		giocatoreQuattroPanel.add(etaQuattroTextField, gbc_etaQuattroTextField);
		etaQuattroTextField.setColumns(10);
		
		idGiocatoreQuattroLabel = new JLabel("Id:");
		GridBagConstraints gbc_idGiocatoreQuattroLabel = new GridBagConstraints();
		gbc_idGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
		gbc_idGiocatoreQuattroLabel.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreQuattroLabel.gridx = 0;
		gbc_idGiocatoreQuattroLabel.gridy = 3;
		giocatoreQuattroPanel.add(idGiocatoreQuattroLabel, gbc_idGiocatoreQuattroLabel);
		
		nuovoGiocatoreQuattroCheckBox = new JCheckBox("Nuovo Giocatore");
		nuovoGiocatoreQuattroCheckBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(nuovoGiocatoreQuattroCheckBox.isSelected())
				{
					idGiocatoreQuattroComboBox.setEnabled(false);
					etaQuattroTextField.setEnabled(true);
				}
				else
				{
					if(idGiocatoreQuattroComboBox.getItemCount() == 0)
					{
						JOptionPane.showMessageDialog(CreaPartitaDialog.this, "Non esistono giocatori nel database");
						nuovoGiocatoreQuattroCheckBox.setSelected(true);
					}
					else
					{
						idGiocatoreQuattroComboBox.setEnabled(true);
						etaQuattroTextField.setEnabled(false);
					}
				}
			}
		});
		
		idGiocatoreQuattroComboBox = new JComboBox(giocatori);
		idGiocatoreQuattroComboBox.setEnabled(false);
		GridBagConstraints gbc_idGiocatoreQuattroComboBox = new GridBagConstraints();
		gbc_idGiocatoreQuattroComboBox.anchor = GridBagConstraints.WEST;
		gbc_idGiocatoreQuattroComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_idGiocatoreQuattroComboBox.gridx = 1;
		gbc_idGiocatoreQuattroComboBox.gridy = 3;
		giocatoreQuattroPanel.add(idGiocatoreQuattroComboBox, gbc_idGiocatoreQuattroComboBox);
		nuovoGiocatoreQuattroCheckBox.setSelected(true);
		GridBagConstraints gbc_nuovoGiocatoreQuattroCheckBox = new GridBagConstraints();
		gbc_nuovoGiocatoreQuattroCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_nuovoGiocatoreQuattroCheckBox.gridx = 2;
		gbc_nuovoGiocatoreQuattroCheckBox.gridy = 3;
		giocatoreQuattroPanel.add(nuovoGiocatoreQuattroCheckBox, gbc_nuovoGiocatoreQuattroCheckBox);
		
		pulsantiPanel = new JPanel();
		GridBagConstraints gbc_pulsantiPanel = new GridBagConstraints();
		gbc_pulsantiPanel.fill = GridBagConstraints.BOTH;
		gbc_pulsantiPanel.gridx = 0;
		gbc_pulsantiPanel.gridy = 5;
		contentPanel.add(pulsantiPanel, gbc_pulsantiPanel);
		pulsantiPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				aggiungiPartita();
			}
		});
		pulsantiPanel.add(okButton);
		
		annullaButton = new JButton("Annulla");
		annullaButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		pulsantiPanel.add(annullaButton);
		setComboBoxGiorni();
	}

}
