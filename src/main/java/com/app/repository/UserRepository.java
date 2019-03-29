package com.app.repository;

import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    long countByUserNameAndPassWord(String userName, String passWord);

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);
}
