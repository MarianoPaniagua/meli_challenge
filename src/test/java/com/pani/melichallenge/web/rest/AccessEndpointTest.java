package com.pani.melichallenge.web.rest;

import static org.mockito.ArgumentMatchers.any;
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

import com.pani.melichallenge.service.access.AccessService;
import com.pani.melichallenge.service.geoinfo.GeoInfoServiceImpl.GeoInfoException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccessEndpointTest {

	private static final String BASE_URL = "/server/rest/access";

	@Mock
	private AccessService service;

	private MockMvc mockMvc;

	@InjectMocks
	private AccessEndpoint accessEndpoint;

	@Before
	public void setUp() {
		this.mockMvc = standaloneSetup(accessEndpoint).build();
	}

	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get(BASE_URL + "/LEJANO").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		verify(service, times(1)).getDistance(any());
	}

	@Test
	public void testException() throws Exception {
		when(service.getDistance(any())).thenThrow(new GeoInfoException("Error"));
		mockMvc.perform(get(BASE_URL + "/LEJANO").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		verify(service, times(1)).getDistance(any());
	}

	@Test
	public void testInvalidParameter() throws Exception {
		mockMvc.perform(get(BASE_URL + "/NOTANLEJANO").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		verify(service, times(0)).getDistance(any());
	}

}
