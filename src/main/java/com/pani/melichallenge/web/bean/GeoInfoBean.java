package com.pani.melichallenge.web.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pani.melichallenge.service.country.Currency;

public class GeoInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ip;
	private String country;
	private String iso_code;
	private List<String> languages;
	private CurrencyBean currencyExchanges;
	private Date time;
	private Double distance;
	private String distanceMetric;
	private List<Currency> currencies;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIso_code() {
		return iso_code;
	}

	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public void addLanguage(String language) {
		this.languages.add(language);
	}

	public CurrencyBean getCurrencyExchanges() {
		return currencyExchanges;
	}

	public void setCurrencyExchanges(CurrencyBean currencyExchanges) {
		this.currencyExchanges = currencyExchanges;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

	public String getDistanceMetric() {
		return distanceMetric;
	}

	public void setDistanceMetric(String distanceMetric) {
		this.distanceMetric = distanceMetric;
	}

}
