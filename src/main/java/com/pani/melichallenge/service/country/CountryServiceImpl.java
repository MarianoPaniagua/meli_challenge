package com.pani.melichallenge.service.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

	private CountryRestClient countryRestClient;

	@Autowired
	public CountryServiceImpl(CountryRestClient countryRestClient) {
		this.countryRestClient = countryRestClient;
	}

	@Override
	@Cacheable(value = "countries")
	public CountryInfoLarge getCountryLargeInfo(String countryCode) {
		return countryRestClient.getCountryLargeInfo(countryCode);
	}

}
