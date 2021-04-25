package utility;

public class InfoGenerali 
{
	public InfoGenerali(int idTurno, int giro, int idGiocatore, int dadoUno, int dadoDue, int colonieCitta, int strade, int carte, int scambi, int punti, int posizione)
	{
		this.idTurno = idTurno;
		this.giro = giro;
		this.idGiocatore = idGiocatore;
		this.colonieCitta = colonieCitta;
		this.strade = strade;
		this.carte = carte;
		this.scambi = scambi;
		this.punti = punti;
		this.posizione = posizione;
		this.dadoUno = dadoUno;
		this.dadoDue = dadoDue;
	}
	
	public int getIdTurno() 
	{
		return idTurno;
	}
	
	public void setIdTurno(int idTurno) 
	{
		this.idTurno = idTurno;
	}
	
	public int getGiro() 
	{
		return giro;
	}
	
	public void setGiro(int giro) 
	{
		this.giro = giro;
	}

	public int getIdGiocatore() 
	{
		return idGiocatore;
	}
	
	public void setIdGiocatore(int idGiocatore) 
	{
		this.idGiocatore = idGiocatore;
	}

	public int getColonieCitta() 
	{
		return colonieCitta;
	}
	
	public void setColonieCitta(int colonieCitta) 
	{
		this.colonieCitta = colonieCitta;
	}

	public int getStrade() 
	{
		return strade;
	}
	
	public void setStrade(int strade) 
	{
		this.strade = strade;
	}

	public int getCarte() 
	{
		return carte;
	}
	
	public void setCarte(int carte) 
	{
		this.carte = carte;
	}

	public int getScambi() 
	{
		return scambi;
	}
	
	public void setScambi(int scambi) 
	{
		this.scambi = scambi;
	}

	public int getPunti() 
	{
		return punti;
	}
	
	public void setPunti(int punti) 
	{
		this.punti = punti;
	}

	public int getPosizione() 
	{
		return posizione;
	}

	public void setPosizione(int posizione) 
	{
		this.posizione = posizione;
	}

	public int getDadoUno() 
	{
		return dadoUno;
	}

	public void setDadoUno(int dadoUno) 
	{
		this.dadoUno = dadoUno;
	}

	public int getDadoDue() 
	{
		return dadoDue;
	}

	public void setDadoDue(int dadoDue) 
	{
		this.dadoDue = dadoDue;
	}

	private int idTurno;
	private int giro;
	private int idGiocatore;
	private int colonieCitta;
	private int strade;
	private int carte;
	private int scambi;
	private int posizione;
	private int punti;
	private int dadoUno;
	private int dadoDue;
}
