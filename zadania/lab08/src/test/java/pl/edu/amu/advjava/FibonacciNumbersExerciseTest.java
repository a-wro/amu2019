package pl.edu.amu.advjava;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FibonacciNumbersExerciseTest {

    private static final int[] EXPECTED = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55};

    @Test
    public void generateFibonacciNumbers() {

        int[] actual = FibonacciNumbersExercise.generateFibonacciNumbers(10);
        assertThat(actual).isEqualTo(EXPECTED);
    }

    @Test
    public void generateFibonacciNumbersWithLambda() {
        int[] actual = FibonacciNumbersExercise.generateFibonacciNumbersWithLambda(10);
        assertThat(actual).isEqualTo(EXPECTED);
    }
}