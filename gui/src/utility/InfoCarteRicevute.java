package utility;

public class InfoCarteRicevute 
{
	public InfoCarteRicevute(int id, int idCartaRisorsa, String tipo)
	{
		this.setId(id);
		this.setIdCartaRisorsa(idCartaRisorsa);
		this.setTipo(tipo);
	}
	
	public int getIdCartaRisorsa() 
	{
		return idCartaRisorsa;
	}
	
	public void setIdCartaRisorsa(int idCartaRisorsa) 
	{
		this.idCartaRisorsa = idCartaRisorsa;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getTipo() 
	{
		return tipo;
	}

	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}

	private int id;
	private int idCartaRisorsa;
	private String tipo;
}
