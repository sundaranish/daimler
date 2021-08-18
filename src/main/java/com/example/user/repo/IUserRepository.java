package com.example.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user.entity.Users;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer> {

	public List<Users> findByUserId(String userId);

	public List<Users> findByUserName(String userName);

}
