package com.pani.melichallenge.service.currency;

import java.util.List;

public interface CurrencyService {
	
	CurrencyResponse getCurrencyData(String baseCurrency, List<String> list);


}
