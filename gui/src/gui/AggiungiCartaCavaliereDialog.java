package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Dialog.ModalityType;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiCartaCavaliereDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ArrayList<Integer> posizioni;
	private Controller controller;
	private int idGiocatore;
	private int idPartita;
	private int idTurno;
	private JComboBox posizioneComboBox;
	private JComboBox idGiocatoreComboBox;
	private JComboBox cartaComboBox;
	
	private ArrayList<Integer> getPosizioniBrigante() throws SQLException
	{
		ArrayList<Integer> posizioni = new ArrayList<Integer>();
		int posizioneAttuale = InterazioneDatabase.getPosizioneBrigante(controller, idPartita);
		for(int i = 0; i <= 18; ++i)
			if(i != posizioneAttuale)
				posizioni.add(i);
		return posizioni;
	}
	
	private void aggiornaIds() throws SQLException
	{
		int posizione = Integer.parseInt(posizioneComboBox.getSelectedItem().toString());
		int idEsagono = InterazioneDatabase.getIdEsagono(controller, idPartita, posizione);
		ArrayList<Integer> cittaColonie = InterazioneDatabase.getIdsCittaColonieSuEsagono(controller, idEsagono);
		ArrayList<Integer> giocatori = new ArrayList<Integer>();
		for(int i = 0; i != cittaColonie.size(); ++i)
		{
			int giocatore = InterazioneDatabase.getIdGiocatore(controller, cittaColonie.get(i));
			if(giocatore != idGiocatore)
				giocatori.add(giocatore);
			
		}
		Set<Integer> s = new HashSet<Integer>(giocatori);
		giocatori.clear();
		giocatori.addAll(s);
		if(giocatori.size() != 0)
		{
			idGiocatoreComboBox.setEnabled(true);
			cartaComboBox.setEnabled(true);
			DefaultComboBoxModel model = new DefaultComboBoxModel(giocatori.toArray());
			idGiocatoreComboBox.setModel(model);
		}
		else
		{
			idGiocatoreComboBox.setEnabled(false);
			cartaComboBox.setEnabled(false);
		}
	}
	
	private boolean checkRisorse() throws SQLException
	{
		String tipo = cartaComboBox.getSelectedItem().toString();
		if(tipo.equals("Nessuna"))
			return true;
		int giocatore = Integer.parseInt(idGiocatoreComboBox.getSelectedItem().toString());
		int count = InterazioneDatabase.countCartaRisorsa(controller, giocatore, idTurno, tipo);
		if(count != 0)
			return true;
		return false;
	}
	
	private void rimuoviRisorsa() throws SQLException
	{
		String tipo = cartaComboBox.getSelectedItem().toString();
		if(!tipo.equals("Nessuna"))
		{
			int giocatoreVecchio = Integer.parseInt(idGiocatoreComboBox.getSelectedItem().toString());
			ArrayList<Integer> id = InterazioneDatabase.getIdsCarteRisorsa(controller, giocatoreVecchio, idTurno, tipo);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, id.get(0), idGiocatore, idTurno);
		}
	}
	
	private void aggiungiCarta() throws SQLException
	{
		if(idGiocatoreComboBox.isEnabled() && checkRisorse())
		{
			rimuoviRisorsa();
			int posizione = Integer.parseInt(posizioneComboBox.getSelectedItem().toString());
			int idEsagono = InterazioneDatabase.getIdEsagono(controller, idPartita, posizione);
			int idBrigante = InterazioneDatabase.getIdBrigante(controller, idPartita);
			InterazioneDatabase.modificaPosizioneBrigante(controller, idBrigante, idEsagono);
			dispose();
		}
		else if(idGiocatoreComboBox.isEnabled() && !checkRisorse())
			JOptionPane.showMessageDialog(this, "Errore il giocatore non possiede la carta indicata");
		else
		{
			int posizione = Integer.parseInt(posizioneComboBox.getSelectedItem().toString());
			int idEsagono = InterazioneDatabase.getIdEsagono(controller, posizione, posizione);
			int idBrigante = InterazioneDatabase.getIdBrigante(controller, idPartita);
			InterazioneDatabase.modificaPosizioneBrigante(controller, idBrigante, idEsagono);
			dispose();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AggiungiCartaCavaliereDialog(Controller controller, int idPartita, int idGiocatore, int idTurno) {
		this.controller = controller;
		this.idPartita = idPartita;
		this.idGiocatore = idGiocatore;
		this.idTurno = idTurno;
		setResizable(false);
		setTitle("Aggiungi Carta Cavaliere");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 337, 227);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel posizioneBriganteLabel = new JLabel("Posizione Brigante:");
			GridBagConstraints gbc_posizioneBriganteLabel = new GridBagConstraints();
			gbc_posizioneBriganteLabel.insets = new Insets(0, 0, 5, 5);
			gbc_posizioneBriganteLabel.anchor = GridBagConstraints.EAST;
			gbc_posizioneBriganteLabel.gridx = 0;
			gbc_posizioneBriganteLabel.gridy = 1;
			contentPanel.add(posizioneBriganteLabel, gbc_posizioneBriganteLabel);
		}
		{
			try 
			{
				posizioneComboBox = new JComboBox(getPosizioniBrigante().toArray());
				posizioneComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						try 
						{
							aggiornaIds();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiCartaCavaliereDialog.this, "Errore nel caricamento dei giocatori");
							dispose();
						}
					}
				});
			} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(this, "Errore nel caricamento delle posizioni");
				dispose();
			}
			GridBagConstraints gbc_posizioneComboBox = new GridBagConstraints();
			gbc_posizioneComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_posizioneComboBox.anchor = GridBagConstraints.WEST;
			gbc_posizioneComboBox.gridx = 1;
			gbc_posizioneComboBox.gridy = 1;
			contentPanel.add(posizioneComboBox, gbc_posizioneComboBox);
		}
		{
			JLabel idGiocatoreLabel = new JLabel("Id Giocatore da derubare:");
			GridBagConstraints gbc_idGiocatoreLabel = new GridBagConstraints();
			gbc_idGiocatoreLabel.anchor = GridBagConstraints.EAST;
			gbc_idGiocatoreLabel.insets = new Insets(0, 0, 5, 5);
			gbc_idGiocatoreLabel.gridx = 0;
			gbc_idGiocatoreLabel.gridy = 2;
			contentPanel.add(idGiocatoreLabel, gbc_idGiocatoreLabel);
		}
		{
			idGiocatoreComboBox = new JComboBox();
			GridBagConstraints gbc_idGiocatoreComboBox = new GridBagConstraints();
			gbc_idGiocatoreComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_idGiocatoreComboBox.anchor = GridBagConstraints.WEST;
			gbc_idGiocatoreComboBox.gridx = 1;
			gbc_idGiocatoreComboBox.gridy = 2;
			contentPanel.add(idGiocatoreComboBox, gbc_idGiocatoreComboBox);
		}
		{
			JLabel cartaLabel = new JLabel("Carta Da Rubare:");
			GridBagConstraints gbc_cartaLabel = new GridBagConstraints();
			gbc_cartaLabel.anchor = GridBagConstraints.EAST;
			gbc_cartaLabel.insets = new Insets(0, 0, 0, 5);
			gbc_cartaLabel.gridx = 0;
			gbc_cartaLabel.gridy = 3;
			contentPanel.add(cartaLabel, gbc_cartaLabel);
		}
		{
			cartaComboBox = new JComboBox();
			cartaComboBox.setModel(new DefaultComboBoxModel(new String[] {"Grano", "Lana", "Legno", "Minerale", "Argilla", "Nessuna"}));
			GridBagConstraints gbc_cartaComboBox = new GridBagConstraints();
			gbc_cartaComboBox.anchor = GridBagConstraints.WEST;
			gbc_cartaComboBox.gridx = 1;
			gbc_cartaComboBox.gridy = 3;
			contentPanel.add(cartaComboBox, gbc_cartaComboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						try 
						{
							aggiungiCarta();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiCartaCavaliereDialog.this, "Errore nell'aggiunta della carta");
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
		try 
		{
			aggiornaIds();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei giocatori");
			dispose();
		}
	}

}
