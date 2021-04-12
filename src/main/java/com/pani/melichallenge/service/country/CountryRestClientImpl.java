package com.pani.melichallenge.service.country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CountryRestClientImpl implements CountryRestClient {

	private static final Logger logger = LoggerFactory.getLogger(CountryRestClientImpl.class);
	private final RestTemplate restTemplate;
	private String apiUrl;

	@Autowired
	public CountryRestClientImpl(RestTemplateBuilder builder, @Value("${country.api.url}") String apiUrl) {
		this.apiUrl = apiUrl;
		this.restTemplate = builder.build();
		logger.info("CountryRestClientImpl started");
	}

	@Override
	public CountryInfoLarge getCountryLargeInfo(String countryCode) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl + "/" + countryCode);
		ResponseEntity<CountryInfoLarge> info = null;
		try {
			info = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(null, null),
					new ParameterizedTypeReference<CountryInfoLarge>() {
					});
		} catch (HttpStatusCodeException e) {
			logger.error("Error fetching country info", e);
			return null;
		}
		return info.getBody();
	}

}
