package com.uam.strategy;

import com.uam.strategy.exam.DummyDataBuilder;
import com.uam.strategy.exam.Exam;
import com.uam.strategy.exam.FinalExamEvaluationStrategy;
import com.uam.strategy.exam.FinishedExam;

import java.util.List;

public class Runner {

    public static void main(String[] args) {
        DummyDataBuilder dataBuilder = new DummyDataBuilder();
        Exam exam = dataBuilder.buildExam();
        List<FinishedExam> finishedExams = dataBuilder.getFinishedExams(exam);

        // use final exam evaluate strategy
        System.out.println("Tested as final exam:");
        finishedExams
                .stream()
                .map(FinishedExam::evaluate)
                .forEachOrdered(System.out::println);


        // switch evaluation strategy
        exam.switchEvaluationStrategy(new FinalExamEvaluationStrategy());

        // use regular exam evaluate strategy
        System.out.println("Tested as regular exam:");
        finishedExams
                .stream()
                .map(FinishedExam::evaluate)
                .forEachOrdered(System.out::println);
    }
}
