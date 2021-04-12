package com.pani.melichallenge.web.rest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pani.melichallenge.service.geoinfo.GeoInfoService;
import com.pani.melichallenge.service.geoinfo.GeoInfoServiceImpl.GeoInfoException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GeoInfoEndpointTest {

	private static final String BASE_URL = "/server/rest/geoinfo";

	@Mock
	private GeoInfoService service;

	private MockMvc mockMvc;
	
	@InjectMocks
	private GeoInfoEndpoint geoInfoEndpoint;

	@Before
	public void setUp() {
		this.mockMvc = standaloneSetup(geoInfoEndpoint).build();
	}

	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get(BASE_URL + "/18.188.12.12").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		verify(service, times(1)).getGeoInfo(anyString());
	}

	@Test
	public void testException() throws Exception {
		when(service.getGeoInfo(anyString())).thenThrow(new GeoInfoException("Error"));
		mockMvc.perform(get(BASE_URL + "/18.188.12.12").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		verify(service, times(1)).getGeoInfo(anyString());
	}

}
