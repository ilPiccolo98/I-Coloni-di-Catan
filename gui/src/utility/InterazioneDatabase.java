package utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InterazioneDatabase 
{
	
	
	public static ArrayList<String> getIdPartite(Controller controller) throws SQLException
	{
		String query = "select idPartita from Partite";
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<String> idPartite = new ArrayList<String>();
		while(rs.next())
			idPartite.add(rs.getString("idPartita"));
		return idPartite;
	}
	
	public static String getDataPartita(Controller controller, int idPartita) throws SQLException
	{
		String query = String.format("select data from Partite where idPartita = %d", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		String data = null;
		while(rs.next())
			data = rs.getString("data");
		return data.substring(0, 10);
	}
	
	public static int getDurataPartita(Controller controller, int idPartita) throws SQLException
	{
		String query = String.format("select durata from Partite where idPartita = %d", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		int durata = 0;
		while(rs.next())
			durata = Integer.parseInt(rs.getString("durata"));
		return durata;
	}
	
	public static ArrayList<String> getIdGiocatori(Controller controller) throws SQLException
	{
		String query = "select idGiocatore from Giocatori";
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<String> id = new ArrayList<String>();
		while(rs.next())
			id.add(rs.getString("idGiocatore"));
		return id;
	}
 	
	public static int nuovoIdPartita(Controller controller) throws SQLException
	{
		String query = "select incrementoIdPartita.nextVal as idPartita from dual";
		ResultSet rs = controller.eseguiQuery(query);
		String id = "";
		while(rs.next())
			id = rs.getString("idPartita");
		return Integer.parseInt(id);
	}
	
	public static int nuovoIdGiocatorePartita(Controller controller) throws SQLException
	{
		String query = "select incrementoIdGiocatorePartita.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		String id = "";
		while(rs.next())
			id = rs.getString("id");
		return Integer.parseInt(id);
	}
	
	public static void aggiungiPartita(Controller controller, int id, int durata, String data) throws SQLException
	{
		String update = String.format("insert into Partite values(%d, to_date('%s', 'dd/mm/yyyy'), %d)", id, data, durata);
		controller.eseguiUpdate(update);
	}
	
	public static int nuovoIdGiocatore(Controller controller) throws SQLException
	{
		String query = "select incrementoIdGiocatore.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		String id = "";
		while(rs.next())
			id = rs.getString("id");
		return Integer.parseInt(id);
	}
	
	public static void aggiungiGiocatore(Controller controller, int id, int eta) throws SQLException
	{
		String update = String.format("insert into Giocatori values(%d, %d)", id, eta);
		controller.eseguiUpdate(update);
	}
	
	public static boolean checkEsisteGiocatore(Controller controller, int id) throws SQLException
	{
		String query = "select count(*) as counter from Giocatori";
		ResultSet rs = controller.eseguiQuery(query);
		int i = 0;
		while(rs.next())
			i = Integer.parseInt(rs.getString("counter"));
		if(i == 0)
			return false;
		return true;
	}
	
	public static void aggiungiGiocatoriPartite(Controller controller, int idPartita,
												int idGiocatoreUno, String nicknameUno, String coloreUno,
												int idGiocatoreDue, String nicknameDue, String coloreDue,
												int idGiocatoreTre, String nicknameTre, String coloreTre,
												int idGiocatoreQuattro, String nicknameQuattro, String coloreQuattro) throws SQLException
	{
		int idGiocatorePartitaUno = nuovoIdGiocatorePartita(controller);
		String giocatoreUno = String.format("into GiocatoriPartite values(%d, %d, %d, '%s', '%s')", idGiocatorePartitaUno, idGiocatoreUno, idPartita, nicknameUno, coloreUno);
		int idGiocatorePartitaDue = nuovoIdGiocatorePartita(controller);
		String giocatoreDue = String.format("into GiocatoriPartite values(%d, %d, %d, '%s', '%s')", idGiocatorePartitaDue, idGiocatoreDue, idPartita, nicknameDue, coloreDue);
		int idGiocatorePartitaTre = nuovoIdGiocatorePartita(controller);
		String giocatoreTre = String.format("into GiocatoriPartite values(%d, %d, %d, '%s', '%s')", idGiocatorePartitaTre, idGiocatoreTre, idPartita, nicknameTre, coloreTre);
		int idGiocatorePartitaQuattro = nuovoIdGiocatorePartita(controller);
		String giocatoreQuattro = String.format("into GiocatoriPartite values(%d, %d, %d, '%s', '%s')", idGiocatorePartitaQuattro, idGiocatoreQuattro, idPartita, nicknameQuattro, coloreQuattro);
		String update = String.format("insert all %s %s %s %s select * from dual", giocatoreUno, giocatoreDue, giocatoreTre, giocatoreQuattro);
		controller.eseguiUpdate(update);
	}
	
	public static String fondiData(String giorno, String mese, String anno)
	{
		if(giorno.length() == 1)
			giorno = "0" + giorno;
		if(mese.length() == 1)
			mese = "0" + mese;
		return String.format("%s/%s/%s", giorno, mese, anno);
	}
	
	public static void rimuoviGiocatore(Controller controller, int id) throws SQLException
	{
		String update = String.format("delete from Giocatori where idGiocatore = %d", id);
		controller.eseguiUpdate(update);
	}
	
	public static void rimuoviPartita(Controller controller, int id) throws SQLException
	{
		String update = String.format("delete from Partite where idPartita = %d", id);
		controller.eseguiUpdate(update);
	}
	
	public static int getIdVertice(Controller controller, int idPartita, int posizione) throws SQLException
	{
		String query = String.format("select V.idVertice from Partite P, Esagoni E, VerticiEsagoni VE, Vertici V where P.idPartita = E.idPartita and E.idEsagono = VE.idEsagono and VE.idVertice = V.idVertice and P.idPartita = %d and V.posizione = %d", idPartita, posizione);
		ResultSet rs = controller.eseguiQuery(query);
		int idVertice = 0;
		while(rs.next())
			idVertice = Integer.parseInt(rs.getString("idVertice"));
		return idVertice;
	}
	
	public static int getIdLato(Controller controller, int idPartita, int posizione) throws SQLException
	{
		String query = String.format("select L.idLato from Partite P, Esagoni E, LatiEsagoni LE, Lati L where P.idPartita = E.idPartita and E.idEsagono = LE.idEsagono and LE.idLato = L.idLato and P.idPartita = %d and L.posizione = %d", idPartita, posizione);
		ResultSet rs = controller.eseguiQuery(query);
		int idLato = 0;
		while(rs.next())
			idLato = Integer.parseInt(rs.getString("idLato"));
		return idLato;
	}
	
	public static int nuovoIdCittaColonia(Controller controller) throws SQLException
	{
		String query = "select incrementoIdCittaColonia.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static int nuovoIdStrada(Controller controller) throws SQLException
	{
		String query = "select incrementoIdStrada.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static ArrayList<Integer> getLatiAssociati(Controller controller, int idVertice) throws SQLException
	{
		ArrayList<Integer> idLati = new ArrayList<Integer>();
		String query = String.format("select VL.idLato from VerticiLati VL where VL.idVertice = %d", idVertice);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			idLati.add(Integer.parseInt(rs.getString("idLato")));
		return idLati;
	}
	
	public static int getPosizioneLato(Controller controller, int idLato) throws SQLException
	{
		String query = String.format("select L.posizione from Lati L where L.idLato = %d", idLato);
		ResultSet rs = controller.eseguiQuery(query);
		int posizione = 0;
		while(rs.next())
			posizione = Integer.parseInt(rs.getString("posizione"));
		return posizione;
	}
	
	public static void aggiungiCittaColonia(Controller controller, int idCittaColonia, int idVertice) throws SQLException
	{
		String update = String.format("insert into CittaColonie values(%d, %d, %s)", idCittaColonia, idVertice, "null");
		controller.eseguiUpdate(update);
	}
	
	public static void rimuoviCittaColonia(Controller controller, int idCittaColonia) throws SQLException
	{
		String update = String.format("delete from CittaColonie CC where CC.idCittaColonia = %d", idCittaColonia);
		controller.eseguiUpdate(update);
	}
	
	public static ArrayList<Integer> getIdGiocatori(Controller controller, int idPartita) throws SQLException
	{
		String query = String.format("select GP.idGiocatore from GiocatoriPartite GP where GP.idPartita = %d", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<Integer> idGiocatori = new ArrayList<Integer>();
		while(rs.next())
			idGiocatori.add(Integer.parseInt(rs.getString("idGiocatore")));
		return idGiocatori;
	}
	
	public static int getMaxGiro(Controller controller, int idPartita, int idGiocatore) throws SQLException
	{
		String query = String.format("select max(T.Giro) as max from Turni T where T.idPartita = %d and T.idGiocatore = %d", idPartita, idGiocatore);
		ResultSet rs = controller.eseguiQuery(query);
		String giroMax = null;
		while(rs.next())
			giroMax = rs.getString("max");
		if(giroMax != null)
			return Integer.parseInt(giroMax);
		return 0;
	}
	
	public static int nuovoIdTurno(Controller controller) throws SQLException
	{
		String query = "select incrementoIdTurno.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static void aggiungiTurno(Controller controller, int idTurno, int giro, int idGiocatore, int idPartita) throws SQLException
	{
		String update = String.format("insert into Turni values(%d, %d, %d, %d)", idTurno, giro, idGiocatore, idPartita);
		controller.eseguiUpdate(update);
	}
	
	public static void aggiungiLancioDadi(Controller controller, int idLancioDadi, int dadoUno, int dadoDue, int idTurno) throws SQLException
	{
		String update = String.format("insert into LanciDadi values(%d, %d, %d, %d)", idLancioDadi, dadoUno, dadoDue, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static int getPosizioneBrigante(Controller controller, int idPartita) throws SQLException
	{
		String query = String.format("select E.posizione from Turni T, Brigante B, Esagoni E where T.idTurno = B.idTurno and B.idEsagono = E.idEsagono and T.idPartita = %d and B.idBrigante = (select max(B1.idBrigante) from Turni T1, Brigante B1 where T1.idTurno = B1.idTurno and T1.idPartita = %d)", idPartita, idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		String posizione = null;
		while(rs.next())
			posizione = rs.getString("posizione");
		if(posizione == null)
			return 0;
		return Integer.parseInt(posizione);
	}
	
	public static int getIdEsagono(Controller controller, int idPartita, int posizione) throws SQLException
	{
		String query = String.format("select E.idEsagono from Esagoni E where E.idPartita = %d and E.posizione = %d", idPartita, posizione);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("idEsagono"));
		return id;
	}
	
	public static int nuovoIdBrigante(Controller controller) throws NumberFormatException, SQLException
	{
		String query = "select incrementoIdBrigante.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static void aggiungiBrigante(Controller controller, int idBrigante, int idEsagono, int idTurno) throws SQLException
	{
		String update = String.format("insert into Brigante values(%d, %d, %d)", idBrigante, idEsagono, idTurno);
		controller.eseguiQuery(update);
	}
	
	public static int nuovoIdLancioDadi(Controller controller) throws SQLException
	{
		String query = "select incrementoIdLancioDadi.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static int getIdEsagonoBrigante(Controller controller, int idPartita) throws NumberFormatException, SQLException
	{
		String query = String.format("select B.idEsagono from Brigante B where B.idBrigante = (select max(B1.idBrigante) from Esagoni E1, Brigante B1 where E1.idPartita = %d and E1.idEsagono = B1.idEsagono)", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		String id = null;
		while(rs.next())
			id = rs.getString("idEsagono");
		if(id == null)
			return getIdEsagono(controller, idPartita, 0);
		return Integer.parseInt(id);
	}
	
	public static int nuovoIdTurnoCittaColonia(Controller controller) throws SQLException
	{
		String query = "select incrementoIdTurnoCittaColonia.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static int nuovoIdStradaTurno(Controller controller) throws SQLException
	{
		String query = "select incrementoIdStradaTurno.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static void aggiungiTurnoCittaColonia(Controller controller, int idTurnoCittaColonia, int idCittaColonia, int idTurno) throws SQLException
	{
		String update = String.format("insert into TurniCittaColonie values(%d, %d, %d)", idTurnoCittaColonia, idCittaColonia, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static void aggiungiStradaTurno(Controller controller, int idStradaTurno, int idStrada, int idTurno) throws SQLException
	{
		String update = String.format("insert into StradeTurni values(%d, %d, %d)", idStradaTurno, idStrada, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static int contaCitta(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(CC.aggiornataDalTurno) as count from TurniCittaColonie TCC, CittaColonie CC where TCC.idCittaColonia = CC.idCittaColonia and TCC.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	public static int contaColonie(Controller controller, int idTurno) throws SQLException
	{
		int citta = contaCitta(controller, idTurno);
		String query = String.format("select count(*) - %d as count from TurniCittaColonie TCC, CittaColonie CC where TCC.idCittaColonia = CC.idCittaColonia and TCC.idTurno = %d", citta, idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	public static int countCartePuntoVittoria(Controller controller, int idGiocatore, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from CarteTurni CT, Carte C, CarteSviluppo CS, CartePuntoVittoria CPV where CT.idCarta = C.idCarta and C.idCartaSviluppo = CS.idCartaSviluppo and CS.idCartaPuntoVittoria = CPV.idCartaPuntoVittoria and C.idGiocatore = %d and CT.idTurno = %d", idGiocatore, idTurno); 
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	public static boolean isCartaRisorsa(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select count(*) as count from Carte C, CarteRisorsa CR where C.idCartaRisorsa = CR.idCartaRisorsa and C.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean isCartaProgresso(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select count(*) as count from Carte C, CarteSviluppo CS, CarteProgresso CP where C.idCartaSviluppo = CS.idCartaSviluppo and CS.idCartaProgresso = CP.idCartaProgresso and C.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean isCartaPuntoVittoria(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select count(*) as count from Carte C, CarteSviluppo CS, CartePuntoVittoria CPV where C.idCartaSviluppo = CS.idCartaSviluppo and CS.idCartaPuntoVittoria = CPV.idCartaPuntoVittoria and C.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean isCartaCavaliere(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select count(*) as count from Carte C, CarteSviluppo CS, CarteCavaliere CC where C.idCartaSviluppo = CS.idCartaSviluppo and CC.idCartaCavaliere = CC.idCartaCavaliere and C.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static int getTurnoDiAcquisizione(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select min(CT.idTurno) as min from Turni T, CarteTurni CT, Carte C where T.idTurno = CT.idTurno and T.giro <> 0 and C.idCarta = CT.idCarta and C.idGiocatore = T.idGiocatore and CT.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int id = -1;
		while(rs.next())
			id = rs.getInt("min");
		return id;
	}
	
	public static ArrayList<InfoCarte> getInfoCarte(Controller controller, int idGiocatore, int idTurno) throws SQLException
	{
		String query = String.format("select C.idCarta from Carte C, CarteTurni CT where C.idCarta = CT.idCarta and C.idGiocatore = %d and CT.idTurno = %d order by C.idCarta", idGiocatore, idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<InfoCarte> info = new ArrayList<InfoCarte>();
		while(rs.next())
		{
			int id = rs.getInt("idCarta");
			InfoCarte i = new InfoCarte(id, false, false, false, false, 0);
			info.add(i);
		}
		for(int i = 0; i != info.size(); ++i)
		{
			boolean cartaRisorsa = isCartaRisorsa(controller, info.get(i).getIdCarta());
			boolean cartaProgresso = isCartaProgresso(controller, info.get(i).getIdCarta());
			boolean cartaCavaliere = isCartaCavaliere(controller, info.get(i).getIdCarta());
			boolean cartaPuntoVittoria = isCartaPuntoVittoria(controller, info.get(i).getIdCarta());
			int turno = getTurnoDiAcquisizione(controller, info.get(i).getIdCarta());
			info.get(i).setCartaRisorsa(cartaRisorsa);
			info.get(i).setCartaProgresso(cartaProgresso);
			info.get(i).setCartaCavaliere(cartaCavaliere);
			info.get(i).setCartaPuntoVittoria(cartaPuntoVittoria);
			info.get(i).setTurnoDiAcquisizione(turno);
		}
		return info;
	}
	
	private static ArrayList<InfoGenerali> getInfoTurni(Controller controller, int idPartita) throws SQLException
	{
		ArrayList<InfoGenerali> info = new ArrayList<InfoGenerali>();
		String query = String.format("select T.idTurno, T.giro, T.idGiocatore from Turni T where T.idPartita = %d and T.giro <> 0 order by T.idTurno", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
		{
			int idTurno = rs.getInt("idTurno");
			int giro = rs.getInt("giro");
			int idGiocatore = rs.getInt("idGiocatore");
			InfoGenerali i = new InfoGenerali(idTurno, giro, idGiocatore, 0, 0, 0, 0, 0, 0, 0, 0);
			info.add(i);
		}
		return info;
		
	}
	
	private static int getPosizioneBriganteDaTurno(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select E.posizione from Brigante B, Esagoni E where B.idEsagono = E.idEsagono and B.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int posizione = -1;
		while(rs.next())
			posizione = rs.getInt("posizione");
		return posizione;
	}
	
	private static int getDadoUno(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select LD.dadoUno from LanciDadi LD where LD.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int dadoUno = 0;
		while(rs.next())
			dadoUno = rs.getInt("dadoUno");
		return dadoUno;
	}
	
	private static int getDadoDue(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select LD.dadoDue from LanciDadi LD where LD.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int dadoDue = 0;
		while(rs.next())
			dadoDue = rs.getInt("dadoDue");
		return dadoDue;
	}
	
	private static int getCountCittaColonie(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from TurniCittaColonie TCC where TCC.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static int getCountStrade(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from StradeTurni ST where ST.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static int getCountCarte(Controller controller, int idTurno, int idGiocatore) throws SQLException
	{
		String query = String.format("select count(*) as count from CarteTurni CT, Carte C where CT.idCarta = C.idCarta and CT.idTurno = %d and C.idGiocatore = %d", idTurno, idGiocatore); 
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static int getCountScambi(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from Scambi S where S.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static int getCountColonie(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from TurniCittaColonie TCC, CittaColonie CC where TCC.idCittaColonia = CC.idCittaColonia and TCC.idTurno = %d and CC.aggiornataDalTurno is null", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static int getCountCitta(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from TurniCittaColonie TCC, CittaColonie CC where TCC.idCittaColonia = CC.idCittaColonia and TCC.idTurno = %d and CC.aggiornataDalTurno is not null", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static int getCountCartePuntoVittoria(Controller controller, int idTurno, int idGiocatore) throws SQLException
	{
		String query = String.format("select count(*) as count from CarteTurni CT, Carte C, CarteSviluppo CS, CartePuntoVittoria CPV where CT.idCarta = C.idCarta and C.idCartaSviluppo = CS.idCartaSviluppo and CS.idCartaPuntoVittoria = CPV.idCartaPuntoVittoria and CT.idTurno = %d and C.idGiocatore = %d", idTurno, idGiocatore);
		ResultSet rs = controller.eseguiQuery(query);
		int count = -1;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	private static boolean haCartaCavaliere(Controller controller, int idTurno, int idGiocatore) throws SQLException
	{
		String query = String.format("select C.idGiocatore from CarteTurni CT, Carte C, CarteSviluppo CS, CarteCavaliere CC where CT.idCarta = C.idCarta and C.idCartaSviluppo = CS.idCartaSviluppo and CS.idCartacavaliere = CC.idCartaCavaliere and CT.idTurno = %d group by C.idGiocatore having count(CC.idCartaCavaliere) >= 3 and count(CC.idCartaCavaliere) = (select max(TMP1.count) as max from (select C.idGiocatore, count(*) as count from CarteTurni CT, Carte C, CarteSviluppo CS, CarteCavaliere CC where CT.idCarta = C.idCarta and C.idCartaSviluppo = CS.idCartaSviluppo and CS.idCartacavaliere = CC.idCartaCavaliere and CT.idTurno = %d group by C.idGiocatore) TMP1)", idTurno, idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		int id = -1;
		while(rs.next())
		{
			id = rs.getInt("idGiocatore");
			++count;
		}
		if(count == 1 && id == idGiocatore)
			return true;
		return false;
	}
	
	private static int getPunti(Controller controller, int idTurno, int idGiocatore, int idPartita, int giro) throws SQLException
	{
		int punti = 0;
		punti = getCountColonie(controller, idTurno);
		punti += getCountCitta(controller, idTurno) * 2;
		punti += getCountCartePuntoVittoria(controller, idTurno, idGiocatore);
		if(haCartaCavaliere(controller, idTurno, idGiocatore))
			punti += 2;
		if(haStradaPiuLunga(controller, idGiocatore, idPartita, giro))
			punti += 2;
		return punti;
	}
	
	public static ArrayList<InfoGenerali> getInfoGenerali(Controller controller, int idPartita) throws SQLException
	{
		ArrayList<InfoGenerali> info = getInfoTurni(controller, idPartita);
		for(int i = 0; i != info.size(); ++i)
		{
			int idTurno = info.get(i).getIdTurno();
			int posizione = getPosizioneBriganteDaTurno(controller, idTurno);
			info.get(i).setPosizione(posizione);
			int dadoUno = getDadoUno(controller, idTurno);
			int dadoDue = getDadoDue(controller, idTurno);
			info.get(i).setDadoUno(dadoUno);
			info.get(i).setDadoDue(dadoDue);
			int cittaColonie = getCountCittaColonie(controller, idTurno);
			info.get(i).setColonieCitta(cittaColonie);
			int strade = getCountStrade(controller, idTurno);
			info.get(i).setStrade(strade);
			int carte = getCountCarte(controller, idTurno, info.get(i).getIdGiocatore());
			info.get(i).setCarte(carte);
			int scambi = getCountScambi(controller, idTurno);
			info.get(i).setScambi(scambi);
			int punti = getPunti(controller, idTurno, info.get(i).getIdGiocatore(), idPartita, info.get(i).getGiro());
			info.get(i).setPunti(punti);
		}
		return info;
	}
	
	public static int getUltimoTurno(Controller controller, int idPartita, int idGiocatore) throws NumberFormatException, SQLException
	{
		String query = String.format("select max(T.idTurno) as max from Turni T where T.idPartita = %d and T.idGiocatore = %d", idPartita, idGiocatore);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("max"));
		return id;
	}
	
	public static int getGiroTurno(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select T.giro from Turni T where T.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int giro = -1;
		while(rs.next())
			giro = Integer.parseInt(rs.getString("giro"));
		return giro;
	}
	
	public static int getPenultimoTurno(Controller controller, int idPartita, int idGiocatore) throws SQLException
	{
		int idUltimoTurno = getUltimoTurno(controller, idPartita, idGiocatore);
		int giro = getGiroTurno(controller, idUltimoTurno);
		String query = String.format("select T.idTurno from Turni T where T.giro = (%d - 1) and T.idPartita = %d and T.idGiocatore = %d", giro, idPartita, idGiocatore);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("idTurno"));
		return id;
	}
	
	public static int getPenultimoTurnoConEsclusione(Controller controller, int idPartita, int idTurnoEscluso) throws SQLException
	{
		String query = String.format("select max(T.idTurno) as max from Turni T where T.idPartita = %d and T.idTurno <> %d", idPartita, idTurnoEscluso);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("max");
		return id;
	}
	
	public static ArrayList<Integer> getIdCittaColonie(Controller controller, int idTurno) throws NumberFormatException, SQLException
	{
		ArrayList<Integer> id = new ArrayList<Integer>();
		String query = String.format("select TCC.idCittaColonia from TurniCittaColonie TCC where TCC.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			id.add(Integer.parseInt(rs.getString("idCittaColonia")));
		return id;
	}
	
	public static ArrayList<Integer> getIdStrade(Controller controller, int idTurno) throws NumberFormatException, SQLException
	{
		ArrayList<Integer> id = new ArrayList<Integer>();
		String query = String.format("select ST.idStrada from StradeTurni ST where ST.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			id.add(Integer.parseInt(rs.getString("idStrada")));
		return id;
	}
	
	public static ArrayList<Integer> getIdCarte(Controller controller, int idTurno) throws NumberFormatException, SQLException
	{
		ArrayList<Integer> id = new ArrayList<Integer>();
		String query = String.format("select CT.idCarta from CarteTurni CT where CT.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			id.add(rs.getInt("idCarta"));
		return id;
	}
	
	public static int nuovoIdCartaTurno(Controller controller) throws SQLException
	{
		String query = "select incrementoIdCartaTurno.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("id"));
		return id;
	}
	
	public static void aggiungiCartaTurno(Controller controller, int idCartaTurno, int idCarta, int idTurno) throws SQLException
	{
		String update = String.format("insert into CarteTurni values(%d, %d, %d)", idCartaTurno, idCarta, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static int getEtaGiocatore(Controller controller, int idGiocatore) throws SQLException
	{
		String query = "select eta from Giocatori";
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = Integer.parseInt(rs.getString("eta"));
		return id;
	}
	
	public static String getNicknameGiocatore(Controller controller, int idGiocatore, int idPartita) throws SQLException
	{
		String query = String.format("select GP.nickname from Giocatori G, GiocatoriPartite GP where G.idGiocatore = GP.idGiocatore and G.idGiocatore = %d and GP.idPartita = %d", idGiocatore, idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		String nickname = null;
		while(rs.next())
			nickname = rs.getString("nickname");
		return nickname;
	}
	
	public static String getColoreGiocatore(Controller controller, int idGiocatore, int idPartita) throws SQLException
	{
		String query = String.format("select GP.colore from Giocatori G, GiocatoriPartite GP where G.idGiocatore = GP.idGiocatore and G.idGiocatore = %d and GP.idPartita = %d", idGiocatore, idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		String colore = null;
		while(rs.next())
			colore = rs.getString("colore");
		return colore;
	}
	
	public static boolean isCitta(Controller controller, int idCittaColonia, int idTurno) throws SQLException
	{
		controller.preparaChiamata("{ ? = call isCitta(?, ?) }");
		controller.setParametroOutInt(1);
		controller.setArgomentoInt(2, idCittaColonia);
		controller.setArgomentoInt(3, idTurno);
		int result = controller.eseguiChiamataFunzioneInt();
		if(result == 1)
			return true;
		return false;
	}

	public static boolean isColonia(Controller controller, int idCittaColonia, int idTurno) throws SQLException
	{
		return !isCitta(controller, idCittaColonia, idTurno);
	}
	
	public static int getTurnoDiCostruzioneCittaColonia(Controller controller, int idCittaColonia) throws SQLException
	{
		String query = String.format("select min(TCC.idTurno) as min from Turni T, TurniCittaColonie TCC where T.idTurno = TCC.idTurno and TCC.idCittaColonia = %d and T.giro <> 0", idCittaColonia);
		ResultSet rs = controller.eseguiQuery(query);
		int min = 0;
		while(rs.next())
			min = Integer.parseInt(rs.getString("min"));
		return min;
	}
	
	public static ArrayList<InfoCittaColonie> getInfoCittaColonie(Controller controller, int idTurno) throws SQLException
	{
		ArrayList<InfoCittaColonie> info = getInfoCittaColoniePrivate(controller, idTurno);
		for(int i = 0; i != info.size(); ++i)
		{
			int id = info.get(i).getId();
			info.get(i).setCitta(isCitta(controller, id, idTurno));
			info.get(i).setColonia(isColonia(controller, id, idTurno));
			info.get(i).setTurnoDiCostruzione(getTurnoDiCostruzioneCittaColonia(controller, id));
		}
		return info;
	}
	
	private static ArrayList<InfoCittaColonie> getInfoCittaColoniePrivate(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select TCC.idCittaColonia, V.posizione from TurniCittaColonie TCC, CittaColonie CC, Vertici V where TCC.idCittaColonia = CC.idCittaColonia and CC.idVertice = V.idVertice and TCC.idTurno = %d order by CC.idCittaColonia", idTurno);
		ArrayList<InfoCittaColonie> info = new ArrayList<InfoCittaColonie>();
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
		{
			int id = rs.getInt("idCittaColonia");
			int posizione = rs.getInt("posizione");
			InfoCittaColonie i = new InfoCittaColonie(id, false, false, posizione, 0);
			info.add(i);
		}
		return info;
	}
	
	public static void copiaRisorse(Controller controller, int idGiocatore, int idPartita, int idTurno, int giro) throws SQLException
	{
		controller.preparaChiamata(" BEGIN CopiaRisorse(?, ?, ?, ?); END;");
		controller.setArgomentoInt(1, idGiocatore);
		controller.setArgomentoInt(2, idPartita);
		controller.setArgomentoInt(3, idTurno);
		controller.setArgomentoInt(4, giro);
		controller.eseguiChiamataProcedura();
	}
	
	public static int getIdCartaRisorsa(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select CR.idCartaRisorsa from Carte C, CarteRisorsa CR where C.idCartaRisorsa = CR.idCartaRisorsa and C.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCartaRisorsa");
		return id;
	}
	
	public static int getIdCartaSviluppo(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select CS.idCartaSviluppo from Carte C, CarteSviluppo CS where C.idCartaSviluppo = CS.idCartaSviluppo and C.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCartaSviluppo");
		return id;
	}
	
	public static int getIdCartaCavaliere(Controller controller, int idCartaSviluppo) throws SQLException
	{
		String query = String.format("select CC.idCartaCavaliere from CarteSviluppo CS, CarteCavaliere CC where CS.idCartaCavaliere = CC.idCartaCavaliere and CS.idCartaSviluppo = %d", idCartaSviluppo);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCartaCavaliere");
		return id;
	}
	
	public static int getIdCartaPuntoVittoria(Controller controller, int idCartaSviluppo) throws SQLException
	{
		String query = String.format("select CPV.idCartaPuntoVittoria from CarteSviluppo CS, CartePuntoVittoria CPV where CS.idCartaPuntoVittoria = CPV.idCartaPuntoVittoria and CS.idCartaSviluppo = %d", idCartaSviluppo);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCartaPuntoVittoria");
		return id;
	}
	
	public static int getIdCartaProgresso(Controller controller, int idCartaSviluppo) throws SQLException
	{
		String query = String.format("select CP.idCartaProgresso from CarteSviluppo CS, CarteProgresso CP where CS.idCartaProgresso = CP.idCartaProgresso and CS.idCartaSviluppo = %d", idCartaSviluppo);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCartaPuntoVittoria");
		return id;
	}
	
	public static String getTipoCartaRisorsa(Controller controller, int idCartaRisorsa) throws SQLException
	{
		String query = String.format("select tipo from CarteRisorsa where idCartaRisorsa = %d", idCartaRisorsa);
		ResultSet rs = controller.eseguiQuery(query);
		String tipo = null;
		while(rs.next())
			tipo = rs.getString("tipo");
		return tipo;
	}
	
	public static boolean isScambioConGiocatore(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select count(*) as count from Scambi S, ScambiGiocatore SG where S.idScambioGiocatore = SG.idScambioGiocatore and S.idScambio = %d", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean isScambioConPorto(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select count(*) as count from Scambi S, ScambiPorto SP where S.idScambioPorto = SP.idScambioPorto and S.idScambio = %d", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean isScambioConGioco(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select count(*) as count from Scambi S, ScambiGioco SG where S.idScambioGioco = SG.idScambioGioco and S.idScambio = %d", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static ArrayList<InfoScambi> getInfoScambi(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select S.idScambio from Scambi S where S.idTurno = %d order by S.idScambio", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<InfoScambi> info = new ArrayList<InfoScambi>();
		while(rs.next())
		{
			int idScambio = rs.getInt("idScambio");
			InfoScambi i = new InfoScambi(idScambio, false, false, false);
			info.add(i);
		}
		for(int i = 0; i != info.size(); ++i)
		{
			int id = info.get(i).getIdScambio();
			info.get(i).setScambioConGiocatore(isScambioConGiocatore(controller, id));
			info.get(i).setScambioConPorto(isScambioConPorto(controller, id));
			info.get(i).setScambioConGioco(isScambioConGioco(controller, id));
		}
		return info;
	}
	
	public static int countCartaRisorsa(Controller controller, int idGiocatore, int idTurno, String tipo) throws SQLException
	{
		String query = String.format("select count(*) as count from Turni T, CarteTurni CT, Carte C, CarteRisorsa CR where T.idTurno = CT.idTurno and CT.idCarta = C.idCarta and C.idCartaRisorsa = CR.idCartaRisorsa and T.idTurno = %d and C.idGiocatore = %d and CR.tipo = '%s'", idTurno, idGiocatore, tipo);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	public static ArrayList<Integer> getIdsCarteRisorsa(Controller controller, int idGiocatore, int idTurno, String tipo) throws SQLException
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String query = String.format("select CR.idCartaRisorsa from Turni T, CarteTurni CT, Carte C, CarteRisorsa CR where T.idTurno = CT.idTurno and CT.idCarta = C.idCarta and C.idCartaRisorsa = CR.idCartaRisorsa and T.idTurno = %d and C.idGiocatore = %d and CR.tipo = '%s'", idTurno, idGiocatore, tipo);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			ids.add(rs.getInt("idCartaRisorsa"));
		return ids;
	}
	
	public static int aggiungiCartaRisorsa(Controller controller, int idGiocatore, int idTurno, String tipo) throws SQLException
	{
		controller.preparaChiamata("{ ? = call AggiungiCartaRisorsa(?, ?, ?) }");
		controller.setParametroOutInt(1);
		controller.setArgomentoInt(2, idGiocatore);
		controller.setArgomentoInt(3, idTurno);
		controller.setArgomentoString(4, tipo);
		return controller.eseguiChiamataFunzioneInt();
	}
	
	public static int getIdCarta(Controller controller, int idCartaRisorsa) throws SQLException
	{
		String query = String.format("select C.idCarta from Carte C where C.idCartaRisorsa = %d", idCartaRisorsa);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCarta");
		return id;
	}
	
	public static void rimuoviCartaRisorsa(Controller controller, int idCartaRisorsa, int idTurno) throws SQLException
	{
		int idCarta = getIdCarta(controller, idCartaRisorsa);
		String update = String.format("delete from CarteTurni CT where CT.idCarta = %d and CT.idTurno = %d", idCarta, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static int aggiungiScambioGioco(Controller controller, int idTurno) throws SQLException
	{
		controller.preparaChiamata("{ ? = call AggiungiScambioGioco(?) }");
		controller.setParametroOutInt(1);
		controller.setArgomentoInt(2, idTurno);
		return controller.eseguiChiamataFunzioneInt();
	}
	
	public static void aggiungiScambiCarteRisorsaDate(Controller controller, int idScambio, int idCartaRisorsa) throws SQLException
	{
		controller.preparaChiamata("BEGIN AggiungiScambiCarteRisorsaDate(?, ?); END;");
		controller.setArgomentoInt(1, idScambio);
		controller.setArgomentoInt(2, idCartaRisorsa);
		controller.eseguiChiamataProcedura();
	}
	
	public static void aggiungiScambiCarteRisorsaRic(Controller controller, int idScambio, int idCartaRisorsa) throws SQLException
	{
		controller.preparaChiamata("BEGIN AggiungiScambiCarteRisorsaRic(?, ?); END;");
		controller.setArgomentoInt(1, idScambio);
		controller.setArgomentoInt(2, idCartaRisorsa);
		controller.eseguiChiamataProcedura();
	}
	
	public static ArrayList<InfoCarteDate> getInfoCarteDate(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select S.idScambioCartaRisorsaData, S.idCartaRisorsa, CR.tipo from ScambiCarteRisorsaDate S, CarteRisorsa CR where S.idCartaRisorsa = CR.idCartaRisorsa and S.idScambio = %d order by S.idScambioCartaRisorsaData", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<InfoCarteDate> info = new ArrayList<InfoCarteDate>();
		while(rs.next())
		{
			int id = rs.getInt("idScambioCartaRisorsaData");
			int idCartaRisorsa = rs.getInt("idCartaRisorsa");
			String tipo = rs.getString("tipo");
			InfoCarteDate i = new InfoCarteDate(id, idCartaRisorsa, tipo);
			info.add(i);
		}
		return info;
	}
	
	public static ArrayList<InfoCarteRicevute> getInfoCarteRicevute(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select S.idScambioCartaRisorsaRicevuta, S.idCartaRisorsa, CR.tipo from ScambiCarteRisorsaRicevute S, CarteRisorsa CR where S.idCartaRisorsa = CR.idCartaRisorsa and S.idScambio = %d order by S.idScambioCartaRisorsaRicevuta", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<InfoCarteRicevute> info = new ArrayList<InfoCarteRicevute>();
		while(rs.next())
		{
			int id = rs.getInt("idScambioCartaRisorsaRicevuta");
			int idCartaRisorsa = rs.getInt("idCartaRisorsa");
			String tipo = rs.getString("tipo");
			InfoCarteRicevute i = new InfoCarteRicevute(id, idCartaRisorsa, tipo);
			info.add(i);
		}
		return info;
	}
	
	public static int getIdPartita(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select T.idPartita from Turni T where T.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idPartita");
		return id;
	}
	
	public static void swapPadroneCartaRisorsa(Controller controller, int idCartaRisorsa, int idNuovoGiocatore, int idTurno) throws SQLException
	{
		String tipo = getTipoCartaRisorsa(controller, idCartaRisorsa);
		rimuoviCartaRisorsa(controller, idCartaRisorsa, idTurno);
		aggiungiCartaRisorsa(controller, idNuovoGiocatore, idTurno, tipo);
	}
	
	public static int aggiungiScambioGiocatore(Controller controller, int idTurno, int idGiocatore) throws SQLException
	{
		controller.preparaChiamata("{ ? = call AggiungiScambioGiocatore(?, ?) }");
		controller.setParametroOutInt(1);
		controller.setArgomentoInt(2, idTurno);
		controller.setArgomentoInt(3, idGiocatore);
		return controller.eseguiChiamataFunzioneInt();
	}
	
	public static int getMaxTurno(Controller controller, int idPartita) throws SQLException
	{
		String query = String.format("select max(T.idTurno) as max from Turni T where T.idPartita = %d", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		int idTurno = 0;
		while(rs.next())
			idTurno = rs.getInt("max");
		return idTurno;
	}
	
	public static int getGiocatoreCoinvolto(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select SG.idGiocatore from Scambi S, ScambiGiocatore SG where S.idScambioGiocatore = SG.idScambioGiocatore and S.idScambio = %d", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idGiocatore");
		return id;
	}
	
	public static ArrayList<InfoEsagonoPorto> getInfoEsagoniPorto(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select EP.idEsagonoPorto, E.posizione, EP.rapportoScambio, EP.specializzazione from TurniCittaColonie TCC, CittaColonie CC, Vertici V, VerticiEsagoni VE, Esagoni E, EsagoniMare EM, EsagoniPorto EP where TCC.idCittaColonia = CC.idCittaColonia and CC.idVertice = V.idVertice and V.idVertice = VE.idVertice and VE.idEsagono = E.idEsagono and E.idEsagonoMare = EM.idEsagonoMare and EM.idEsagonoPorto = EP.idEsagonoPorto and TCC.idTurno = %d", idTurno);
		ArrayList<InfoEsagonoPorto> info = new ArrayList<InfoEsagonoPorto>();
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
		{
			int id = rs.getInt("idEsagonoPorto");
			int posizione = rs.getInt("posizione");
			int rapportoScambio = rs.getInt("rapportoScambio");
			String specializzazione = rs.getString("specializzazione");
			InfoEsagonoPorto i = new InfoEsagonoPorto(id, posizione, rapportoScambio, specializzazione);
			info.add(i);
		}
		return info;
	}
	
	public static int aggiungiScambioPorto(Controller controller, int idTurno, int idEsagonoPorto) throws SQLException
	{
		controller.preparaChiamata("{ ? = call AggiungiScambioPorto(?, ?) }");
		controller.setParametroOutInt(1);
		controller.setArgomentoInt(2, idTurno);
		controller.setArgomentoInt(3, idEsagonoPorto);
		return controller.eseguiChiamataFunzioneInt();
	}
	
	public static int getIdEsagono(Controller controller, int idScambio) throws SQLException
	{
		String query = String.format("select SP.idEsagonoPorto from Scambi S, ScambiPorto SP where S.idScambioPorto = SP.idScambioPorto and S.idScambio = %d", idScambio);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idEsagonoPorto");
		return id;
	}
	
	public static boolean verticeOccupato(Controller controller, int idPartita, int posizione) throws SQLException
	{
		String query = String.format("select count(*) as count from Turni T, TuniCittaColonie TCC, CittaColonie CC, Vertici V where T.idTurno = TCC.idTurno and TCC.idCittaColonia = CC.idCittaColonia and CC.idVertice = V.idVertice and T.idPartita = %d and V.posizione = %d", idPartita, posizione); 
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static void aggiungiCartaCavaliere(Controller controller, int idGiocatore, int idTurno) throws SQLException
	{
		controller.preparaChiamata("BEGIN AggiungiCartaCavaliere(?, ?); END;");
		controller.setArgomentoInt(1, idTurno);
		controller.setArgomentoInt(2, idGiocatore);
		controller.eseguiChiamataProcedura();
	}
	
	public static void aggiungiCartaPuntoVittoria(Controller controller, int idGiocatore, int idTurno) throws SQLException
	{
		controller.preparaChiamata("BEGIN AggiungiCartaPuntoVittoria(?, ?); END;");
		controller.setArgomentoInt(1, idTurno);
		controller.setArgomentoInt(2, idGiocatore);
		controller.eseguiChiamataProcedura();
	}
	
	public static void aggiungiCartaProgresso(Controller controller, int idGiocatore, int idTurno) throws SQLException
	{
		controller.preparaChiamata("BEGIN AggiungiCartaProgresso(?, ?); END;");
		controller.setArgomentoInt(1, idTurno);
		controller.setArgomentoInt(2, idGiocatore);
		controller.eseguiChiamataProcedura();
	}
	
	public static ArrayList<Integer> getVerticiAdiacenti(Controller controller, int idLato) throws SQLException
	{
		String query = String.format("select VL.idVertice from VerticiLati VL where VL.idLato = %d", idLato);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<Integer> vertici = new ArrayList<Integer>();
		while(rs.next())
			vertici.add(rs.getInt("idVertice"));
		return vertici;
	}
	
	public static ArrayList<Integer> getLatiAdiacenti(Controller controller, int idVertice) throws SQLException
	{
		String query = String.format("select VL.idLato from VerticiLati VL where VL.idVertice = %d", idVertice);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<Integer> lati = new ArrayList<Integer>();
		while(rs.next())
			lati.add(rs.getInt("idLato"));
		return lati;
	}
	
	public static boolean checkEsisteCittaColonia(Controller controller, int idTurno, int idVertice) throws SQLException
	{
		String query = String.format("select count(*) as count from TurniCittaColonie TCC, CittaColonie CC, Vertici V where TCC.idCittaColonia = CC.idCittaColonia and CC.idVertice = V.idVertice and TCC.idTurno = %d and V.idVertice = %d", idTurno, idVertice);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean checkEsisteStrada(Controller controller, int idTurno, int idLato) throws SQLException
	{
		String query = String.format("select count(*) as count from StradeTurni ST, Strade S, Lati L where ST.idStrada = S.idStrada and S.idLato = L.idLato and ST.idTurno = %d and L.idLato = %d", idTurno, idLato);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static ArrayList<InfoStrada> getIdsLatiStrade(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select S.idLato, S.idStrada from StradeTurni ST, Strade S where ST.idStrada = S.idStrada and ST.idTurno = %d", idTurno); 
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<InfoStrada> info = new ArrayList<InfoStrada>();
		while(rs.next())
		{
			int idLato = rs.getInt("idLato");
			int idStrada = rs.getInt("idStrada");
			info.add(new InfoStrada(idStrada, idLato));
		}
			
		return info;
	}
	
	public static int getPosizioneVertice(Controller controller, int idVertice) throws SQLException
	{
		String query = String.format("select V.posizione from Vertici V where V.idVertice = %d", idVertice);
		ResultSet rs = controller.eseguiQuery(query);
		int posizione = -1;
		while(rs.next())
			posizione = rs.getInt("posizione");
		return posizione;
	}
	
	public static void aggiungiColonia(Controller controller, int idTurno, int idVertice, int idStrada) throws SQLException
	{
		String query = "select incrementoIdCittaColonia.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int idCittaColonia = 0;
		while(rs.next())
			idCittaColonia = rs.getInt("id");
		query = "select incrementoIdTurnoCittaColonia.nextVal as id from dual";
		rs = controller.eseguiQuery(query);
		int idTurnoCittaColonia = 0;
		while(rs.next())
			idTurnoCittaColonia = rs.getInt("id");
		String update = String.format("insert into CittaColonie values(%d, %d, %s)", idCittaColonia, idVertice, "null");
		controller.eseguiUpdate(update);
		update = String.format("update Strade S set S.idCittaColoniaSucc = %d where S.idStrada = %d", idCittaColonia, idStrada);
		controller.eseguiUpdate(update);
		update = String.format("insert into TurniCittaColonie values(%d, %d, %d)", idTurnoCittaColonia, idCittaColonia, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static void aggiornaACitta(Controller controller, int idCittaColonia, int idTurno) throws SQLException
	{
		controller.preparaChiamata("BEGIN AggiornaACitta(?, ?); END;");
		controller.setArgomentoInt(1, idCittaColonia);
		controller.setArgomentoInt(2, idTurno);
		controller.eseguiChiamataProcedura();
	}
	
	public static ArrayList<InfoGeneraliStrada> getInfoGeneraliStrade(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select TMP1.idStrada, TMP1.posizione, TMP2.idTurno from (select S1.idStrada, L.posizione from StradeTurni ST1, Strade S1, Lati L where ST1.idStrada = S1.idStrada and S1.idLato = L.idLato and ST1.idTurno = %d) TMP1, (select ST1.idStrada, ST1.idTurno from StradeTurni ST1 where ST1.idTurno = (select min(ST2.idTurno) from Turni T, StradeTurni ST2  where T.idTurno = ST2.idTurno and ST2.idStrada = ST1.idStrada and T.giro <> 0)) TMP2 where TMP1.idStrada = TMP2.idStrada order by TMP1.idStrada", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<InfoGeneraliStrada> i = new ArrayList<InfoGeneraliStrada>();
		while(rs.next())
		{
			int id = rs.getInt("idStrada");
			int posizione = rs.getInt("posizione");
			int turnoDiCostruzione = rs.getInt("idTurno");
			InfoGeneraliStrada info = new InfoGeneraliStrada(id, posizione, turnoDiCostruzione);
			i.add(info);
		}
		return i;
	}
	
	public static ArrayList<Integer> getIdsVerticiCittaColonie(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select CC.idVertice from TurniCittaColonie TCC, CittaColonie CC where TCC.idCittaColonia = CC.idCittaColonia and TCC.idTurno = %d", idTurno);
		ArrayList<Integer> vertici = new ArrayList<Integer>();
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			vertici.add(rs.getInt("idVertice"));
		return vertici;
	}
	
	public static ArrayList<Integer> getChiaviIdLatiStrade(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select S.idLato from StradeTurni ST, Strade S where ST.idStrada = S.idStrada and ST.idTurno = %d", idTurno);
		ArrayList<Integer> lati = new ArrayList<Integer>();
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			lati.add(rs.getInt("idLato"));
		return lati;
	}
	
	public static int getIdCittaColonia(Controller controller, int idVertice) throws SQLException
	{
		String query = String.format("select CC.idCittaColonia from CittaColonie CC where CC.idVertice = %d", idVertice);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCittaColonia");
		return id;
	}
	
	public static int getIdStrada(Controller controller, int idLato) throws SQLException
	{
		String query = String.format("select S.idStrada from Strade S where S.idLato = %d", idLato);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idStrada");
		return id;
	}
	
	public static void aggiungiStrada(Controller controller, int idStradaPrecedente, int idCittaColonia, int idTurno, int idLato) throws SQLException
	{
		String query = "select incrementoIdStrada.nextVal as id from dual";
		ResultSet rs = controller.eseguiQuery(query);
		int idStrada = 0;
		while(rs.next())
			idStrada = rs.getInt("id");
		if(idCittaColonia != 0)
		{
			String update = String.format("insert into Strade values(%d, %d, %s, %d)", idStrada, idCittaColonia, "null", idLato);
			controller.eseguiUpdate(update);
		}
		else
		{
			String update = String.format("insert into Strade values(%d, %s, %s, %d)", idStrada, "null", "null", idLato);
			controller.eseguiUpdate(update);
			query = "select incrementoIdStradaSucc.nextVal as id from dual";
			rs = controller.eseguiQuery(query);
			int idStradaSucc = 0;
			while(rs.next())
				idStradaSucc = rs.getInt("id");
			update = String.format("insert into StradeSuccessive values(%d, %d, %d)", idStradaSucc, idStradaPrecedente, idStrada);
			controller.eseguiUpdate(update);
			query = "select incrementoIdStradaPrec.nextVal as id from dual";
			rs = controller.eseguiQuery(query);
			int idStradaPrec = 0;
			while(rs.next())
				idStradaPrec = rs.getInt("id");
			update = String.format("insert into StradePrecedenti values(%d, %d, %d)", idStradaPrec, idStrada, idStradaPrecedente);
			controller.eseguiUpdate(update);
		}
		query = "select incrementoIdStradaTurno.nextVal as id from dual";
		int idStradaTurno = 0;
		rs = controller.eseguiQuery(query);
		while(rs.next())
			idStradaTurno = rs.getInt("id");
		String update = String.format("insert into StradeTurni values(%d, %d, %d)", idStradaTurno, idStrada, idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static int getTurnoMinCittaColonia(Controller controller, int idCittaColonia) throws SQLException
	{
		String query = String.format("select min(TCC.idTurno) as min from TurniCittaColonie TCC where TCC.idCittaColonia = %d", idCittaColonia);
		ResultSet rs = controller.eseguiQuery(query);
		int idTurno = -1;
		while(rs.next())
			idTurno = rs.getInt("min");
		return idTurno;
	}
	
	public static int getTurnoMinStrada(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select min(ST.idTurno) as min from StradeTurni ST where ST.idStrada = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		int idTurno = -1;
		while(rs.next())
			idTurno = rs.getInt("min");
		return idTurno;
	}
	
	public static int getTurnoMinCarta(Controller controller, int idCarta) throws SQLException
	{
		String query = String.format("select min(CT.idTurno) as min from CarteTurni CT where CT.idCarta = %d", idCarta);
		ResultSet rs = controller.eseguiQuery(query);
		int idTurno = -1;
		while(rs.next())
			idTurno = rs.getInt("min");
		return idTurno;
	}
	
	public static void resettaCitta(Controller controller, int idTurno) throws SQLException 
	{
		String update = String.format("update CittaColonie CC set CC.aggiornataDalTurno = null where CC.aggiornataDalTurno = %d", idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static void eliminaCittaColoniaDalTurno(Controller controller, int idTurno) throws SQLException
	{
		ArrayList<Integer> cittaColonie = new ArrayList<Integer>();
		String query = String.format("select * from TurniCittaColonie TCC where TCC.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			cittaColonie.add(rs.getInt("idCittaColonia"));
		for(int i = 0; i != cittaColonie.size(); ++i)
		{
			int turnoMin = getTurnoMinCittaColonia(controller, cittaColonie.get(i));
			if(turnoMin == idTurno)
			{
				String update = String.format("delete from CittaColonie CC where CC.idCittaColonia = %d", cittaColonie.get(i));
				controller.eseguiUpdate(update);
			}
		}
		String update = String.format("delete from TurniCittaColonie TCC where TCC.idTurno = %d", idTurno);
		controller.eseguiUpdate(update);
		
	}
	
	public static void eliminaStradeDalTurno(Controller controller, int idTurno) throws SQLException
	{
		ArrayList<Integer> strade = new ArrayList<Integer>();
		String query = String.format("select * from StradeTurni ST where ST.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			strade.add(rs.getInt("idStrada"));
		for(int i = 0; i != strade.size(); ++i)
		{
			int turnoMin = getTurnoMinStrada(controller, strade.get(i));
			if(turnoMin == idTurno)
			{
				String update = String.format("delete from Strade S where S.idStrada = %d", strade.get(i));
				controller.eseguiUpdate(update);
			}
		}
		String update = String.format("delete from StradeTurni ST where ST.idTurno = %d", idTurno);
		controller.eseguiUpdate(update);
		
	}
	
	public static void eliminaCartaRisorsa(Controller controller, int idCartaRisorsa) throws SQLException
	{
		String update = String.format("delete from CarteRisorsa CR where CR.idCartaRisorsa = %d", idCartaRisorsa);
		controller.eseguiUpdate(update);
	}
	
	public static void eliminaCartaSviluppo(Controller controller, int idCartaSviluppo) throws SQLException
	{
		int idCartaCavaliere = getIdCartaCavaliere(controller, idCartaSviluppo);
		int idCartaPuntoVittoria = getIdCartaPuntoVittoria(controller, idCartaSviluppo);
		int idCartaProgresso = getIdCartaProgresso(controller, idCartaSviluppo);
		String update = null;
		if(idCartaCavaliere != 0)
			update = String.format("delete from CarteCavaliere CC where CC.idCartaCavaliere = %d", idCartaCavaliere);
		else if(idCartaPuntoVittoria != 0)
			update = String.format("delete from CartePuntoVittoria CPV where CPV.idCartaPuntoVittoria = %d", idCartaPuntoVittoria);
		else
			update = String.format("delete from CarteProgresso CP where CP.idCartaProgresso = %d", idCartaProgresso);
		controller.eseguiUpdate(update);
	}
	
	public static void eliminaCarteDalTurno(Controller controller, int idTurno) throws SQLException
	{
		ArrayList<Integer> carte = new ArrayList<Integer>();
		String query = String.format("select * from CarteTurni CT where CT.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			carte.add(rs.getInt("idCarta"));
		for(int i = 0; i != carte.size(); ++i)
		{
			int turnoMin = getTurnoMinCarta(controller, carte.get(i));
			if(turnoMin == idTurno)
			{
				int idCartaRisorsa = getIdCartaRisorsa(controller, carte.get(i));
				int idCartaSviluppo = getIdCartaSviluppo(controller, carte.get(i));
				if(idCartaRisorsa != 0)
					eliminaCartaRisorsa(controller, idCartaRisorsa);
				else
					eliminaCartaSviluppo(controller, idCartaSviluppo);
			}
		}
		String update = String.format("delete from CarteTurni CT where CT.idTurno = %d", idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static void eliminaTurno(Controller controller, int idTurno) throws SQLException
	{
		String update = String.format("delete from Turni T where T.idTurno = %d", idTurno);
		controller.eseguiUpdate(update);
	}
	
	public static void cancellaTurno(Controller controller, int idTurno) throws SQLException
	{
		eliminaStradeDalTurno(controller, idTurno);
		resettaCitta(controller, idTurno);
		eliminaCittaColoniaDalTurno(controller, idTurno);
		eliminaStradeDalTurno(controller, idTurno);
		eliminaCarteDalTurno(controller, idTurno);
		eliminaTurno(controller, idTurno);
	}
	
	public static void aggiungiStradaSenzaTurno(Controller controller, int idStrada, int idLato, int idCittaColonia) throws SQLException
	{
		String update = String.format("insert into Strade values(%d, %d, %s, %d)", idStrada, idCittaColonia, "null", idLato);
		controller.eseguiUpdate(update);
	}
	
	public static int getIdVertice(Controller controller, int idCittaColonia) throws SQLException
	{
		String query = String.format("select CC.idVertice from CittaColonie CC where CC.idCittaColonia = %d", idCittaColonia);
		ResultSet rs = controller.eseguiQuery(query);
		int idVertice = 0;
		while(rs.next())
			idVertice = rs.getInt("idVertice");
		return idVertice;
	}
	
	public static void aggiungiRisorseCittaColonie(Controller controller, int idTurno, int idGiocatore, int idCittaColonia) throws SQLException
	{
		int idVertice = getIdVertice(controller, idCittaColonia);
		controller.preparaChiamata("BEGIN AggiungiRisorseCittaColonie(?, ?, ?); END;");
		controller.setArgomentoInt(1, idTurno);
		controller.setArgomentoInt(2, idVertice);
		controller.setArgomentoInt(3, idGiocatore);
		controller.eseguiChiamataProcedura();
	}
	
	public static int countCarteRisorsa(Controller controller, int idTurno, int idGiocatore) throws SQLException
	{
		String query = String.format("select count(*) as count from CarteTurni CT, Carte C, CarteRisorsa CR where CT.idCarta = C.idCarta and C.idCartaRisorsa = CR.idCartaRisorsa and C.idGiocatore = %d and CT.idTurno = %d", idGiocatore, idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	public static int countCittaColonieAdiacentiPorti(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from TurniCittaColonie TCC, CittaColonie CC, Vertici V, VerticiEsagoni VE, Esagoni E, EsagoniMare EM, EsagoniPorto EP where TCC.idCittaColonia = CC.idCittaColonia and CC.idVertice = V.idVertice and V.idVertice = VE.idVertice and VE.idEsagono = E.idEsagono and E.idEsagonoMare = EM.idEsagonoMare and EM.idEsagonoPorto = EP.idEsagonoPorto and TCC.idTurno = %d", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		return count;
	}
	
	public static ArrayList<Integer> getIdsCittaColonieSuEsagono(Controller controller, int idEsagono) throws SQLException
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String query = String.format("select CC.idCittaColonia from CittaColonie CC, Vertici V, VerticiEsagoni VE where CC.idVertice = V.idVertice and V.idVertice = VE.idVertice and VE.idEsagono = %d", idEsagono);
		ResultSet rs = controller.eseguiQuery(query);
		while(rs.next())
			ids.add(rs.getInt("idCittaColonia"));
		return ids;
	}
	
	public static int getIdGiocatore(Controller controller, int idCittaColonia) throws SQLException
	{
		String query = String.format("select distinct(T.idGiocatore) from CittaColonie CC, TurniCittaColonie TCC, Turni T where CC.idCittaColonia = TCC.idCittaColonia and TCC.idTurno = T.idTurno and CC.idCittaColonia = %d", idCittaColonia);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idGiocatore");
		return id;
	}
	
	public static int getIdBrigante(Controller controller, int idPartita) throws SQLException
	{
		String query = String.format("select max(B.idBrigante) as max from Partite P, Turni T, Brigante B where P.idPartita = T.idPartita and T.idTurno = B.idTurno and P.idPartita = %d", idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("max");
		return id;
	}
	
	public static void modificaPosizioneBrigante(Controller controller, int idBrigante, int idEsagono) throws SQLException
	{
		String update = String.format("update Brigante B set B.idEsagono = %d where B.idBrigante = %d", idEsagono, idBrigante);
		controller.eseguiUpdate(update);
	}
	
	public static boolean haColoniaSuccessiva(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select S.idCittaColoniaSucc from Strade S where S.idStrada = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		int id = 0;
		while(rs.next())
			id = rs.getInt("idCittaColoniaSucc");
		if(id != 0)
			return true;
		return false;
	}
	
	public static boolean haStradaSuccessiva(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select count(*) as count from StradeSuccessive SS where SS.idStradaCorrente = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count != 0)
			return true;
		return false;
	}
	
	public static int getIdLato(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select S.idLato from Strade S where S.idStrada = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		int idLato = 0;
		while(rs.next())
			idLato = rs.getInt("idLato");
		return idLato;
	}
	
	public static ArrayList<Integer> getStradeConCittaColoniePrec(Controller controller, int idTurno) throws SQLException
	{
		String query = String.format("select S.idStrada from StradeTurni ST, Strade S where ST.idStrada = S.idStrada and ST.idTurno = %d and S.idCittaColoniaPrec is not null", idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<Integer> strade = new ArrayList<Integer>();
		while(rs.next())
			strade.add(rs.getInt("idStrada"));
		return strade;
	}
	
	public static int getCittaColoniaPrec(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select S.idCittaColoniaPrec from Strade S where S.idStrada = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		int idCittaColonia = 0;
		while(rs.next())
			idCittaColonia = rs.getInt("idCittaColoniaPrec");
		return idCittaColonia;
	}
	
	public static int getCittaColoniaSucc(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select S.idCittaColoniaSucc from Strade S where S.idStrada = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		int idCittaColonia = 0;
		while(rs.next())
			idCittaColonia = rs.getInt("idCittaColoniaSucc");
		return idCittaColonia;
	}
	
	public static ArrayList<Integer> getStradeSuccessive(Controller controller, int idStrada) throws SQLException
	{
		String query = String.format("select SS.idStradaSuccessiva from StradeSuccessive SS where SS.idStradaCorrente = %d", idStrada);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<Integer> strade = new ArrayList<Integer>();
		while(rs.next())
			strade.add(rs.getInt("idStradaSuccessiva"));
		return strade;
	}
	
	public static ArrayList<Integer> getStradeSuccessiveAColonie(Controller controller, int idCittaColonia) throws SQLException
	{
		String query = String.format("select S.idStrada from Strade S where S.idCittaColoniaPrec = %d", idCittaColonia);
		ResultSet rs = controller.eseguiQuery(query);
		ArrayList<Integer> strade = new ArrayList<Integer>();
		while(rs.next())
			strade.add(rs.getInt("idStrada"));
		return strade;
	}
	
	public static boolean stradaAppartieneAlTurno(Controller controller, int idStrada, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from StradeTurni ST where ST.idStrada = %d and ST.idTurno = %d", idStrada, idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	public static boolean cittaColoniaAppartieneAlTurno(Controller controller, int idCittaColonia, int idTurno) throws SQLException
	{
		String query = String.format("select count(*) as count from TurniCittaColonie TCC where TCC.idCittaColonia = %d and TCC.idTurno = %d", idCittaColonia, idTurno);
		ResultSet rs = controller.eseguiQuery(query);
		int count = 0;
		while(rs.next())
			count = rs.getInt("count");
		if(count == 0)
			return false;
		return true;
	}
	
	private static int getLunghezzaStrada(Controller controller, int idLatoCorrente, boolean visitato[], int lunghezza, int idTurno) throws SQLException
	{
		++lunghezza;
		int posizione = getPosizioneLato(controller, idLatoCorrente);
		visitato[posizione] = true;
		ArrayList<Integer> verticiAdiacenti = getVerticiAdiacenti(controller, idLatoCorrente);
		for(int i = 0; i != verticiAdiacenti.size(); ++i)
		{
			int cittaColonia = getIdCittaColonia(controller, verticiAdiacenti.get(i));
			if(cittaColonia == 0 || cittaColoniaAppartieneAlTurno(controller, cittaColonia, idTurno))
			{
				ArrayList<Integer> latiAdiacenti = getLatiAdiacenti(controller, verticiAdiacenti.get(i));
				for(int y = 0; y != latiAdiacenti.size(); ++y)
				{
					int idStrada = getIdStrada(controller, latiAdiacenti.get(y));
					posizione = getPosizioneLato(controller, latiAdiacenti.get(y));
					if(!visitato[posizione] && idStrada != 0 && stradaAppartieneAlTurno(controller, idStrada, idTurno))
					{
						int temp = getLunghezzaStrada(controller, latiAdiacenti.get(y), visitato, lunghezza, idTurno);
						if(temp > lunghezza)
							lunghezza = temp;
					}
				}
			}
		}
		posizione = getPosizioneLato(controller, idLatoCorrente);
		visitato[posizione] = false;
		return lunghezza;
	}
	
	private static int getStradaPiuLunga(Controller controller, int idTurno) throws SQLException
	{
		boolean[] visitato = new boolean[72];
		for(int i = 0; i != visitato.length; ++i)
			visitato[i] = false;
		ArrayList<Integer> strade = getStradeConCittaColoniePrec(controller, idTurno);
		int lunghezza = 0;
		for(int i = 0; i != strade.size(); ++i)
		{
			int idLato = getIdLato(controller, strade.get(i));
			int temp = getLunghezzaStrada(controller, idLato, visitato, 0, idTurno);
			if(temp > lunghezza)
				lunghezza = temp;
		}
		return lunghezza;
	}
	
	public static int getIdTurno(Controller controller, int idGiocatore, int giro, int idPartita) throws SQLException
	{
		String query = String.format("select T.idTurno from Turni T where T.idGiocatore = %d and T.giro = %d and T.idPartita = %d", idGiocatore, giro, idPartita);
		ResultSet rs = controller.eseguiQuery(query);
		int idTurno = -1;
		while(rs.next())
			idTurno = rs.getInt("idTurno");
		return idTurno;
	}
	
	private static int getIdGiocatoreConStradaPiuLunga(Controller controller, int giro, int idPartita) throws SQLException
	{
		ArrayList<Integer> giocatori = getIdGiocatori(controller, idPartita);
		int idTurnoUno = getIdTurno(controller, giocatori.get(0), giro, idPartita);
		if(idTurnoUno == -1)
			idTurnoUno = getIdTurno(controller, giocatori.get(0), giro - 1, idPartita);
		int idTurnoDue = getIdTurno(controller, giocatori.get(1), giro, idPartita);
		if(idTurnoDue == -1)
			idTurnoDue = getIdTurno(controller, giocatori.get(1), giro - 1, idPartita);
		int idTurnoTre = getIdTurno(controller, giocatori.get(2), giro, idPartita);
		if(idTurnoTre == -1)
			idTurnoTre = getIdTurno(controller, giocatori.get(2), giro - 1, idPartita);
		int idTurnoQuattro = getIdTurno(controller, giocatori.get(3), giro, idPartita);
		if(idTurnoQuattro == -1)
			idTurnoQuattro = getIdTurno(controller, giocatori.get(3), giro - 1, idPartita);
			int stradaUno = getStradaPiuLunga(controller, idTurnoUno);
		int stradaDue = getStradaPiuLunga(controller, idTurnoDue);
		int stradaTre = getStradaPiuLunga(controller, idTurnoTre);
		int stradaQuattro = getStradaPiuLunga(controller, idTurnoQuattro);
		if(stradaUno > stradaDue && stradaUno > stradaTre && stradaUno > stradaQuattro && stradaUno >= 5)
			return giocatori.get(0);
		else if(stradaDue > stradaUno && stradaDue > stradaTre && stradaDue > stradaQuattro && stradaDue >= 5)
			return giocatori.get(1);
		else if(stradaTre > stradaUno && stradaTre > stradaDue && stradaTre > stradaQuattro && stradaTre >= 5)
			return giocatori.get(2);
		else if(stradaQuattro > stradaUno && stradaQuattro > stradaDue && stradaQuattro > stradaTre && stradaQuattro >= 5)
			return giocatori.get(3);
		return 0;
	}
	
	private static boolean haStradaPiuLunga(Controller controller, int idGiocatore, int idPartita, int giro) throws SQLException
	{
		int id = getIdGiocatoreConStradaPiuLunga(controller, giro, idPartita);
		if(id == idGiocatore)
			return true;
		return false;
	}
	
	public static void rimuoviCarta(Controller controller, int idCarta, int idTurno) throws SQLException
	{
		String update = String.format("delete from CarteTurni CT where CT.idCarta = %d and CT.idTurno = %d", idCarta, idTurno);
		controller.eseguiUpdate(update);
	}
}
	
