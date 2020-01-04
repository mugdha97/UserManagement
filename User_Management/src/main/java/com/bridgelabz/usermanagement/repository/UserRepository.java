package com.bridgelabz.usermanagement.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.usermanagement.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUserName(String name);

}
