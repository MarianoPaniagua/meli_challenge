package com.pani.melichallenge.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pani.melichallenge.service.geoinfo.GeoInfoService;
import com.pani.melichallenge.web.bean.GeoInfoBean;

@RestController
@RequestMapping("/server/rest/geoinfo")
public class GeoInfoEndpoint {

	private GeoInfoService service;

	@Autowired
	public GeoInfoEndpoint(GeoInfoService service) {
		super();
		this.service = service;
	}

	@GetMapping(path = { "/{ip}" })
	public GeoInfoBean getGeoInfo(HttpServletRequest request, @PathVariable(name = "ip", required = true) String ip)
			throws JsonProcessingException {
		return service.getGeoInfo(ip);
	}
}
