package utility;

public class InfoEsagonoPorto 
{
	public InfoEsagonoPorto(int id, int posizione, int rapportoScambio, String specializzazione)
	{
		this.id = id;
		this.setPosizione(posizione);
		this.setRapportoScambio(rapportoScambio);
		this.setSpecializzazione(specializzazione);
	}
	
	public int getPosizione() 
	{
		return posizione;
	}
	
	public void setPosizione(int posizione) 
	{
		this.posizione = posizione;
	}

	public int getRapportoScambio() 
	{
		return rapportoScambio;
	}

	public void setRapportoScambio(int rapportoScambio) 
	{
		this.rapportoScambio = rapportoScambio;
	}

	public String getSpecializzazione() 
	{
		return specializzazione;
	}

	public void setSpecializzazione(String specializzazione) 
	{
		this.specializzazione = specializzazione;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	private int id;
	private int posizione;
	private int rapportoScambio;
	private String specializzazione;
}
