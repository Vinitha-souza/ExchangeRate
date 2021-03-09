package com.exchangerate.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.exchangerate.main.service.CurrencyService;
import com.exchangerate.main.service.Response;

@RestController
public class CurrencyController {

	@Autowired
	private CurrencyService currencyservice;

    //To retrieve list of supported currencies
	@GetMapping("/getAllCurrencies")
	public List<Response> getAllCurrencies() {
		return currencyservice.getAllCurrencies();
	}

	// To retrieve the ECB reference rate
	@GetMapping("/getECBReferenceRate")
	public ResponseEntity<Response> getECBReferenceRate(@RequestParam(required = false) String fromCurrency,
			@RequestParam String toCurrency, @RequestParam(required = false) Double value) {

		// required toCurrency
		if (toCurrency == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		return currencyservice.getECBReferenceRate(fromCurrency, toCurrency, value);
	}
}
