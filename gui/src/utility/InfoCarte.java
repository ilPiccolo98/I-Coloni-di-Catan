package utility;

public class InfoCarte 
{
	public InfoCarte(int idCarta, boolean cartaRisorsa, boolean cartaProgresso, boolean cartaCavaliere, boolean cartaPuntoVittoria, int turnoDiAcquisizione)
	{
		this.idCarta = idCarta;
		this.cartaRisorsa = cartaRisorsa;
		this.cartaProgresso = cartaProgresso;
		this.cartaCavaliere = cartaCavaliere;
		this.turnoDiAcquisizione = turnoDiAcquisizione;
		this.cartaPuntoVittoria = cartaPuntoVittoria;
	}
	
	public int getIdCarta() 
	{
		return idCarta;
	}
	
	public void setIdCarta(int idCarta) 
	{
		this.idCarta = idCarta;
	}
	
	public boolean isCartaRisorsa() 
	{
		return cartaRisorsa;
	}

	public void setCartaRisorsa(boolean cartaRisorsa) 
	{
		this.cartaRisorsa = cartaRisorsa;
	}

	public boolean isCartaProgresso() 
	{
		return cartaProgresso;
	}

	public void setCartaProgresso(boolean cartaSviluppo) 
	{
		this.cartaProgresso = cartaSviluppo;
	}

	public boolean isCartaCavaliere() 
	{
		return cartaCavaliere;
	}

	public void setCartaCavaliere(boolean cartaCavaliere) 
	{
		this.cartaCavaliere = cartaCavaliere;
	}

	public int getTurnoDiAcquisizione() 
	{
		return turnoDiAcquisizione;
	}

	public void setTurnoDiAcquisizione(int turnoDiAcquisizione) 
	{
		this.turnoDiAcquisizione = turnoDiAcquisizione;
	}

	public boolean isCartaPuntoVittoria() 
	{
		return cartaPuntoVittoria;
	}

	public void setCartaPuntoVittoria(boolean cartaPuntoVittoria) 
	{
		this.cartaPuntoVittoria = cartaPuntoVittoria;
	}

	private int idCarta;
	private boolean cartaRisorsa;
	private boolean cartaProgresso;
	private boolean cartaCavaliere;
	private boolean cartaPuntoVittoria;
	private int turnoDiAcquisizione;
}
