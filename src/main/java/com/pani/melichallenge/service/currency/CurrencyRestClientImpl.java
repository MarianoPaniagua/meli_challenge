package com.pani.melichallenge.service.currency;

import java.util.List;
import java.util.stream.Collectors;

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
public class CurrencyRestClientImpl implements CurrencyRestClient {

	private static final Logger logger = LoggerFactory.getLogger(CurrencyRestClientImpl.class);
	private final RestTemplate restTemplate;
	private String apiUrl;
	private String apiKey;

	@Autowired
	public CurrencyRestClientImpl(RestTemplateBuilder builder, @Value("${currency.api.url}") String apiUrl,
			@Value("${currency.api.key}") String apiKey) {
		this.apiUrl = apiUrl;
		this.restTemplate = builder.build();
		this.apiKey = apiKey;
		logger.info("CurrencyRestClientImpl started");
	}

	@Override
	public CurrencyResponse getCurrencyData(String baseCurrency, List<String> list) {
		String values = list.stream().map(String::toUpperCase).collect(Collectors.joining(","));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("access_key", apiKey)
				.queryParam("base", baseCurrency).queryParam("symbols", values);
		ResponseEntity<CurrencyResponse> info = null;
		try {
			info = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(null, null),
					new ParameterizedTypeReference<CurrencyResponse>() {
					});
		} catch (HttpStatusCodeException e) {
			logger.error("Error fetching currency info", e);
			return null;
		}
		return info.getBody();
	}

}
