package utility;

public class InfoCittaColonie 
{
	public InfoCittaColonie(int id, boolean citta, boolean colonia, int posizione, int turnoDiCostruzione)
	{
		this.id = id;
		this.citta = citta;
		this.colonia = colonia;
		this.posizione = posizione;
		this.turnoDiCostruzione = turnoDiCostruzione;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public boolean isCitta() 
	{
		return citta;
	}
	
	public void setCitta(boolean citta) 
	{
		this.citta = citta;
	}

	public boolean isColonia() 
	{
		return colonia;
	}
	
	public void setColonia(boolean colonia) 
	{
		this.colonia = colonia;
	}

	public int getPosizione() 
	{
		return posizione;
	}
	
	public void setPosizione(int posizione) 
	{
		this.posizione = posizione;
	}

	public int getTurnoDiCostruzione() 
	{
		return turnoDiCostruzione;
	}

	public void setTurnoDiCostruzione(int turnoDiCostruzione) 
	{
		this.turnoDiCostruzione = turnoDiCostruzione;
	}

	private int id;
	private boolean citta;
	private boolean colonia;
	private int posizione;
	private int turnoDiCostruzione;
}
