package com.emmanuel.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emmanuel.app.ws.exceptions.UserServiceException;
import com.emmanuel.app.ws.service.AddressService;
import com.emmanuel.app.ws.service.UserService;
import com.emmanuel.app.ws.shared.dto.AdressDTO;
import com.emmanuel.app.ws.shared.dto.UserDto;
import com.emmanuel.app.ws.ui.model.request.UserDetailsRequestModel;
import com.emmanuel.app.ws.ui.model.response.AddressRest;
import com.emmanuel.app.ws.ui.model.response.ErrorMessages;
import com.emmanuel.app.ws.ui.model.response.OperationStatusModel;
import com.emmanuel.app.ws.ui.model.response.RequestOperationStatus;
import com.emmanuel.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressService addressesService; 

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	// this method will be triggered when it receives the http post request
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		// Instantiating userRst object
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new NullPointerException("The object is Null");

		// Creating User data transfer Object populating this object with information
		// I've received
		// from request body
		// UserDto userDto = new UserDto();
		// BeanUtils.copyProperties(userDetails, userDto);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		// createUser method will be back to userDto wic will can use returnValue with
		// information that
		// we'll going back to the user
		UserDto createdUser = userService.createUser(userDto);
		// will take the created user object and then will copy information into the
		// return value
		returnValue = modelMapper.map(createdUser, UserRest.class);
		// info that will be return to postMan httpClient
		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		// Instantiating userRst object
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new NullPointerException("The object is Null");

		// Creating User data transfer Object populating this object with information
		// I've received
		// from request body
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		// createUser method will be back to userDto wic will can use returnValue with
		// information that
		// we'll going back to the user
		UserDto updatedUser = userService.updateUser(id, userDto);
		// will take the created user object and then will copy information into the
		// return value
		BeanUtils.copyProperties(updatedUser, returnValue);
		// info that will be return to postMan httpClient
		return returnValue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteMapping(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "2") int limit) {
		List<UserRest> returnValue = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;

	}

	// http:localhost/8080/mobile-app-ws/users/gfdkgjdsg/addresses
	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public CollectionModel<AddressRest> getUserAddresses(@PathVariable String id) {
		List<AddressRest> returnValue = new ArrayList<>();
		List<AdressDTO> addressDto = addressesService.getAddresses(id);
		if (addressDto != null && !addressDto.isEmpty()) {
			java.lang.reflect.Type listType = new TypeToken<List<AddressRest>>() {
			}.getType();

			returnValue = new ModelMapper().map(addressDto, listType);
			
			for(AddressRest addressRest : returnValue) {
				Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
						.getUserAddress(id,addressRest.getAddressId()))
						.withSelfRel();
				addressRest.add(selfLink);
			}
		}
		
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserAddresses(id))
				.withSelfRel();
		
		return CollectionModel.of(returnValue, userLink, selfLink);
		
		
	}
	
	/**
	 * @param userId
	 * @param addressId
	 * @return
	 */
	@GetMapping(path="/{userId}/addresses/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE})
	public EntityModel<AddressRest> getUserAddress(@PathVariable String userId,@PathVariable String addressId) {
		
		AdressDTO adressDTO = addressService.getAddress(addressId);
		
		ModelMapper modelMapper = new ModelMapper();
		AddressRest returnValue = modelMapper.map(adressDTO, AddressRest.class);
		
		//http://localhost:8080/users/<userId>
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");
		//http://localhost:8080/users/<userId>/addresses
		Link userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId))
				//.slash(userId)
				//.slash("addresses")
				.withRel("addresses");
		//http://localhost:8080/users/<userId>/addresses/{addressesId}
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserAddress(userId,addressId))
				.withSelfRel();
				//.slash(userId)
				//.slash("addresses")
				//.slash(addressId)
		
		//returnValue.add(userLink);
		//returnValue.add(userAddressesLink);
		//returnValue.add(selfLink);
		
	
		return 	EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink ));
	}
	
	//http://localhost:8080/mobile-app-ws/users/email-verification?token?sddsa
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(path="/email-verification", produces = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE})
	public OperationStatusModel verifyEmailToken (@RequestParam(value = "token")String token){
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
		
		boolean isVerified = userService.verifyEmailToken(token);
		
		if(isVerified)
		{
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}
		else
		{
			returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
		}
		
		return returnValue;
		
	}

}
