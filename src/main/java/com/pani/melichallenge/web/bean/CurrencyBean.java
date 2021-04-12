package com.pani.melichallenge.web.bean;

import java.io.Serializable;
import java.util.Map;

public class CurrencyBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long timestamp;
	private String base;
	private Map<String, Double> rates;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

}
