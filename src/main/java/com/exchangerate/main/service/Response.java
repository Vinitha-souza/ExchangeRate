package com.exchangerate.main.service;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

public class Response {
	public String currency;
	public Double spot;
	public Double convertedExValue;
	public String link;

	Response(String currency, Double spot, Double convertedExValue) {
		this.currency = currency;
		this.spot = spot;
		if (convertedExValue != null) {
			try {
			this.convertedExValue = Double.parseDouble((new DecimalFormat("####0.0000")).format(convertedExValue));
			} catch (ArithmeticException e) {
				System.out.println(e);
			}
			this.link = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/eurofxref-graph-"
					+ currency.toLowerCase() + ".en.html";
		}
	}

}
