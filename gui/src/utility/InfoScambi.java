package utility;

public class InfoScambi 
{
	public InfoScambi(int idScambio, boolean scambioConGiocatore, boolean scambioConPorto, boolean scambioConGioco)
	{
		this.idScambio = idScambio;
		this.scambioConGiocatore = scambioConGiocatore;
		this.scambioConPorto = scambioConPorto;
		this.scambioConGioco = scambioConGioco;
	}
	
	public int getIdScambio() 
	{
		return idScambio;
	}
	
	public void setIdScambio(int idScambio) 
	{
		this.idScambio = idScambio;
	}
	
	public boolean isScambioConGiocatore() 
	{
		return scambioConGiocatore;
	}
	
	public void setScambioConGiocatore(boolean scambioConGiocatore) 
	{
		this.scambioConGiocatore = scambioConGiocatore;
	}

	public boolean isScambioConPorto() 
	{
		return scambioConPorto;
	}
	
	public void setScambioConPorto(boolean scambioConPorto) 
	{
		this.scambioConPorto = scambioConPorto;
	}

	public boolean isScambioConGioco() 
	{
		return scambioConGioco;
	}
	
	public void setScambioConGioco(boolean scambioConGioco) 
	{
		this.scambioConGioco = scambioConGioco;
	}

	private int idScambio;
	private boolean scambioConGiocatore;
	private boolean scambioConPorto;
	private boolean scambioConGioco;
}
