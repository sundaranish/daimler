package com.example.user.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.user.entity.Users;

@Component
public class UserRepository {

	@Autowired
	private IUserRepository userRepository;

	public Users saveUser(Users user) {
		try {
			user = userRepository.save(user);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	public List<Users> getUser(String userId) {
		List<Users> users;
		try {
			users = userRepository.findByUserId(userId);
		} catch (Exception e) {
			throw e;
		}
		return users;
	}

	public Users getUserByUserName(String userName) {
		List<Users> users;
		try {
			users = userRepository.findByUserName(userName);
			if (users.size() >= 1 && null != users.get(0)) {
				return users.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public List<Users> getAllUser() {
		List<Users> users;
		try {
			users = userRepository.findAll();
		} catch (Exception e) {
			throw e;
		}

		return users;

	}

	public IUserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
