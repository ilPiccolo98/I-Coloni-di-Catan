package utility;

public class InfoGeneraliStrada 
{
	public InfoGeneraliStrada(int id, int posizione, int turnoDiCostruzione)
	{
		this.setId(id);
		this.setPosizione(posizione);
		this.setTurnoDiCostruzione(turnoDiCostruzione);
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
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
	private int posizione;
	private int turnoDiCostruzione;
}
