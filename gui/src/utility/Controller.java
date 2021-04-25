package utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller
{
	public Controller() throws ClassNotFoundException
	{
		initJDBC();
	}
	
	public void connettiAlDatabase(String host, String databaseName, String username, String password) throws SQLException
	{
		connection = DriverManager.getConnection(host + ":" + databaseName, username, password);
		if(connection != null)
			statement = connection.createStatement();
	}
	
	public void chiudiConnessione() throws SQLException
	{
		if(statement != null)
			statement.close();
		if(callableStatement != null)
			callableStatement.close();
		if(connection != null)
			connection.close();
		statement = null;
		callableStatement = null;
		connection = null;
	}
	
	public ResultSet eseguiQuery(String query) throws SQLException
	{
		if(statement != null)
			return statement.executeQuery(query);
		return null;
	}
	
	public int eseguiUpdate(String update) throws SQLException
	{
		if(statement != null)
			return statement.executeUpdate(update);
		return -1;
	}
	
	public void preparaChiamata(String funzione) throws SQLException
	{
		callableStatement = connection.prepareCall(funzione);
	}
	
	public void setParametroOutInt(int indice) throws SQLException
	{
		callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
	}
	
	public void setArgomentoInt(int indice, int variabile) throws SQLException
	{
		callableStatement.setInt(indice, variabile);
	}
	
	public void setArgomentoString(int indice, String variabile) throws SQLException
	{
		callableStatement.setString(indice, variabile);
	}
	
	
	public int eseguiChiamataFunzioneInt() throws SQLException
	{
		callableStatement.executeUpdate();
		return callableStatement.getInt(1);
	}
	
	public void eseguiChiamataProcedura() throws SQLException
	{
		callableStatement.executeUpdate();
	}
	
	public boolean esisteConnessione()
	{
		if(connection != null)
			return true;
		return false;
	}
	
	private void initJDBC() throws ClassNotFoundException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
	}
	
	private Connection connection = null;
	private Statement statement = null;
	private CallableStatement callableStatement = null;
}
