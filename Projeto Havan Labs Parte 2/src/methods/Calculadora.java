package methods;

import java.util.HashMap;

public class Calculadora {
	private double tax;
	
	public Calculadora(double tax) {
		this.tax = tax / 100;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax / 100;
	}
	
	public double convert(double origin, double destiny, double quant) {
		return (origin * quant) / destiny;
	}
	
	public double tax(double quant, double currency) {
		return quant * currency * tax;
	}
	
	public String sum(String conversao, String taxa) {
		double sum = Double.parseDouble(conversao.replaceAll(",", ".")) + Double.parseDouble(taxa.replaceAll(",", "."));
		return String.format("%.2f", sum);
	}

}
