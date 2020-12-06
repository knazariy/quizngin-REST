package com.nklymok.quizngin.controller;

import com.nklymok.quizngin.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.nklymok.quizngin.repository.CompletionRepository;
import com.nklymok.quizngin.repository.QuizRepository;
import com.nklymok.quizngin.repository.UserRepository;
import com.nklymok.quizngin.service.CompletionService;
import com.nklymok.quizngin.service.QuizService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private final UserRepository userRepository;
    private final CompletionRepository completionRepository;
    private final CompletionService completionService;

    @Autowired
    public QuizController(QuizRepository quizRepository,
                          QuizService quizService,
                          UserRepository userRepository,
                          CompletionRepository completionRepository,
                          CompletionService completionService) {
        this.quizRepository = quizRepository;
        this.quizService = quizService;
        this.userRepository = userRepository;
        this.completionRepository = completionRepository;
        this.completionService = completionService;
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Integer id, Principal principal) {
        Quiz response = quizRepository.findById(id).orElse(null);
        if (response == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Unable to find the quiz");
        }

        System.out.println(principal.getName());

        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Integer id, Principal principal) {
        Quiz targetQuiz = quizRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if (targetQuiz.getAuthorUsername().equals(principal.getName())) {
            quizRepository.delete(targetQuiz);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, Principal principal) {
        quiz.setAuthorUsername(principal.getName());
        return quizRepository.save(quiz);
    }

    @GetMapping
    public Iterable<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return quizService.getAllQuizzesPaged(page, pageSize, sortBy);
    }

    @PostMapping("/{id}/solve")
    public QuizResponse checkSolution(@PathVariable Integer id,
                                      @RequestBody(required = false) UserResponse userResponse,
                                      Principal principal) {
        Quiz solvedQuiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Unable to find the quiz"));

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User Not Found by Username"
        ));

        if (userResponse == null) {
            return new QuizResponse(solvedQuiz.check(null));
        }
        QuizResponse quizResponse = new QuizResponse(solvedQuiz.check(userResponse.getAnswers()));

        if (quizResponse.isSuccess()) {
            Completion completion = new Completion(Instant.now(), solvedQuiz.getId());
            completion.setUser(user);
            completionRepository.save(completion);
        }

        return quizResponse;
    }

    @GetMapping("/completed")
    public Iterable<Completion> getAllCompletions(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "time") String sortBy,
                                                  Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User Not Found by Username"
        ));
        return completionService.getAllCompletionsPaged(user.getId(), page, pageSize, sortBy);
    }
}
