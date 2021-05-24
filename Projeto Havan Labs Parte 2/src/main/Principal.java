package main;

import connection.Conexao;
import methods.Calculadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Principal {

	public static void main(String[] args) throws SQLException, ParseException {
		Connection myConn = Conexao.createConnection();
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		Calculadora calculadora = new Calculadora(10); // Create a calculadora object
		SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");

		int opt = 0;

		do {			
			opt = Integer.parseInt(JOptionPane.showInputDialog("Opcoes"));
			switch (opt) {
			case 1:
				String clientName = JOptionPane.showInputDialog("Nome").toUpperCase();
				String firstCurrency = JOptionPane.showInputDialog("Moeda origem").toUpperCase();
				String secCurrency = JOptionPane.showInputDialog("Moeda destino").toUpperCase();
				double quant = Float.parseFloat(JOptionPane.showInputDialog("Quantidade"));

				double firstCurrencyValue = Conexao.getCurrencyValue(firstCurrency);
				double secCurrencyValue = Conexao.getCurrencyValue(secCurrency);
				double converted = calculadora.convert(firstCurrencyValue, secCurrencyValue, quant);
				double tax = calculadora.tax(converted);

				try {
					Conexao.insertRegister(clientName, firstCurrency, secCurrency, quant, converted, tax);
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					if (myRs != null) {
						myRs.close();
					}
				}
				break;
			case 2:
				String currencyName = JOptionPane.showInputDialog("Nome moeda").toUpperCase();
				double currencyValue = Float.parseFloat(JOptionPane.showInputDialog("Quantidade"));

				Conexao.insertCurrency(currencyName, currencyValue);
				break;
			case 3:
				int filter = Integer.parseInt(JOptionPane.showInputDialog("Opções de filtro \n 1 - Data\n 2 - Nome do cliente \n 3 - Todos"));
				/*String inicialDateInput = JOptionPane.showInputDialog("Data inicial na ordem yyyy/mm/dd");
				Date inicialDate = format.parse(inicialDateInput);*/
				
				if(filter == 2) {
					clientName = JOptionPane.showInputDialog("Nome moeda").toUpperCase();
					Conexao.getRegistersByName(clientName);					
				} else if (filter == 3){
					Conexao.getAllRegisters();
				}
				
				break;
			}
		} while (opt != 9);
	}

}
















