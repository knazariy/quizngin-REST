package com.nklymok.quizngin.repository;

import com.nklymok.quizngin.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer integer);

    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
