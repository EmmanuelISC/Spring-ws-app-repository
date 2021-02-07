package com.emmanuel.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmanuel.app.ws.io.entity.AddressEntity;
import com.emmanuel.app.ws.io.entity.UserEntity;
import com.emmanuel.app.ws.io.repositories.AddressRepository;
import com.emmanuel.app.ws.io.repositories.UserRepository;
import com.emmanuel.app.ws.service.AddressService;
import com.emmanuel.app.ws.shared.dto.AdressDTO;
import com.emmanuel.app.ws.ui.model.response.AddressRest;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AdressDTO> getAddresses(String userId) {
		
		List<AdressDTO> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity==null) return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		for(AddressEntity addressEntity:addresses)
		{
			returnValue.add(modelMapper.map(addressEntity, AdressDTO.class));
		}
		return returnValue;
	}

	@Override
	public AdressDTO getAddress(String addressId) {
		
		AdressDTO returnValue =  null;
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if(addressEntity != null)
		{
			returnValue = new ModelMapper().map(addressEntity, AdressDTO.class);
		}
		
		return returnValue;
	}

}
