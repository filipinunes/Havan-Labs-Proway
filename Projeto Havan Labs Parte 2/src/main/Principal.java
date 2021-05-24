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
		Conexao.createDataBase();
		Conexao.createCurrencyTable();
		Conexao.createRegisterTable();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		Calculadora calculadora = new Calculadora(10); // Create a calculadora object
		SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");

		int opt = 0;

		do {
			opt = Integer.parseInt(JOptionPane
					.showInputDialog("Opcoes \n 1 - Nova operacao \n 2 - Nova moeda \n 3 - Relatorios \n 9 - Sair"));
			switch (opt) {
			case 1:
				String clientName = JOptionPane.showInputDialog("Nome").toUpperCase();
				String firstCurrency = JOptionPane.showInputDialog("Moeda origem").toUpperCase();
				String secCurrency = JOptionPane.showInputDialog("Moeda destino").toUpperCase();
				double quant = Float.parseFloat(JOptionPane.showInputDialog("Quantidade"));

				double firstCurrencyValue = Conexao.getCurrencyValue(firstCurrency);
				double secCurrencyValue = Conexao.getCurrencyValue(secCurrency);
				double converted = calculadora.convert(firstCurrencyValue, secCurrencyValue, quant);
				double tax = calculadora.tax(quant, firstCurrencyValue);

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
				int filter = Integer.parseInt(JOptionPane.showInputDialog("Opções de filtro "
						+ "\n 1 - Todos por nome "
						+ "\n 2 - Todos registros "
						+ "\n 3 - Valor total"));
				
				if (filter == 1) {
					clientName = JOptionPane.showInputDialog("Nome cliente").toUpperCase();
					Conexao.getRegistersByName(clientName);
				} else {
					Conexao.getAllRegisters();
				}

				break;
			}
		} while (opt != 9);
	}

}
