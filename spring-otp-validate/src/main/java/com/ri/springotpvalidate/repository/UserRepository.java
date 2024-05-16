package com.ri.springotpvalidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ri.springotpvalidate.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,String> {
  public UserEntity findByUsername(String username);
}
