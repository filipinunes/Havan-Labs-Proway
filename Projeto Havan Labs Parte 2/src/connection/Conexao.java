
package connection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	// Cria uma conexao com o banco
	public static Connection myConn = null;

	public static Connection createConnection() throws SQLException {

		String dbUrl = "jdbc:mysql://localhost:3306/operacoes?useSSL=false";
		String user = "proway";
		String password = "proway";

		try {
			myConn = DriverManager.getConnection(dbUrl, user, password);

			System.out.println("Conexao com o banco de dados bem sucedida.");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return myConn;
	}
	
	public static void createDataBase() throws SQLException{
		Statement myStmt = myConn.createStatement();
		myStmt.execute("create database if not exists operacoes;");
		myStmt.execute("use operacoes;");
	}
	
	public static void createCurrencyTable() throws SQLException{
		Statement myStmt = myConn.createStatement();
		myStmt.execute("CREATE TABLE IF NOT EXISTS moedas(\r\n" + 
				"id int not null auto_increment,\r\n" + 
				"currency_name varchar(30), \r\n" + 
				"currency_value decimal(15,2),\r\n" + 
				"primary key(id)\r\n" + 
				");");
	}
	
	public static void createRegisterTable() throws SQLException{
		Statement myStmt = myConn.createStatement();
		myStmt.execute("CREATE TABLE if not exists `registros` (\r\n" + 
				"  `id` int NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `current_data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\r\n" + 
				"  `client_name` varchar(30) DEFAULT NULL,\r\n" + 
				"  `first_currency` varchar(15) DEFAULT NULL,\r\n" + 
				"  `sec_currency` varchar(15) DEFAULT NULL,\r\n" + 
				"  `orig_value` decimal(15,2) DEFAULT NULL,\r\n" + 
				"  `converted_value` decimal(15,2) DEFAULT NULL,\r\n" + 
				"  `paid_tax` decimal(8,2) DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`id`)\r\n" + 
				") ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8");
	}

	public static int insertCurrency(String name, double value) throws SQLException {
		Statement myStmt = myConn.createStatement();
		int rowsAffected = myStmt.executeUpdate("insert into moedas"
					+ " (currency_name, currency_value) values ('" + name + "', " + value + ");");
		return rowsAffected;
	}
	
	public static int insertRegister(String name, String fCurrency, String sCurrency, double origValue, double convertedValue, double tax) throws SQLException {
		Statement myStmt = myConn.createStatement();
		int rowsAffected = myStmt.executeUpdate("insert into registros "
				+ "(client_name, first_currency, sec_currency, orig_value, converted_value, paid_tax) values "
				+ "('" + name + "', '" + fCurrency + "', '" + sCurrency + "', " + origValue + ", " + convertedValue + ", " + tax + ");");
		return rowsAffected;
	}
	
	public static double getCurrencyValue(String currencyName) throws SQLException {
		double value = 0;
		
		Statement myStmt = myConn.createStatement();
		ResultSet myRs = myStmt.executeQuery("SELECT currency_value FROM moedas where currency_name = '"+currencyName+"'");
		if(myRs != null && myRs.next()) {
            value = myRs.getDouble("currency_value");
        }
		
		return value;
	}
	
	public static void getAllRegisters() throws SQLException {
		Statement myStmt = myConn.createStatement();
		ResultSet myRs = myStmt.executeQuery("SELECT * FROM registros");
		while(myRs.next()) {
            System.out.print("Id: " + myRs.getInt("id") + ", data: " + myRs.getDate("current_data")
            + ", cliente: " + myRs.getString("client_name") + ", Origem: " + myRs.getString("first_currency")
            + ", Destino: " + myRs.getString("sec_currency") + ", Valor original: " + myRs.getBigDecimal("orig_value")
            + ", Valor convertido: " + myRs.getBigDecimal("converted_value") + ", taxa: " + myRs.getBigDecimal("paid_tax") + "\n");
        }
		
	}
	
	public static void getRegistersByName(String name) throws SQLException{
		Statement myStmt = myConn.createStatement();
		ResultSet myRs = myStmt.executeQuery("select * from registros where client_name = '" + name +"';");
		while(myRs.next()) {
            System.out.print("Id: " + myRs.getInt("id") + ", data: " + myRs.getDate("current_data")
            + ", cliente: " + myRs.getString("client_name") + ", Origem: " + myRs.getString("first_currency")
            + ", Destino: " + myRs.getString("sec_currency") + ", Valor original: " + myRs.getBigDecimal("orig_value")
            + ", Valor convertido: " + myRs.getBigDecimal("converted_value") + ", taxa: " + myRs.getBigDecimal("paid_tax") + "\n");
		}
		
	}
	
	
}









