package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Dialog.ModalityType;

public class AggiungiColonieStradeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int idPartita = 0;
	private Controller controller;
	private JComboBox verticeUnoGiocatoreUnoComboBox;
	private JComboBox latoUnoGiocatoreUnoComboBox;
	private JComboBox verticeDueGiocatoreUnoComboBox;
	private JComboBox latoDueGiocatoreUnoComboBox;
	private JComboBox verticeUnoGiocatoreDueComboBox;
	private JComboBox latoUnoGiocatoreDueComboBox;
	private JComboBox verticeDueGiocatoreDueComboBox;
	private JComboBox latoDueGiocatoreDueComboBox;
	private JComboBox verticeUnoGiocatoreTreComboBox;
	private JComboBox latoUnoGiocatoreTreComboBox;
	private JComboBox verticeDueGiocatoreTreComboBox;
	private JComboBox latoDueGiocatoreTreComboBox;
	private JComboBox verticeUnoGiocatoreQuattroComboBox;
	private JComboBox latoUnoGiocatoreQuattroComboBox;
	private JComboBox verticeDueGiocatoreQuattroComboBox;
	private JComboBox latoDueGiocatoreQuattroComboBox;
	private boolean prosegui = false;
	private int idGiocatoreUno = 0;
	private int idGiocatoreDue = 0;
	private int idGiocatoreTre = 0;
	private int idGiocatoreQuattro = 0;

	private Object[] generaVertici()
	{
		ArrayList<Integer> vertici = new ArrayList<Integer>();
		for(int i = 0; i <= 53; ++i)
			vertici.add(i);
		return vertici.toArray();
	}
	
	private Object[] generaLati(int posizioneVertice) throws SQLException
	{
		ArrayList<Integer> idLati = new ArrayList<Integer>();
		int idVertice = InterazioneDatabase.getIdVertice(controller, idPartita, posizioneVertice);
		idLati.addAll(InterazioneDatabase.getLatiAssociati(controller, idVertice));
		ArrayList<Integer> posizioni = new ArrayList<Integer>();
		for(int i = 0; i != idLati.size(); ++i)
			posizioni.add(InterazioneDatabase.getPosizioneLato(controller, idLati.get(i)));
		return posizioni.toArray();
	}
	
	private void setLatiComboBox(JComboBox comboBoxVertici, JComboBox comboBoxLati)
	{
		String posizioneVertice = comboBoxVertici.getSelectedItem().toString();
		try 
		{
			Object[] lati = generaLati(Integer.parseInt(posizioneVertice));
			comboBoxLati.setModel(new DefaultComboBoxModel(lati));
		}
		catch (NumberFormatException e1) 
		{
			
		} 
		catch (SQLException e1) 
		{
			JOptionPane.showMessageDialog(AggiungiColonieStradeDialog.this, "Errore nel caricamento dei dati");
			dispose();
		}
	}
	
	private int aggiungiColonia(int idVertice) throws SQLException
	{
		int idCittaColonia = InterazioneDatabase.nuovoIdCittaColonia(controller);
		InterazioneDatabase.aggiungiCittaColonia(controller, idCittaColonia, idVertice);
		return idCittaColonia;
	}
	
	private int aggiungiColonia(JComboBox posizioneVertice) throws SQLException
	{
		int posizione = Integer.parseInt(posizioneVertice.getSelectedItem().toString());
		int idVertice = InterazioneDatabase.getIdVertice(controller, idPartita, posizione);
		return aggiungiColonia(idVertice);
	}
	
	private int aggiungiStrada(int idLato, int idCittaColonia) throws SQLException
	{
		int idStrada = InterazioneDatabase.nuovoIdStrada(controller);
		InterazioneDatabase.aggiungiStradaSenzaTurno(controller, idStrada, idLato, idCittaColonia);
		return idStrada;
	}
	
	private int aggiungiStrada(JComboBox posizioneLato, int idCittaColonia) throws SQLException
	{
		int posizione = Integer.parseInt(posizioneLato.getSelectedItem().toString());
		int idLato = InterazioneDatabase.getIdLato(controller, idPartita, posizione);
		return aggiungiStrada(idLato, idCittaColonia);
	}
	
	private int creaTurnoZero(int idGiocatore) throws SQLException
	{
		int idTurno = InterazioneDatabase.nuovoIdTurno(controller);
		InterazioneDatabase.aggiungiTurno(controller, idTurno, 0, idGiocatore, idPartita);
		InterazioneDatabase.copiaRisorse(controller, idGiocatore, idPartita, idTurno, 0);
		return idTurno;
	}
	
	private void aggiungiColonieStrade()
	{
		int idCittaColoniaUnoGiocatoreUno = 0;
		int idCittaColoniaDueGiocatoreUno = 0;
		int idCittaColoniaUnoGiocatoreDue = 0;
		int idCittaColoniaDueGiocatoreDue = 0;
		int idCittaColoniaUnoGiocatoreTre = 0;
		int idCittaColoniaDueGiocatoreTre = 0;
		int idCittaColoniaUnoGiocatoreQuattro = 0;
		int idCittaColoniaDueGiocatoreQuattro = 0;
		try
		{
			idCittaColoniaUnoGiocatoreUno = aggiungiColonia(verticeUnoGiocatoreUnoComboBox);
			int idStradaUnoGiocatoreUno = aggiungiStrada(latoUnoGiocatoreUnoComboBox, idCittaColoniaUnoGiocatoreUno);
			idCittaColoniaDueGiocatoreUno = aggiungiColonia(verticeDueGiocatoreUnoComboBox);
			int idStradaDueGiocatoreUno = aggiungiStrada(latoDueGiocatoreUnoComboBox, idCittaColoniaDueGiocatoreUno);
			idCittaColoniaUnoGiocatoreDue = aggiungiColonia(verticeUnoGiocatoreDueComboBox);
			int idStradaUnoGiocatoreDue = aggiungiStrada(latoUnoGiocatoreDueComboBox, idCittaColoniaUnoGiocatoreDue);
			idCittaColoniaDueGiocatoreDue = aggiungiColonia(verticeDueGiocatoreDueComboBox);
			int idStradaDueGiocatoreDue = aggiungiStrada(latoDueGiocatoreDueComboBox, idCittaColoniaDueGiocatoreDue);
			idCittaColoniaUnoGiocatoreTre = aggiungiColonia(verticeUnoGiocatoreTreComboBox);
			int idStradaUnoGiocatoreTre = aggiungiStrada(latoUnoGiocatoreTreComboBox, idCittaColoniaUnoGiocatoreTre);
			idCittaColoniaDueGiocatoreTre = aggiungiColonia(verticeDueGiocatoreTreComboBox);
			int idStradaDueGiocatoreTre = aggiungiStrada(latoDueGiocatoreTreComboBox, idCittaColoniaDueGiocatoreTre);
			idCittaColoniaUnoGiocatoreQuattro = aggiungiColonia(verticeUnoGiocatoreQuattroComboBox);
			int idStradaUnoGiocatoreQuattro = aggiungiStrada(latoUnoGiocatoreQuattroComboBox, idCittaColoniaUnoGiocatoreQuattro);
			idCittaColoniaDueGiocatoreQuattro = aggiungiColonia(verticeDueGiocatoreQuattroComboBox);
			int idStradaDueGiocatoreQuattro = aggiungiStrada(latoDueGiocatoreQuattroComboBox, idCittaColoniaDueGiocatoreQuattro);
			int idTurnoGiocatoreUno = creaTurnoZero(idGiocatoreUno);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaUnoGiocatoreUno, idTurnoGiocatoreUno);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaDueGiocatoreUno, idTurnoGiocatoreUno);
			InterazioneDatabase.aggiungiRisorseCittaColonie(controller, idTurnoGiocatoreUno, idGiocatoreUno, idCittaColoniaDueGiocatoreUno);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaUnoGiocatoreUno, idTurnoGiocatoreUno);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaDueGiocatoreUno, idTurnoGiocatoreUno);
			int idTurnoGiocatoreDue = creaTurnoZero(idGiocatoreDue);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaUnoGiocatoreDue, idTurnoGiocatoreDue);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaDueGiocatoreDue, idTurnoGiocatoreDue);
			InterazioneDatabase.aggiungiRisorseCittaColonie(controller, idTurnoGiocatoreDue, idGiocatoreDue, idCittaColoniaDueGiocatoreDue);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaUnoGiocatoreDue, idTurnoGiocatoreDue);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaDueGiocatoreDue, idTurnoGiocatoreDue);
			int idTurnoGiocatoreTre = creaTurnoZero(idGiocatoreTre);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaUnoGiocatoreTre, idTurnoGiocatoreTre);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaDueGiocatoreTre, idTurnoGiocatoreTre);
			InterazioneDatabase.aggiungiRisorseCittaColonie(controller, idTurnoGiocatoreTre, idGiocatoreTre, idCittaColoniaDueGiocatoreTre);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaUnoGiocatoreTre, idTurnoGiocatoreTre);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaDueGiocatoreTre, idTurnoGiocatoreTre);
			int idTurnoGiocatoreQuattro = creaTurnoZero(idGiocatoreQuattro);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaUnoGiocatoreQuattro, idTurnoGiocatoreQuattro);
			InterazioneDatabase.aggiungiTurnoCittaColonia(controller, InterazioneDatabase.nuovoIdTurnoCittaColonia(controller), idCittaColoniaDueGiocatoreQuattro, idTurnoGiocatoreQuattro);
			InterazioneDatabase.aggiungiRisorseCittaColonie(controller, idTurnoGiocatoreQuattro, idGiocatoreQuattro, idCittaColoniaDueGiocatoreQuattro);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaUnoGiocatoreQuattro, idTurnoGiocatoreQuattro);
			InterazioneDatabase.aggiungiStradaTurno(controller, InterazioneDatabase.nuovoIdStradaTurno(controller), idStradaDueGiocatoreQuattro, idTurnoGiocatoreQuattro);
			prosegui = true;
			dispose();
		}
		catch(SQLException e)
		{
			try 
			{
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaUnoGiocatoreUno);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaDueGiocatoreUno);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaUnoGiocatoreDue);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaDueGiocatoreDue);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaUnoGiocatoreTre);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaDueGiocatoreTre);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaUnoGiocatoreQuattro);
				InterazioneDatabase.rimuoviCittaColonia(controller, idCittaColoniaDueGiocatoreQuattro);
				JOptionPane.showMessageDialog(this, "Errore nell'inserimento delle colonie e strade");
			} 
			catch (SQLException e1) 
			{
				JOptionPane.showMessageDialog(this, "Fatal error");
				dispose();
			}
		}
	}
	
	public boolean getProsegui()
	{
		return prosegui;
	}

	/**
	 * Create the dialog.
	 */
	public AggiungiColonieStradeDialog(int idPartita, Controller controller, int idGiocatoreUno, int idGiocatoreDue, int idGiocatoreTre, int idGiocatoreQuattro) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		Object[] vertici = generaVertici();
		setTitle("Aggiungi Colonie Strade");
		setBounds(100, 100, 481, 496);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Giocatore Uno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel verticeUnoGiocatoreUnoLabel = new JLabel("Vertice Colonia 1:");
				GridBagConstraints gbc_verticeUnoGiocatoreUnoLabel = new GridBagConstraints();
				gbc_verticeUnoGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeUnoGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreUnoLabel.gridx = 0;
				gbc_verticeUnoGiocatoreUnoLabel.gridy = 1;
				panel.add(verticeUnoGiocatoreUnoLabel, gbc_verticeUnoGiocatoreUnoLabel);
			}
			{
				verticeUnoGiocatoreUnoComboBox = new JComboBox(vertici);
				verticeUnoGiocatoreUnoComboBox.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeUnoGiocatoreUnoComboBox, latoUnoGiocatoreUnoComboBox);
					}
				});
				GridBagConstraints gbc_verticeUnoGiocatoreUnoComboBox = new GridBagConstraints();
				gbc_verticeUnoGiocatoreUnoComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreUnoComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeUnoGiocatoreUnoComboBox.gridx = 1;
				gbc_verticeUnoGiocatoreUnoComboBox.gridy = 1;
				panel.add(verticeUnoGiocatoreUnoComboBox, gbc_verticeUnoGiocatoreUnoComboBox);
			}
			{
				JLabel latoUnoGiocatoreUnoLabel = new JLabel("Lato Strada 1:");
				GridBagConstraints gbc_latoUnoGiocatoreUnoLabel = new GridBagConstraints();
				gbc_latoUnoGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_latoUnoGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoUnoGiocatoreUnoLabel.gridx = 2;
				gbc_latoUnoGiocatoreUnoLabel.gridy = 1;
				panel.add(latoUnoGiocatoreUnoLabel, gbc_latoUnoGiocatoreUnoLabel);
			}
			{
				latoUnoGiocatoreUnoComboBox = new JComboBox();
				GridBagConstraints gbc_latoUnoGiocatoreUnoComboBox = new GridBagConstraints();
				gbc_latoUnoGiocatoreUnoComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoUnoGiocatoreUnoComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoUnoGiocatoreUnoComboBox.gridx = 3;
				gbc_latoUnoGiocatoreUnoComboBox.gridy = 1;
				panel.add(latoUnoGiocatoreUnoComboBox, gbc_latoUnoGiocatoreUnoComboBox);
			}
			{
				JLabel verticeDueGiocatoreUnoLabel = new JLabel("Vertice Colonia 2:");
				GridBagConstraints gbc_verticeDueGiocatoreUnoLabel = new GridBagConstraints();
				gbc_verticeDueGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeDueGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreUnoLabel.gridx = 0;
				gbc_verticeDueGiocatoreUnoLabel.gridy = 2;
				panel.add(verticeDueGiocatoreUnoLabel, gbc_verticeDueGiocatoreUnoLabel);
			}
			{
				verticeDueGiocatoreUnoComboBox = new JComboBox(vertici);
				verticeDueGiocatoreUnoComboBox.addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeDueGiocatoreUnoComboBox, latoDueGiocatoreUnoComboBox);
					}
				});
				GridBagConstraints gbc_verticeDueGiocatoreUnoComboBox = new GridBagConstraints();
				gbc_verticeDueGiocatoreUnoComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreUnoComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeDueGiocatoreUnoComboBox.gridx = 1;
				gbc_verticeDueGiocatoreUnoComboBox.gridy = 2;
				panel.add(verticeDueGiocatoreUnoComboBox, gbc_verticeDueGiocatoreUnoComboBox);
			}
			{
				JLabel latoDueGiocatoreUnoLabel = new JLabel("Lato Strada 2:");
				GridBagConstraints gbc_latoDueGiocatoreUnoLabel = new GridBagConstraints();
				gbc_latoDueGiocatoreUnoLabel.anchor = GridBagConstraints.EAST;
				gbc_latoDueGiocatoreUnoLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoDueGiocatoreUnoLabel.gridx = 2;
				gbc_latoDueGiocatoreUnoLabel.gridy = 2;
				panel.add(latoDueGiocatoreUnoLabel, gbc_latoDueGiocatoreUnoLabel);
			}
			{
				latoDueGiocatoreUnoComboBox = new JComboBox();
				GridBagConstraints gbc_latoDueGiocatoreUnoComboBox = new GridBagConstraints();
				gbc_latoDueGiocatoreUnoComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoDueGiocatoreUnoComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoDueGiocatoreUnoComboBox.gridx = 3;
				gbc_latoDueGiocatoreUnoComboBox.gridy = 2;
				panel.add(latoDueGiocatoreUnoComboBox, gbc_latoDueGiocatoreUnoComboBox);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Giocatore Due", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel verticeUnoGiocatoreDueLabel = new JLabel("Vertice Colonia 1:");
				GridBagConstraints gbc_verticeUnoGiocatoreDueLabel = new GridBagConstraints();
				gbc_verticeUnoGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeUnoGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreDueLabel.gridx = 0;
				gbc_verticeUnoGiocatoreDueLabel.gridy = 1;
				panel.add(verticeUnoGiocatoreDueLabel, gbc_verticeUnoGiocatoreDueLabel);
			}
			{
				verticeUnoGiocatoreDueComboBox = new JComboBox(vertici);
				verticeUnoGiocatoreDueComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeUnoGiocatoreDueComboBox, latoUnoGiocatoreDueComboBox);
					}
				});
				GridBagConstraints gbc_verticeUnoGiocatoreDueComboBox = new GridBagConstraints();
				gbc_verticeUnoGiocatoreDueComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreDueComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeUnoGiocatoreDueComboBox.gridx = 1;
				gbc_verticeUnoGiocatoreDueComboBox.gridy = 1;
				panel.add(verticeUnoGiocatoreDueComboBox, gbc_verticeUnoGiocatoreDueComboBox);
			}
			{
				JLabel latoUnoGiocatoreDueLabel = new JLabel("Lato Strada 1:");
				GridBagConstraints gbc_latoUnoGiocatoreDueLabel = new GridBagConstraints();
				gbc_latoUnoGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_latoUnoGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoUnoGiocatoreDueLabel.gridx = 2;
				gbc_latoUnoGiocatoreDueLabel.gridy = 1;
				panel.add(latoUnoGiocatoreDueLabel, gbc_latoUnoGiocatoreDueLabel);
			}
			{
				latoUnoGiocatoreDueComboBox = new JComboBox();
				GridBagConstraints gbc_latoUnoGiocatoreDueComboBox = new GridBagConstraints();
				gbc_latoUnoGiocatoreDueComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoUnoGiocatoreDueComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoUnoGiocatoreDueComboBox.gridx = 3;
				gbc_latoUnoGiocatoreDueComboBox.gridy = 1;
				panel.add(latoUnoGiocatoreDueComboBox, gbc_latoUnoGiocatoreDueComboBox);
			}
			{
				JLabel verticeDueGiocatoreDueLabel = new JLabel("Vertice Colonia 2:");
				GridBagConstraints gbc_verticeDueGiocatoreDueLabel = new GridBagConstraints();
				gbc_verticeDueGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeDueGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreDueLabel.gridx = 0;
				gbc_verticeDueGiocatoreDueLabel.gridy = 2;
				panel.add(verticeDueGiocatoreDueLabel, gbc_verticeDueGiocatoreDueLabel);
			}
			{
				verticeDueGiocatoreDueComboBox = new JComboBox(vertici);
				verticeDueGiocatoreDueComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeDueGiocatoreDueComboBox, latoDueGiocatoreDueComboBox);
					}
				});
				GridBagConstraints gbc_verticeDueGiocatoreDueComboBox = new GridBagConstraints();
				gbc_verticeDueGiocatoreDueComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreDueComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeDueGiocatoreDueComboBox.gridx = 1;
				gbc_verticeDueGiocatoreDueComboBox.gridy = 2;
				panel.add(verticeDueGiocatoreDueComboBox, gbc_verticeDueGiocatoreDueComboBox);
			}
			{
				JLabel latoDueGiocatoreDueLabel = new JLabel("Lato Strada 2:");
				GridBagConstraints gbc_latoDueGiocatoreDueLabel = new GridBagConstraints();
				gbc_latoDueGiocatoreDueLabel.anchor = GridBagConstraints.EAST;
				gbc_latoDueGiocatoreDueLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoDueGiocatoreDueLabel.gridx = 2;
				gbc_latoDueGiocatoreDueLabel.gridy = 2;
				panel.add(latoDueGiocatoreDueLabel, gbc_latoDueGiocatoreDueLabel);
			}
			{
				latoDueGiocatoreDueComboBox = new JComboBox();
				GridBagConstraints gbc_latoDueGiocatoreDueComboBox = new GridBagConstraints();
				gbc_latoDueGiocatoreDueComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoDueGiocatoreDueComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoDueGiocatoreDueComboBox.gridx = 3;
				gbc_latoDueGiocatoreDueComboBox.gridy = 2;
				panel.add(latoDueGiocatoreDueComboBox, gbc_latoDueGiocatoreDueComboBox);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Giocatore Tre", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 2;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel verticeUnoGiocatoreTreLabel = new JLabel("Vertice Colonia 1:");
				GridBagConstraints gbc_verticeUnoGiocatoreTreLabel = new GridBagConstraints();
				gbc_verticeUnoGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeUnoGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreTreLabel.gridx = 0;
				gbc_verticeUnoGiocatoreTreLabel.gridy = 1;
				panel.add(verticeUnoGiocatoreTreLabel, gbc_verticeUnoGiocatoreTreLabel);
			}
			{
				verticeUnoGiocatoreTreComboBox = new JComboBox(vertici);
				verticeUnoGiocatoreTreComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeUnoGiocatoreTreComboBox, latoUnoGiocatoreTreComboBox);
					}
				});
				GridBagConstraints gbc_verticeUnoGiocatoreTreComboBox = new GridBagConstraints();
				gbc_verticeUnoGiocatoreTreComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreTreComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeUnoGiocatoreTreComboBox.gridx = 1;
				gbc_verticeUnoGiocatoreTreComboBox.gridy = 1;
				panel.add(verticeUnoGiocatoreTreComboBox, gbc_verticeUnoGiocatoreTreComboBox);
			}
			{
				JLabel latoUnoGiocatoreTreLabel = new JLabel("Lato Strada 1:");
				GridBagConstraints gbc_latoUnoGiocatoreTreLabel = new GridBagConstraints();
				gbc_latoUnoGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_latoUnoGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoUnoGiocatoreTreLabel.gridx = 2;
				gbc_latoUnoGiocatoreTreLabel.gridy = 1;
				panel.add(latoUnoGiocatoreTreLabel, gbc_latoUnoGiocatoreTreLabel);
			}
			{
				latoUnoGiocatoreTreComboBox = new JComboBox();
				GridBagConstraints gbc_latoUnoGiocatoreTreComboBox = new GridBagConstraints();
				gbc_latoUnoGiocatoreTreComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoUnoGiocatoreTreComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoUnoGiocatoreTreComboBox.gridx = 3;
				gbc_latoUnoGiocatoreTreComboBox.gridy = 1;
				panel.add(latoUnoGiocatoreTreComboBox, gbc_latoUnoGiocatoreTreComboBox);
			}
			{
				JLabel verticeDueGiocatoreTreLabel = new JLabel("Vertice Colonia 2:");
				GridBagConstraints gbc_verticeDueGiocatoreTreLabel = new GridBagConstraints();
				gbc_verticeDueGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeDueGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreTreLabel.gridx = 0;
				gbc_verticeDueGiocatoreTreLabel.gridy = 2;
				panel.add(verticeDueGiocatoreTreLabel, gbc_verticeDueGiocatoreTreLabel);
			}
			{
				verticeDueGiocatoreTreComboBox = new JComboBox(vertici);
				verticeDueGiocatoreTreComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeDueGiocatoreTreComboBox, latoDueGiocatoreTreComboBox);
					}
				});
				GridBagConstraints gbc_verticeDueGiocatoreTreComboBox = new GridBagConstraints();
				gbc_verticeDueGiocatoreTreComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreTreComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeDueGiocatoreTreComboBox.gridx = 1;
				gbc_verticeDueGiocatoreTreComboBox.gridy = 2;
				panel.add(verticeDueGiocatoreTreComboBox, gbc_verticeDueGiocatoreTreComboBox);
			}
			{
				JLabel latoDueGiocatoreTreLabel = new JLabel("Lato Strada 2:");
				GridBagConstraints gbc_latoDueGiocatoreTreLabel = new GridBagConstraints();
				gbc_latoDueGiocatoreTreLabel.anchor = GridBagConstraints.EAST;
				gbc_latoDueGiocatoreTreLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoDueGiocatoreTreLabel.gridx = 2;
				gbc_latoDueGiocatoreTreLabel.gridy = 2;
				panel.add(latoDueGiocatoreTreLabel, gbc_latoDueGiocatoreTreLabel);
			}
			{
				latoDueGiocatoreTreComboBox = new JComboBox();
				GridBagConstraints gbc_latoDueGiocatoreTreComboBox = new GridBagConstraints();
				gbc_latoDueGiocatoreTreComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoDueGiocatoreTreComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoDueGiocatoreTreComboBox.gridx = 3;
				gbc_latoDueGiocatoreTreComboBox.gridy = 2;
				panel.add(latoDueGiocatoreTreComboBox, gbc_latoDueGiocatoreTreComboBox);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Giocatore Quattro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 3;
			contentPanel.add(panel, gbc_panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel verticeUnoGiocatoreQuattroLabel = new JLabel("Vertice Colonia 1:");
				GridBagConstraints gbc_verticeUnoGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_verticeUnoGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeUnoGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreQuattroLabel.gridx = 0;
				gbc_verticeUnoGiocatoreQuattroLabel.gridy = 1;
				panel.add(verticeUnoGiocatoreQuattroLabel, gbc_verticeUnoGiocatoreQuattroLabel);
			}
			{
				verticeUnoGiocatoreQuattroComboBox = new JComboBox(vertici);
				verticeUnoGiocatoreQuattroComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeUnoGiocatoreQuattroComboBox, latoUnoGiocatoreQuattroComboBox);		
					}
				});
				GridBagConstraints gbc_verticeUnoGiocatoreQuattroComboBox = new GridBagConstraints();
				gbc_verticeUnoGiocatoreQuattroComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeUnoGiocatoreQuattroComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeUnoGiocatoreQuattroComboBox.gridx = 1;
				gbc_verticeUnoGiocatoreQuattroComboBox.gridy = 1;
				panel.add(verticeUnoGiocatoreQuattroComboBox, gbc_verticeUnoGiocatoreQuattroComboBox);
			}
			{
				JLabel latoUnoGiocatoreQuattroLabel = new JLabel("Lato Strada 1:");
				GridBagConstraints gbc_latoUnoGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_latoUnoGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_latoUnoGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoUnoGiocatoreQuattroLabel.gridx = 2;
				gbc_latoUnoGiocatoreQuattroLabel.gridy = 1;
				panel.add(latoUnoGiocatoreQuattroLabel, gbc_latoUnoGiocatoreQuattroLabel);
			}
			{
				latoUnoGiocatoreQuattroComboBox = new JComboBox();
				GridBagConstraints gbc_latoUnoGiocatoreQuattroComboBox = new GridBagConstraints();
				gbc_latoUnoGiocatoreQuattroComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoUnoGiocatoreQuattroComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoUnoGiocatoreQuattroComboBox.gridx = 3;
				gbc_latoUnoGiocatoreQuattroComboBox.gridy = 1;
				panel.add(latoUnoGiocatoreQuattroComboBox, gbc_latoUnoGiocatoreQuattroComboBox);
			}
			{
				JLabel verticeDueGiocatoreQuattroLabel = new JLabel("Vertice Colonia 2:");
				GridBagConstraints gbc_verticeDueGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_verticeDueGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_verticeDueGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreQuattroLabel.gridx = 0;
				gbc_verticeDueGiocatoreQuattroLabel.gridy = 2;
				panel.add(verticeDueGiocatoreQuattroLabel, gbc_verticeDueGiocatoreQuattroLabel);
			}
			{
				verticeDueGiocatoreQuattroComboBox = new JComboBox(vertici);
				verticeDueGiocatoreQuattroComboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) 
					{
						if(e.getStateChange() == e.SELECTED)
							setLatiComboBox(verticeDueGiocatoreQuattroComboBox, latoDueGiocatoreQuattroComboBox);		
					}
				});
				GridBagConstraints gbc_verticeDueGiocatoreQuattroComboBox = new GridBagConstraints();
				gbc_verticeDueGiocatoreQuattroComboBox.insets = new Insets(0, 0, 5, 5);
				gbc_verticeDueGiocatoreQuattroComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_verticeDueGiocatoreQuattroComboBox.gridx = 1;
				gbc_verticeDueGiocatoreQuattroComboBox.gridy = 2;
				panel.add(verticeDueGiocatoreQuattroComboBox, gbc_verticeDueGiocatoreQuattroComboBox);
			}
			{
				JLabel latoDueGiocatoreQuattroLabel = new JLabel("Lato Strada 2:");
				GridBagConstraints gbc_latoDueGiocatoreQuattroLabel = new GridBagConstraints();
				gbc_latoDueGiocatoreQuattroLabel.anchor = GridBagConstraints.EAST;
				gbc_latoDueGiocatoreQuattroLabel.insets = new Insets(0, 0, 5, 5);
				gbc_latoDueGiocatoreQuattroLabel.gridx = 2;
				gbc_latoDueGiocatoreQuattroLabel.gridy = 2;
				panel.add(latoDueGiocatoreQuattroLabel, gbc_latoDueGiocatoreQuattroLabel);
			}
			{
				latoDueGiocatoreQuattroComboBox = new JComboBox();
				GridBagConstraints gbc_latoDueGiocatoreQuattroComboBox = new GridBagConstraints();
				gbc_latoDueGiocatoreQuattroComboBox.insets = new Insets(0, 0, 5, 0);
				gbc_latoDueGiocatoreQuattroComboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_latoDueGiocatoreQuattroComboBox.gridx = 3;
				gbc_latoDueGiocatoreQuattroComboBox.gridy = 2;
				panel.add(latoDueGiocatoreQuattroComboBox, gbc_latoDueGiocatoreQuattroComboBox);
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
						aggiungiColonieStrade();
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
		this.idPartita = idPartita;
		this.controller = controller;
		this.idGiocatoreUno = idGiocatoreUno;
		this.idGiocatoreDue = idGiocatoreDue;
		this.idGiocatoreTre = idGiocatoreTre;
		this.idGiocatoreQuattro = idGiocatoreQuattro;
		setLatiComboBox(verticeUnoGiocatoreUnoComboBox, latoUnoGiocatoreUnoComboBox);
		setLatiComboBox(verticeDueGiocatoreUnoComboBox, latoDueGiocatoreUnoComboBox);
		setLatiComboBox(verticeUnoGiocatoreDueComboBox, latoUnoGiocatoreDueComboBox);
		setLatiComboBox(verticeDueGiocatoreDueComboBox, latoDueGiocatoreDueComboBox);
		setLatiComboBox(verticeUnoGiocatoreTreComboBox, latoUnoGiocatoreTreComboBox);
		setLatiComboBox(verticeDueGiocatoreTreComboBox, latoDueGiocatoreTreComboBox);
		setLatiComboBox(verticeUnoGiocatoreQuattroComboBox, latoUnoGiocatoreQuattroComboBox);		
		setLatiComboBox(verticeDueGiocatoreQuattroComboBox, latoDueGiocatoreQuattroComboBox);
	}

}
