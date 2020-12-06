package com.nklymok.quizngin.repository;

import com.nklymok.quizngin.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Integer> {
    @Override
    Optional<Quiz> findById(Integer integer);
    @Override
    Page<Quiz> findAll(Pageable page);
}
