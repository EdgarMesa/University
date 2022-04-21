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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class VampireNumbersV2
{

    private static final BufferedReader CIN = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter COUT = new PrintWriter(new BufferedOutputStream(System.out), true);

    public static void main(String[] args) throws IOException
    {

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
        input.stream().map(VampireNumbersV2::isVampire).forEach(System.out::println);
        COUT.flush();
    }

    // Given the string form of a number return "Yes." if n is vampire or "No."
    // if it is not.
    private static String isVampire(final String n)
    {
        // Test that the length is even
        final int length = n.length();
        if(length % 2 != 0) return "No.";

        // Test all of the possible divisors to try to find a fang
        final int nAsInt = Integer.valueOf(n);
        return IntStream.rangeClosed(2, (int)Math.sqrt(nAsInt)).anyMatch(x->
        {
            // Test that x divides n
            if(nAsInt % x != 0) return false;

            // Divide to find y
            int y = nAsInt / x;

            // Do they each have length / 2 digits?
            if(("" + x).length() != length / 2 ||
                    ("" + y).length() != length / 2) return false;

            // Does n contains precisely all the digits from x and from y
            return sameDigits(n, "" + x + y);
        }) ? "Yes." : "No.";
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
