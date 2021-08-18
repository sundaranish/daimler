package com.example.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.util.JwtUtil;
import com.example.user.entity.AuthRequest;
import com.example.user.entity.AuthResponse;
import com.example.user.entity.Users;
import com.example.user.model.UserDto;
import com.example.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Users> addUser(@RequestHeader Map<String, String> headers, @RequestBody UserDto userDto) {
		Users user;
		try {
			System.out.println("Adding a new user " + userDto.getUserId());
			user = convertToEntity(userDto);
			user = userService.saveUserDetails(user);

		} catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/authenticate")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public AuthResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			
			System.out.println("authenticating a user " + authRequest.getUserName());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		} catch (Exception e) {
			throw new Exception("Invalid username/password");
		}
		String token = jwtUtil.generateToken(authRequest.getUserName());
		Date d = jwtUtil.extractExpiration(token);
		long secs = (d.getTime()) / 1000;
		AuthResponse authResponse = new AuthResponse();
		authResponse.setToken(token);
		authResponse.setExpiresIn(secs);
		return authResponse;
	}

	@GetMapping("/user/{userId}")
	@ResponseBody
	public ResponseEntity<List<UserDto>> getUsersByUserId(@RequestHeader Map<String, String> headers,
			@PathVariable("userId") String userId) {
		List<Users> users;
		List<UserDto> usersDtos = new ArrayList<UserDto>();
		try {
			System.out.println("getting a user - " + userId);
			users = userService.getUserByUserId(userId);
			if (users.size() >= 1) {
				for (Users user : users) {
					UserDto userDto = convertToDto(user);
					usersDtos.add(userDto);
				}
				return new ResponseEntity<>(usersDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(usersDtos, HttpStatus.OK);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@GetMapping("/user")
	@ResponseBody
	public ResponseEntity<List<UserDto>> getAllUser(@RequestHeader Map<String, String> headers) {
		List<Users> users;
		List<UserDto> usersDtos = new ArrayList<UserDto>();
		try {
			System.out.println("getting all user...");
			users = userService.getAllUser();
			if (users.size() >= 1) {
				for (Users user : users) {
					UserDto userDto = convertToDto(user);
					usersDtos.add(userDto);
				}
				return new ResponseEntity<>(usersDtos, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(usersDtos, HttpStatus.OK);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	private UserDto convertToDto(Users user) throws ParseException {
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}

	private Users convertToEntity(UserDto userDto) throws ParseException {
		Users user = modelMapper.map(userDto, Users.class);
		return user;
	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
