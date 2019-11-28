package pl.edu.amu.advjava;


import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import pl.edu.amu.advjava.StreamExcercises.CustomNumber;
import pl.edu.amu.advjava.StreamExcercises.CustomNumberCollection;

public class StreamExcercisesTest {

    private static final Collection<CustomNumber> TEST_NUMBERS =
        asList(new CustomNumber(1), new CustomNumber(-2), new CustomNumber(3), new CustomNumber(-4),
            new CustomNumber(5), new CustomNumber(-6));

    @Test
    public void sum() {
        int sum = StreamExcercises.sum(TEST_NUMBERS);
        assertThat(sum).isEqualTo(-3);
    }

    @Test
    public void positiveNumbers() {
        Collection<CustomNumber> positiveNumbers = StreamExcercises.positiveNumbers(TEST_NUMBERS);
        assertThat(positiveNumbers).extracting(n -> n.number).containsExactlyInAnyOrder(1, 3, 5);
    }

    @Test
    public void squares() {
        List<Integer> positiveNumbers = StreamExcercises.squares(TEST_NUMBERS);
        assertThat(positiveNumbers).containsExactlyInAnyOrder(1, 4, 9, 16, 25, 36);
    }

    @Test
    public void sumOfNegativeNumbers() {
        int sumOfNegativeNumbers = StreamExcercises.sumOfNegativeNumbers(TEST_NUMBERS);
        assertThat(sumOfNegativeNumbers).isEqualTo(-12);
    }

    @Test
    public void productOfNumbers() {
        long productOfNumbers = StreamExcercises.productOfNumbers(
            asList(
                new CustomNumberCollection(TEST_NUMBERS),
                new CustomNumberCollection(TEST_NUMBERS)
            )
        );

        assertThat(productOfNumbers).isEqualTo(518400);
    }

    @Test
    public void squareRoots() {
        Map<Integer, Set<Integer>> actual = StreamExcercises.squareRoots(
            asList(new CustomNumber(5), new CustomNumber(6), new CustomNumber(-5), new CustomNumber(-6),
                new CustomNumber(4)));

        assertThat(actual)
            .containsEntry(16,new HashSet<>(asList(4)))
            .containsEntry(25,new HashSet<>(asList(5,-5)))
            .containsEntry(36,new HashSet<>(asList(6,-6)))
            .hasSize(3);
    }
}