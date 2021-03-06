package com.nklymok.quizngin.service;

import com.nklymok.quizngin.model.Completion;
import com.nklymok.quizngin.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    private CompletionRepository repository;

    @Autowired
    public CompletionService(CompletionRepository repository) {
        this.repository = repository;
    }

    public Page<Completion> getAllCompletionsPaged(Integer userId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return repository.findAllByUser_Id(userId, paging);
    }

}
