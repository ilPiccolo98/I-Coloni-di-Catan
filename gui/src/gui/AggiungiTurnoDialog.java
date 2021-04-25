package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InterazioneDatabase;

import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AggiungiTurnoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField giroTextField;
	private Controller controller;
	private int idPartita;
	private JComboBox idGiocatoreComboBox;
	private JComboBox dadoUnoComboBox;
	private JComboBox dadoDueComboBox;
	private JLabel posizioneBriganteLabel;
	private JComboBox posizioneBriganteComboBox;
	private boolean prosegui = false;
	private JTextField carteDaScartareUno;
	private JTextField legnoUno;
	private JTextField argillaUno;
	private JTextField mineraleUno;
	private JTextField granoUno;
	private JTextField lanaUno;
	private JTextField carteDaScartareDue;
	private JTextField legnoDue;
	private JTextField argillaDue;
	private JTextField mineraleDue;
	private JTextField granoDue;
	private JTextField lanaDue;
	private JTextField carteDaScartareTre;
	private JTextField legnoTre;
	private JTextField argillaTre;
	private JTextField mineraleTre;
	private JTextField granoTre;
	private JTextField lanaTre;
	private JTextField carteDaScartareQuattro;
	private JTextField legnoQuattro;
	private JTextField argillaQuattro;
	private JTextField mineraleQuattro;
	private JTextField granoQuattro;
	private JTextField lanaQuattro;
	private JPanel pannelloCarte;
	private JPanel giocatoreUnoPanel;
	private JPanel giocatoreDuePanel;
	private JPanel giocatoreTrePanel;
	private JPanel giocatoreQuattroPanel;
	private JLabel lblId;
	private JTextField idGiocatoreUnoTextBox;
	private JLabel lblNewLabel_19;
	private JTextField idGiocatoreDueTextBox;
	private JLabel lblNewLabel_20;
	private JTextField idGiocatoreTreTextBox;
	private JLabel lblNewLabel_21;
	private JTextField idGiocatoreQuattroTextBox;
	private ArrayList<Integer> idsGiocatori;
	private JPanel pannelloSaccheggio;
	private JLabel giocatoreDaDerubareLabel;
	private JLabel cartaDaDerubareLabel;
	private JComboBox cartaDaDerubareComboBox;
	private JComboBox giocatoreDaDerubareComboBox;

	private ArrayList<Integer> generaIdGiocatori() throws SQLException
	{
		return InterazioneDatabase.getIdGiocatori(controller, idPartita);
	}
	
	private void setGiroTextField() throws SQLException
	{
		int idGiocatore = Integer.parseInt(idGiocatoreComboBox.getSelectedItem().toString());
		int giro = InterazioneDatabase.getMaxGiro(controller, idPartita, idGiocatore);
		giroTextField.setText(Integer.toString(giro + 1));
	}
	
	private boolean checkGiro() throws SQLException
	{
		int nuovoGiro = Integer.parseInt(giroTextField.getText());
		for(int i = 0; i != idGiocatoreComboBox.getItemCount(); ++i)
		{
			int idGiocatore = Integer.parseInt(idGiocatoreComboBox.getItemAt(i).toString());
			int giro = InterazioneDatabase.getMaxGiro(controller, idPartita, idGiocatore);
			if(giro == (nuovoGiro - 2))
				return false;
		}
		return true;
	}
	
	private Object[] generaNumeriDadi()
	{
		ArrayList<Integer> numeri = new ArrayList<Integer>();
		for(int i = 1; i <= 6; ++i)
			numeri.add(i);
		return numeri.toArray();
	}
	
	private int sommaDadi()
	{
		return Integer.parseInt(dadoUnoComboBox.getSelectedItem().toString()) + Integer.parseInt(dadoDueComboBox.getSelectedItem().toString());
	}
	
	private Object[] generaNumeriPosizioneBrigante() throws SQLException
	{
		int posizioneAttuale = InterazioneDatabase.getPosizioneBrigante(controller, idPartita);
		ArrayList<Integer> posizioni = new ArrayList<Integer>();
		for(int i = 0; i <= 38; ++i)
			if(i != posizioneAttuale)
				posizioni.add(i);
		return posizioni.toArray();
	}
	
	private void setPosizioneBriganteComboBox()
	{
		if(sommaDadi() == 7)
		{
			posizioneBriganteLabel.setEnabled(true);
			posizioneBriganteComboBox.setEnabled(true);
			try 
			{
				setPanels();
			} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(this, "Errore nel caricamento delle carte da scartare");
				dispose();
			}
			try 
			{
				aggiornaIds();
			} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(this, "Errore nel caricamento dei giocatori da derubare");
				dispose();
			}
		}
		else
		{
			posizioneBriganteLabel.setEnabled(false);
			posizioneBriganteComboBox.setEnabled(false);
			cartaDaDerubareLabel.setEnabled(false);
			giocatoreDaDerubareComboBox.setEnabled(false);
			disablePannels();
		}
	}
	
	private void aggiungiBrigante(int idBrigante, int idTurno) throws SQLException
	{
		if(posizioneBriganteComboBox.isEnabled())
		{
			int posizione = Integer.parseInt(posizioneBriganteComboBox.getSelectedItem().toString());
			int idEsagono = InterazioneDatabase.getIdEsagono(controller, idPartita, posizione);
			InterazioneDatabase.aggiungiBrigante(controller, idBrigante, idEsagono, idTurno);
		}
		else
		{	
			int idEsagono = InterazioneDatabase.getIdEsagonoBrigante(controller, idPartita);
			InterazioneDatabase.aggiungiBrigante(controller, idBrigante, idEsagono, idTurno);
		}
	}
	
	private void aggiungiTurno() throws SQLException
	{
		int idGiocatore = Integer.parseInt(idGiocatoreComboBox.getSelectedItem().toString());
		int giro = Integer.parseInt(giroTextField.getText());
		int idTurno = InterazioneDatabase.nuovoIdTurno(controller);
		InterazioneDatabase.aggiungiTurno(controller, idTurno, giro, idGiocatore, idPartita);
		InterazioneDatabase.copiaRisorse(controller, idGiocatore, idPartita, idTurno, giro);
		int idBrigante = InterazioneDatabase.nuovoIdBrigante(controller);
		aggiungiBrigante(idBrigante, idTurno);
		int idLancioDadi = InterazioneDatabase.nuovoIdLancioDadi(controller);
		int dadoUno = Integer.parseInt(dadoUnoComboBox.getSelectedItem().toString());
		int dadoDue = Integer.parseInt(dadoDueComboBox.getSelectedItem().toString());
		InterazioneDatabase.aggiungiLancioDadi(controller, idLancioDadi, dadoUno, dadoDue, idTurno);
	
	}
	
	public boolean getProsegui()
	{
		return prosegui;
	}
	
	private void enableGiocatoreUnoPanel()
	{
		giocatoreUnoPanel.setEnabled(true);
		int n = giocatoreUnoPanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreUnoPanel.getComponent(i).setEnabled(true);
	}
	
	private void disableGiocatoreUnoPanel()
	{
		giocatoreUnoPanel.setEnabled(false);
		int n = giocatoreUnoPanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreUnoPanel.getComponent(i).setEnabled(false);
	}
	
	private void enableGiocatoreDuePanel()
	{
		giocatoreDuePanel.setEnabled(true);
		int n = giocatoreDuePanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreDuePanel.getComponent(i).setEnabled(true);
	}
	
	private void disableGiocatoreDuePanel()
	{
		giocatoreDuePanel.setEnabled(false);
		int n = giocatoreDuePanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreDuePanel.getComponent(i).setEnabled(false);
	}
	
	private void enableGiocatoreTrePanel()
	{
		giocatoreTrePanel.setEnabled(true);
		int n = giocatoreTrePanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreTrePanel.getComponent(i).setEnabled(true);
	}
	
	private void disableGiocatoreTrePanel()
	{
		giocatoreTrePanel.setEnabled(false);
		int n = giocatoreTrePanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreTrePanel.getComponent(i).setEnabled(false);
	}
	
	private void enableGiocatoreQuattroPanel()
	{
		giocatoreQuattroPanel.setEnabled(true);
		int n = giocatoreQuattroPanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreQuattroPanel.getComponent(i).setEnabled(true);
	}
	
	private void disableGiocatoreQuattroPanel()
	{
		giocatoreQuattroPanel.setEnabled(false);
		int n = giocatoreQuattroPanel.getComponentCount();
		for(int i = 0; i != n; ++i)
			giocatoreQuattroPanel.getComponent(i).setEnabled(false);
	}
	
	private void disablePannels()
	{
		disableGiocatoreUnoPanel();
		disableGiocatoreDuePanel();
		disableGiocatoreTrePanel();
		disableGiocatoreQuattroPanel();
	}
	
	private void setGiocatoreUnoPanel() throws SQLException
	{
		int id = idsGiocatori.get(0);
		idGiocatoreUnoTextBox.setText(Integer.toString(id));
		int maxTurno = InterazioneDatabase.getMaxTurno(controller, idPartita);
		int carte = InterazioneDatabase.countCarteRisorsa(controller, maxTurno, id);
		if(carte > 7)
		{
			enableGiocatoreUnoPanel();
			carteDaScartareUno.setText(Integer.toString(carte / 2));
		}
	}
	
	private void setGiocatoreDuePanel() throws SQLException
	{
		int id = idsGiocatori.get(1);
		idGiocatoreDueTextBox.setText(Integer.toString(id));
		int maxTurno = InterazioneDatabase.getMaxTurno(controller, idPartita);
		int carte = InterazioneDatabase.countCarteRisorsa(controller, maxTurno, id);
		if(carte > 7)
		{
			enableGiocatoreDuePanel();
			carteDaScartareDue.setText(Integer.toString(carte / 2));
		}
	}
	
	private void setGiocatoreTrePanel() throws SQLException
	{
		int id = idsGiocatori.get(2);
		idGiocatoreTreTextBox.setText(Integer.toString(id));
		int maxTurno = InterazioneDatabase.getMaxTurno(controller, idPartita);
		int carte = InterazioneDatabase.countCarteRisorsa(controller, maxTurno, id);
		if(carte > 7)
		{
			enableGiocatoreTrePanel();
			carteDaScartareTre.setText(Integer.toString(carte / 2));
		}
	}
	
	private void setGiocatoreQuattroPanel() throws SQLException
	{
		int id = idsGiocatori.get(3);
		idGiocatoreQuattroTextBox.setText(Integer.toString(id));
		int maxTurno = InterazioneDatabase.getMaxTurno(controller, idPartita);
		int carte = InterazioneDatabase.countCarteRisorsa(controller, maxTurno, id);
		if(carte > 7)
		{
			enableGiocatoreQuattroPanel();
			carteDaScartareQuattro.setText(Integer.toString(carte / 2));
		}
	}
	
	private void setPanels() throws SQLException
	{
		setGiocatoreUnoPanel();
		setGiocatoreDuePanel();
		setGiocatoreTrePanel();
		setGiocatoreQuattroPanel();
	}
	
	private boolean areEmptyTextFieldsGiocatoreUno()
	{
		if(giocatoreUnoPanel.isEnabled())
		{
			if(legnoUno.getText().equals("") || lanaUno.getText().equals("") || 
			   argillaUno.getText().equals("") || mineraleUno.getText().equals("") || 
			   granoUno.getText().equals(""))
				return true;
			return false;
		}
		return false;
	}
	
	private boolean areEmptyTextFieldsGiocatoreDue()
	{
		if(giocatoreDuePanel.isEnabled())
		{
			if(legnoDue.getText().equals("") || lanaDue.getText().equals("") || argillaDue.getText().equals("") ||
			   mineraleDue.getText().equals("") || granoDue.getText().equals(""))
				return true;
			return false;
		}
		return false;
	}
	
	private boolean areEmptyTextFieldsGiocatoreTre()
	{
		if(giocatoreTrePanel.isEnabled())
		{
			if(legnoTre.getText().equals("") || lanaTre.getText().equals("") || argillaTre.getText().equals("") ||
			   mineraleTre.getText().equals("") || granoTre.getText().equals(""))
				return true;
			return false;
		}
		return false;
	}
	
	private boolean areEmptyTextFieldsGiocatoreQuattro()
	{
		if(giocatoreQuattroPanel.isEnabled())
		{
			if(legnoQuattro.getText().equals("") || lanaQuattro.getText().equals("") || argillaQuattro.getText().equals("") ||
					   mineraleQuattro.getText().equals("") || granoQuattro.getText().equals(""))
				return true;
			return false;
		}
		return false;
	}
	
	private boolean checkQuantitaGiocatoreUno()
	{
		if(giocatoreUnoPanel.isEnabled())
		{
			int quatitaRichiesta = Integer.parseInt(carteDaScartareUno.getText());
			int sommaQuantita = 0;
			sommaQuantita += Integer.parseInt(legnoUno.getText());
			sommaQuantita += Integer.parseInt(lanaUno.getText());
			sommaQuantita += Integer.parseInt(granoUno.getText());
			sommaQuantita += Integer.parseInt(mineraleUno.getText());
			sommaQuantita += Integer.parseInt(argillaUno.getText());
			if(sommaQuantita != quatitaRichiesta)
				return false;
		}
		return true;
	}
	
	private boolean checkQuantitaGiocatoreDue()
	{
		if(giocatoreDuePanel.isEnabled())
		{
			int quatitaRichiesta = Integer.parseInt(carteDaScartareDue.getText());
			int sommaQuantita = 0;
			sommaQuantita += Integer.parseInt(legnoDue.getText());
			sommaQuantita += Integer.parseInt(lanaDue.getText());
			sommaQuantita += Integer.parseInt(granoDue.getText());
			sommaQuantita += Integer.parseInt(mineraleDue.getText());
			sommaQuantita += Integer.parseInt(argillaDue.getText());
			if(sommaQuantita != quatitaRichiesta)
				return false;
		}
		return true;
	}
	
	private boolean checkQuantitaGiocatoreTre()
	{
		if(giocatoreTrePanel.isEnabled())
		{
			int quatitaRichiesta = Integer.parseInt(carteDaScartareTre.getText());
			int sommaQuantita = 0;
			sommaQuantita += Integer.parseInt(legnoTre.getText());
			sommaQuantita += Integer.parseInt(lanaTre.getText());
			sommaQuantita += Integer.parseInt(granoTre.getText());
			sommaQuantita += Integer.parseInt(mineraleTre.getText());
			sommaQuantita += Integer.parseInt(argillaTre.getText());
			if(sommaQuantita != quatitaRichiesta)
				return false;
		}
		return true;
	}

	private boolean checkQuantitaGiocatoreQuattro()
	{
		if(giocatoreQuattroPanel.isEnabled())
		{
			int quatitaRichiesta = Integer.parseInt(carteDaScartareQuattro.getText());
			int sommaQuantita = 0;
			sommaQuantita += Integer.parseInt(legnoQuattro.getText());
			sommaQuantita += Integer.parseInt(lanaQuattro.getText());
			sommaQuantita += Integer.parseInt(granoQuattro.getText());
			sommaQuantita += Integer.parseInt(mineraleQuattro.getText());
			sommaQuantita += Integer.parseInt(argillaQuattro.getText());
			if(sommaQuantita != quatitaRichiesta)
				return false;
		}
		return true;
	}
	
	private boolean checkCarteDaScartare() throws SQLException, Exception
	{
		checkQuantitaRisorse();
		if(!areEmptyTextFieldsGiocatoreUno() && !areEmptyTextFieldsGiocatoreDue() &&  
		   !areEmptyTextFieldsGiocatoreTre() && !areEmptyTextFieldsGiocatoreQuattro() && 
		   checkQuantitaGiocatoreUno() && checkQuantitaGiocatoreDue() && 
		   checkQuantitaGiocatoreTre() && checkQuantitaGiocatoreQuattro())
			return true;
		return false;
	}
	
	private boolean checkQuantitaRisorse(int idGiocatore, int idTurno, int quantitaVoluta, String tipo) throws SQLException
	{
		String cartaDaDerubare = cartaDaDerubareComboBox.getSelectedItem().toString();
		int id = Integer.parseInt(giocatoreDaDerubareComboBox.getSelectedItem().toString());
		if(id == idGiocatore && cartaDaDerubare.equals(tipo))
			++quantitaVoluta;
		int quantitaTotale = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, tipo);
		if(quantitaTotale < quantitaVoluta)
			return false;
		return true;
	}
	
	private boolean checkQuantitaRisorseGiocatoreUno(int idTurno) throws SQLException
	{
		if(giocatoreUnoPanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreUnoTextBox.getText());
			int legno = Integer.parseInt(legnoUno.getText());
			int grano = Integer.parseInt(granoUno.getText());
			int lana = Integer.parseInt(lanaUno.getText());
			int minerale = Integer.parseInt(mineraleUno.getText());
			int argilla = Integer.parseInt(argillaUno.getText());
			int somma = legno + grano + lana + minerale + argilla;
			int quantitaTotale = Integer.parseInt(carteDaScartareUno.getText());
			if(quantitaTotale != somma)
				return false;
			boolean checkLegno = checkQuantitaRisorse(id, idTurno, legno, "Legno");
			boolean checkGrano = checkQuantitaRisorse(id, idTurno, grano, "Grano");
			boolean checkLana = checkQuantitaRisorse(id, idTurno, lana, "Lana");
			boolean checkMinerale = checkQuantitaRisorse(id, idTurno, minerale, "Minerale");
			boolean checkArgilla = checkQuantitaRisorse(id, idTurno, argilla, "Argilla");
			if(!checkLegno || !checkGrano || !checkLana || !checkMinerale || !checkArgilla)
				return false;
		}
		return true;
	}
	
	private boolean checkQuantitaRisorseGiocatoreDue(int idTurno) throws SQLException
	{
		if(giocatoreDuePanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreDueTextBox.getText());
			int legno = Integer.parseInt(legnoDue.getText());
			int grano = Integer.parseInt(granoDue.getText());
			int lana = Integer.parseInt(lanaDue.getText());
			int minerale = Integer.parseInt(mineraleDue.getText());
			int argilla = Integer.parseInt(argillaDue.getText());
			int somma = legno + grano + lana + minerale + argilla;
			int quantitaTotale = Integer.parseInt(carteDaScartareDue.getText());
			if(quantitaTotale != somma)
				return false;
			boolean checkLegno = checkQuantitaRisorse(id, idTurno, legno, "Legno");
			boolean checkGrano = checkQuantitaRisorse(id, idTurno, grano, "Grano");
			boolean checkLana = checkQuantitaRisorse(id, idTurno, lana, "Lana");
			boolean checkMinerale = checkQuantitaRisorse(id, idTurno, minerale, "Minerale");
			boolean checkArgilla = checkQuantitaRisorse(id, idTurno, argilla, "Argilla");
			if(!checkLegno || !checkGrano || !checkLana || !checkMinerale || !checkArgilla)
				return false;
		}
		return true;
	}
	
	private boolean checkQuantitaRisorseGiocatoreTre(int idTurno) throws SQLException
	{
		if(giocatoreTrePanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreTreTextBox.getText());
			int legno = Integer.parseInt(legnoTre.getText());
			int grano = Integer.parseInt(granoTre.getText());
			int lana = Integer.parseInt(lanaTre.getText());
			int minerale = Integer.parseInt(mineraleTre.getText());
			int argilla = Integer.parseInt(argillaTre.getText());
			int somma = legno + grano + lana + minerale + argilla;
			int quantitaTotale = Integer.parseInt(carteDaScartareTre.getText());
			if(quantitaTotale != somma)
				return false;
			boolean checkLegno = checkQuantitaRisorse(id, idTurno, legno, "Legno");
			boolean checkGrano = checkQuantitaRisorse(id, idTurno, grano, "Grano");
			boolean checkLana = checkQuantitaRisorse(id, idTurno, lana, "Lana");
			boolean checkMinerale = checkQuantitaRisorse(id, idTurno, minerale, "Minerale");
			boolean checkArgilla = checkQuantitaRisorse(id, idTurno, argilla, "Argilla");
			if(!checkLegno || !checkGrano || !checkLana || !checkMinerale || !checkArgilla)
				return false;
		}
		return true;
	}
	
	private boolean checkQuantitaRisorseGiocatoreQuattro(int idTurno) throws SQLException
	{
		if(giocatoreQuattroPanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreQuattroTextBox.getText());
			int legno = Integer.parseInt(legnoQuattro.getText());
			int grano = Integer.parseInt(granoQuattro.getText());
			int lana = Integer.parseInt(lanaQuattro.getText());
			int minerale = Integer.parseInt(mineraleQuattro.getText());
			int argilla = Integer.parseInt(argillaQuattro.getText());
			int somma = legno + grano + lana + minerale + argilla;
			int quantitaTotale = Integer.parseInt(carteDaScartareQuattro.getText());
			if(quantitaTotale != somma)
				return false;
			boolean checkLegno = checkQuantitaRisorse(id, idTurno, legno, "Legno");
			boolean checkGrano = checkQuantitaRisorse(id, idTurno, grano, "Grano");
			boolean checkLana = checkQuantitaRisorse(id, idTurno, lana, "Lana");
			boolean checkMinerale = checkQuantitaRisorse(id, idTurno, minerale, "Minerale");
			boolean checkArgilla = checkQuantitaRisorse(id, idTurno, argilla, "Argilla");
			if(!checkLegno || !checkGrano || !checkLana || !checkMinerale || !checkArgilla)
				return false;
		}
		return true;
	}
	
	private void checkQuantitaRisorse() throws SQLException, Exception
	{
		int turnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		boolean uno = checkQuantitaRisorseGiocatoreUno(turnoMax);
		boolean due = checkQuantitaRisorseGiocatoreDue(turnoMax);
		boolean tre = checkQuantitaRisorseGiocatoreTre(turnoMax);
		boolean quattro = checkQuantitaRisorseGiocatoreQuattro(turnoMax);
		if(!uno || !due || !tre || !quattro)
			throw new Exception();
	}
	
	private void rimuoviCarteRisorsa(int idGiocatore, int idTurno, int quantita, String tipo) throws SQLException, Exception
	{
		ArrayList<Integer> carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, tipo);
		for(int i = 0; i != quantita; ++i)
			InterazioneDatabase.rimuoviCartaRisorsa(controller, carte.get(i), idTurno);
	}
	
	private void rimuoviCarteGiocatoreUno(int idTurno) throws SQLException, Exception
	{
		if(giocatoreUnoPanel.isEnabled())
		{
			int quantitaLegno = Integer.parseInt(legnoUno.getText());
			int quantitaLana = Integer.parseInt(lanaUno.getText());
			int quantitaGrano = Integer.parseInt(granoUno.getText());
			int quantitaArgilla = Integer.parseInt(argillaUno.getText());
			int quantitaMinerale = Integer.parseInt(mineraleUno.getText());
			int id = Integer.parseInt(idGiocatoreUnoTextBox.getText());
			rimuoviCarteRisorsa(id, idTurno, quantitaLegno, "Legno");
			rimuoviCarteRisorsa(id, idTurno, quantitaLana, "Lana");
			rimuoviCarteRisorsa(id, idTurno, quantitaGrano, "Grano");
			rimuoviCarteRisorsa(id, idTurno, quantitaArgilla, "Argilla");
			rimuoviCarteRisorsa(id, idTurno, quantitaMinerale, "Minerale");
		}
	}
	
	private void rimuoviCarteGiocatoreDue(int idTurno) throws SQLException, Exception
	{
		if(giocatoreDuePanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreDueTextBox.getText());
			int quantita = Integer.parseInt(legnoDue.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Legno");
			quantita = Integer.parseInt(lanaDue.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Lana");
			quantita = Integer.parseInt(granoDue.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Grano");
			quantita = Integer.parseInt(argillaDue.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Argilla");
			quantita = Integer.parseInt(mineraleDue.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Minerale");
		}
	}
	
	private void rimuoviCarteGiocatoreTre(int idTurno) throws SQLException, Exception
	{
		if(giocatoreTrePanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreTreTextBox.getText());
			int quantita = Integer.parseInt(legnoTre.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Legno");
			quantita = Integer.parseInt(lanaTre.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Lana");
			quantita = Integer.parseInt(granoTre.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Grano");
			quantita = Integer.parseInt(argillaTre.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Argilla");
			quantita = Integer.parseInt(mineraleTre.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Minerale");
		}
	}
	
	private void rimuoviCarteGiocatoreQuattro(int idTurno) throws SQLException, Exception
	{
		if(giocatoreQuattroPanel.isEnabled())
		{
			int id = Integer.parseInt(idGiocatoreQuattroTextBox.getText());
			int quantita = Integer.parseInt(legnoQuattro.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Legno");
			quantita = Integer.parseInt(lanaQuattro.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Lana");
			quantita = Integer.parseInt(granoQuattro.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Grano");
			quantita = Integer.parseInt(argillaQuattro.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Argilla");
			quantita = Integer.parseInt(mineraleQuattro.getText());
			rimuoviCarteRisorsa(id, idTurno, quantita, "Minerale");
		}
	}
	
	private void rimuoviCarte() throws SQLException, Exception
	{
		int turnoMax = InterazioneDatabase.getMaxTurno(controller, idPartita);
		rimuoviCarteGiocatoreUno(turnoMax);
		rimuoviCarteGiocatoreDue(turnoMax);
		rimuoviCarteGiocatoreTre(turnoMax);
		rimuoviCarteGiocatoreQuattro(turnoMax);
	}
	
	private void aggiornaIds() throws SQLException
	{
		int posizione = Integer.parseInt(posizioneBriganteComboBox.getSelectedItem().toString());
		int idEsagono = InterazioneDatabase.getIdEsagono(controller, idPartita, posizione);
		ArrayList<Integer> cittaColonie = InterazioneDatabase.getIdsCittaColonieSuEsagono(controller, idEsagono);
		ArrayList<Integer> giocatori = new ArrayList<Integer>();
		int idGiocatore = Integer.parseInt(idGiocatoreComboBox.getSelectedItem().toString());
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
			giocatoreDaDerubareComboBox.setEnabled(true);
			cartaDaDerubareComboBox.setEnabled(true);
			DefaultComboBoxModel model = new DefaultComboBoxModel(giocatori.toArray());
			giocatoreDaDerubareComboBox.setModel(model);
		}
		else
		{
			giocatoreDaDerubareComboBox.setEnabled(false);
			cartaDaDerubareComboBox.setEnabled(false);
		}
	}
	
	private void rimuoviCarteDaDerubare() throws SQLException
	{
		int idTurno = InterazioneDatabase.getMaxTurno(controller, idPartita);
		String tipo = cartaDaDerubareComboBox.getSelectedItem().toString();
		if(cartaDaDerubareComboBox.isEnabled() && !tipo.equals("Nessuna"))
		{
			int nuovoGiocatore = Integer.parseInt(idGiocatoreComboBox.getSelectedItem().toString());
			int giocatoreTarget = Integer.parseInt(giocatoreDaDerubareComboBox.getSelectedItem().toString());
			ArrayList<Integer> id = InterazioneDatabase.getIdsCarteRisorsa(controller, giocatoreTarget, idTurno, tipo);
			InterazioneDatabase.swapPadroneCartaRisorsa(controller, id.get(0), nuovoGiocatore, idTurno);
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Controller c = new Controller();
			c.connettiAlDatabase("jdbc:oracle:thin:@localhost:1521", "orcl", "system", "mydatabase");
			AggiungiTurnoDialog dialog = new AggiungiTurnoDialog(c, 1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AggiungiTurnoDialog(Controller controller, int idPartita) {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Aggiungi Turno");
		this.controller = controller;
		this.idPartita = idPartita;
		Object[] idGiocatori = null;
		Object[] numeriDadi = generaNumeriDadi();
		Object[] posizioniBrigante = null;
		try 
		{
			posizioniBrigante = generaNumeriPosizioneBrigante();
		} 
		catch (SQLException e2) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento delle posizioni del brigante");
			dispose();
		}
		try 
		{
			idGiocatori = generaIdGiocatori().toArray();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento degli idGiocatori");
			dispose();
		}
		setBounds(100, 100, 669, 557);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setPreferredSize(new Dimension(500, 250));
			contentPanel.add(tabbedPane);
			{
				JPanel panel = new JPanel();
				panel.setToolTipText("");
				tabbedPane.addTab("Aggiungi Turno", null, panel, null);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
				gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				{
					JLabel idGiocatoreLabel = new JLabel("IdGiocatore:");
					GridBagConstraints gbc_idGiocatoreLabel = new GridBagConstraints();
					gbc_idGiocatoreLabel.insets = new Insets(0, 0, 5, 5);
					gbc_idGiocatoreLabel.gridx = 0;
					gbc_idGiocatoreLabel.gridy = 1;
					panel.add(idGiocatoreLabel, gbc_idGiocatoreLabel);
				}
				{
					idGiocatoreComboBox = new JComboBox(idGiocatori);
					GridBagConstraints gbc_idGiocatoreComboBox = new GridBagConstraints();
					gbc_idGiocatoreComboBox.insets = new Insets(0, 0, 5, 0);
					gbc_idGiocatoreComboBox.gridx = 1;
					gbc_idGiocatoreComboBox.gridy = 1;
					panel.add(idGiocatoreComboBox, gbc_idGiocatoreComboBox);
					{
						JLabel lblNewLabel = new JLabel("Giro:");
						GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
						gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel.gridx = 0;
						gbc_lblNewLabel.gridy = 2;
						panel.add(lblNewLabel, gbc_lblNewLabel);
					}
					{
						giroTextField = new JTextField();
						GridBagConstraints gbc_giroTextField = new GridBagConstraints();
						gbc_giroTextField.insets = new Insets(0, 0, 5, 0);
						gbc_giroTextField.gridx = 1;
						gbc_giroTextField.gridy = 2;
						panel.add(giroTextField, gbc_giroTextField);
						giroTextField.setEditable(false);
						giroTextField.setColumns(10);
					}
					{
						JLabel dadoUnoLabel = new JLabel("Dado Uno:");
						GridBagConstraints gbc_dadoUnoLabel = new GridBagConstraints();
						gbc_dadoUnoLabel.insets = new Insets(0, 0, 5, 5);
						gbc_dadoUnoLabel.gridx = 0;
						gbc_dadoUnoLabel.gridy = 3;
						panel.add(dadoUnoLabel, gbc_dadoUnoLabel);
					}
					{
						dadoUnoComboBox = new JComboBox(numeriDadi);
						GridBagConstraints gbc_dadoUnoComboBox = new GridBagConstraints();
						gbc_dadoUnoComboBox.insets = new Insets(0, 0, 5, 0);
						gbc_dadoUnoComboBox.gridx = 1;
						gbc_dadoUnoComboBox.gridy = 3;
						panel.add(dadoUnoComboBox, gbc_dadoUnoComboBox);
						{
							JLabel dadoDueLabel = new JLabel("Dado Due:");
							GridBagConstraints gbc_dadoDueLabel = new GridBagConstraints();
							gbc_dadoDueLabel.insets = new Insets(0, 0, 5, 5);
							gbc_dadoDueLabel.gridx = 0;
							gbc_dadoDueLabel.gridy = 4;
							panel.add(dadoDueLabel, gbc_dadoDueLabel);
						}
						{
							dadoDueComboBox = new JComboBox(numeriDadi);
							GridBagConstraints gbc_dadoDueComboBox = new GridBagConstraints();
							gbc_dadoDueComboBox.insets = new Insets(0, 0, 5, 0);
							gbc_dadoDueComboBox.gridx = 1;
							gbc_dadoDueComboBox.gridy = 4;
							panel.add(dadoDueComboBox, gbc_dadoDueComboBox);
							{
								posizioneBriganteLabel = new JLabel("Posizione Brigante:");
								GridBagConstraints gbc_posizioneBriganteLabel = new GridBagConstraints();
								gbc_posizioneBriganteLabel.insets = new Insets(0, 0, 0, 5);
								gbc_posizioneBriganteLabel.gridx = 0;
								gbc_posizioneBriganteLabel.gridy = 5;
								panel.add(posizioneBriganteLabel, gbc_posizioneBriganteLabel);
								posizioneBriganteLabel.setEnabled(false);
							}
							{
								posizioneBriganteComboBox = new JComboBox(posizioniBrigante);
								posizioneBriganteComboBox.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) 
									{
										try 
										{
											aggiornaIds();
										} 
										catch (SQLException e1) 
										{
											JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore nel caricamento dei giocatori da derubare");
											dispose();
										}
									}
								});
								GridBagConstraints gbc_posizioneBriganteComboBox = new GridBagConstraints();
								gbc_posizioneBriganteComboBox.gridx = 1;
								gbc_posizioneBriganteComboBox.gridy = 5;
								panel.add(posizioneBriganteComboBox, gbc_posizioneBriganteComboBox);
								posizioneBriganteComboBox.setEnabled(false);
							}
							dadoDueComboBox.addItemListener(new ItemListener() 
							{
								public void itemStateChanged(ItemEvent e) 
								{
									setPosizioneBriganteComboBox();
								}
							});
						}
						dadoUnoComboBox.addItemListener(new ItemListener() 
						{
							public void itemStateChanged(ItemEvent e) 
							{
								setPosizioneBriganteComboBox();
							}
						});
					}
					idGiocatoreComboBox.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) 
						{
							try 
							{
								setGiroTextField();
							} 
							catch (SQLException e1) 
							{
								JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore nel caricamento degli idGiocatori");
								dispose();
							}
							if(posizioneBriganteComboBox.isEnabled())
							{
								try 
								{
									aggiornaIds();
								} 
								catch (SQLException e1) 
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore nel caricamento dei giocatori da derubare");
									dispose();
								}
							}
						}
					});
				}
			}
			{
				pannelloCarte = new JPanel();
				tabbedPane.addTab("Scarta Carte", null, pannelloCarte, null);
				GridBagLayout gbl_pannelloCarte = new GridBagLayout();
				gbl_pannelloCarte.columnWidths = new int[]{0, 0, 0};
				gbl_pannelloCarte.rowHeights = new int[]{0, 0, 0, 0, 0};
				gbl_pannelloCarte.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
				gbl_pannelloCarte.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
				pannelloCarte.setLayout(gbl_pannelloCarte);
				{
					giocatoreUnoPanel = new JPanel();
					giocatoreUnoPanel.setBorder(new TitledBorder(null, "Giocatore Uno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					GridBagConstraints gbc_giocatoreUnoPanel = new GridBagConstraints();
					gbc_giocatoreUnoPanel.anchor = GridBagConstraints.WEST;
					gbc_giocatoreUnoPanel.insets = new Insets(0, 0, 5, 5);
					gbc_giocatoreUnoPanel.fill = GridBagConstraints.VERTICAL;
					gbc_giocatoreUnoPanel.gridx = 0;
					gbc_giocatoreUnoPanel.gridy = 0;
					pannelloCarte.add(giocatoreUnoPanel, gbc_giocatoreUnoPanel);
					GridBagLayout gbl_giocatoreUnoPanel = new GridBagLayout();
					gbl_giocatoreUnoPanel.columnWidths = new int[]{0, 0, 0};
					gbl_giocatoreUnoPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
					gbl_giocatoreUnoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
					gbl_giocatoreUnoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					giocatoreUnoPanel.setLayout(gbl_giocatoreUnoPanel);
					{
						lblId = new JLabel("ID:");
						GridBagConstraints gbc_lblId = new GridBagConstraints();
						gbc_lblId.anchor = GridBagConstraints.EAST;
						gbc_lblId.insets = new Insets(0, 0, 5, 5);
						gbc_lblId.gridx = 0;
						gbc_lblId.gridy = 0;
						giocatoreUnoPanel.add(lblId, gbc_lblId);
					}
					{
						idGiocatoreUnoTextBox = new JTextField();
						idGiocatoreUnoTextBox.setEditable(false);
						GridBagConstraints gbc_idGiocatoreUnoTextBox = new GridBagConstraints();
						gbc_idGiocatoreUnoTextBox.insets = new Insets(0, 0, 5, 0);
						gbc_idGiocatoreUnoTextBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_idGiocatoreUnoTextBox.gridx = 1;
						gbc_idGiocatoreUnoTextBox.gridy = 0;
						giocatoreUnoPanel.add(idGiocatoreUnoTextBox, gbc_idGiocatoreUnoTextBox);
						idGiocatoreUnoTextBox.setColumns(10);
					}
					{
						JLabel lblCarteDaScartare = new JLabel("Carte Da Scartare:");
						GridBagConstraints gbc_lblCarteDaScartare = new GridBagConstraints();
						gbc_lblCarteDaScartare.insets = new Insets(0, 0, 5, 5);
						gbc_lblCarteDaScartare.anchor = GridBagConstraints.EAST;
						gbc_lblCarteDaScartare.gridx = 0;
						gbc_lblCarteDaScartare.gridy = 1;
						giocatoreUnoPanel.add(lblCarteDaScartare, gbc_lblCarteDaScartare);
					}
					{
						carteDaScartareUno = new JTextField();
						carteDaScartareUno.setEditable(false);
						GridBagConstraints gbc_carteDaScartareUno = new GridBagConstraints();
						gbc_carteDaScartareUno.anchor = GridBagConstraints.WEST;
						gbc_carteDaScartareUno.insets = new Insets(0, 0, 5, 0);
						gbc_carteDaScartareUno.gridx = 1;
						gbc_carteDaScartareUno.gridy = 1;
						giocatoreUnoPanel.add(carteDaScartareUno, gbc_carteDaScartareUno);
						carteDaScartareUno.setColumns(10);
					}
					{
						JLabel lblQuantitLegno = new JLabel("Quantit\u00E0 Legno");
						GridBagConstraints gbc_lblQuantitLegno = new GridBagConstraints();
						gbc_lblQuantitLegno.anchor = GridBagConstraints.EAST;
						gbc_lblQuantitLegno.insets = new Insets(0, 0, 5, 5);
						gbc_lblQuantitLegno.gridx = 0;
						gbc_lblQuantitLegno.gridy = 2;
						giocatoreUnoPanel.add(lblQuantitLegno, gbc_lblQuantitLegno);
					}
					{
						legnoUno = new JTextField();
						legnoUno.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									legnoUno.setText("");
								}
							}
						});
						GridBagConstraints gbc_legnoUno = new GridBagConstraints();
						gbc_legnoUno.anchor = GridBagConstraints.WEST;
						gbc_legnoUno.insets = new Insets(0, 0, 5, 0);
						gbc_legnoUno.gridx = 1;
						gbc_legnoUno.gridy = 2;
						giocatoreUnoPanel.add(legnoUno, gbc_legnoUno);
						legnoUno.setColumns(10);
					}
					{
						JLabel lblQuantitArgilla = new JLabel("Quantit\u00E0 Argilla");
						GridBagConstraints gbc_lblQuantitArgilla = new GridBagConstraints();
						gbc_lblQuantitArgilla.anchor = GridBagConstraints.EAST;
						gbc_lblQuantitArgilla.insets = new Insets(0, 0, 5, 5);
						gbc_lblQuantitArgilla.gridx = 0;
						gbc_lblQuantitArgilla.gridy = 3;
						giocatoreUnoPanel.add(lblQuantitArgilla, gbc_lblQuantitArgilla);
					}
					{
						argillaUno = new JTextField();
						argillaUno.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									argillaUno.setText("");
								}
							}
						});
						GridBagConstraints gbc_argillaUno = new GridBagConstraints();
						gbc_argillaUno.anchor = GridBagConstraints.WEST;
						gbc_argillaUno.insets = new Insets(0, 0, 5, 0);
						gbc_argillaUno.gridx = 1;
						gbc_argillaUno.gridy = 3;
						giocatoreUnoPanel.add(argillaUno, gbc_argillaUno);
						argillaUno.setColumns(10);
					}
					{
						JLabel lblQuantitMinerale = new JLabel("Quantit\u00E0 Minerale:");
						GridBagConstraints gbc_lblQuantitMinerale = new GridBagConstraints();
						gbc_lblQuantitMinerale.anchor = GridBagConstraints.EAST;
						gbc_lblQuantitMinerale.insets = new Insets(0, 0, 5, 5);
						gbc_lblQuantitMinerale.gridx = 0;
						gbc_lblQuantitMinerale.gridy = 4;
						giocatoreUnoPanel.add(lblQuantitMinerale, gbc_lblQuantitMinerale);
					}
					{
						mineraleUno = new JTextField();
						mineraleUno.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									mineraleUno.setText("");
								}
							}
						});
						GridBagConstraints gbc_mineraleUno = new GridBagConstraints();
						gbc_mineraleUno.anchor = GridBagConstraints.WEST;
						gbc_mineraleUno.insets = new Insets(0, 0, 5, 0);
						gbc_mineraleUno.gridx = 1;
						gbc_mineraleUno.gridy = 4;
						giocatoreUnoPanel.add(mineraleUno, gbc_mineraleUno);
						mineraleUno.setColumns(10);
					}
					{
						JLabel lblQuantitGrano = new JLabel("Quantit\u00E0 Grano");
						GridBagConstraints gbc_lblQuantitGrano = new GridBagConstraints();
						gbc_lblQuantitGrano.anchor = GridBagConstraints.EAST;
						gbc_lblQuantitGrano.insets = new Insets(0, 0, 5, 5);
						gbc_lblQuantitGrano.gridx = 0;
						gbc_lblQuantitGrano.gridy = 5;
						giocatoreUnoPanel.add(lblQuantitGrano, gbc_lblQuantitGrano);
					}
					{
						granoUno = new JTextField();
						granoUno.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									granoUno.setText("");
								}
							}
						});
						GridBagConstraints gbc_granoUno = new GridBagConstraints();
						gbc_granoUno.anchor = GridBagConstraints.WEST;
						gbc_granoUno.insets = new Insets(0, 0, 5, 0);
						gbc_granoUno.gridx = 1;
						gbc_granoUno.gridy = 5;
						giocatoreUnoPanel.add(granoUno, gbc_granoUno);
						granoUno.setColumns(10);
					}
					{
						JLabel lblQuantitLana = new JLabel("Quantit\u00E0 Lana:");
						GridBagConstraints gbc_lblQuantitLana = new GridBagConstraints();
						gbc_lblQuantitLana.anchor = GridBagConstraints.EAST;
						gbc_lblQuantitLana.insets = new Insets(0, 0, 0, 5);
						gbc_lblQuantitLana.gridx = 0;
						gbc_lblQuantitLana.gridy = 6;
						giocatoreUnoPanel.add(lblQuantitLana, gbc_lblQuantitLana);
					}
					{
						lanaUno = new JTextField();
						lanaUno.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									lanaUno.setText("");
								}
							}
						});
						GridBagConstraints gbc_lanaUno = new GridBagConstraints();
						gbc_lanaUno.anchor = GridBagConstraints.WEST;
						gbc_lanaUno.gridx = 1;
						gbc_lanaUno.gridy = 6;
						giocatoreUnoPanel.add(lanaUno, gbc_lanaUno);
						lanaUno.setColumns(10);
					}
				}
				{
					giocatoreDuePanel = new JPanel();
					giocatoreDuePanel.setBorder(new TitledBorder(null, "Giocatore Due", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					GridBagConstraints gbc_giocatoreDuePanel = new GridBagConstraints();
					gbc_giocatoreDuePanel.anchor = GridBagConstraints.WEST;
					gbc_giocatoreDuePanel.insets = new Insets(0, 0, 5, 0);
					gbc_giocatoreDuePanel.fill = GridBagConstraints.VERTICAL;
					gbc_giocatoreDuePanel.gridx = 1;
					gbc_giocatoreDuePanel.gridy = 0;
					pannelloCarte.add(giocatoreDuePanel, gbc_giocatoreDuePanel);
					GridBagLayout gbl_giocatoreDuePanel = new GridBagLayout();
					gbl_giocatoreDuePanel.columnWidths = new int[]{0, 0, 0};
					gbl_giocatoreDuePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
					gbl_giocatoreDuePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
					gbl_giocatoreDuePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					giocatoreDuePanel.setLayout(gbl_giocatoreDuePanel);
					{
						lblNewLabel_19 = new JLabel("ID:");
						GridBagConstraints gbc_lblNewLabel_19 = new GridBagConstraints();
						gbc_lblNewLabel_19.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_19.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_19.gridx = 0;
						gbc_lblNewLabel_19.gridy = 0;
						giocatoreDuePanel.add(lblNewLabel_19, gbc_lblNewLabel_19);
					}
					{
						idGiocatoreDueTextBox = new JTextField();
						idGiocatoreDueTextBox.setEditable(false);
						GridBagConstraints gbc_idGiocatoreDueTextBox = new GridBagConstraints();
						gbc_idGiocatoreDueTextBox.insets = new Insets(0, 0, 5, 0);
						gbc_idGiocatoreDueTextBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_idGiocatoreDueTextBox.gridx = 1;
						gbc_idGiocatoreDueTextBox.gridy = 0;
						giocatoreDuePanel.add(idGiocatoreDueTextBox, gbc_idGiocatoreDueTextBox);
						idGiocatoreDueTextBox.setColumns(10);
					}
					{
						JLabel lblNewLabel_1 = new JLabel("Carte Da Scartare:");
						GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
						gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_1.gridx = 0;
						gbc_lblNewLabel_1.gridy = 1;
						giocatoreDuePanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
					}
					{
						carteDaScartareDue = new JTextField();
						carteDaScartareDue.setEditable(false);
						GridBagConstraints gbc_carteDaScartareDue = new GridBagConstraints();
						gbc_carteDaScartareDue.anchor = GridBagConstraints.WEST;
						gbc_carteDaScartareDue.insets = new Insets(0, 0, 5, 0);
						gbc_carteDaScartareDue.gridx = 1;
						gbc_carteDaScartareDue.gridy = 1;
						giocatoreDuePanel.add(carteDaScartareDue, gbc_carteDaScartareDue);
						carteDaScartareDue.setColumns(10);
					}
					{
						JLabel lblNewLabel_2 = new JLabel("Quantit\u00E0 Legno:");
						GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
						gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_2.gridx = 0;
						gbc_lblNewLabel_2.gridy = 2;
						giocatoreDuePanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
					}
					{
						legnoDue = new JTextField();
						legnoDue.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									legnoDue.setText("");
								}
							}
						});
						GridBagConstraints gbc_legnoDue = new GridBagConstraints();
						gbc_legnoDue.anchor = GridBagConstraints.WEST;
						gbc_legnoDue.insets = new Insets(0, 0, 5, 0);
						gbc_legnoDue.gridx = 1;
						gbc_legnoDue.gridy = 2;
						giocatoreDuePanel.add(legnoDue, gbc_legnoDue);
						legnoDue.setColumns(10);
					}
					{
						JLabel lblNewLabel_3 = new JLabel("Quantit\u00E0 Argilla:");
						GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
						gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_3.gridx = 0;
						gbc_lblNewLabel_3.gridy = 3;
						giocatoreDuePanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
					}
					{
						argillaDue = new JTextField();
						argillaDue.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									argillaDue.setText("");
								}
							}
						});
						GridBagConstraints gbc_argillaDue = new GridBagConstraints();
						gbc_argillaDue.anchor = GridBagConstraints.WEST;
						gbc_argillaDue.insets = new Insets(0, 0, 5, 0);
						gbc_argillaDue.gridx = 1;
						gbc_argillaDue.gridy = 3;
						giocatoreDuePanel.add(argillaDue, gbc_argillaDue);
						argillaDue.setColumns(10);
					}
					{
						JLabel lblNewLabel_4 = new JLabel("Quantit\u00E0 Minerale:");
						GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
						gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_4.gridx = 0;
						gbc_lblNewLabel_4.gridy = 4;
						giocatoreDuePanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
					}
					{
						mineraleDue = new JTextField();
						mineraleDue.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									mineraleDue.setText("");
								}
							}
						});
						GridBagConstraints gbc_mineraleDue = new GridBagConstraints();
						gbc_mineraleDue.anchor = GridBagConstraints.WEST;
						gbc_mineraleDue.insets = new Insets(0, 0, 5, 0);
						gbc_mineraleDue.gridx = 1;
						gbc_mineraleDue.gridy = 4;
						giocatoreDuePanel.add(mineraleDue, gbc_mineraleDue);
						mineraleDue.setColumns(10);
					}
					{
						JLabel lblNewLabel_5 = new JLabel("Quantit\u00E0 Grano:");
						GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
						gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_5.gridx = 0;
						gbc_lblNewLabel_5.gridy = 5;
						giocatoreDuePanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
					}
					{
						granoDue = new JTextField();
						granoDue.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									granoDue.setText("");
								}
							}
						});
						GridBagConstraints gbc_granoDue = new GridBagConstraints();
						gbc_granoDue.anchor = GridBagConstraints.WEST;
						gbc_granoDue.insets = new Insets(0, 0, 5, 0);
						gbc_granoDue.gridx = 1;
						gbc_granoDue.gridy = 5;
						giocatoreDuePanel.add(granoDue, gbc_granoDue);
						granoDue.setColumns(10);
					}
					{
						JLabel lblNewLabel_6 = new JLabel("Quantit\u00E0 Lana:");
						GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
						gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
						gbc_lblNewLabel_6.gridx = 0;
						gbc_lblNewLabel_6.gridy = 6;
						giocatoreDuePanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
					}
					{
						lanaDue = new JTextField();
						lanaDue.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									lanaDue.setText("");
								}
							}
						});
						GridBagConstraints gbc_lanaDue = new GridBagConstraints();
						gbc_lanaDue.anchor = GridBagConstraints.WEST;
						gbc_lanaDue.gridx = 1;
						gbc_lanaDue.gridy = 6;
						giocatoreDuePanel.add(lanaDue, gbc_lanaDue);
						lanaDue.setColumns(10);
					}
				}
				{
					giocatoreTrePanel = new JPanel();
					giocatoreTrePanel.setBorder(new TitledBorder(null, "Giocatore Tre", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					GridBagConstraints gbc_giocatoreTrePanel = new GridBagConstraints();
					gbc_giocatoreTrePanel.insets = new Insets(0, 0, 5, 5);
					gbc_giocatoreTrePanel.fill = GridBagConstraints.BOTH;
					gbc_giocatoreTrePanel.gridx = 0;
					gbc_giocatoreTrePanel.gridy = 1;
					pannelloCarte.add(giocatoreTrePanel, gbc_giocatoreTrePanel);
					GridBagLayout gbl_giocatoreTrePanel = new GridBagLayout();
					gbl_giocatoreTrePanel.columnWidths = new int[]{0, 0, 0};
					gbl_giocatoreTrePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
					gbl_giocatoreTrePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
					gbl_giocatoreTrePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					giocatoreTrePanel.setLayout(gbl_giocatoreTrePanel);
					{
						lblNewLabel_20 = new JLabel("ID:");
						GridBagConstraints gbc_lblNewLabel_20 = new GridBagConstraints();
						gbc_lblNewLabel_20.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_20.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_20.gridx = 0;
						gbc_lblNewLabel_20.gridy = 0;
						giocatoreTrePanel.add(lblNewLabel_20, gbc_lblNewLabel_20);
					}
					{
						idGiocatoreTreTextBox = new JTextField();
						idGiocatoreTreTextBox.setEditable(false);
						GridBagConstraints gbc_idGiocatoreTreTextBox = new GridBagConstraints();
						gbc_idGiocatoreTreTextBox.insets = new Insets(0, 0, 5, 0);
						gbc_idGiocatoreTreTextBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_idGiocatoreTreTextBox.gridx = 1;
						gbc_idGiocatoreTreTextBox.gridy = 0;
						giocatoreTrePanel.add(idGiocatoreTreTextBox, gbc_idGiocatoreTreTextBox);
						idGiocatoreTreTextBox.setColumns(10);
					}
					{
						JLabel lblNewLabel_7 = new JLabel("Carte Da Scartare:");
						GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
						gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_7.gridx = 0;
						gbc_lblNewLabel_7.gridy = 1;
						giocatoreTrePanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
					}
					{
						carteDaScartareTre = new JTextField();
						carteDaScartareTre.setEditable(false);
						GridBagConstraints gbc_carteDaScartareTre = new GridBagConstraints();
						gbc_carteDaScartareTre.anchor = GridBagConstraints.WEST;
						gbc_carteDaScartareTre.insets = new Insets(0, 0, 5, 0);
						gbc_carteDaScartareTre.gridx = 1;
						gbc_carteDaScartareTre.gridy = 1;
						giocatoreTrePanel.add(carteDaScartareTre, gbc_carteDaScartareTre);
						carteDaScartareTre.setColumns(10);
					}
					{
						JLabel lblNewLabel_8 = new JLabel("Quantit\u00E0 Legno:");
						GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
						gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_8.gridx = 0;
						gbc_lblNewLabel_8.gridy = 2;
						giocatoreTrePanel.add(lblNewLabel_8, gbc_lblNewLabel_8);
					}
					{
						legnoTre = new JTextField();
						legnoTre.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									legnoTre.setText("");
								}
							}
						});
						GridBagConstraints gbc_legnoTre = new GridBagConstraints();
						gbc_legnoTre.anchor = GridBagConstraints.WEST;
						gbc_legnoTre.insets = new Insets(0, 0, 5, 0);
						gbc_legnoTre.gridx = 1;
						gbc_legnoTre.gridy = 2;
						giocatoreTrePanel.add(legnoTre, gbc_legnoTre);
						legnoTre.setColumns(10);
					}
					{
						JLabel lblNewLabel_9 = new JLabel("Quantit\u00E0 Argilla:");
						GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
						gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_9.gridx = 0;
						gbc_lblNewLabel_9.gridy = 3;
						giocatoreTrePanel.add(lblNewLabel_9, gbc_lblNewLabel_9);
					}
					{
						argillaTre = new JTextField();
						argillaTre.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									argillaTre.setText("");
								}
							}
						});
						GridBagConstraints gbc_argillaTre = new GridBagConstraints();
						gbc_argillaTre.anchor = GridBagConstraints.WEST;
						gbc_argillaTre.insets = new Insets(0, 0, 5, 0);
						gbc_argillaTre.gridx = 1;
						gbc_argillaTre.gridy = 3;
						giocatoreTrePanel.add(argillaTre, gbc_argillaTre);
						argillaTre.setColumns(10);
					}
					{
						JLabel lblNewLabel_10 = new JLabel("Quantit\u00E0 Minerale:");
						GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
						gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_10.gridx = 0;
						gbc_lblNewLabel_10.gridy = 4;
						giocatoreTrePanel.add(lblNewLabel_10, gbc_lblNewLabel_10);
					}
					{
						mineraleTre = new JTextField();
						mineraleTre.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									mineraleTre.setText("");
								}
							}
						});
						GridBagConstraints gbc_mineraleTre = new GridBagConstraints();
						gbc_mineraleTre.anchor = GridBagConstraints.WEST;
						gbc_mineraleTre.insets = new Insets(0, 0, 5, 0);
						gbc_mineraleTre.gridx = 1;
						gbc_mineraleTre.gridy = 4;
						giocatoreTrePanel.add(mineraleTre, gbc_mineraleTre);
						mineraleTre.setColumns(10);
					}
					{
						JLabel lblNewLabel_11 = new JLabel("Quantit\u00E0 Grano:");
						GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
						gbc_lblNewLabel_11.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_11.gridx = 0;
						gbc_lblNewLabel_11.gridy = 5;
						giocatoreTrePanel.add(lblNewLabel_11, gbc_lblNewLabel_11);
					}
					{
						granoTre = new JTextField();
						granoTre.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									granoTre.setText("");
								}
							}
						});
						GridBagConstraints gbc_granoTre = new GridBagConstraints();
						gbc_granoTre.insets = new Insets(0, 0, 5, 0);
						gbc_granoTre.fill = GridBagConstraints.HORIZONTAL;
						gbc_granoTre.gridx = 1;
						gbc_granoTre.gridy = 5;
						giocatoreTrePanel.add(granoTre, gbc_granoTre);
						granoTre.setColumns(10);
					}
					{
						JLabel lblNewLabel_12 = new JLabel("Quantit\u00E0 Lana:");
						GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
						gbc_lblNewLabel_12.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_12.insets = new Insets(0, 0, 0, 5);
						gbc_lblNewLabel_12.gridx = 0;
						gbc_lblNewLabel_12.gridy = 6;
						giocatoreTrePanel.add(lblNewLabel_12, gbc_lblNewLabel_12);
					}
					{
						lanaTre = new JTextField();
						lanaTre.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									lanaTre.setText("");
								}
							}
						});
						GridBagConstraints gbc_lanaTre = new GridBagConstraints();
						gbc_lanaTre.fill = GridBagConstraints.HORIZONTAL;
						gbc_lanaTre.gridx = 1;
						gbc_lanaTre.gridy = 6;
						giocatoreTrePanel.add(lanaTre, gbc_lanaTre);
						lanaTre.setColumns(10);
					}
				}
				{
					giocatoreQuattroPanel = new JPanel();
					giocatoreQuattroPanel.setBorder(new TitledBorder(null, "Giocatore Quattro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					GridBagConstraints gbc_giocatoreQuattroPanel = new GridBagConstraints();
					gbc_giocatoreQuattroPanel.anchor = GridBagConstraints.WEST;
					gbc_giocatoreQuattroPanel.insets = new Insets(0, 0, 5, 0);
					gbc_giocatoreQuattroPanel.fill = GridBagConstraints.VERTICAL;
					gbc_giocatoreQuattroPanel.gridx = 1;
					gbc_giocatoreQuattroPanel.gridy = 1;
					pannelloCarte.add(giocatoreQuattroPanel, gbc_giocatoreQuattroPanel);
					GridBagLayout gbl_giocatoreQuattroPanel = new GridBagLayout();
					gbl_giocatoreQuattroPanel.columnWidths = new int[]{0, 0, 0};
					gbl_giocatoreQuattroPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
					gbl_giocatoreQuattroPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
					gbl_giocatoreQuattroPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					giocatoreQuattroPanel.setLayout(gbl_giocatoreQuattroPanel);
					{
						lblNewLabel_21 = new JLabel("ID:");
						GridBagConstraints gbc_lblNewLabel_21 = new GridBagConstraints();
						gbc_lblNewLabel_21.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_21.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_21.gridx = 0;
						gbc_lblNewLabel_21.gridy = 0;
						giocatoreQuattroPanel.add(lblNewLabel_21, gbc_lblNewLabel_21);
					}
					{
						idGiocatoreQuattroTextBox = new JTextField();
						idGiocatoreQuattroTextBox.setEditable(false);
						GridBagConstraints gbc_idGiocatoreQuattroTextBox = new GridBagConstraints();
						gbc_idGiocatoreQuattroTextBox.insets = new Insets(0, 0, 5, 0);
						gbc_idGiocatoreQuattroTextBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_idGiocatoreQuattroTextBox.gridx = 1;
						gbc_idGiocatoreQuattroTextBox.gridy = 0;
						giocatoreQuattroPanel.add(idGiocatoreQuattroTextBox, gbc_idGiocatoreQuattroTextBox);
						idGiocatoreQuattroTextBox.setColumns(10);
					}
					{
						JLabel lblNewLabel_13 = new JLabel("Carte Da Scartare:");
						GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
						gbc_lblNewLabel_13.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_13.gridx = 0;
						gbc_lblNewLabel_13.gridy = 1;
						giocatoreQuattroPanel.add(lblNewLabel_13, gbc_lblNewLabel_13);
					}
					{
						carteDaScartareQuattro = new JTextField();
						carteDaScartareQuattro.setEditable(false);
						GridBagConstraints gbc_carteDaScartareQuattro = new GridBagConstraints();
						gbc_carteDaScartareQuattro.anchor = GridBagConstraints.WEST;
						gbc_carteDaScartareQuattro.insets = new Insets(0, 0, 5, 0);
						gbc_carteDaScartareQuattro.gridx = 1;
						gbc_carteDaScartareQuattro.gridy = 1;
						giocatoreQuattroPanel.add(carteDaScartareQuattro, gbc_carteDaScartareQuattro);
						carteDaScartareQuattro.setColumns(10);
					}
					{
						JLabel lblNewLabel_14 = new JLabel("Quantit\u00E0 Legno:");
						GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
						gbc_lblNewLabel_14.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_14.gridx = 0;
						gbc_lblNewLabel_14.gridy = 2;
						giocatoreQuattroPanel.add(lblNewLabel_14, gbc_lblNewLabel_14);
					}
					{
						legnoQuattro = new JTextField();
						legnoQuattro.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									legnoQuattro.setText("");
								}
							}
						});
						GridBagConstraints gbc_legnoQuattro = new GridBagConstraints();
						gbc_legnoQuattro.anchor = GridBagConstraints.WEST;
						gbc_legnoQuattro.insets = new Insets(0, 0, 5, 0);
						gbc_legnoQuattro.gridx = 1;
						gbc_legnoQuattro.gridy = 2;
						giocatoreQuattroPanel.add(legnoQuattro, gbc_legnoQuattro);
						legnoQuattro.setColumns(10);
					}
					{
						JLabel lblNewLabel_15 = new JLabel("Quantit\u00E0 Argilla:");
						GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
						gbc_lblNewLabel_15.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_15.gridx = 0;
						gbc_lblNewLabel_15.gridy = 3;
						giocatoreQuattroPanel.add(lblNewLabel_15, gbc_lblNewLabel_15);
					}
					{
						argillaQuattro = new JTextField();
						argillaQuattro.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									argillaQuattro.setText("");
								}
							}
						});
						GridBagConstraints gbc_argillaQuattro = new GridBagConstraints();
						gbc_argillaQuattro.anchor = GridBagConstraints.WEST;
						gbc_argillaQuattro.insets = new Insets(0, 0, 5, 0);
						gbc_argillaQuattro.gridx = 1;
						gbc_argillaQuattro.gridy = 3;
						giocatoreQuattroPanel.add(argillaQuattro, gbc_argillaQuattro);
						argillaQuattro.setColumns(10);
					}
					{
						JLabel lblNewLabel_16 = new JLabel("Quantit\u00E0 Minerale:");
						GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
						gbc_lblNewLabel_16.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_16.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_16.gridx = 0;
						gbc_lblNewLabel_16.gridy = 4;
						giocatoreQuattroPanel.add(lblNewLabel_16, gbc_lblNewLabel_16);
					}
					{
						mineraleQuattro = new JTextField();
						mineraleQuattro.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									mineraleQuattro.setText("");
								}
							}
						});
						GridBagConstraints gbc_mineraleQuattro = new GridBagConstraints();
						gbc_mineraleQuattro.anchor = GridBagConstraints.WEST;
						gbc_mineraleQuattro.insets = new Insets(0, 0, 5, 0);
						gbc_mineraleQuattro.gridx = 1;
						gbc_mineraleQuattro.gridy = 4;
						giocatoreQuattroPanel.add(mineraleQuattro, gbc_mineraleQuattro);
						mineraleQuattro.setColumns(10);
					}
					{
						JLabel lblNewLabel_17 = new JLabel("Quantit\u00E0 Grano:");
						GridBagConstraints gbc_lblNewLabel_17 = new GridBagConstraints();
						gbc_lblNewLabel_17.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_17.insets = new Insets(0, 0, 5, 5);
						gbc_lblNewLabel_17.gridx = 0;
						gbc_lblNewLabel_17.gridy = 5;
						giocatoreQuattroPanel.add(lblNewLabel_17, gbc_lblNewLabel_17);
					}
					{
						granoQuattro = new JTextField();
						granoQuattro.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									granoQuattro.setText("");
								}
							}
						});
						GridBagConstraints gbc_granoQuattro = new GridBagConstraints();
						gbc_granoQuattro.anchor = GridBagConstraints.WEST;
						gbc_granoQuattro.insets = new Insets(0, 0, 5, 0);
						gbc_granoQuattro.gridx = 1;
						gbc_granoQuattro.gridy = 5;
						giocatoreQuattroPanel.add(granoQuattro, gbc_granoQuattro);
						granoQuattro.setColumns(10);
					}
					{
						JLabel lblNewLabel_18 = new JLabel("Quantit\u00E0 Lana:");
						GridBagConstraints gbc_lblNewLabel_18 = new GridBagConstraints();
						gbc_lblNewLabel_18.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_18.insets = new Insets(0, 0, 0, 5);
						gbc_lblNewLabel_18.gridx = 0;
						gbc_lblNewLabel_18.gridy = 6;
						giocatoreQuattroPanel.add(lblNewLabel_18, gbc_lblNewLabel_18);
					}
					{
						lanaQuattro = new JTextField();
						lanaQuattro.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) 
							{
								if(e.getKeyCode() != e.VK_BACK_SPACE && (e.getKeyChar() < '0' || e.getKeyChar() > '9'))
								{
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore! Non e' possibile inserire dei caratteri");
									lanaQuattro.setText("");
								}
							}
						});
						GridBagConstraints gbc_lanaQuattro = new GridBagConstraints();
						gbc_lanaQuattro.anchor = GridBagConstraints.WEST;
						gbc_lanaQuattro.gridx = 1;
						gbc_lanaQuattro.gridy = 6;
						giocatoreQuattroPanel.add(lanaQuattro, gbc_lanaQuattro);
						lanaQuattro.setColumns(10);
					}
				}
			}
			{
				pannelloSaccheggio = new JPanel();
				tabbedPane.addTab("Saccheggio", null, pannelloSaccheggio, null);
				GridBagLayout gbl_pannelloSaccheggio = new GridBagLayout();
				gbl_pannelloSaccheggio.columnWidths = new int[]{0, 0, 0, 0};
				gbl_pannelloSaccheggio.rowHeights = new int[]{0, 0, 0, 0};
				gbl_pannelloSaccheggio.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
				gbl_pannelloSaccheggio.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
				pannelloSaccheggio.setLayout(gbl_pannelloSaccheggio);
				{
					giocatoreDaDerubareLabel = new JLabel("Id Giocatore da derubare:");
					GridBagConstraints gbc_giocatoreDaDerubareLabel = new GridBagConstraints();
					gbc_giocatoreDaDerubareLabel.insets = new Insets(0, 0, 5, 5);
					gbc_giocatoreDaDerubareLabel.anchor = GridBagConstraints.EAST;
					gbc_giocatoreDaDerubareLabel.gridx = 1;
					gbc_giocatoreDaDerubareLabel.gridy = 1;
					pannelloSaccheggio.add(giocatoreDaDerubareLabel, gbc_giocatoreDaDerubareLabel);
				}
				{
					giocatoreDaDerubareComboBox = new JComboBox();
					GridBagConstraints gbc_giocatoreDaDerubareComboBox = new GridBagConstraints();
					gbc_giocatoreDaDerubareComboBox.insets = new Insets(0, 0, 5, 0);
					gbc_giocatoreDaDerubareComboBox.anchor = GridBagConstraints.WEST;
					gbc_giocatoreDaDerubareComboBox.gridx = 2;
					gbc_giocatoreDaDerubareComboBox.gridy = 1;
					pannelloSaccheggio.add(giocatoreDaDerubareComboBox, gbc_giocatoreDaDerubareComboBox);
				}
				{
					cartaDaDerubareLabel = new JLabel("Carta da derubare:");
					GridBagConstraints gbc_cartaDaDerubareLabel = new GridBagConstraints();
					gbc_cartaDaDerubareLabel.anchor = GridBagConstraints.EAST;
					gbc_cartaDaDerubareLabel.insets = new Insets(0, 0, 0, 5);
					gbc_cartaDaDerubareLabel.gridx = 1;
					gbc_cartaDaDerubareLabel.gridy = 2;
					pannelloSaccheggio.add(cartaDaDerubareLabel, gbc_cartaDaDerubareLabel);
				}
				{
					cartaDaDerubareComboBox = new JComboBox();
					cartaDaDerubareComboBox.setModel(new DefaultComboBoxModel(new String[] {"Grano", "Legno", "Lana", "Minerale", "Argilla", "Nessuna"}));
					GridBagConstraints gbc_cartaDaDerubareComboBox = new GridBagConstraints();
					gbc_cartaDaDerubareComboBox.anchor = GridBagConstraints.WEST;
					gbc_cartaDaDerubareComboBox.gridx = 2;
					gbc_cartaDaDerubareComboBox.gridy = 2;
					pannelloSaccheggio.add(cartaDaDerubareComboBox, gbc_cartaDaDerubareComboBox);
				}
			}
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
							if(!checkGiro())
								JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Aggiungere prima il giro precedente agli altri giocatori");
							else
							{	
								if(posizioneBriganteComboBox.isEnabled() && checkCarteDaScartare())
								{
									aggiungiTurno();
									rimuoviCarte();
									rimuoviCarteDaDerubare();
									setGiroTextField();
									DefaultComboBoxModel model = new DefaultComboBoxModel(generaNumeriPosizioneBrigante());
									posizioneBriganteComboBox.setModel(model);
									prosegui = true;
									disablePannels();
									setPanels();
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Turno aggiunto con successo");
								}
								else if(!posizioneBriganteComboBox.isEnabled())
								{
									aggiungiTurno();
									setGiroTextField();
									DefaultComboBoxModel model = new DefaultComboBoxModel(generaNumeriPosizioneBrigante());
									posizioneBriganteComboBox.setModel(model);
									prosegui = true;
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Turno aggiunto con successo");
								}
								else
									JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore valori inseriti errati");
							}
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore nell'aggiunta del turno");
							dispose();
						}
						catch(Exception e1)
						{
							JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore nel numero di carte inserito");
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
			setGiroTextField();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento del giro del turno");
			dispose();
		}
		pannelloCarte.setEnabled(false);
		try 
		{
			idsGiocatori = generaIdGiocatori();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(AggiungiTurnoDialog.this, "Errore nel caricamento dei dati");
			dispose();
		}
		disablePannels();
		giocatoreDaDerubareComboBox.setEnabled(false);
		cartaDaDerubareComboBox.setEnabled(false);
	}

}
