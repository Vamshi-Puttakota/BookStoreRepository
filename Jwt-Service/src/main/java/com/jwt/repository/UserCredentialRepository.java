package com.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.entity.UserCredential;

import java.util.Optional;
import java.util.List;


public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
   // Optional<UserCredential> findByName(String username);
    Optional<UserCredential> findByCustomerId(String customerId);;
}