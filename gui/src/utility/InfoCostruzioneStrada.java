package utility;

public class InfoCostruzioneStrada 
{
	public InfoCostruzioneStrada(int idLato, int idCittaColonia, int idStrada)
	{
		this.setIdLato(idLato);
		this.setIdCittaColonia(idCittaColonia);
		this.setIdStrada(idStrada);
	}
	
	public int getIdLato() 
	{
		return idLato;
	}
	
	public void setIdLato(int idLato) 
	{
		this.idLato = idLato;
	}

	public int getIdCittaColonia() 
	{
		return idCittaColonia;
	}

	public void setIdCittaColonia(int idCittaColonia) 
	{
		this.idCittaColonia = idCittaColonia;
	}

	public int getIdStrada() 
	{
		return idStrada;
	}

	public void setIdStrada(int idStrada) 
	{
		this.idStrada = idStrada;
	}

	private int idLato;
	private int idCittaColonia;
	private int idStrada;
}
