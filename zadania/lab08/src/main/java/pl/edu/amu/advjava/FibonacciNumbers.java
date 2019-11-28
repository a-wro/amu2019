package pl.edu.amu.advjava;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

final class FibonacciNumbersExercise {

    /*
         ZADANIE: dopasuj implementację klasy FibonacciSupplier tak, żeby generowała kolejne liczby ciągu
         Fibonacciego.
     */
    static int[] generateFibonacciNumbers(int n) {
        return IntStream.generate(new FibonacciSupplier()).limit(n).toArray();
    }

    /*
     *   ZADANIE: dopasuj implementację wyrażenia lambda tak, żeby generowało kolejne liczby ciągu Fibonacciego.
     */
    static int[] generateFibonacciNumbersWithLambda(int n) {

        /*
        F0 = 0
        F1 = 1
        Fn = Fn-1 + Fn-2
        Fn-2 = Fn-1
        Fn-1 = Fn
         */

        final int F0 = 0;
        final int F1 = 1;

        int[] f = {F0, F1};

        return IntStream.generate(() -> {
            int cache = f[0] + f[1];
            f[0] = f[1];
            f[1] = cache;
            return f[0];

        }).limit(n).toArray();
    }

}

final class FibonacciSupplier implements IntSupplier {

    private static final int fib0 = 0;
    private static final int fib1 = 1;

    private int previous = fib0;
    private int current = fib1;

    @Override
    public int getAsInt() {
        int next = current;
        current += previous;
        previous = next;
        return next;
    }
}

