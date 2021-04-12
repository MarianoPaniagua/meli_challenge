package com.pani.melichallenge.service.geoinfo;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pani.melichallenge.service.country.CountryInfoLarge;
import com.pani.melichallenge.service.country.CountryService;
import com.pani.melichallenge.service.country.Currency;
import com.pani.melichallenge.service.country.Language;
import com.pani.melichallenge.service.currency.CurrencyResponse;
import com.pani.melichallenge.service.currency.CurrencyService;
import com.pani.melichallenge.service.ip.IpService;
import com.pani.melichallenge.service.queue.QueueService;
import com.pani.melichallenge.web.bean.CurrencyBean;
import com.pani.melichallenge.web.bean.GeoInfoBean;

@Service
public class GeoInfoServiceImpl implements GeoInfoService {

	private IpService ipService;
	private CountryService countryService;
	private CurrencyService currencyService;
	private QueueService queueService;
	private static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public GeoInfoServiceImpl(IpService ipService, CountryService countryService, CurrencyService currencyService,
			QueueService queueService) {
		this.ipService = ipService;
		this.countryService = countryService;
		this.currencyService = currencyService;
		this.queueService = queueService;
	}

	@Override
	public GeoInfoBean getGeoInfo(String ip) {
		try {
			String code = ipService.getCountryInfoByIp(ip).getCountryCode().toLowerCase();
			CountryInfoLarge largeInfo = countryService.getCountryLargeInfo(code);
			double distance = distanceFromBsAs(largeInfo, "K");
			CurrencyResponse currency = currencyService.getCurrencyData("EUR",
					largeInfo.getCurrencies().stream().map(Currency::getCode).collect(Collectors.toList()));
			GeoInfoBean bean = createBean(largeInfo, distance, createCurrencyBean(currency), ip);
			queueService.sendMessage(mapper.writeValueAsString(bean));
			return bean;
		} catch (Exception e) {
			throw new GeoInfoException("Error fetching data", e);
		}
	}

	private CurrencyBean createCurrencyBean(CurrencyResponse currency) {
		CurrencyBean bean = new CurrencyBean();
		bean.setBase(currency.getBase());
		bean.setRates(currency.getRates());
		bean.setTimestamp(currency.getTimestamp());
		return bean;
	}

	private GeoInfoBean createBean(CountryInfoLarge largeInfo, double distance, CurrencyBean currency, String ip) {
		GeoInfoBean info = new GeoInfoBean();
		info.setCountry(largeInfo.getName());
		info.setDistance(distance);
		info.setIso_code(largeInfo.getAlpha2Code());
		info.setIp(ip);
		info.setCurrencies(largeInfo.getCurrencies());
		info.setCurrencyExchanges(currency);
		info.setTime(new Date());
		info.setDistanceMetric("Km");
		info.setLanguages(largeInfo.getLanguages().stream().map(Language::getName).collect(Collectors.toList()));
		return info;
	}

	// -34.607337, -58.376465 taken from Google Maps :D
	private double distanceFromBsAs(CountryInfoLarge countryDestination, String unit) {
		if ((-34.607337 == countryDestination.getLatlng().get(0))
				&& (-58.376465 == countryDestination.getLatlng().get(1))) {
			return 0;
		} else {
			double theta = -58.376465 - countryDestination.getLatlng().get(1);
			double dist = Math.sin(Math.toRadians(-34.607337))
					* Math.sin(Math.toRadians(countryDestination.getLatlng().get(0)))
					+ Math.cos(Math.toRadians(-34.607337))
							* Math.cos(Math.toRadians(countryDestination.getLatlng().get(0)))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public static class GeoInfoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public GeoInfoException(String message, Throwable e) {
			super(message, e);
		}

		public GeoInfoException(String message) {
			super(message);
		}

	}

}
