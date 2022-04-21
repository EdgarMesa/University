// Version 1 factors v to form all pairs of divisors v (with multiplicity i.e.
// the same pairs of divisors can be formed many ways).
//
// Version 2 tests all possible integers less than sqrt(v) to see if they are a
// fang.
//
// Which do you think is faster? Which would you rather code?

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class VampireNumbersV1
{

    private static final BufferedReader CIN = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter COUT = new PrintWriter(new BufferedOutputStream(System.out));

    // Going to use a tree set as it will keep the primes in order
    private static final TreeSet<Integer> PRIME_SET = new TreeSet<>();

    public static void main(String[] args) throws IOException
    {
        // We only need to factor up 100,000,000 so we need primes up to
        // sqrt(100,000,000). I go a little bit past that just in case.
        findPrimes(14141);

        // Read all of the input into a list... I could process the input a
        // line at a time. However, I want to try the new Stream API. For this
        // I could just use the lines method of CIN, but then there would not
        // be a nice way to detect the ending 0 and the program would not
        // terminate when reading input from the console (redirected input from
        // a file would still be fine). Anyway, the input is going into a list.
        List<String> input = new LinkedList<>();
        for(String s = CIN.readLine(); !s.startsWith("0"); s = CIN.readLine()) input.add(s);

        // Trying some Java 8 features. Turn the input into a stream. Map the
        // isVampire predicate to each input to turn it into the result,
        // then print each result out
        input.stream().map(VampireNumbersV1::isVampire).forEach(COUT::println);
        COUT.flush();
    }

    // Sieve for primes up to a given size. Must be called with the correct
    // bound before using factor
    private static void findPrimes(int size)
    {
        boolean[] seen = new boolean[size + 1];
        int i = 2;
        while(i < size)
        {
            PRIME_SET.add(i);
            for(int j = i; j < size; j += i) seen[j] = true;
            while(seen[i]) i++;
        }
    }

    // Factor n into a list of primes
    private static List<Integer> factor(int n)
    {
        final List<Integer> result = new ArrayList<>();

        // Make sure n is positive
        n = Math.abs(n);

        // Keep factoring until we get to n = 1
        while(n > 1)
        {
            // Find the upper bound for the next factor
            final double root = Math.sqrt(n);

            // Search for a prime factor in the set of primes
            Integer prime = null;
            for(Integer i : PRIME_SET)
            {
                // If we go past the bound we can stop as n itself is prime
                if(i > root) break;

                // Test the current prime to see if it is a factor. If so then
                // this search is over.
                if(n % i == 0)
                {
                    prime = i;
                    break;
                }
            }

            // If no prime factor was found n itself is prime
            if(prime == null) prime = n;

            // Print the factor and reduce n for the next search
            result.add(prime);
            n /= prime;
        }
        return result;
    }

    // Given the string form of a number return "Yes." if n is vampire or "No."
    // if it is not.
    private static String isVampire(String n)
    {
        // Test that the length is even
        int length = n.length();
        if(length % 2 != 0) return "No.";

        // Get the factors of n
        List<Integer> factors = factor(Integer.valueOf(n));

        // Try each non-empty proper subset of factors
        for(int i = 1; i < (1 << factors.size()) - 1; i++)
        {
            // The int subset is the binary representation of the subset of
            // factors that make up one of the divisors of n = x*y. If
            // subset = 5, 101 in binary, then x is the product of the first
            // and third prime factors of n. The int subset needs to be final
            // so lambdas can access it (this is why I am not just using i).
            final int subset = i;

            // Build x by multiplying all of the factors in the subset
            final int x = IntStream.range(0, factors.size()).filter(j -> ((1 << j) & subset) > 0).map(j -> factors.get(j)).reduce(1, (a,b)->a*b);

            // Just divide to find y
            final int y = Integer.valueOf(n) / x;

            // Do they each have length / 2 digits?
            if(("" + x).length() != length / 2 ||
                    ("" + y).length() != length / 2) continue;

            // Does n contains precisely all the digits from x and from y
            if(sameDigits(n, "" + x + y)) return "Yes.";
        }
        return "No.";
    }

    // Given the string form of two numbers return true if they have the same
    // digits and false if they do not.
    private static boolean sameDigits(String a, String b)
    {
        return Arrays.equals(buildDigitCounts(a), buildDigitCounts(b));
    }

    // Given the string form of a number return the frequency distribution of
    // its digits
    private static int[] buildDigitCounts(String n)
    {
        final int counts[] = new int[10];
        for(char d : n.toCharArray()) if(Character.isDigit(d)) counts[d - '0']++;
        return counts;
    }
}