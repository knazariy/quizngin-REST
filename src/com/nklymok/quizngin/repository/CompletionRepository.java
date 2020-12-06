package com.nklymok.quizngin.repository;

import com.nklymok.quizngin.model.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends PagingAndSortingRepository<Completion, Integer> {
    Page<Completion> findAllByUser_Id(Integer user_id, Pageable pageable);
}
