package utility;

public class InfoStrada 
{
	public InfoStrada(int idStrada, int idLato)
	{
		this.setIdStrada(idStrada);
		this.setIdLato(idLato);
	}
	
	public int getIdStrada() 
	{
		return idStrada;
	}
	
	public void setIdStrada(int idStrada) 
	{
		this.idStrada = idStrada;
	}

	public int getIdLato() 
	{
		return idLato;
	}

	public void setIdLato(int idLato) 
	{
		this.idLato = idLato;
	}

	private int idStrada;
	private int idLato;
}
