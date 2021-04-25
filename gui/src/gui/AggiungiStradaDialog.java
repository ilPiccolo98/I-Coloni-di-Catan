package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utility.Controller;
import utility.InfoCostruzioneStrada;
import utility.InfoStrada;
import utility.InterazioneDatabase;

import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AggiungiStradaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Controller controller;
	private int idTurno;
	private int idGiocatore;
	private ArrayList<InfoCostruzioneStrada> lati;
	private JComboBox latoStradaComboBox_1;
	
	private boolean checkEsisteLato(ArrayList<InfoCostruzioneStrada> info, int lato)
	{
		for(int i = 0; i != info.size(); ++i)
			if(info.get(i).getIdLato() == lato)
				return true;
		return false;
	}
	
	private ArrayList<InfoCostruzioneStrada> getLatiAdiacentiCittaColonie() throws SQLException
	{
		ArrayList<InfoCostruzioneStrada> posizioni = new ArrayList<InfoCostruzioneStrada>();
		ArrayList<Integer> vertici = InterazioneDatabase.getIdsVerticiCittaColonie(controller, idTurno);
		for(int i = 0; i != vertici.size(); ++i)
		{
			ArrayList<Integer> lati = InterazioneDatabase.getLatiAdiacenti(controller, vertici.get(i));
			for(int y = 0; y != lati.size(); ++y)
			{
				int strada = InterazioneDatabase.getIdStrada(controller, lati.get(y));
				if(strada == 0)
				{
					int idLato = lati.get(y);
					int idCittaColonia = InterazioneDatabase.getIdCittaColonia(controller, vertici.get(i));
					int idStrada = 0;
					InfoCostruzioneStrada a = new InfoCostruzioneStrada(idLato, idCittaColonia, idStrada);
					if(!checkEsisteLato(posizioni, idLato))
						posizioni.add(a);
				}
			}
		}
		return posizioni;
	}
	
	private void getLatiLiberi(int idStrada, int idVertice, ArrayList<InfoCostruzioneStrada> info, ArrayList<InfoCostruzioneStrada> latiEsistenti) throws SQLException
	{
		int idLato = InterazioneDatabase.getIdLato(controller, idStrada);
		ArrayList<Integer> verticiAdiacenti = InterazioneDatabase.getVerticiAdiacenti(controller, idLato);
		int nuovoVertice = 0;
		for(int i = 0; i != verticiAdiacenti.size(); ++i)
		{
			if(verticiAdiacenti.get(i) != idVertice && !InterazioneDatabase.checkEsisteCittaColonia(controller, idTurno, verticiAdiacenti.get(i)))
			{
				nuovoVertice = verticiAdiacenti.get(i);
				ArrayList<Integer> latiAdiacenti = InterazioneDatabase.getLatiAdiacenti(controller, verticiAdiacenti.get(i));
				for(int y = 0; y != latiAdiacenti.size(); ++y)
				{
					int strada = InterazioneDatabase.getIdStrada(controller, latiAdiacenti.get(y));
					if(strada == 0)
					{
						int lato = latiAdiacenti.get(y);
						InfoCostruzioneStrada a = new InfoCostruzioneStrada(lato, 0, idStrada);
						if(!checkEsisteLato(latiEsistenti, lato))
							info.add(a);
					}
				}
			}
		}
		ArrayList<Integer> stradeSuccessive = InterazioneDatabase.getStradeSuccessive(controller, idStrada);
		for(int i = 0; i != stradeSuccessive.size(); ++i)
			getLatiLiberi(stradeSuccessive.get(i), nuovoVertice, info, latiEsistenti);
	}
	
	private ArrayList<InfoCostruzioneStrada> getLatiAdiacentiStrade(ArrayList<InfoCostruzioneStrada> latiEsistenti) throws SQLException
	{
		ArrayList<InfoCostruzioneStrada> info = new ArrayList<InfoCostruzioneStrada>();
		ArrayList<Integer> strade = InterazioneDatabase.getStradeConCittaColoniePrec(controller, idTurno);
		for(int i = 0; i != strade.size(); ++i)
		{
			int cittaColonia = InterazioneDatabase.getCittaColoniaPrec(controller, strade.get(i));
			int idVertice = InterazioneDatabase.getIdVertice(controller, cittaColonia);
			getLatiLiberi(strade.get(i), idVertice, info, latiEsistenti);
		}
		return info;
	}
	
	private ArrayList<InfoCostruzioneStrada> getLati() throws SQLException
	{
		ArrayList<InfoCostruzioneStrada> lati = getLatiAdiacentiCittaColonie();
		lati.addAll(getLatiAdiacentiStrade(lati));
		return lati;
	}
	
	private ArrayList<Integer> getPosizioni() throws SQLException
	{
		ArrayList<Integer> posizioni = new ArrayList<Integer>();
		for(int i = 0; i != lati.size(); ++i)
			posizioni.add(InterazioneDatabase.getPosizioneLato(controller, lati.get(i).getIdLato()));
		return posizioni;
	}
	
	private boolean checkRisorse() throws SQLException
	{
		int argilla = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Argilla");
		int legno = InterazioneDatabase.countCartaRisorsa(controller, idGiocatore, idTurno, "Legno");
		if(argilla == 0 || legno == 0)
			return false;
		return true;
	}
	
	private void rimuoviRisorse() throws SQLException
	{
		final int argilla = 1;
		final int legno = 1;
		ArrayList<Integer> carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Argilla");
		for(int i = 0; i != argilla; ++i)
			InterazioneDatabase.rimuoviCartaRisorsa(controller, carte.get(i), idTurno);
		carte = InterazioneDatabase.getIdsCarteRisorsa(controller, idGiocatore, idTurno, "Legno");
		for(int i = 0; i != legno; ++i)
			InterazioneDatabase.rimuoviCartaRisorsa(controller, carte.get(i), idTurno);
	}
	
	private void aggiungiStrada() throws SQLException
	{
		if(!checkRisorse())
			JOptionPane.showMessageDialog(this, "Errore la quantità di risorse è errata");
		else
		{
			rimuoviRisorse();
			int index = latoStradaComboBox_1.getSelectedIndex();
			InfoCostruzioneStrada info = lati.get(index);
			InterazioneDatabase.aggiungiStrada(controller, info.getIdStrada(), info.getIdCittaColonia(), idTurno, info.getIdLato());
			JOptionPane.showMessageDialog(this, "Strada aggiunta con successo");
			dispose();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	
	public AggiungiStradaDialog(Controller controller, int idTurno, int idGiocatore) {
		this.controller = controller;
		this.idTurno = idTurno;
		this.idGiocatore = idGiocatore;
		try 
		{
			lati = getLati();
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento dei lati");
			dispose();
		}
		setResizable(false);
		setTitle("Aggiungi Strada");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 224, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		{
			JLabel latoStradaLabel = new JLabel("Lato Strada:");
			contentPanel.add(latoStradaLabel);
		}
		{
			JComboBox latoStradaComboBox = null;
			try 
			{
				latoStradaComboBox_1 = new JComboBox(getPosizioni().toArray());
			} 
			catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(this, "Errore nel caricamento delle posizioni");
				dispose();
			}
			contentPanel.add(latoStradaComboBox_1);
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
						try 
						{
							aggiungiStrada();
						} 
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(AggiungiStradaDialog.this, "Errore nell'inserimento della strada");
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
	}

}
