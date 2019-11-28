package pl.edu.amu.advjava;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

final class StreamExcercises {

    /*
         ZADANIE: dla podanej kolekcji zwróć sumę opakowanych liczb.
     */
    static int sum(Collection<CustomNumber> numbers) {
        return numbers.stream()
                .map(cn -> cn.number)
                .reduce(Integer::sum).get();
    }

    /*
         ZADANIE: dla podanej kolekcji zwróć tylko te nieujemne.
     */
    static List<CustomNumber> positiveNumbers(Collection<CustomNumber> numbers) {
        return numbers.stream()
                .filter(CustomNumber::isPositive)
                .collect(Collectors.toList());
    }

    /*
         ZADANIE: dla podanej kolekcji zwróć kolekcję zawierającą kwadraty opakowanych liczb.
     */
    static List<Integer> squares(Collection<CustomNumber> numbers) {
        return numbers.stream()
                .map(customNumber -> customNumber.number)
                .map(number -> number * number)
                .collect(Collectors.toList());
    }

    /*
         ZADANIE: dla podanej kolekcji zwróć sumę liczb ujemnych z kolekcji.
     */
    static int sumOfNegativeNumbers(Collection<CustomNumber> numbers) {
        return numbers.stream()
                .filter(CustomNumber::isNegative)
                .map(cn -> cn.number)
                .reduce(0, Integer::sum);
    }

    /*
         ZADANIE: dla podanej kolekcji iloczyn liczb z wszystkich kolekcji.
     */
    static long productOfNumbers(Collection<CustomNumberCollection> customNumberCollection) {

        LongBinaryOperator longMultiplication = (l1, l2) -> l1 * l2;
        long longIdentity = 1;

        return customNumberCollection.stream()
                .flatMapToLong(collection ->
                        LongStream.of(collection.getCustomNumbers().stream().mapToLong(customNumber ->
                                (long) customNumber.number)
                                .reduce(longIdentity, longMultiplication)))
                .reduce(longIdentity, longMultiplication);
    }

    /*
         ZADANIE: zwracana mapa powinna zawierać jako klucz kwadrat liczby z kolekcji, a jako wartość - liczby,
         które są pierwiastkami kwadratowymi tej liczby.
     */
    static Map<Integer, Set<Integer>> squareRoots(Collection<CustomNumber> numbers) {

        Function<Integer, Set<Integer>> findSquareRoots = (number) ->
                numbers.stream()
                        .map(customNumber -> customNumber.number)
                        .filter(integer -> Math.pow(integer, 2) == number)
                        .collect(Collectors.toSet());

        return numbers.stream()
                .map(customNumber -> customNumber.number * customNumber.number)
                .collect(
                        Collectors.toMap(Function.identity(),
                                findSquareRoots,
                                (prev, current) -> current));
    }

    static final class CustomNumber {
        int number;

        CustomNumber(int number) {
            this.number = number;
        }

        boolean isPositive() {
            return number >= 0;
        }

        boolean isNegative() {
            return !isPositive();
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    static final class CustomNumberCollection {
        private Collection<CustomNumber> customNumbers;

        CustomNumberCollection(Collection<CustomNumber> customNumbers) {
            this.customNumbers = customNumbers;
        }

        Collection<CustomNumber> getCustomNumbers() {
            return customNumbers;
        }
    }
}
