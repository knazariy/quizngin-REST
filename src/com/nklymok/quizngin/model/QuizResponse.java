package com.nklymok.quizngin.model;

public class QuizResponse {
    private final boolean success;
    private final String feedback;

    public QuizResponse(boolean correct) {
        if (correct) {
            this.success = true;
            this.feedback = "Congratulations, you're right!";
        } else {
            this.success = false;
            this.feedback = "Wrong answer! Please, try again.";
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
