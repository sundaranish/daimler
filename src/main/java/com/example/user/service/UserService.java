package com.example.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.entity.Users;
import com.example.user.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Users saveUserDetails(Users user) {
		try {
			System.out.println("Entering post user service...");

			System.out.println(user.getTitle());
			System.out.println(user.getDescription());
			System.out.println(user.getUserId());
			user = userRepository.saveUser(user);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	public List<Users> getUserByUserId(String userId) {
		List<Users> user;
		try {
			System.out.println("Entering getAllUser...");

			user = userRepository.getUser(userId);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	public List<Users> getAllUser() {
		List<Users> user;
		try {
			System.out.println("Entering getAllUser...");

			user = userRepository.getAllUser();
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
