package com.pani.melichallenge.service.currency;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	private CurrencyRestClient restClient;

	public CurrencyServiceImpl(CurrencyRestClient restClient) {
		super();
		this.restClient = restClient;
	}

	@Override
	public CurrencyResponse getCurrencyData(String baseCurrency, List<String> list) {
		return restClient.getCurrencyData(baseCurrency, list);
	}

}
