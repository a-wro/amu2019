package com.uam.strategy.exam;

import java.util.Map;

public class FinishedExam {

    private final Exam exam;

    private final Map<ExamQuestion, ExamAnswer> examAnswers;

    FinishedExam(Exam exam, Map<ExamQuestion, ExamAnswer> examAnswers) {
        this.exam = exam;
        this.examAnswers = examAnswers;
    }

    public Grade evaluate() {
        return exam.evaluate(examAnswers);
    }
}
