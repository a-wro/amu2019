package com.uam.strategy.exam;

import java.util.Map;

public class RegularExamEvaluationStrategy implements EvaluationStrategy {

    public Grade evaluate(Map<ExamQuestion, ExamAnswer> answers) {
        double points = 0;

        for (Map.Entry<ExamQuestion, ExamAnswer> entry : answers.entrySet()) {
            ExamAnswer correctAnswer = entry.getKey().getCorrectAnswer();
            ExamAnswer actualAnswer = entry.getValue();

            if (correctAnswer.equals(actualAnswer)) {
                points += 1;
            }
        }
            return pointsToGrade(points, answers.size());
    }


    /**
     *
     * @param points - amount of points that someone got
     * @param maxPoints - maximum amount of points for an exam
     * @return
     */
    private Grade pointsToGrade(double points, double maxPoints) {
        double percentage = points / maxPoints;

        if (percentage < 0.2) {
            return Grade.F;
        } else if (percentage < 0.3) {
            return Grade.E;
        } else if (percentage < 0.5) {
            return Grade.D;
        } else if (percentage < 0.6) {
            return Grade.C;
        } else if (percentage < 0.7) {
            return Grade.B;
        }
        return Grade.A;
    }
}
