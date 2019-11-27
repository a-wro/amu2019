package com.uam.strategy.exam;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyDataBuilder {

    public List<FinishedExam> getFinishedExams(Exam exam) {
        return ImmutableList.of(
                buildFinishedExam(exam, 3),
                buildFinishedExam(exam, 0),
                buildFinishedExam(exam, 1)
        );
    }

    public Exam buildExam() {
        List<ExamQuestion> questions = new ArrayList<>();

        // first question
        ExamAnswer firstQuestionCorrectAnswer = new ExamAnswer("Lorem Ipsum");
        ExamQuestion firstQuestion = new ExamQuestion("Lorem Ipsum?", Lists.newArrayList(firstQuestionCorrectAnswer), firstQuestionCorrectAnswer);

        // second question
        ExamAnswer secondQuestionCorrectAnswer = new ExamAnswer("Lorem Ipsum");
        ExamQuestion secondQuestion = new ExamQuestion("Lorem Ipsum?", Lists.newArrayList(secondQuestionCorrectAnswer), secondQuestionCorrectAnswer);

        // collect all questions
        questions.add(firstQuestion);
        questions.add(secondQuestion);

        EvaluationStrategy evaluationStrategy = new RegularExamEvaluationStrategy();

        return new Exam(questions, evaluationStrategy);
    }

    private FinishedExam buildFinishedExam(Exam exam, int numberOfCorrectAnswers) {
        Map<ExamQuestion, ExamAnswer> examAnswers = new HashMap<>();

        for (ExamQuestion question : exam.getQuestions()) {
            if (numberOfCorrectAnswers > 0) {
                examAnswers.put(question, question.getCorrectAnswer());
                numberOfCorrectAnswers--;
            } else {
                examAnswers.put(question, new ExamAnswer("wrong answer"));
            }
        }
        return new FinishedExam(exam, examAnswers);
    }

}
