package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

	private String hostname;
	private int    port;
	private String database;
	private String username;
	private String password;
	
	private Connection connection;
	
	public DataSource() {
		try {
			
			hostname = "localhost";
			port 	 = 3306;
			database = "locadora";
			username = "root";
			password = "0102301023";
			
			String ssl = "?useTimezone=true&serverTimezone=UTC&useSSL=false";		// Tratamento de erro de falta de SSL
			String url = "jdbc:mysql://"+hostname+":"+port+"/"+database+ssl;
			//jdbc:mysql://localhost:3306/sistema?useTimezone=true&serverTimezone=UTC&useSSL=false;
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());		// Driver da Biblioteca importada
			connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Conexão Efetuada!");
			
		} catch(SQLException ex) {
			System.err.println("Erro na Conexão " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral " + ex.getMessage());
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeDataSource() {
		try {

			connection.close();
			System.out.println("Conexão Encerrada!");
			
		} catch(Exception ex) {
			System.err.println("Erro ao Desconectar " + ex.getMessage());
		}
	}
	
}
