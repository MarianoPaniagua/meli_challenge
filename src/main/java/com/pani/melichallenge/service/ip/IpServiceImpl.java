package com.pani.melichallenge.service.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpServiceImpl implements IpService {

	private IpRestClient ipRestClient;

	@Autowired
	public IpServiceImpl(IpRestClient ipRestClient) {
		this.ipRestClient = ipRestClient;
	}

	@Override
	public CountryInfo getCountryInfoByIp(String ip) {
		return ipRestClient.getInfoByIp(ip);
	}

}
