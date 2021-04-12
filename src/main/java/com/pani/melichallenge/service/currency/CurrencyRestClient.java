package com.pani.melichallenge.service.currency;

import java.util.List;

public interface CurrencyRestClient {
	
	CurrencyResponse getCurrencyData(String baseCurrency, List<String> list);

}
