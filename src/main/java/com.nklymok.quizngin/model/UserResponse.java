package com.nklymok.quizngin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserResponse {
    List<Integer> answers;

    @JsonCreator
    public UserResponse(@JsonProperty("answer") List<Integer> answers) {
        this.answers = answers;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answer) {
        this.answers = answer;
    }
}
