package com.emmanuel.app.ws.service;

import java.util.List;

import com.emmanuel.app.ws.shared.dto.AdressDTO;

public interface AddressService {

	List<AdressDTO> getAddresses(String userId);
	AdressDTO getAddress(String addressId);
}
