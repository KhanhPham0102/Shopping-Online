package com.app.repository;

import com.app.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findByName(String name);

    List<Store> findByUserId(Integer userId);

    Optional<Store> findByIdAndUserId(Integer id, Integer userId);
}
