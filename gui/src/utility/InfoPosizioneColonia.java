package utility;

public class InfoPosizioneColonia 
{
	public InfoPosizioneColonia(int idStrada, int idVertice)
	{
		this.setIdStrada(idStrada);
		this.setIdVertice(idVertice);
	}
	
	public int getIdStrada() 
	{
		return idStrada;
	}
	
	public void setIdStrada(int idStrada) 
	{
		this.idStrada = idStrada;
	}

	public int getIdVertice() 
	{
		return idVertice;
	}

	public void setIdVertice(int idVertice) 
	{
		this.idVertice = idVertice;
	}

	private int idStrada;
	private int idVertice;
}
