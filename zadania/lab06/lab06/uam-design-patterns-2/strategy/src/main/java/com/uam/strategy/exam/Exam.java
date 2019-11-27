package com.uam.strategy.exam;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Map;

public class Exam {

    private List<ExamQuestion> questions;
    private EvaluationStrategy evaluationStrategy;

    public Exam(List<ExamQuestion> questions, EvaluationStrategy evaluationStrategy) {
        Preconditions.checkArgument(!questions.isEmpty(), "Exam have to have at least one question.");
        this.evaluationStrategy = evaluationStrategy;
        this.questions = questions;
    }

    public List<ExamQuestion> getQuestions() {
        return questions;
    }

    public void switchEvaluationStrategy(EvaluationStrategy newEvaluationStrategy) {
        this.evaluationStrategy = newEvaluationStrategy;
    }

    public Grade evaluate(Map<ExamQuestion, ExamAnswer> answers) {
        return evaluationStrategy.evaluate(answers);
    }
}
