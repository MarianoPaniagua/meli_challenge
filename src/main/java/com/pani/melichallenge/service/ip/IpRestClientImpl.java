package com.pani.melichallenge.service.ip;

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
public class IpRestClientImpl implements IpRestClient {

	private static final Logger logger = LoggerFactory.getLogger(IpRestClientImpl.class);
	private final RestTemplate restTemplate;
	private String apiUrl;

	@Autowired
	public IpRestClientImpl(RestTemplateBuilder builder, @Value("${ip.api.url}") String apiUrl) {
		this.apiUrl = apiUrl;
		this.restTemplate = builder.build();
		logger.info("IpRestClientImpl started");
	}

	// api get coordinates from the middle of the country
	@Override
	public CountryInfo getInfoByIp(String ip) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl + "?" + ip);
		ResponseEntity<CountryInfo> info = null;
		try {
			info = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(null, null),
					new ParameterizedTypeReference<CountryInfo>() {
					});
		} catch (HttpStatusCodeException e) {
			logger.error("Error fetching ip info", e);
			return null;
		}
		return info.getBody();
	}

}
