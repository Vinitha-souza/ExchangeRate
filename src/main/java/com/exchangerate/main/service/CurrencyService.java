package com.exchangerate.main.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.exchangerate.main.dao.CurrencyRepository;
import com.exchangerate.main.model.Currency;

@Service
public class CurrencyService {
	@Autowired
	private CurrencyRepository currencyRepo;

	public ResponseEntity<Response> getECBReferenceRate(String fromCurrencyName, String toCurrencyName, Double value) {
		ResponseEntity<Response>response = null;
		// Find currency from database
		Currency toCurrency = currencyRepo.findByname(toCurrencyName.toUpperCase());

		if (toCurrency == null) {
			return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
		}

		
		// since our base currency is euro
		if (fromCurrencyName == null || fromCurrencyName.toUpperCase() == "EURO") {
			Response res = new Response(toCurrency.getName(), toCurrency.getSpot(), null);
			response = new ResponseEntity<Response>(res, HttpStatus.OK);
		}

		if (fromCurrencyName != null) {
			// get the from currency from the database
			Currency fromCurrency = currencyRepo.findByname(fromCurrencyName.toUpperCase());

			if (fromCurrency != null) {

				double exchangeRate = findExchangeRate(fromCurrency, toCurrency, value);
				Response res = new Response(toCurrency.getName(), toCurrency.getSpot(), exchangeRate);
				response = new ResponseEntity<Response>(res, HttpStatus.OK);
			}
		}

		return response;
	}

	// Business logic based on our Base currency Euro
	private double findExchangeRate(Currency fromCurrency, Currency toCurrency, Double value) {
		try {
			double baseCurrency = 1 / fromCurrency.getSpot();
			double exChangeRate = baseCurrency * toCurrency.getSpot();
			return value == null ? exChangeRate : exChangeRate * value;
		} catch (ArithmeticException e) {
			System.out.println(e);
			return 0;
		}
	}

	// Send only required data
	public List<Response> getAllCurrencies() {
		try {
			List<Currency> currencies = currencyRepo.findAll();
			return currencies.stream().map(currency -> {
				return new Response(currency.getName(), currency.getSpot(), null);
			}).collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}


