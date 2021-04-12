package com.pani.melichallenge.web.bean;

import java.io.Serializable;

import com.pani.melichallenge.service.access.Access;
import com.pani.melichallenge.service.access.AccessType;

public class AccessBean implements Serializable{

	private static final long serialVersionUID = 1L;

	AccessType type;

	Access access;

	public AccessType getType() {
		return type;
	}

	public void setType(AccessType type) {
		this.type = type;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

}
