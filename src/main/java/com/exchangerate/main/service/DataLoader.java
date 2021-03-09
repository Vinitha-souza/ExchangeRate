package com.exchangerate.main.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.exchangerate.main.dao.CurrencyRepository;
import com.exchangerate.main.model.Currency;

@Component
public class DataLoader implements ApplicationRunner {

	private CurrencyRepository currRepository;

	@Autowired
	public DataLoader(CurrencyRepository currRepository) {
		this.currRepository = currRepository;
	}

	/* This method will run on load of application 
	   External Api is used here to get all latest exchangerates
	*/
	public void run(ApplicationArguments args) {
		final String uri = "https://api.exchangeratesapi.io/latest";

		RestTemplate restTemplate = new RestTemplate();
		ExteranlECR result = restTemplate.getForObject(uri, ExteranlECR.class);

		// System.out.println(result.rates);

		Iterator it = result.rates.entrySet().iterator();
		while (it.hasNext()) {
			Currency currency = new Currency();
			Map.Entry<String, Double> pair = (Map.Entry) it.next();
			currency.setName(pair.getKey());
			currency.setSpot(pair.getValue());
			//Exchangerates will be saved to in memory h2 database
			currRepository.save(currency);
		}

	}
}

class ExteranlECR {
	public Map<String, Double> rates = new HashMap<String, Double>();
	public String base;
	public Date date;
}