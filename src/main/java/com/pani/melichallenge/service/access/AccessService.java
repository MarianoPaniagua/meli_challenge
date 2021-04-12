package com.pani.melichallenge.service.access;

import com.pani.melichallenge.web.bean.AccessBean;

public interface AccessService {

	public AccessBean getDistance(AccessType type);

	void storeNewCall(String code, Double distance, String country);
}
