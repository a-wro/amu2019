package com.uam.strategy.exam;

import java.util.Map;

interface EvaluationStrategy {

    Grade evaluate(Map<ExamQuestion, ExamAnswer> answers);

}
