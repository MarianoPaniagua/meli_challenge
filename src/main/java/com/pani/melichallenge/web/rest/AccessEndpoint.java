package com.pani.melichallenge.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pani.melichallenge.service.access.AccessService;
import com.pani.melichallenge.service.access.AccessType;
import com.pani.melichallenge.service.geoinfo.GeoInfoServiceImpl.GeoInfoException;
import com.pani.melichallenge.web.bean.AccessBean;

@RestController
@RequestMapping("/server/rest/access")
public class AccessEndpoint {

	AccessService service;

	@Autowired
	public  AccessEndpoint(AccessService service) {
		this.service = service;
	}

	@GetMapping(path = { "/{type}" })
	public AccessBean getAccesss(HttpServletRequest request,
			@PathVariable(name = "type", required = true) AccessType type) throws GeoInfoException {
		return service.getDistance(type);
	}

}
