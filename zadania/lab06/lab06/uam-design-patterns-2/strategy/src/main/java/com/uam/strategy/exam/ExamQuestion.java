package com.uam.strategy.exam;

import java.util.List;

class ExamQuestion {

    private String value;

    private List<ExamAnswer> answers;

    private ExamAnswer correctAnswer;

    ExamQuestion(String value, List<ExamAnswer> answers, ExamAnswer correctAnswer) {
        this.value = value;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    String getValue() {
        return value;
    }

    List<ExamAnswer> getAnswers() {
        return answers;
    }

    ExamAnswer getCorrectAnswer() {
        return correctAnswer;
    }
}
