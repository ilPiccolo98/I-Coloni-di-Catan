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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;

public class SelezionaPartitaDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Controller controller;
	private JComboBox partiteComboBox;
	private boolean confermato = false;
	
	public boolean getConfermato()
	{
		return confermato;
	}
	
	public int getPartitaSelezionata()
	{
		Object id = partiteComboBox.getSelectedItem();
		if(id != null)
		{
			String idString = id.toString();
			return Integer.parseInt(idString);
		}
		return -1;
	}
	
	public Object[] generaPartite() throws SQLException
	{
		return InterazioneDatabase.getIdPartite(controller).toArray();
	}

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public SelezionaPartitaDialog(Controller controller){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Seleziona Partita");
		setBounds(100, 100, 378, 159);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		this.controller = controller;
		Object[] partite = null;
		try 
		{
			partite = generaPartite();
		} 
		catch (SQLException e1) 
		{
			JOptionPane.showMessageDialog(this, "Errore nel caricamento delle partite");
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						confermato = true;
						dispose();
					}
				});
				panel.add(okButton);
			}
			{
				JButton annullaButton = new JButton("Annulla");
				annullaButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						dispose();
					}
				});
				panel.add(annullaButton);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel idPartitaLabel = new JLabel("ID Partita:");
				panel.add(idPartitaLabel);
			}
			partiteComboBox = new JComboBox(partite);
			panel.add(partiteComboBox);
		}
	}

}
