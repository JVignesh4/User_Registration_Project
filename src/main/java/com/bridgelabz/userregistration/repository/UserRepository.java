package com.bridgelabz.userregistration.repository;

import com.bridgelabz.userregistration.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByEmail(String email);


}